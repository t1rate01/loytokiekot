package com.example.backend.security;

import java.util.Base64;

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

import com.example.backend.security.dto.RegisterDto;
import com.example.backend.security.dto.UpdateDto;
import com.example.backend.users.User;

import jakarta.validation.Valid;


@CrossOrigin
@RestController
public class SecurityRestController {

    @Autowired
    SecurityService securityService;

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        
        User user = securityService.addUser(registerDto.getUsername(), registerDto.getPassword(), registerDto.getEmail(), registerDto.getPhonenumber(), registerDto.getRegion(), registerDto.getCity(), registerDto.getDescription());
        if(user == null){
            return ResponseEntity.badRequest().body("User already exists");
        }
        return ResponseEntity.ok("User created");
    }

  @PostMapping("/api/login")
public ResponseEntity<String> login(@RequestHeader("Authorization") String basicAuth) {
    try {
        if (basicAuth != null && basicAuth.startsWith("Basic")) {
            String credentials = basicAuth.split(" ")[1];
            String user[] = new String(Base64.getDecoder().decode(credentials)).split(":");
            String token = securityService.login(user[0], user[1]);
            if (token != null) {
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password");
            }
        } else {
            return ResponseEntity.badRequest().body("Missing or wrong authorization header");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password");
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