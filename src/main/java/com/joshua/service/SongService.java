package com.joshua.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joshua.repository.SongRepository;
import com.joshua.repository.entities.SongEntity;
import com.joshua.service.model.Song;


public class SongService implements serviceInterface <SongEntity, Song> {
    private static final Logger logger = LoggerFactory.getLogger(PlaylistService.class);
    SongRepository songRepository = new SongRepository();

    public Integer addSong(SongEntity songEntity) {
        Integer generatedId = null;
        try {
            generatedId = songRepository.findByNames(songEntity.getSongName(), songEntity.getArtistName());
            if(generatedId == null) {
                try{
                    generatedId = songRepository.addSong(songEntity);
                }catch(SQLException e) {
                    logger.error("could not add into song due to duplicate or SQL error");
                }
            }
            else {
                return generatedId;
            }
        }catch (SQLException e){
            logger.error("could not look through song table");
        }
        return generatedId;
    }

    public boolean addSongtoPlaylist (Integer playlistId, SongEntity songEntity) {
        boolean added = false;
        try {
            added = songRepository.addSongtoPlaylist(playlistId, songEntity);
        }catch (SQLException e){
            logger.error("could not insert into playlist due to duplicate or SQL error");
        }
        return added;
    }

    @Override
    public Optional<SongEntity> getById(Integer id) {
        try {
            Optional<SongEntity> songEntity = songRepository.findById(id);
            return songEntity;
        }catch (SQLException e) {
            logger.error("could not get song from database");
            return Optional.empty();
        }
    }

    @Override
    public List<SongEntity> getAll() {
        List<SongEntity> songs;
        try {
            songs = songRepository.findAll();
            return songs;
        }catch(SQLException e) {
            logger.error("could not access database to retrieve songs");
            return List.of();
        }
    }

    @Override
    public boolean deletebyId(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletebyId'");
    }

    @Override
    public Optional<Song> convertEntityToModel(SongEntity entity) {
        Song song = new Song();
        song.setId(entity.getSongId());
        song.setName(entity.getSongName());
        song.setArtist(entity.getArtistName());
        return Optional.of(song);
    }

    @Override
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
}
