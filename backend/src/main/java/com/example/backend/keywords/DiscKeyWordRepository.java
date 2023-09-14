package com.example.backend.keywords;

import org.springframework.stereotype.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DiscKeyWordRepository extends JpaRepository<DiscKeyword, Long> {
    

}
