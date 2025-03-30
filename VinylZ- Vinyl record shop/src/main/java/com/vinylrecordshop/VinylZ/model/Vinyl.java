package com.vinylrecordshop.VinylZ.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vinyl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String vinylName;

    private String price;

    private Date releaseDate;

    private Integer stock;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToMany
    private List<Author> authors;

    public List<Long> getAuthorIds() {
        return authors.stream()
                .map(Author::getId)
                .collect(Collectors.toList());
    }
}
