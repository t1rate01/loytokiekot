package com.example.backend.keywords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.backend.discs.DiscRepository;
import com.example.backend.users.UserRepository;
import com.example.backend.users.User;
import com.example.backend.discs.Disc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
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

    @Scheduled(fixedRate = 60000 * 60 * 3)  // CHECKS EVERY 3 HOURS
    public void findMatchingKeywords() {
        List<Disc> allDiscs = discRepository.findAll();
        List<User> allUsers = userRepository.findAll();

        // ... (the rest of your existing setup code)

        for (Disc disc : allDiscs) {
            if (disc.isNotified()) {
                continue;
            }

            Set<String> discKeywords = disc.getDiscKeywords().stream()
                    .map(DiscKeyword::getValue)
                    .collect(Collectors.toSet());

            for (User user : allUsers) {
                if (disc.getPostedBy().getId().equals(user.getId())) {
                    continue;
                }

                Set<String> userKeywords = userKeywordsMapping.get(user.getId());

                // Find any matching keyword
                Optional<String> matchingKeywordOpt = userKeywords.stream().filter(discKeywords::contains).findFirst();

                if (matchingKeywordOpt.isPresent()) {
                    String matchingKeyword = matchingKeywordOpt.get();
                    disc.setNotified(true);
                    discRepository.save(disc);
                    System.out.println("Disc notified");
                    
                    notificationService.notifyMatch(disc, user, matchingKeyword);  // Call the notification service

                    break;  // Break the inner loop as we have found a match
                }
            }
        }
    }
}
