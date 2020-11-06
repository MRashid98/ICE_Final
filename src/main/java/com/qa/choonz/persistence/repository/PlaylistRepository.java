package com.qa.choonz.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.choonz.persistence.domain.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

	
	@Query(value ="SELECT * FROM playlist Where user_id=?1", nativeQuery = true)
    List<Playlist> readUserPlaylists(Long id);
	
	@Query(value = "SELECT tracks_id from track_playlists WHERE playlists_id=?1",nativeQuery = true)
	List<Long> readTrackIds(Long id);
}
