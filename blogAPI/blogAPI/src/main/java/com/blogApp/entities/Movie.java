package com.blogApp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide the Movie Title")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Please provide the Movie Director")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "Please provide the Movie Studio")
    private String studio;

    @ElementCollection
    @CollectionTable(name="movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false)
    @NotNull(message = "Please provide the Movie Release Year")
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "Please provide the Movie Poster")
    private String poster;
}
