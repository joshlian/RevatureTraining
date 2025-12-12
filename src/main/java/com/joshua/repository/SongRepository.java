package com.joshua.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.joshua.repository.entities.SongEntity;
import com.joshua.utility.DatabaseConnection;

public class SongRepository implements RepoInterface <SongEntity>{
    
    private Connection connection = DatabaseConnection.getConnection();

    public boolean addSong (SongEntity songEntity) throws SQLException {
        String sql = "INSERT INTO Song (songname, artistname) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, songEntity.getSongName());
            stmt.setString(2, songEntity.getArtistName());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        }
    }
    public boolean addSongtoPlaylist(Integer playlistId, SongEntity songEntity) throws SQLException {

        String sql = "INSERT INTO PlaylistSong (playlistId, songId) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            stmt.setInt(2, songEntity.getSongId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        }
    }

    @Override
    public List<SongEntity> findAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Optional<SongEntity> findById(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
