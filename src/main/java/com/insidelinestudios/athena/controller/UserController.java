package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.exception.CredentialsInvalidException;
import com.insidelinestudios.athena.exception.ResourceNotFoundException;
import com.insidelinestudios.athena.model.User;
import com.insidelinestudios.athena.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final long ZERO_LONG = 0L;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users/validate/{email}/{password}")
    public ResponseEntity<Boolean> validUserCredentials(@PathVariable(value = "email") String email, @PathVariable(value = "password") String password) throws CredentialsInvalidException {
        Optional<Long> isValid = Optional.ofNullable(userRepository.isValidLogin(email, password)
                .orElseThrow(() -> new CredentialsInvalidException("Invalid user/pass combo.")));
        return isValid.map(aLong -> ResponseEntity.ok().body(aLong > ZERO_LONG)).orElseGet(() -> ResponseEntity.ok().body(false));
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

        user.setEmail(userDetails.getEmail());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        user.setUpdatedAt(new Date());
        user.setUpdatedBy(userDetails.getUpdatedBy());

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
