package com.example.backend.discs;

import java.time.LocalDateTime;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.example.backend.keywords.DiscKeyword;
import com.example.backend.users.User;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.List;



@Entity
@Table(name = "discs")
public class Disc {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "posted_by", nullable = false)
    private User postedBy;  // viittaa user entityyn

    @OneToMany(mappedBy = "disc")
    private List<DiscKeyword> discKeywords;

    public Disc() {
    }

    public Disc(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, User postedBy, List<DiscKeyword> discKeywords) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.postedBy = postedBy;
        this.discKeywords = discKeywords;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Disc)) {
            return false;
        }
        Disc disc = (Disc) o;
        return Objects.equals(id, disc.id) && Objects.equals(createdAt, disc.createdAt) && Objects.equals(updatedAt, disc.updatedAt) && Objects.equals(postedBy, disc.postedBy) && Objects.equals(discKeywords, disc.discKeywords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt, postedBy, discKeywords);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", postedBy='" + getPostedBy() + "'" +
            ", discKeywords='" + getDiscKeywords() + "'" +
            "}";
    }

}
