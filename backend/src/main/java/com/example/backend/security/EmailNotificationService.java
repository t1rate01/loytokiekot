package com.example.backend.security;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    public void sendResetPasswordEmail(String email, String tempToken) {
        System.out.println("Email reset requested for " + email);
        System.out.println(buildResetPasswordEmail(tempToken));
    }
    

    public String buildResetPasswordEmail(String tempToken) {
        String resetPasswordUrl = "https://yourwebsite.com/reset-password?token=" + tempToken;
        
        return "Hello, \n\n"
            + "We received a request to reset your password. Click the link below to reset your password: \n\n"
            + resetPasswordUrl + "\n\n"
            + "If you didn't request a password reset, you can ignore this email.\n"
            + "Link is valid for 15 minutes. \n\n"
            + "Best regards,\n"
            + "Your Website Team";
    }
    
}
