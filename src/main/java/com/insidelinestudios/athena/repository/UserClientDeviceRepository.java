package com.insidelinestudios.athena.repository;

import com.insidelinestudios.athena.model.UserClientDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserClientDeviceRepository extends JpaRepository<UserClientDevice, Long> {

    @Query("SELECT ucd FROM UserClientDevice ucd WHERE ucd.user.id = :id")
    List<UserClientDevice> getUserClientDevicesByUserId(@Param("id") long userId);

    @Query("SELECT ucd FROM UserClientDevice ucd WHERE ucd.client.id = :id")
    List<UserClientDevice> getUserClientDevicesByClientId(@Param("id") long clientId);

    @Query("SELECT ucd FROM UserClientDevice ucd WHERE ucd.device.id = :id")
    List<UserClientDevice> getUserClientDevicesByDeviceId(@Param("id") long deviceId);
}
