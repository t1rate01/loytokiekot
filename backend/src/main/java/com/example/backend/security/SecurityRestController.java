package com.example.backend.security;

import java.util.Base64;
import java.util.Optional;

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

import com.example.backend.keywords.dto.UserInfoDto;
import com.example.backend.security.dto.LoginErrorDto;
import com.example.backend.security.dto.LoginResponseDto;
import com.example.backend.security.dto.RegisterDto;
import com.example.backend.security.dto.UpdateDto;
import com.example.backend.users.User;
import com.example.backend.users.UserRepository;

import jakarta.validation.Valid;


@CrossOrigin
@RestController
public class SecurityRestController {

    @Autowired
    SecurityService securityService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        
        User user = securityService.addUser(registerDto.getUsername(), registerDto.getPassword(), registerDto.getEmail(), registerDto.getPhonenumber(), registerDto.getRegion(), registerDto.getCity(), registerDto.getDescription());
        if(user == null){
            return ResponseEntity.badRequest().body("User already exists");
        }
        return ResponseEntity.ok("User created");
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String basicAuth) {
        try {
            if (basicAuth != null && basicAuth.startsWith("Basic")) {
                String credentials = basicAuth.split(" ")[1];
                String user[] = new String(Base64.getDecoder().decode(credentials)).split(":");
                String token = securityService.login(user[0], user[1]);
                
                if (token != null && !token.equals("WrongPwd") && !token.equals("NotFound")) { // error messages defined in login function
                    // Valid token, find user to get additional details
                    Optional<User> userOptional = userRepository.findByUsername(user[0]);
                    if (userOptional.isPresent()) {
                        User loggedInUser = userOptional.get();
                        LoginResponseDto responseDto = new LoginResponseDto();
                        responseDto.setToken(token);
                        responseDto.setKeepDiscsFor(loggedInUser.getKeepDiscsFor());
                        responseDto.setSharePhonenumber(loggedInUser.isSharePhonenumber());
                        responseDto.setCanPostDiscs(loggedInUser.isCanPostDiscs());
                        responseDto.setUsername(loggedInUser.getUsername());
                        responseDto.setEmail(loggedInUser.getEmail());
                        responseDto.setPhonenumber(loggedInUser.getPhonenumber());
                        responseDto.setRegion(loggedInUser.getRegion());
                        responseDto.setCity(loggedInUser.getCity());
                        responseDto.setDescription(loggedInUser.getDescription());
                        return ResponseEntity.ok(responseDto);
                    }
                } else {
                    // Authentication failed, create an error response
                    LoginErrorDto responseDto = new LoginErrorDto();
                    if ("WrongPwd".equals(token)) {
                        responseDto.setErrorMessage("Wrong password");
                    } else if ("NotFound".equals(token)) {
                        responseDto.setErrorMessage("User not found");
                    } else {
                        responseDto.setErrorMessage("Unauthorized");
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
                }
            } 
            new LoginErrorDto("Bad request");
            return ResponseEntity.badRequest().body(new LoginErrorDto("Bad request"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginErrorDto(  "Unauthorized"));
        }
    }

    @PostMapping("/api/user/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody String email){
        if(email != null && email.trim().length() > 0){
            try {
                Boolean reset = securityService.forgotPassword(email);
                if (reset) {
                    return ResponseEntity.ok("Password reset link sent");
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not found");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
        }
    }

    @GetMapping("/api/user/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username){
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserInfoDto userInfoDto = new UserInfoDto();
            
            userInfoDto.setUsername(user.getUsername());
            userInfoDto.setEmail(user.getEmail());
            userInfoDto.setPhonenumber(user.getSharePhonenumber() ? user.getPhonenumber() : "Not shared");
            userInfoDto.setDescription(user.getCanPostDiscs() ? user.getDescription() : "Basic user");
            userInfoDto.setRegion(user.getRegion());
            userInfoDto.setCity(user.getCity());
            
            return ResponseEntity.ok(userInfoDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/api/user/id/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id){
        Optional<User> userOptional = userRepository.findById(id);
        
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserInfoDto userInfoDto = new UserInfoDto();
            
            userInfoDto.setUsername(user.getUsername());
            userInfoDto.setEmail(user.getEmail());
            userInfoDto.setPhonenumber(user.getSharePhonenumber() ? user.getPhonenumber() : "Not shared");
            userInfoDto.setDescription(user.getCanPostDiscs() ? user.getDescription() : "Basic user");
            userInfoDto.setRegion(user.getRegion());
            userInfoDto.setCity(user.getCity());
            
            return ResponseEntity.ok(userInfoDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }


    @DeleteMapping("/api/user")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization")String token){
        try {
            String username = securityService.getUsernameFromToken(token);
            if (username != null) {
                String message = securityService.deleteUser(username);
                return ResponseEntity.ok(message);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
 
    @PatchMapping("/api/user")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String token, @Valid @RequestBody UpdateDto updateDto){
        try {
            String username = securityService.getUsernameFromToken(token);
            if (username != null) {
                String message = securityService.updateUser(username, updateDto);
                return ResponseEntity.ok(message);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PostMapping("/api/user/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody UpdateDto newPassword){
        if(token != null && newPassword.getPassword() != null){
            try {
                Boolean reset = securityService.resetPassword(token, newPassword.getPassword());
                if (reset) {
                    return ResponseEntity.ok("Password reset");
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Link not valid");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
        }
    }



    

}