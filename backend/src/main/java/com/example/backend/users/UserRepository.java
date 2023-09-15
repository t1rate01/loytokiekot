package com.example.backend.users;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> deleteByUsername(String username);

    @Query("SELECT u FROM User u JOIN FETCH u.keywords WHERE u.id = :userId")
    Optional<User> findById(@Param("userId") Long userId);

    
}
