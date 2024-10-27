package com.blogApp.repo;

import com.blogApp.entities.movies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepo extends JpaRepository<movies,Integer> {
}
