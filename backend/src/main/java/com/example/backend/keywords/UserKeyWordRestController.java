package com.example.backend.keywords;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.keywords.dto.UserKeyWordDto;
import com.example.backend.security.SecurityService;
import com.example.backend.users.User;
import com.example.backend.users.UserRepository;


import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
public class UserKeyWordRestController {

    @Autowired
    UserKeyWordService userKeyWordService;
    @Autowired
    SecurityService securityService;
    @Autowired
    UserRepository userRepository;

    public UserKeyWordRestController(UserKeyWordService userKeyWordService, SecurityService securityService) {
        this.userKeyWordService = userKeyWordService;
        this.securityService = securityService;
    }

    @GetMapping("/api/user/keywords")
    public ResponseEntity<List<UserKeyWordDto>> getAllUserKeywords(@RequestHeader("Authorization") String bearer) {
        String username = securityService.verifyToken(bearer);
    
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        }
    
        Optional<User> userOptional = userRepository.findByUsername(username);
    
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        }
    
        User user = userOptional.get();
    
        List<UserKeyword> userKeywords = userKeyWordService.getAllKeyWordsByUser(user);
        List<UserKeyWordDto> userKeyWordDtos = userKeywords.stream()
                .map(uk -> new UserKeyWordDto().id(uk.getId()).keyword(uk.getValue()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(userKeyWordDtos);
    }
    

    @PostMapping("/api/user/keywords")
    public ResponseEntity<String> addUserKeyWord(@RequestHeader("Authorization")String bearer, @RequestBody UserKeyWordDto dto){
      if(bearer != null){
        try {
            String username = securityService.verifyToken(bearer);
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }
            User user = userOptional.get();
            UserKeyword userKeyword = new UserKeyword();
            userKeyword.setValue(dto.getKeyword());
            userKeyword.setUser(user);
            userKeyWordService.addKeyWord(userKeyword);
            return ResponseEntity.ok("Keyword added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @DeleteMapping("/api/user/keywords/{id}")
    public ResponseEntity<String> deleteUserKeyWord(@RequestHeader("Authorization")String bearer, @PathVariable Long id){
        if(bearer != null){
            try {
                String username = securityService.verifyToken(bearer);
                if (username == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
                }
                Optional<User> userOptional = userRepository.findByUsername(username);
                if (userOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
                }
                User user = userOptional.get();
                
                // Get the existing keyword by ID
                UserKeyword existingKeyword = userKeyWordService.getKeyWordById(id);
                if (existingKeyword == null || !existingKeyword.getUser().getId().equals(user.getId())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this keyword");
                }
                
                // Delete the keyword
                userKeyWordService.deleteKeyWord(id);
                
                return ResponseEntity.ok("Keyword deleted");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    

    @PatchMapping("/api/user/keywords")
    public ResponseEntity<String> updateUserKeyWord(@RequestHeader("Authorization")String bearer, @RequestBody UserKeyWordDto dto){
        if(bearer != null){
            try {
                String username = securityService.verifyToken(bearer);
                if (username == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
                }
                Optional<User> userOptional = userRepository.findByUsername(username);
                if (userOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
                }
                User user = userOptional.get();
                
                // Get the existing keyword by ID
                UserKeyword existingKeyword = userKeyWordService.getKeyWordById(dto.getId());
                if (existingKeyword == null || !existingKeyword.getUser().getId().equals(user.getId())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to update this keyword");
                }

                // Update the keyword value
                existingKeyword.setValue(dto.getKeyword());
                
                // Save the updated keyword
                userKeyWordService.updateKeyWord(existingKeyword);
                
                return ResponseEntity.ok("Keyword updated");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

}
