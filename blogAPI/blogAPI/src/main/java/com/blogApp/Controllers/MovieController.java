package com.blogApp.Controllers;
import com.blogApp.Service.movieService;
import com.blogApp.dto.MovieDTO;
import com.blogApp.exceptions.EmptyFileException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.internal.util.MutableLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.List;
@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
   private final movieService  movieservice;

    public MovieController(movieService movieservice) {
        this.movieservice = movieservice;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-movie")
    public ResponseEntity<MovieDTO> addMovieHandler(@RequestPart MultipartFile file,@RequestPart String movieDto) throws IOException {
        if(file.isEmpty()){
            try {
                throw new EmptyFileException("File is empty! Please provide a different file");
            } catch (EmptyFileException e) {
                throw new RuntimeException(e);
            }
        }
        MovieDTO dto=convertToMovieDTO(movieDto);

    return new ResponseEntity<>(movieservice.addMovie(dto,file), HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDTO> getMovieHandler(@PathVariable Integer movieId){
        return ResponseEntity.ok(movieservice.getMovie(movieId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDTO>> getAllMovies(){
     return ResponseEntity.ok(movieservice.getAllMovies());
    }

    @PutMapping("update/{movieId}")
    public ResponseEntity<MovieDTO> updateMovieHandler(@PathVariable Integer movieId,@RequestPart MultipartFile file , String movieDtoObj){
        if(file.isEmpty()){
            file=null;
        }
        MovieDTO movieDto= null;
        try {
            movieDto = convertToMovieDTO(movieDtoObj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            return ResponseEntity.ok(movieservice.updateMovie(movieId, movieDto,file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId){
            return ResponseEntity.ok(movieservice.deleteMovie(movieId));
    }
    private MovieDTO convertToMovieDTO(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(movieDtoObj,MovieDTO.class);
    }

}
