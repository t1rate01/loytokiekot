package com.example.backend.discs;

import org.springframework.stereotype.Repository;

import com.example.backend.users.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;




@Repository
public interface DiscRepository extends JpaRepository<Disc, Long> {
    
    @Query("SELECT d FROM Disc d JOIN FETCH d.discKeywords")
    List<Disc> findAllWithKeywords();

    @Query("SELECT d FROM Disc d WHERE d.region = ?1")
    List<Disc> findByRegion(String region);

    @Query("SELECT d FROM Disc d WHERE d.city = ?1")
    List<Disc> findByCity(String city);

    List<Disc> findByPostedBy(User postedBy);
    
}
