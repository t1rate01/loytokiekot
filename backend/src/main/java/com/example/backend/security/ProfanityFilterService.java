package com.example.backend.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Service
public class ProfanityFilterService {

    private Set<String> profanityWords;

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/wordlist.json");
            if(inputStream != null) {
                profanityWords = new HashSet<>(Arrays.asList(mapper.readValue(inputStream, String[].class)));
            } else {
                // Handle the case where the input stream is null, possibly because the file was not found
                System.err.println("Could not find wordlist.json");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public Boolean containsProfanity(List<String> words) {
        if (profanityWords == null || words.isEmpty()) {
            return false;
        }

        for (String word : words) {
            if(word != null && profanityWords.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
        
    }

    /* Learn:
    String text = "Hello     World\nJava\tis fun";
    String[] parts = text.split("\\s+");

    The parts array will contain the following elements:
    ["Hello", "World", "Java", "is", "fun"] 
    */



}