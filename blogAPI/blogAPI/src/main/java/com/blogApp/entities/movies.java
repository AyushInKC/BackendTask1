package com.blogApp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer movieId;
    @Column(nullable = false,length = 200)
    @NotBlank(message = "Please provide the Movie Title")
   private String title;
    @Column(nullable = false)
    @NotBlank(message = "Please provide the Movie Title")
   private String director;
    @Column(nullable = false)
    @NotBlank(message = "Please provide the Movie Title")
   private String studio;
    @ElementCollection
    @CollectionTable(name="movie_cast")
   private Set<String> movieCast;
    @Column(nullable = false)
    @NotBlank(message = "Please provide the Movie Title")
   private Integer releaseYear;
    @Column(nullable = false)
    @NotBlank(message = "Please provide the Movie Title")
   private String poster;

}
