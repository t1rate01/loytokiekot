package com.example.backend.keywords;


import org.springframework.stereotype.Service;
import com.example.backend.users.User;
import com.example.backend.discs.Disc;

@Service
public class NotificationService {

    public void notifyMatch(Disc disc, User user, String matchingKeyword) {
        // EDIT LATER FOR ACTUAL USE, FOR TESTING PURPOSES PRINTING TO CONSOLE
        System.out.println("Notification Service Activated!");
        System.out.println("Match Details:");
        System.out.println("Disc ID: " + disc.getId());
        System.out.println("Disc Name: " + disc.getDiscname()); 
        System.out.println("User ID: " + user.getId());
        System.out.println("User Email: " + user.getEmail()); 
        System.out.println("Matching Keyword: " + matchingKeyword);
    }
}


// TESTAA