package com.qa.choonz.rest.dto;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Playlist;

import java.util.Objects;

public class TrackDTO {

    private long id;
    private String name;
//    private Album album;
//    private Playlist playlist;
    private int duration;
    private String lyrics;

    public TrackDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Album getAlbum() {
//        return album;
//    }
//
//    public void setAlbum(Album album) {
//        this.album = album;
//    }
//
//    public Playlist getPlaylist() {
//        return playlist;
//    }
//
//    public void setPlaylist(Playlist playlist) {
//        this.playlist = playlist;
//    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackDTO trackDTO = (TrackDTO) o;
        return id == trackDTO.id &&
                duration == trackDTO.duration &&
                name.equals(trackDTO.name) &&
//                album.equals(trackDTO.album) &&
//                playlist.equals(trackDTO.playlist) &&
                lyrics.equals(trackDTO.lyrics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name,/* album, playlist,*/ duration, lyrics);
    }

    @Override
    public String toString() {
        return "TrackDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                /*", album=" + album +
                ", playlist=" + playlist +*/
                ", duration=" + duration +
                ", lyrics='" + lyrics + '\'' +
                '}';
    }
}
