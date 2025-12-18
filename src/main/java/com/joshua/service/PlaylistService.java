package com.joshua.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joshua.repository.PlaylistRepository;
import com.joshua.repository.entities.PlaylistEntity;
import com.joshua.service.model.Playlist;

public class PlaylistService implements serviceInterface <PlaylistEntity, Playlist>{
    private static final Logger logger = LoggerFactory.getLogger(PlaylistService.class);
    private final PlaylistRepository playlistRepository = new PlaylistRepository();
    
    public boolean create(String playlistName)
    {
        boolean created = false;
        try {
            created = playlistRepository.create(playlistName);
        } catch (SQLException e) {
            logger.error("could not create playlist in database due to SQL error");
        }
        return created;
    }

    @Override
    public List<PlaylistEntity> getAll() {
        try {
             return playlistRepository.findAll();
        } catch (SQLException e) {
            logger.error("Could not retrieve data from database due to SQL error");
            return List.of();
        }
    }

    @Override
    public boolean deletebyId(Integer id) {
        boolean deleted = false;
        try {
            deleted = playlistRepository.delete(id);
        } catch (SQLException e) {
            logger.error("could not delete from database due to SQL error");
        } 
        return deleted;
    }

    @Override
    public Optional<Playlist> convertEntityToModel(PlaylistEntity entity) {
        Playlist playlist = new Playlist();
        playlist.setId(entity.getPlaylistId());
        playlist.setName(entity.getPlaylistName());
        return Optional.of(playlist);
    }

    @Override
    public List<Playlist> getAllModels() {
        List<PlaylistEntity> playlistEntities = getAll();
        List<Playlist> playlists = new ArrayList<>();
        for (PlaylistEntity PE : playlistEntities){
            Optional<Playlist> playlist = convertEntityToModel(PE);
            if(playlist.isPresent()){
                playlists.add(playlist.get());
            }
        }
        return playlists;
    }

    public boolean update(Integer Id, String newName) {
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setPlaylistId(Id);
        playlistEntity.setPlaylistName(newName);
        boolean updated = false;
        try {
            updated = playlistRepository.update(playlistEntity);
        } catch (SQLException e) {
            logger.error("could not update from database due to SQL error");
        }
        return updated;
    }
}
