package com.joshua.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joshua.repository.SongRepository;
import com.joshua.repository.entities.SongEntity;
import com.joshua.service.model.Playlist;
import com.joshua.service.model.Song; 

 
public class SongService implements serviceInterface <SongEntity, Song> {
    private static final Logger logger = LoggerFactory.getLogger(PlaylistService.class);
    private final SongRepository songRepository;

    public SongService (SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Integer addSong(String songName, String artistName) {
        SongEntity songEntity = new SongEntity();
        songEntity.setSongName(songName);
        songEntity.setArtistName(artistName);
        Integer generatedId = null;
        try {
            generatedId = songRepository.findByNames(songEntity);
            if(generatedId == null) {
                try{
                    generatedId = songRepository.addSong(songEntity);
                }catch(SQLException e) {
                    logger.error("could not add into song due to SQL error");
                }
            }
            else {
                return generatedId; 
            }
        }catch (SQLException e){
            logger.error("could not look through song table due to SQL error");
        }
        return generatedId;
    }

    public boolean addSongtoPlaylist (Integer playlistId, Integer songID) {
        boolean added = false;
        try {
            added = songRepository.addSongtoPlaylist(playlistId, songID);
        }catch (SQLException e){
            logger.error("could not insert into playlist due to duplicate or SQL error");
        }
        return added;
    }

    @Override
    public List<SongEntity> getAll() {
        try {
            List<SongEntity> songs = songRepository.getAll();
            return songs;
        }catch(SQLException e) {
            logger.error("could not access database to retrieve songs");
            return List.of();
        }
    }

    @Override
    public List<Song> getAllModels() {
        List <SongEntity> songs = getAll();
        List<Song> S = new ArrayList<>();
        for(SongEntity song : songs) {
            Optional<Song> SONGS = convertEntityToModel(song);
            if(SONGS.isPresent()){
                S.add(SONGS.get());
            }
        }
        return S;
    }

    public Optional<SongEntity> getById(Integer id) {
        try {
            Optional<SongEntity> songEntity = songRepository.getById(id);
            return songEntity;
        }catch (SQLException e) {
            logger.error("could not get song from database");
            return Optional.empty();
        }
    }

    public Optional<Song> getModelById(Integer id) {
        Optional<SongEntity> SE = getById(id);
        if(SE.isPresent()) {
            Optional<Song> song = convertEntityToModel(SE.get());
            return song;
        }
        else {
            return Optional.empty();
        }
    }

    public Playlist getSongsByPlaylsitId(Integer id) {
        Playlist playlist = new Playlist();
        List <Song> songs = new ArrayList<>();
        try {
            List <SongEntity> songEntities = songRepository.getSongsByPlaylsitId(id);
            for(SongEntity song : songEntities) {
                Optional <Song> S = convertEntityToModel(song);
                if(S.isPresent()) {
                    songs.add(S.get());
                }
            }
            playlist.setId(id);
            playlist.setSongs(songs);
            return playlist;
        }catch (SQLException e) {
            logger.error("could not get list of songs in database");
            playlist.setSongs(songs);
            return playlist;
        }
    }

    public boolean update (Integer id, String songName, String artistName) {
        SongEntity songEntity = new SongEntity();
        songEntity.setSongId(id);
        songEntity.setSongName(songName);
        songEntity.setArtistName(artistName);
        try {
            return songRepository.update(songEntity);
        } catch (SQLException e) {
            logger.error("could not update the database due to SQL failure");
            return false;
        }
    }

    @Override 
    public boolean deletebyId(Integer id) {
        try {
            return(songRepository.deletebyId(id));
        }catch (SQLException e) {
            logger.error("could not delete from the database due to SQL errror");
            return false;
        }
    }

    public boolean deleteSongInPlaylist(Integer songId, Integer playlistId) {
        try {
            return songRepository.deleteSongInPlaylist(songId , playlistId);
        } catch (SQLException e) {
            logger.error("Could not delete from database use to SQL error");
            return false;
        }
    }

    @Override 
    public Optional<Song> convertEntityToModel(SongEntity entity) {
        Song song = new Song();
        song.setId(entity.getSongId());
        song.setName(entity.getSongName());
        song.setArtist(entity.getArtistName());
        return Optional.of(song);
    }
}
