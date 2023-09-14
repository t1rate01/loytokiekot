package com.example.backend.keywords;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.users.User;
import java.util.List;


@Repository
public interface UserKeyWordRepository extends JpaRepository<UserKeyword, Long> {
    
    @Query("SELECT k FROM UserKeyword k JOIN FETCH k.user WHERE k.user = :user")
    List<UserKeyword> findAllByUser(@Param("user") User user);
}

