package com.blogApp.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class MovieDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;


    @NotBlank(message = "Please provide the Movie Title")
    private String title;


    @NotBlank(message = "Please provide the Movie Director")
    private String director;

    @NotBlank(message = "Please provide the Movie Studio")
    private String studio;
    private Set<String> movieCast;
    private Integer releaseYear;
    @NotBlank(message = "Please provide the Movie Poster")
    private String poster;
    @NotBlank(message = "Please provide the poster's URL")
    private String posterUrl;
}
