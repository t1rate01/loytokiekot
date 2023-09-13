package com.example.backend.keywords;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class KeyWordService {

    @Autowired
    DiscKeyWordRepository keyWordRepository;

    public KeyWordService() {
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
