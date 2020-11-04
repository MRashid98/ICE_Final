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

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.service.PlaylistService;

@SpringBootTest
public class PlaylistControllerUnitTest {
	
	@Autowired
	private PlaylistController controller;
	
	@Autowired
	private ModelMapper mapper;
	
	@MockBean
    private PlaylistService service;

    private List<Playlist> playlists;
    private Playlist testPlaylist;
    private Playlist testPlaylistWithId;
    private PlaylistDTO playlistsDTO;
    private final Long id = 1L;
    
    private String testName = "Funky fresh";

    private PlaylistDTO mapToDTO(Playlist playlist) {
        return this.mapper.map(playlist, PlaylistDTO.class);
    }
    
    @BeforeEach
    void init() {
        this.playlists = new ArrayList<>();
        this.testPlaylist = new Playlist(testName);
        this.testPlaylistWithId = new Playlist(testPlaylist.getName());
        this.testPlaylistWithId.setId(id);
        this.playlists.add(testPlaylistWithId);
        this.playlistsDTO = this.mapToDTO(testPlaylistWithId);
    }
    
    @Test
    void createTest() {
        PlaylistDTO dto = mapper.map(testPlaylist,PlaylistDTO.class);
        when(this.service.create(testPlaylist))
            .thenReturn(this.playlistsDTO);
        
        assertThat(new ResponseEntity<PlaylistDTO>(this.playlistsDTO, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(dto));
        
        verify(this.service, times(1))
            .create(this.testPlaylist);
    }
    
    @Test
    void readOneTest() {
        when(this.service.read(this.id))
            .thenReturn(this.playlistsDTO);
        
        assertThat(new ResponseEntity<PlaylistDTO>(this.playlistsDTO, HttpStatus.OK))
                .isEqualTo(this.controller.read(this.id));
        
        verify(this.service, times(1))
            .read(this.id);
    }

    @Test
    void readAllTest() {
        when(service.read())
            .thenReturn(this.playlists
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.read().getBody()
                .isEmpty()).isFalse();
        
        verify(this.service, times(1))
            .read();
    }
    @Test
    void readUserPlaylistsTest() {
        when(service.readUserPlaylists(id))
            .thenReturn(this.playlists
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.readUserPlaylists(1l).getBody()
                .isEmpty()).isFalse();
        
        verify(this.service, times(1))
            .readUserPlaylists(1l);
    }
    
    @Test
    void addTrackTest() {
        when(service.addTrack(id,id))
            .thenReturn(this.playlistsDTO);
        
        assertThat(this.controller.addTrack(id,id).hasBody());
        
        verify(this.service, times(1))
            .addTrack(id,id);
    }
    
    @Test
    void removeTrackTest() {
        when(service.removeTrack(id,id))
            .thenReturn(this.playlistsDTO);
        
        assertThat(this.controller.removeTrack(id,id).hasBody());
        
        verify(this.service, times(1))
            .removeTrack(id,id);
    }
    
    @Test
    void updateTest() {
        // given
    	PlaylistDTO newPlaylist = new PlaylistDTO(id, testName);
    	PlaylistDTO updatedPlaylist = new PlaylistDTO(this.id, newPlaylist.getName());

        when(this.service.update(newPlaylist, this.id))
            .thenReturn(updatedPlaylist);
        
        assertThat(new ResponseEntity<PlaylistDTO>(updatedPlaylist, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.update(newPlaylist, this.id));
        
        verify(this.service, times(1))
            .update(newPlaylist, this.id);
    }
    
    @Test
    void deleteTest() {
        this.controller.delete(id);

        verify(this.service, times(1))
            .delete(id);
    }

}
