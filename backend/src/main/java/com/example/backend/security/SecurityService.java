package com.example.backend.security;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import com.auth0.jwt.JWT;               // AUTH
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm; // AUTH
import com.auth0.jwt.exceptions.JWTVerificationException;  // AUTH
import com.auth0.jwt.interfaces.DecodedJWT;  // AUTH
import com.example.backend.security.dto.UpdateDto;
import com.example.backend.users.User;
import com.example.backend.users.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class SecurityService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    Encoder enco;

    @Value("${jwt.secret}")  // AUTH
    private String jwtKey;  // KORJAA MYÖHEMMIN

    @Value("${frontend.url}")
    private String baseUrl;

    public SecurityService() {
    }

    @Transactional
    public User addUser(String username, String password, String email, String phonenumber, String region, String city, String description) {
        // null checks
        if(username == null || username.trim().length() == 0){
            throw new IllegalArgumentException("All user fields must be filled");
        }

        // check username is available
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }

        // check email is available
        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }
        
        String encoderPassword = enco.encode(password);
        
        User user = new User(username, encoderPassword, email, phonenumber, region, city, description);
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
                return  "WrongPwd";
            }
        } else {
            return  "NotFound";
        }
    }

    public String createToken(String username){
        Algorithm algorithm = Algorithm.HMAC256(jwtKey);
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))  // 24H
            .sign(algorithm);
    }

    public String createTempToken(String username){
        Algorithm algorithm = Algorithm.HMAC256(jwtKey);
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 15))  // 15 min
            .sign(algorithm);
    }

    public String deleteUser(String username) {
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isPresent()) {
        userRepository.delete(userOptional.get());
        return "User deleted";
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
    }

    @Transactional
    public String updateUser(String username, UpdateDto updateDto) {
        User user = userRepository.findByUsername(username)
                                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        
           if(updateDto.getUsername() != null && updateDto.getUsername().trim().length() > 0){
               if(userRepository.findByUsername(updateDto.getUsername()).isPresent()) {
                     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
                }
            user.setUsername(updateDto.getUsername());
         }
            if(updateDto.getPassword() != null && updateDto.getPassword().trim().length() > 0){
                user.setPassword(enco.encode(updateDto.getPassword()));
            }
            if(updateDto.getEmail() != null && updateDto.getEmail().trim().length() > 0){
                if(userRepository.findByEmail(updateDto.getEmail()).isPresent()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
                }
                user.setEmail(updateDto.getEmail());
            }
            if(updateDto.getPhonenumber() != null && updateDto.getPhonenumber().trim().length() > 0){
                user.setPhonenumber(updateDto.getPhonenumber());
            }
            if(updateDto.getRegion() != null && updateDto.getRegion().trim().length() > 0){
                user.setRegion(updateDto.getRegion());
            }
            if(updateDto.getCity() != null && updateDto.getCity().trim().length() > 0){
                user.setCity(updateDto.getCity());
            }
            if(updateDto.getDescription() != null && updateDto.getDescription().trim().length() > 0){
                user.setDescription(updateDto.getDescription());
            }
            if(updateDto.getSharePhonenumber() != null){
                user.setSharePhonenumber(updateDto.getSharePhonenumber());
                // log to console
                System.out.println("Share phone number: " + updateDto.getSharePhonenumber());
            }


            userRepository.save(user);
            return "User updated";
    }

    // FUTURE THOUGHTS, TOKEN HANDLING FOR "STAY LOGGED IN"

    public String verifyToken(String token){    // Can take Bearer + token or just token
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

   
    
    public String getUsernameFromToken(String token) {   // checks for token, is token valid and returns username
        try {
            return verifyToken(token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }

    public boolean forgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            String tempToken = createTempToken(userOptional.get().getUsername());
            
            // TODO: Send an email with the reset link containing the tempToken as a URL parameter
            
            return true;
        }
        return false;
    }

    public boolean validateEmail(String email){
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; 
        return email.matches(emailRegex);
    }

    public boolean resetPassword(String token, String newPassword){
        String username = verifyToken(token);
        if(username != null){
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setPassword(enco.encode(newPassword));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
    

    
}
