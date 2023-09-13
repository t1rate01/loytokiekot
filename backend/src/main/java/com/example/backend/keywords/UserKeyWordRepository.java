package com.example.backend.keywords;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserKeyWordRepository extends JpaRepository<Keyword, Long> {
    
}
