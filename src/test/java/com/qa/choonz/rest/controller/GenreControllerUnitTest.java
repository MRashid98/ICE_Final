package com.qa.choonz.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.service.GenreService;

@SpringBootTest
public class GenreControllerUnitTest {
	
	@Autowired
	private GenreController controller;
	
	@Autowired
	private ModelMapper mapper;
	
	@MockBean
    private GenreService service;

    private List<Genre> genre;
    private Genre testGenre;
    private Genre testGenreWithId;
    private GenreDTO genreDTO;
    private final Long id = 1L;
    
    private String testName = "Funkyfresh";

    private GenreDTO mapToDTO(Genre genre) {
        return this.mapper.map(genre, GenreDTO.class);
    }
    
    @BeforeEach
    void init() {
        this.genre = new ArrayList<>();
        this.testGenre = new Genre(testName);
        this.testGenreWithId = new Genre(testGenre.getName());
        this.testGenreWithId.setId(id);
        this.genre.add(testGenreWithId);
        this.genreDTO = this.mapToDTO(testGenreWithId);
    }
    
    @Test
    void createTest() {
        GenreDTO dto = mapper.map(testGenre,GenreDTO.class);
        when(this.service.create(testGenre))
            .thenReturn(this.genreDTO);
        
        assertThat(new ResponseEntity<GenreDTO>(this.genreDTO, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(dto));
        
        verify(this.service, times(1))
            .create(this.testGenre);
    }
    
    @Test
    void readOneTest() {

        when(this.service.read(this.id))
            .thenReturn(this.genreDTO);
        
        assertThat(new ResponseEntity<GenreDTO>(this.genreDTO, HttpStatus.OK))
                .isEqualTo(this.controller.read(this.id));
        
        verify(this.service, times(1))
            .read(this.id);
    }

    @Test
    void readAllTest() {
        when(service.read())
            .thenReturn(this.genre
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.read().getBody()
                .isEmpty()).isFalse();
        
        verify(this.service, times(1))
            .read();
    }
    @Test
    void readAllDescTest() {
        when(service.readDesc())
            .thenReturn(this.genre
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.readDesc().getBody()
                .isEmpty()).isFalse();
        
        verify(this.service, times(1))
            .readDesc();
    }
    @Test
    void readByNameTest() {
        when(service.readByName())
            .thenReturn(this.genre
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.readByName().getBody()
                .isEmpty()).isFalse();
        
        verify(this.service, times(1))
            .readByName();
    }
    @Test
    void readByNameDescTest() {
        when(service.readByNameDesc())
            .thenReturn(this.genre
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.readByNameDesc().getBody()
                .isEmpty()).isFalse();
        
        verify(this.service, times(1))
            .readByNameDesc();
    }
    
    @Test
    void updateTest() {
    	GenreDTO newGenre = new GenreDTO(id, testName);
    	GenreDTO updatedGenre = new GenreDTO(this.id, newGenre.getName());

        when(this.service.update(newGenre, this.id))
            .thenReturn(updatedGenre);
        
        assertThat(new ResponseEntity<GenreDTO>(updatedGenre, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.update(newGenre, this.id));
        
        verify(this.service, times(1))
            .update(newGenre, this.id);
    }
    
    @Test
    void deleteTest() {
        this.controller.delete(id);

        verify(this.service, times(1))
            .delete(id);
    }

}
