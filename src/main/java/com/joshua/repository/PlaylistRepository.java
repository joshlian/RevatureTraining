package com.joshua.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.joshua.repository.entities.PlaylistEntity;
import com.joshua.utility.DatabaseConnection;

public class PlaylistRepository implements RepoInterface <PlaylistEntity>{

    private Connection connection = DatabaseConnection.getConnection();

    public boolean create(String playlistName) throws SQLException {
        String sql ="INSERT INTO Playlist(playlistname) VALUES (?)";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, playlistName);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public List <PlaylistEntity> getAll() throws SQLException {
        List <PlaylistEntity> playlist = new ArrayList<>();
        String sql ="SELECT * FROM Playlist ORDER BY playlistid";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){ 
                    PlaylistEntity p = new PlaylistEntity();
                    p.setPlaylistId(rs.getInt("playlistid"));
                    p.setPlaylistName(rs.getString("playlistname"));
                    playlist.add(p);
                }
            }
        }
        return playlist;
    }

    @Override
    public boolean update(PlaylistEntity playlistEntity) throws SQLException {
        String sql = "UPDATE playlist SET playlistname = ? WHERE playlistid = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, playlistEntity.getPlaylistName());
            stmt.setInt(2, playlistEntity.getPlaylistId());
            int rowsAffected =  stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean deletebyId(Integer id) throws SQLException {
        String sql ="DELETE FROM Playlist WHERE playlistid = (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
