package com.example.backend.keywords;


import org.springframework.stereotype.Service;
import com.example.backend.users.User;
import com.example.backend.discs.Disc;


@Service
public class NotificationService {

    public void notifyMatch(Disc disc, User user, String matchingKeyword) {
        // EDIT LATER FOR ACTUAL USE, FOR TESTING PURPOSES PRINTING TO CONSOLE

        User finder = disc.getPostedBy();
        User ownerOfDisc = user;

        String findersDetails = "Finder: " + finder.getUsername() + ", " + finder.getEmail() + ", " + finder.getPhonenumber();
        String foundDisc = "Found disc: " + disc.getDiscname();
        String ownersDetails = "Owner: " + ownerOfDisc.getUsername() + ", " + ownerOfDisc.getEmail() + ", " + ownerOfDisc.getPhonenumber();

        System.out.println("DISC MATCHED: MATCHING KEYWORD: " + matchingKeyword);
        System.out.println(findersDetails);
        System.out.println(foundDisc);
        System.out.println(ownersDetails);

    }
}


// TESTAA