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

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.service.ArtistService;

@SpringBootTest
public class ArtistControllerUnitTest {
	
	@Autowired
	private ArtistController controller;
	
	@Autowired
	private ModelMapper mapper;
	
	@MockBean
    private ArtistService service;

    private List<Artist> artists;
    private Artist testArtist;
    private Artist testArtistWithId;
    private ArtistDTO artistDTO;
    private final Long id = 1L;
    
    private String testName = "Skepta";

    private ArtistDTO mapToDTO(Artist artist) {
        return this.mapper.map(artist, ArtistDTO.class);
    }
    
    @BeforeEach
    void init() {
        this.artists = new ArrayList<>();
        this.testArtist = new Artist(testName);
        this.testArtistWithId = new Artist(testArtist.getName());
        this.testArtistWithId.setId(id);
        this.artists.add(testArtistWithId);
        this.artistDTO = this.mapToDTO(testArtistWithId);
    }
    
    @Test
    void createTest() {
        ArtistDTO dto = mapper.map(testArtist,ArtistDTO.class);
        when(this.service.create(testArtist))
            .thenReturn(this.artistDTO);
        
        assertThat(new ResponseEntity<ArtistDTO>(this.artistDTO, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(dto));
        
        verify(this.service, times(1))
            .create(this.testArtist);
    }
    
    @Test
    void readOneTest() {
        when(this.service.read(this.id))
            .thenReturn(this.artistDTO);
        
        assertThat(new ResponseEntity<ArtistDTO>(this.artistDTO, HttpStatus.OK))
                .isEqualTo(this.controller.read(this.id));
        
        verify(this.service, times(1))
            .read(this.id);
    }

    @Test
    void readAllTest() {
        when(service.read())
            .thenReturn(this.artists
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
            .thenReturn(this.artists
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
            .thenReturn(this.artists
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
            .thenReturn(this.artists
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
    	ArtistDTO newArtist= new ArtistDTO(id, testName);
    	ArtistDTO updatedArtist= new ArtistDTO(this.id, newArtist.getName());

        when(this.service.update(newArtist, this.id))
            .thenReturn(updatedArtist);
        
        assertThat(new ResponseEntity<ArtistDTO>(updatedArtist, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.update(newArtist, this.id));
        
        verify(this.service, times(1))
            .update(newArtist, this.id);
    }
    
    @Test
    void deleteTest() {
        this.controller.delete(id);

        verify(this.service, times(1))
            .delete(id);
    }

}
