package com.example.backend.discs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.example.backend.discs.dto.DiscDto;
import com.example.backend.discs.dto.GetDiscDto;
import com.example.backend.discs.dto.UpdateDiscDto;
import com.example.backend.keywords.DiscKeyWordRepository;
import com.example.backend.keywords.DiscKeyWordService;
import com.example.backend.keywords.DiscKeyword;
import com.example.backend.security.SecurityService;
import com.example.backend.users.UserRepository;

import com.example.backend.users.User;

@CrossOrigin
@RestController
public class DiscRestController {

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
    @Autowired
    DiscKeyWordService discKeyWordService;
 

    public DiscRestController(DiscService discService,  UserRepository userRepository, DiscRepository discRepository, DiscKeyWordRepository discKeywordRepository, SecurityService securityService, DiscKeyWordService discKeyWordService)
    {
        this.discService = discService;
        this.userRepository = userRepository;
        this.discRepository = discRepository;
        this.discKeywordRepository = discKeywordRepository;
        this.securityService = securityService;
        this.discKeyWordService = discKeyWordService;
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
    
        Disc newDisc = new Disc(postedBy, discDto.getDiscname(), discDto.getRegion(), discDto.getCity());
        
    
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

        if(discKeyWordService.DiscKeyWordMatchesUserKeyWord(discKeywords)){ // Boolean check, important !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //newDisc.setNotified(true);

           
        
         //  NEED FUNCTION HERE TO TRIGGER AN ACTUAL NOTIFICATION, AND THEN SET NOTIFIED TO TRUE
            
            return ResponseEntity.ok("Disc created successfully, owner notified");  // Placeholder for testing
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

    @GetMapping("/api/discs/region/{region}")
    public ResponseEntity<List<GetDiscDto>> getDiscsByRegion(@PathVariable String region) {
        List<Disc> discs = discService.getDiscsByRegion(region);
        List<GetDiscDto> getDiscDtos = new ArrayList<>();
        for (Disc disc : discs) {
            getDiscDtos.add(new GetDiscDto(disc));
        }
        return ResponseEntity.ok(discs.stream()
                              .map(GetDiscDto::new)
                              .collect(Collectors.toList()));

    }

    @GetMapping("/api/discs/city/{city}")
    public ResponseEntity<List<GetDiscDto>> getDiscsByCity(@PathVariable String city) {
        List<Disc> discs = discService.getDiscsByCity(city);
        List<GetDiscDto> getDiscDtos = new ArrayList<>();
        for (Disc disc : discs) {
            getDiscDtos.add(new GetDiscDto(disc));
        }
        return ResponseEntity.ok(discs.stream()
                              .map(GetDiscDto::new)
                              .collect(Collectors.toList()));

    }

    @GetMapping("/api/discs/{id}")
    public ResponseEntity<GetDiscDto> getDisc(@PathVariable Long id) {
        Disc disc = discService.getDisc(id);
        if (disc == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new GetDiscDto(disc));
    }

    @GetMapping("/api/discs/user/{username}")
    public ResponseEntity<List<GetDiscDto>> getDiscsByUser(@PathVariable String username) {
    

        List<Disc> discs = discService.getDiscsByUser(username);
        List<GetDiscDto> getDiscDtos = new ArrayList<>();
        for (Disc disc : discs) {
            getDiscDtos.add(new GetDiscDto(disc));
        }
        return ResponseEntity.ok(discs.stream()
                              .map(GetDiscDto::new)
                              .collect(Collectors.toList()));

    }

    @GetMapping("/api/discs/user")
    public ResponseEntity<List<GetDiscDto>> getDiscsByToken(
            @RequestHeader("Authorization") String token) {
        try {
            String username = securityService.getUsernameFromToken(token);
            if (username != null) {
                List<Disc> discs = discService.getDiscsByUser(username);
                return ResponseEntity.ok(discs.stream()
                                    .map(GetDiscDto::new)
                                    .collect(Collectors.toList()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    

    @PatchMapping("/api/discs/{id}")
    public ResponseEntity<String> updateDisc(@RequestHeader("Authorization") String token ,@PathVariable Long id, @RequestBody UpdateDiscDto updateDiscDto){
        try {
            String username = securityService.getUsernameFromToken(token);
            if (username != null) {
                discService.updateDiscById(id, updateDiscDto);
                return ResponseEntity.ok("Disc updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
        
    }

    @DeleteMapping("/api/discs/{id}")
    public ResponseEntity<String> deleteDisc(@RequestHeader("Authorization") String token, @PathVariable Long id){
        try {
            String username = securityService.getUsernameFromToken(token);
            if (username != null) {
                discService.deleteDisc(id);
                return ResponseEntity.ok("Disc deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }


}
