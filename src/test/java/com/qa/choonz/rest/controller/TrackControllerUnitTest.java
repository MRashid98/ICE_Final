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

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.service.TrackService;

@SpringBootTest
public class TrackControllerUnitTest {
	
	@Autowired
	private TrackController controller;
	
	@Autowired
	private ModelMapper mapper;
	
	@MockBean
    private TrackService service;

    private List<Track> tracks;
    private Track testTrack;
    private Track testTrackWithId;
    private TrackDTO trackDTO;
    private final Long id = 1L;
    
    private String testName = "Aint nothing changed";

    private TrackDTO mapToDTO(Track track) {
        return this.mapper.map(track, TrackDTO.class);
    }
    
    @BeforeEach
    void init() {
        this.tracks = new ArrayList<>();
        this.testTrack = new Track(testName);
        this.testTrackWithId = new Track(testTrack.getName());
        this.testTrackWithId.setId(id);
        this.tracks.add(testTrackWithId);
        this.trackDTO = this.mapToDTO(testTrackWithId);
    }
    
    @Test
    void createTest() {
        TrackDTO dto = mapper.map(testTrack,TrackDTO.class);
        when(this.service.create(testTrack))
            .thenReturn(this.trackDTO);
        
        assertThat(new ResponseEntity<TrackDTO>(this.trackDTO, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(dto));
        
        verify(this.service, times(1))
            .create(this.testTrack);
    }
    
    @Test
    void readOneTest() {
        when(this.service.read(this.id))
            .thenReturn(this.trackDTO);
        
        assertThat(new ResponseEntity<TrackDTO>(this.trackDTO, HttpStatus.OK))
                .isEqualTo(this.controller.read(this.id));
        
        verify(this.service, times(1))
            .read(this.id);
    }

    @Test
    void readAllTest() {
        when(service.read())
            .thenReturn(this.tracks
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.read().getBody()
                .isEmpty()).isFalse();
        
        verify(this.service, times(1))
            .read();
    }
    @Test
    void readAlbumTest() {
        when(service.readAlbum(1l))
            .thenReturn(this.tracks
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.readAlbum(1l).getBody()
                .isEmpty()).isFalse();
        
        verify(this.service, times(1))
            .readAlbum(1l);
    }
    
    @Test
    void updateTest() {
        TrackDTO newTrack= new TrackDTO(id, testName);
        TrackDTO updatedTrack= new TrackDTO(this.id, newTrack.getName());

        when(this.service.update(newTrack, this.id))
            .thenReturn(updatedTrack);
        
        assertThat(new ResponseEntity<TrackDTO>(updatedTrack, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.update(newTrack, this.id));
        
        verify(this.service, times(1))
            .update(newTrack, this.id);
    }
    
    @Test
    void deleteTest() {
        this.controller.delete(id);

        verify(this.service, times(1))
            .delete(id);
    }

}
