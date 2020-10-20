package com.insidelinestudios.athena.repository;

import com.insidelinestudios.athena.exception.CredentialsInvalidException;
import com.insidelinestudios.athena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT COUNT(uzr) FROM User uzr WHERE uzr.email = :email AND uzr.password = :password")
    Optional<Long> isValidLogin(@Param("email") String email, @Param("password") String password) throws CredentialsInvalidException;

}
