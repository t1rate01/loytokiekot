package com.example.backend.discs;

import java.time.LocalDateTime;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.backend.keywords.DiscKeyword;
import com.example.backend.security.Auditable;
import com.example.backend.users.User;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "discs")
@EntityListeners(AuditingEntityListener.class)
public class Disc extends Auditable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String discname;

    @ManyToOne
    @JoinColumn(name = "posted_by", nullable = false)
    private User postedBy;  // viittaa user entityyn

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "disc")
    private List<DiscKeyword> discKeywords = new ArrayList<>();

    @Column(name = "notified", nullable = false)
    private boolean notified = false;


    public Disc() {
    }

    public Disc(User postedBy) {
        this.postedBy = postedBy;
        this.notified = false;
    }

    public void addKeyword(DiscKeyword discKeyword) {
        discKeywords.add(discKeyword);
        discKeyword.setDisc(this);
    }
    
    public void removeKeyword(DiscKeyword discKeyword) {
        discKeywords.remove(discKeyword);
        discKeyword.setDisc(null);
    }
    
    public String getDiscname() {
        return this.discname;
    }

    public void setDiscname(String discname) {
        this.discname = discname;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public User getPostedBy() {
        return this.postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public List<DiscKeyword> getDiscKeywords() {
        return this.discKeywords;
    }

    public void setDiscKeywords(List<DiscKeyword> discKeywords) {
        this.discKeywords = discKeywords;
    }

    public boolean isNotified() {
        return this.notified;
    }

    public boolean getNotified() {
        return this.notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public Disc id(Long id) {
        setId(id);
        return this;
    }

    public Disc createdAt(LocalDateTime createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    public Disc updatedAt(LocalDateTime updatedAt) {
        setUpdatedAt(updatedAt);
        return this;
    }

    public Disc postedBy(User postedBy) {
        setPostedBy(postedBy);
        return this;
    }

    public Disc discKeywords(List<DiscKeyword> discKeywords) {
        setDiscKeywords(discKeywords);
        return this;
    }

    public Disc notified(boolean notified) {
        setNotified(notified);
        return this;
    }


}
