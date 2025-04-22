package com.example.backend.keywords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.backend.discs.DiscRepository;
import com.example.backend.users.UserRepository;

import jakarta.transaction.Transactional;

import com.example.backend.users.User;
import com.example.backend.discs.Disc;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Set;





@Service
public class KeyWordMatchingService {

    @Autowired
    DiscRepository discRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DiscKeyWordRepository discKeyWordRepository;

    @Autowired
    UserKeyWordRepository userKeyWordRepository;

    @Autowired
    DiscKeyWordService discKeyWordService;
    
    @Autowired
    NotificationService notificationService;

    @Transactional
    @Scheduled(fixedRate = 60000 * 60 * 3)  // CHECKS EVERY 3 HOURS
    public void findMatchingKeywords() {
        System.out.println("Keyword matching service activated!");
        List<User> allUsers = userRepository.findAll();
    

        int totalMatches = 0;
        int totalDiscKeywordsProcessed = 0;
        int totalUserKeywordsProcessed = 0;

        for (User user : allUsers) {
            Set<String> userKeywords = user.getKeywords().stream()
                    .map(UserKeyword::getValue) // Get the value of the keyword
                    .collect(Collectors.toSet());
            totalUserKeywordsProcessed += userKeywords.size();

            Set<Long> processedDiscs = new HashSet<>();
            
            int page = 0;
            Page<Disc> discPage;
            do {
                discPage = discRepository.findNonNotifiedDiscsByKeywords(userKeywords, PageRequest.of(page, 100));
                List<Disc> matchingDiscs = discPage.getContent();

                for (Disc disc : matchingDiscs) {
                    if (processedDiscs.contains(disc.getId()) || disc.getPostedBy().getId().equals(user.getId())) {
                        continue;
                    }
                    processedDiscs.add(disc.getId());

                    // Get the matching keyword(s)
                    Set<String> discKeywords = disc.getDiscKeywords().stream()
                            .map(DiscKeyword::getValue)
                            .collect(Collectors.toSet());
                    totalDiscKeywordsProcessed += discKeywords.size();
                    
                    Optional<String> matchingKeywordOpt = userKeywords.stream().filter(discKeywords::contains).findFirst();

                    if (matchingKeywordOpt.isPresent()) {
                        String matchingKeyword = matchingKeywordOpt.get();
                        disc.setNotified(true);
                        
                        notificationService.notifyMatch(disc, user, matchingKeyword);  // Call the notification service

                        totalMatches++;  // Increment the match counter
                    }
                }
                
                discRepository.saveAll(matchingDiscs);
                page++;
            } while (page < discPage.getTotalPages());
        }

        System.out.println("Total matches found: " + totalMatches);  // Print the total number of matches found
        System.out.println("Total disc keywords processed: " + totalDiscKeywordsProcessed);  // Print the total number of disc keywords processed
        System.out.println("Total user keywords processed: " + totalUserKeywordsProcessed);  // Print the total number of user keywords processed
    }


}
