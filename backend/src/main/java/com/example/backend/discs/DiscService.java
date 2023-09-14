package com.example.backend.discs;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.discs.dto.UpdateDiscDto;
import com.example.backend.keywords.DiscKeyWordRepository;
import com.example.backend.keywords.DiscKeyword;
import com.example.backend.users.User;
import com.example.backend.users.UserRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Disc> getAllDiscs() {
        return discRepository.findAll();
    }

    public List<Disc> getAllDiscsWithKeywords() {
        return discRepository.findAllWithKeywords();
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

    public void updateDiscById(Long id, UpdateDiscDto updateDiscDto){
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
