package com.example.backend.discs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.keywords.DiscKeyWordRepository;
import com.example.backend.keywords.DiscKeyword;
import com.example.backend.security.SecurityService;
import com.example.backend.users.UserRepository;


import com.example.backend.users.User;

@CrossOrigin
@RestController
public class DiscController {

    @Autowired
    DiscService discService;
    @Autowired
    SecurityService securityService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DiscRepository discRepository;
    @Autowired
    DiscKeyWordRepository discKeywordRepository;
 

    public DiscController(DiscService discService,  UserRepository userRepository, DiscRepository discRepository, DiscKeyWordRepository discKeywordRepository)
    {
        this.discService = discService;
        this.userRepository = userRepository;
        this.discRepository = discRepository;
        this.discKeywordRepository = discKeywordRepository;
    }

    @PostMapping("/api/discs")
    public ResponseEntity<String> addDisc(
            @RequestHeader("Authorization") String bearer, 
            @RequestBody DiscDto discDto) {
        
        // Username from token, then find the user
        String username = securityService.verifyToken(bearer);
        
        // Check if username is null 
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(username + " is not authorized");
        }
    
        User postedBy = userOptional.get();
    
        Disc newDisc = new Disc(postedBy);
        newDisc.setDiscname(discDto.getDiscname());
    
        // Get the keywords from DTO
        List<String> keywords = discDto.getKeywords();
    
        // Create a new list to store the DiscKeyword objects
        List<DiscKeyword> discKeywords = new ArrayList<>();
    
        // Create the DiscKeyword objects from the keywords
        for (String keyword : keywords) {
            DiscKeyword discKeyword = new DiscKeyword();
            discKeyword.setValue(keyword);
            discKeywords.add(discKeyword);
        }
    
        try {
            discService.addDiscWithKeywords(newDisc, discKeywords);
        } catch (DataIntegrityViolationException e) {
            // In case of DB error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create disc");
        }
    
        return ResponseEntity.ok("Disc created successfully");
    }

    @GetMapping("/api/discs")
    public ResponseEntity<List<GetDiscDto>> getDiscs() {
        List<Disc> discs = discService.getAllDiscsWithKeywords();
        List<GetDiscDto> getDiscDtos = new ArrayList<>();
        for (Disc disc : discs) {
            getDiscDtos.add(new GetDiscDto(disc));
        }
        return ResponseEntity.ok(getDiscDtos);
    }

}
