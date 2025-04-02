package mk.ukim.finki.emtlab.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mk.ukim.finki.emtlab.model.enumerations.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Author author;

    private Integer availableCopies;
    private boolean deleted = false; // Soft delete flag

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;


    public Book(String name, Category category, Author author, Integer availableCopies) {
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = availableCopies;
        this.deleted = false; // Default to false (not deleted)
    }


}

