package com.example.backend.keywords;

import org.springframework.stereotype.Service;



import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class DiscKeyWordService {

    @Autowired
    DiscKeyWordRepository keyWordRepository;
    @Autowired
    UserKeyWordRepository userKeyWordRepository;

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

    public Boolean DiscKeyWordMatchesUserKeyWord(List<DiscKeyword> discKeywords){  // WHEN ADDING DISCS, CHECKS IF DISC KEYWORDS MATCH USER KEYWORDS, RETURNS BOOLEAN
        List<UserKeyword> currentKeyWords = userKeyWordRepository.findAll();
        for (DiscKeyword discKeyword : discKeywords) {
            for (UserKeyword userKeyword : currentKeyWords) {
                if (discKeyword.getValue().equals(userKeyword.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    
    
}
