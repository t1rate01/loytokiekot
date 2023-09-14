package com.example.backend.keywords;

import org.springframework.stereotype.Service;

import com.example.backend.users.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserKeyWordService {

    @Autowired
    UserKeyWordRepository keyWordRepository;

    public UserKeyWordService() {
    }

    public List<UserKeyword> getAllKeyWords() {
        return keyWordRepository.findAll();
    }

    public UserKeyword getKeyWordById(Long id) {
        return keyWordRepository.findById(id).orElse(null);
    }

    public void addKeyWord(UserKeyword keyWord) {
        keyWordRepository.save(keyWord);
    }

    public void updateKeyWord(UserKeyword keyWord) {
        keyWordRepository.save(keyWord);
    }

    public void deleteKeyWord(Long id) {
        keyWordRepository.deleteById(id);
    }


    public List<UserKeyword> getAllKeyWordsByUser(User user) {
        return keyWordRepository.findAllByUser(user);
    }


}
