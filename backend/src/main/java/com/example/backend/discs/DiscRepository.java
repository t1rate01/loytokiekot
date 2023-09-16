package com.example.backend.discs;

import org.springframework.stereotype.Repository;


import com.example.backend.users.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




@Repository
public interface DiscRepository extends JpaRepository<Disc, Long> {
    
    @Query("SELECT d FROM Disc d JOIN FETCH d.discKeywords ORDER BY d.createdAt DESC")
    Page<Disc> findAllWithKeywords(Pageable pageable);


    @Query("SELECT d FROM Disc d WHERE d.region = ?1")
    List<Disc> findByRegion(String region);

    @Query("SELECT d FROM Disc d WHERE d.city = ?1")
    List<Disc> findByCity(String city);

    List<Disc> findByPostedBy(User postedBy);

    @Query("SELECT d FROM Disc d JOIN d.discKeywords dk WHERE dk.value IN :keywords AND d.notified = false")
    Page<Disc> findNonNotifiedDiscsByKeywords(@Param("keywords") Set<String> keywords, Pageable pageable);

    List<Disc> findByExpirationDateBefore(LocalDate date);

    @Query("SELECT d FROM Disc d JOIN d.discKeywords dk WHERE dk.value IN :values")
    List<Disc> findMatchingDiscs(@Param("values") List<String> values);


    
}




 