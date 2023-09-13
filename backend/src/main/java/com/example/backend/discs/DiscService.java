package com.example.backend.discs;

import org.springframework.stereotype.Service;

import com.example.backend.keywords.DiscKeyWordRepository;
import com.example.backend.keywords.DiscKeyword;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DiscService {

    @Autowired
    DiscRepository discRepository;
    @Autowired
    DiscKeyWordRepository discKeywordRepository;

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

    public void deleteDisc(Long id) {
        discRepository.deleteById(id);
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
