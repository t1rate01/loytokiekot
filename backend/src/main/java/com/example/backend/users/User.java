package com.example.backend.users;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


import com.example.backend.discs.Disc;
import com.example.backend.keywords.UserKeyword;
import com.example.backend.security.Auditable;

@Entity
@Table(name = "users")
public class User extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String phonenumber;

    @Column(length = 2000, columnDefinition = "VARCHAR(2000)")
    private String description;


    private String region;

    private String city;

    @Column(name = "canPostDiscs", nullable = false, columnDefinition = "boolean default false")
    private boolean canPostDiscs = false;

    @Column(name= "sharePhoneNumber", nullable = false, columnDefinition = "boolean default false")
    private boolean sharePhonenumber = false;

    @Column(name = "keepDiscsFor", nullable = false, columnDefinition = "int default 30")
    private int keepDiscsFor = 30;

    

    /*@CreatedDate     // AIKA on määritetty app propertiesissa UTC, jotta käännettävissä oli db/app missä tahansa hostattuna
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;*/

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)  // cascade = remove, when user is deleted, all userkeywords are deleted
    private List<UserKeyword> keywords;

    @OneToMany(mappedBy = "postedBy", cascade = CascadeType.REMOVE) // cascade = remove, when user is deleted, all users discs are deleted
    private List<Disc> discs;



    public User() {
    }

    public User(String username, String password, String email, String phonenumber, String region, String city, String description) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.keywords = new ArrayList<>();  // INIT WITH EMPTY LIST
        this.discs = new ArrayList<>();
        this.region = region;
        this.city = city;
        this.description = description;
        this.canPostDiscs = false;
        this.keepDiscsFor = 30;
        this.sharePhonenumber = false;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getKeepDiscsFor() {
        return this.keepDiscsFor;
    }

    public void setKeepDiscsFor(int keepDiscsFor) {
        this.keepDiscsFor = keepDiscsFor;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean sharePhoneNumber() {
        return this.sharePhonenumber;
    }

    public void sharePhoneNumber(boolean sharePhonenumber) {
        this.sharePhonenumber = sharePhonenumber;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isCanPostDiscs() {
        return this.canPostDiscs;
    }

    public boolean getCanPostDiscs() {
        return this.canPostDiscs;
    }

    public void setCanPostDiscs(boolean canPostDiscs) {
        this.canPostDiscs = canPostDiscs;
    }

    public boolean isSharePhonenumber() {
        return this.sharePhonenumber;
    }

    public boolean getSharePhonenumber() {
        return this.sharePhonenumber;
    }

    public void setSharePhonenumber(boolean sharePhonenumber) {
        this.sharePhonenumber = sharePhonenumber;
    }
    public List<UserKeyword> getKeywords() {
        return this.keywords;
    }

    public void setKeywords(List<UserKeyword> keywords) {
        this.keywords = keywords;
    }

    public List<Disc> getDiscs() {
        return this.discs;
    }

    public void setDiscs(List<Disc> discs) {
        this.discs = discs;
    }

}
