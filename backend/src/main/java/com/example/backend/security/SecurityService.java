package com.example.backend.security;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;               // AUTH
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm; // AUTH
import com.auth0.jwt.exceptions.JWTVerificationException;  // AUTH
import com.auth0.jwt.interfaces.DecodedJWT;  // AUTH
import com.example.backend.users.User;
import com.example.backend.users.UserRepository;








@Service
public class SecurityService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    Encoder enco;

    @Value("${jwt.secret}")  // AUTH
    private String jwtKey;  // KORJAA MYÖHEMMIN

    public SecurityService() {
    }

    public User addUser(String username, String password, String email, String phonenumber) {
        String encoderPassword = enco.encode(password);
        
        User user = new User(username, encoderPassword, email, phonenumber);
        userRepository.save(user);
        return user;
    }

    public String login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(enco.matches(password, user.getPassword())){
                return createToken(user.getUsername());
            } else {
                return "Wrong password";
            }
        } else {
            return "User not found";
        }
    }

    public String createToken(String username){
        Algorithm algorithm = Algorithm.HMAC256(jwtKey);
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))  // 24H
            .sign(algorithm);
    }

    // FUTURE THOUGHTS, TOKEN HANDLING FOR "STAY LOGGED IN"

    public String verifyToken(String token){
        Algorithm algo = Algorithm.HMAC256(jwtKey);
        JWTVerifier verifier = JWT.require(algo).build();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    
}
