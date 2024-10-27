package com.blogApp.repo;

import com.blogApp.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepo extends JpaRepository<Movie,Integer> {
}
