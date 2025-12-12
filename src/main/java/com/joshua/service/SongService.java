package com.joshua.service;

import java.sql.SQLException;
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

    public void addSong(SongEntity songEntity) {
        try{
            songRepository.addSong(songEntity);
        }catch(SQLException e) {
            logger.error("could not add into song");
        }
    }
    public boolean addSongtoPlaylist (Integer playlistId, SongEntity songEntity) {
        boolean added = false;
        try {
            added = songRepository.addSongtoPlaylist(playlistId, songEntity);
        }catch (SQLException e){
            logger.error("could not insert into playlist");
            e.printStackTrace();
        }
        return added;
    }

    @Override
    public Optional<SongEntity> getById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public List<SongEntity> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public boolean deletebyId(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletebyId'");
    }

    @Override
    public Optional<Song> convertEntityToModel(SongEntity entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertEntityToModel'");
    }

    @Override
    public Optional<Song> getModelById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getModelById'");
    }

}
