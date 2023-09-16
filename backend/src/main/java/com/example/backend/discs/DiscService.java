package com.example.backend.discs;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.discs.dto.DiscDto;
import com.example.backend.discs.dto.UpdateDiscDto;
import com.example.backend.keywords.DiscKeyWordRepository;
import com.example.backend.keywords.DiscKeyword;
import com.example.backend.keywords.UserKeyword;
import com.example.backend.users.User;
import com.example.backend.users.UserRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Service
public class DiscService {

    @Autowired
    DiscRepository discRepository;
    @Autowired
    DiscKeyWordRepository discKeywordRepository;
    @Autowired
    UserRepository userRepository;

    public DiscService() {
    }

   
    public void updateDiscById(Long id, UpdateDiscDto updateDiscDto){   // For update, works with Dto also when new keywords are added
            Disc disc = discRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disc not found"));

            updateDiscDto.getDiscname().ifPresent(disc::setDiscname);
            
            updateDiscDto.getKeywords().ifPresent(keywordStrings -> {
                List<DiscKeyword> discKeywords = keywordStrings.stream()
                        .map(keywordString -> {
                            DiscKeyword discKeyword = new DiscKeyword();
                            discKeyword.setValue(keywordString);
                            return discKeyword;
                        })
                        .collect(Collectors.toList());
                disc.setDiscKeywords(discKeywords);
            });

            discRepository.save(disc);
    }

    @Transactional
    public List<DiscDto> findDiscsMatchingUserKeywords(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User user = userOptional.get();
        List<String> userKeywords = user.getKeywords().stream()
                                   .map(UserKeyword::getValue)
                                   .collect(Collectors.toList());

        List<Disc> discs = discRepository.findMatchingDiscs(userKeywords);
        
        // Transform Disc entities to Disc DTOs to avoid lazy initialization issues
        return discs.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
    }

    private DiscDto toDto(Disc disc) {
        DiscDto dto = new DiscDto();
        dto.setDiscname(disc.getDiscname());
        dto.setRegion(disc.getRegion());
        dto.setCity(disc.getCity());
        dto.setId(disc.getId());
        
        dto.setKeywords(disc.getDiscKeywords().stream()
                                .map(DiscKeyword::getValue)
                                .collect(Collectors.toList()));
        return dto;
    }
   
    public Boolean checkIfNotified(Disc disc) {  // FOR THE TIMED CHECK FUNCTION
        return disc.isNotified();
    }

     public List<Disc> getAllDiscs() {
        return discRepository.findAll();
    }

    public Page<Disc> getAllDiscsWithKeywords(Pageable pageable) {
        return discRepository.findAllWithKeywords(pageable);
    }


    public Disc getDisc(Long id) {
        return discRepository.findById(id).orElse(null);
    }

    public void addDisc(Disc disc) {
        discRepository.save(disc);
    }

    public void updateDisc(Disc disc) {
        discRepository.save(disc);
    }

    public void deleteDisc(Long id) {
        discRepository.deleteById(id);
    }

    public List<Disc> getDiscsByRegion(String region) {
        return discRepository.findByRegion(region);
    }

    public List<Disc> getDiscsByCity(String city) {
        return discRepository.findByCity(city);
    }

    public List<Disc> getDiscsByUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return discRepository.findByPostedBy(user);
    }

     @Transactional
    public void addDiscWithKeywords(Disc disc, List<DiscKeyword> discKeywords) {
        discRepository.save(disc);
        
        for (DiscKeyword discKeyword : discKeywords) {
            discKeyword.setDisc(disc);
        }
        discKeywordRepository.saveAll(discKeywords);
    }
    
}
