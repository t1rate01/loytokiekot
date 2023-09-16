package com.example.backend.security;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RestController;

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



   /*  @DeleteMapping("/api/user")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token) {
        try {
            if (token != null && token.startsWith("Bearer")) {
                String jwt = token.split(" ")[1];
                String username = securityService.verifyToken(jwt);
                if (username != null) {
                    String message = securityService.deleteUser(username);
                    return ResponseEntity.ok(message);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
                }
            } else {
                return ResponseEntity.badRequest().body("Missing or wrong authorization header");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    } */

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

}