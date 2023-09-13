package com.example.backend.security;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.users.User;


@CrossOrigin
@RestController
public class SecurityRest {

    @Autowired
    SecurityService securityService;

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String phonenumber) {
        if(username == null || password == null || email == null || phonenumber == null){
            return ResponseEntity.badRequest().body("Missing parameters");
        }

        User user = securityService.addUser(username, password, email, phonenumber);
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
}
