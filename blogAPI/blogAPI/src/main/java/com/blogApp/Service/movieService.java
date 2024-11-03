package com.blogApp.Service;

import com.blogApp.dto.MovieDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface movieService {
   MovieDTO addMovie(MovieDTO moviedto, MultipartFile file) throws IOException;
   MovieDTO getMovie(Integer movieId);
   List<MovieDTO> getAllMovies();
   MovieDTO updateMovie(Integer movieId, MovieDTO movieDto, MultipartFile file) throws IOException;
}
