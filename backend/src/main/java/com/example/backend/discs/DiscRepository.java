package com.example.backend.discs;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface DiscRepository extends JpaRepository<Disc, Long> {
    
    @Query("SELECT d FROM Disc d JOIN FETCH d.discKeywords")
    List<Disc> findAllWithKeywords();
    
}
