package com.blogApp.Service;

import com.blogApp.dto.MovieDTO;
import com.blogApp.entities.Movie;
import com.blogApp.repo.MoviesRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
       if(Files.exists(Paths.get(path+ File.separator+file.getOriginalFilename()))){
           throw new RuntimeException("File already exists! Please enter another file name!");
       }
        String uploadedFileName=fileService.uploadFile(path,file);
        //Step 2 will be to set the value of field 'poster' as the filename
        moviedto.setPoster(uploadedFileName);
       //Step 3 will be map the dto to movie object
        Movie movie=new Movie(
                null,
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
     //Step 1 to check the data in refer to the DB and if exixts fetch the data of the given ID
        Movie movie = moviesRepo.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id = " + movieId));
        //Step 2 generate the poster url
        String posterUrl=baseUrl+"/file/"+ movie.getPoster();
        //Step 3 Map to movieDto object and return it
        MovieDTO response=new MovieDTO(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),posterUrl
        );
        return response;
    }

    @Override
    public List<MovieDTO> getAllMovies() {

        //Step 1 is to fetch all the data from the db
      List<Movie> movies=moviesRepo.findAll();
      List<MovieDTO> movieDTOS=new ArrayList<>();

        //Step 2 is to itearte over the list , generate posterurl for each movieobj amd map to Moviedto obj
       for(Movie movie:movies){
           String posterUrl=baseUrl+"/file/"+movie.getPoster();
           MovieDTO response=new MovieDTO(
                   movie.getMovieId(),
                   movie.getTitle(),
                   movie.getDirector(),
                   movie.getStudio(),
                   movie.getMovieCast(),
                   movie.getReleaseYear(),
                   movie.getPoster(),posterUrl
           );
           movieDTOS .add(response);
       }
        return movieDTOS;
    }

    @Override
    public MovieDTO updateMovie(Integer movieId, MovieDTO movieDto, MultipartFile file) throws IOException {
      //Step 1 first we have to check wheter the movieobj exists with the given movieId
        Movie mv = moviesRepo.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id = " + movieId));
        // Step 2 if file is null do nothing else then delete the existing file associated with the record and upload the new file
     String fileName=mv.getPoster();

     if(file!=null){
         Files.deleteIfExists(Paths.get(path+File.separator+fileName));
         fileName=fileService.uploadFile(path,file);
     }
        //Step 3 now set the moviedtos poster value according to step 2
        movieDto.setPoster(fileName);

        //Step 4 map it to the movie obj
       Movie movie=new Movie(
               mv.getMovieId(),
               movieDto.getTitle(),
               movieDto.getDirector(),
               movieDto.getStudio(),
               movieDto.getMovieCast(),
               movieDto.getReleaseYear(),
               movieDto.getPoster()
       );
        //Step 5 save the movie obj-> return the saved movie
          Movie updatedMovie=moviesRepo.save(movie);
        //Step 6 generate posterUrl for the movie
        String posterUrl=baseUrl+"/file/"+fileName;
        //Step 7 map to movieDto and return
        MovieDTO response=new MovieDTO(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),posterUrl);
        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) {
        //1. Check wheter the movie object in DB
        Movie mv = moviesRepo.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id = " + movieId));
        Integer id=mv.getMovieId();
        //2. delete the file assosciated with this object
        try {
            Files.deleteIfExists(Paths.get(path+File.separator+mv.getPoster()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //3.Delete the file
  moviesRepo.delete(mv);
        return "Movie Deleted with the Id:- "+id;
    }
}
