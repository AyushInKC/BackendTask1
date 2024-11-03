package com.blogApp.Controllers;
import com.blogApp.Service.movieService;
import com.blogApp.dto.MovieDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.internal.util.MutableLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
   private final movieService  movieservice;

    public MovieController(movieService movieservice) {
        this.movieservice = movieservice;
    }

    @PostMapping("/add-movie")
    public ResponseEntity<MovieDTO> addMovieHandler(@RequestPart MultipartFile file,@RequestPart String movieDto) throws IOException {
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
    private MovieDTO convertToMovieDTO(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(movieDtoObj,MovieDTO.class);
    }

}