package com.blogApp.Service;

import com.blogApp.dto.MovieDTO;
import com.blogApp.entities.Movie;
import com.blogApp.repo.MoviesRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class MovieServiceImplement implements movieService {
    private final MoviesRepo moviesRepo;
    private final FileService fileService;
    public MovieServiceImplement(MoviesRepo moviesRepo, FileService fileService) {
        this.moviesRepo = moviesRepo;
        this.fileService = fileService;

    }
    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;
    @Override
    public MovieDTO addMovie(MovieDTO moviedto, MultipartFile file) throws IOException {
        //Step 1 will be to upload the file

        String uploadedFileName=fileService.uploadFile(path,file);
        //Step 2 will be to set the value of field 'poster' as the filename
        moviedto.setPoster(uploadedFileName);
       //Step 3 will be map the dto to movie object
        Movie movie=new Movie(
                moviedto.getMovieId(),
                moviedto.getTitle(),
                moviedto.getDirector(),
                moviedto.getStudio(),
                moviedto.getMovieCast(),
                moviedto.getReleaseYear(),
                moviedto.getPoster()
        );
       //Step 4 will be to save the Movie Object  -----> Saved the Movie Object
        Movie savedMovie=moviesRepo.save(movie);
        //Step 5  Generate the poster-url
        String posterUrl=baseUrl+"/file/"+uploadedFileName;
        //Step 6 will be map movie object to the DTO object and return it
        MovieDTO response=new MovieDTO(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),posterUrl
        );
        return response;
    }

    @Override
    public MovieDTO getMovie(Integer movieId) {
        return null;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return null;
    }
}
