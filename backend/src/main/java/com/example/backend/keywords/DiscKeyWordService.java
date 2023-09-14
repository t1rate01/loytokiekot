package com.example.backend.keywords;

import org.springframework.stereotype.Service;



import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class DiscKeyWordService {

    @Autowired
    DiscKeyWordRepository keyWordRepository;

    public DiscKeyWordService() {
    }

    public List<DiscKeyword> getAllKeyWords() {
        return keyWordRepository.findAll();
    }

    public DiscKeyword getKeyWordById(Long id) {
        return keyWordRepository.findById(id).orElse(null);
    }

    public void addKeyWord(DiscKeyword keyWord) {
        keyWordRepository.save(keyWord);
    }

    public void updateKeyWord(DiscKeyword keyWord) {
        keyWordRepository.save(keyWord);
    }

    public void deleteKeyWord(Long id) {
        keyWordRepository.deleteById(id);
    }

    
    
}
