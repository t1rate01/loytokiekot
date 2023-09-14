package com.example.backend.keywords;
import com.example.backend.users.User;

import jakarta.persistence.*;
import java.util.Objects;



@Entity
@Table(name = "user_keywords")
public class UserKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;  // viittaa user entityyn


    public UserKeyword() {
    }

    public UserKeyword(String value, User user) {
        this.value = value;
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserKeyword id(Long id) {
        setId(id);
        return this;
    }

    public UserKeyword value(String value) {
        setValue(value);
        return this;
    }

    public UserKeyword user(User user) {
        setUser(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserKeyword)) {
            return false;
        }
        UserKeyword keyword = (UserKeyword) o;
        return Objects.equals(id, keyword.id) && Objects.equals(value, keyword.value) && Objects.equals(user, keyword.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, user);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", value='" + getValue() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
    
}
