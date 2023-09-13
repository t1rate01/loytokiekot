package com.example.backend.keywords;

import jakarta.persistence.*;
import java.util.Objects;
import com.example.backend.discs.Disc;

@Entity
@Table(name = "disc_keywords")
public class DiscKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "disc_id", nullable = false)
    private Disc disc;  // viittaa disc entityyn


    public DiscKeyword() {
    }

    public DiscKeyword(String value, Disc disc) {
        this.value = value;
        this.disc = disc;
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

    public Disc getDisc() {
        return this.disc;
    }

    public void setDisc(Disc disc) {
        this.disc = disc;
    }

    public DiscKeyword id(Long id) {
        setId(id);
        return this;
    }

    public DiscKeyword value(String value) {
        setValue(value);
        return this;
    }

    public DiscKeyword disc(Disc disc) {
        setDisc(disc);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DiscKeyword)) {
            return false;
        }
        DiscKeyword discKeyword = (DiscKeyword) o;
        return Objects.equals(id, discKeyword.id) && Objects.equals(value, discKeyword.value) && Objects.equals(disc, discKeyword.disc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, disc);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", value='" + getValue() + "'" +
            ", disc='" + getDisc() + "'" +
            "}";
    }
    
    
}
