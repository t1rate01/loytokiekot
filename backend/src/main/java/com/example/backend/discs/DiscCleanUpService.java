package com.example.backend.discs;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class DiscCleanUpService {

    @Autowired
    DiscRepository discRepository;

    @Scheduled(cron = "0 0 0 * * *") // run every day at midnight
    public void cleanupExpiredDiscs() {
        System.out.println("Running cleanup of expired discs...");
        try {
            LocalDate today = LocalDate.now(ZoneId.of("Europe/Helsinki"));
            List<Disc> expiredDiscs = discRepository.findByExpirationDateBefore(today);
            
            int numberOfExpiredDiscs = expiredDiscs.size(); // get the number of expired discs before deleting them
            
            discRepository.deleteAll(expiredDiscs);
            
            // Log the number of deleted discs
            System.out.println(String.format("Cleanup completed. Number of expired discs deleted: %d", numberOfExpiredDiscs));
        } catch (Exception e) {
            // Log the exception
            System.err.println("An error occurred during the cleanup of expired discs: " + e.getMessage());
        }
    }



}