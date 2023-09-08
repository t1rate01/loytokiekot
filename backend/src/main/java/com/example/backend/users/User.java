package com.example.backend.users;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.Objects;

import com.example.backend.discs.Disc;
import com.example.backend.keywords.Keyword;

@Entity
@Table(name = "users")
public class User {

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

    @CreatedDate     // AIKA on määritetty app propertiesissa UTC, jotta käännettävissä oli db/app missä tahansa hostattuna
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Keyword> keywords;

    @OneToMany(mappedBy = "postedBy")
    private List<Disc> discs;


    public User() {
    }

    public User(Long id, String username, String password, String email, String phonenumber, LocalDateTime createdAt, LocalDateTime updatedAt, List<Keyword> keywords, List<Disc> discs) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.keywords = keywords;
        this.discs = discs;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Keyword> getKeywords() {
        return this.keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public List<Disc> getDiscs() {
        return this.discs;
    }

    public void setDiscs(List<Disc> discs) {
        this.discs = discs;
    }

    public User id(Long id) {
        setId(id);
        return this;
    }

    public User username(String username) {
        setUsername(username);
        return this;
    }

    public User password(String password) {
        setPassword(password);
        return this;
    }

    public User email(String email) {
        setEmail(email);
        return this;
    }

    public User phonenumber(String phonenumber) {
        setPhonenumber(phonenumber);
        return this;
    }

    public User createdAt(LocalDateTime createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    public User updatedAt(LocalDateTime updatedAt) {
        setUpdatedAt(updatedAt);
        return this;
    }

    public User keywords(List<Keyword> keywords) {
        setKeywords(keywords);
        return this;
    }

    public User discs(List<Disc> discs) {
        setDiscs(discs);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(phonenumber, user.phonenumber) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt) && Objects.equals(keywords, user.keywords) && Objects.equals(discs, user.discs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, phonenumber, createdAt, updatedAt, keywords, discs);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", phonenumber='" + getPhonenumber() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", discs='" + getDiscs() + "'" +
            "}";
    }


}
