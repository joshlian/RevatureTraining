package com.joshua.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.joshua.repository.entities.SongEntity;
import com.joshua.utility.DatabaseConnection;

public class SongRepository implements RepoInterface <SongEntity>{
    
    private Connection connection = DatabaseConnection.getConnection();

    public Integer findByNames (SongEntity songEntity) throws SQLException{
        String sql = "SELECT songid FROM song WHERE songname = ? AND artistname = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, songEntity.getSongName());
            stmt.setString(2, songEntity.getArtistName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("songid");
                }
            }
        }
        return null;
    }

    public Integer addSong (SongEntity songEntity) throws SQLException {
        String sql = "INSERT INTO Song (songname, artistname) VALUES (?, ?)";
        Integer generatedId = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, songEntity.getSongName());
            stmt.setString(2, songEntity.getArtistName());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
                return generatedId;
            }
        }
        return generatedId;
    
    }
    public boolean addSongtoPlaylist(Integer playlistId, Integer songID) throws SQLException {

        String sql = "INSERT INTO PlaylistSong (playlistId, songId) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            stmt.setInt(2, songID);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        }
    }

    @Override
    public List<SongEntity> findAll() throws SQLException {
        String sql = "SELECT * FROM Song ORDER BY songid";
        List<SongEntity> songs = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){ 
                    SongEntity s = new SongEntity();
                    s.setSongId(rs.getInt("songid"));
                    s.setSongName(rs.getString("songname"));
                    s.setArtistName(rs.getString("artistname"));
                    songs.add(s);
                }
                return songs;
            }
        }
    }

    @Override
    public Optional<SongEntity> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM Song WHERE songid = (?)";
        SongEntity se = new SongEntity();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    se.setSongId(rs.getInt("songid"));
                    se.setSongName(rs.getString("songname"));
                    se.setArtistName(rs.getString("artistname"));
                    return Optional.of(se);
                }
            }
        }
        return Optional.empty();
    }     

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM Song WHERE songid = (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean update(SongEntity entity) throws SQLException {
        String sql = "UPDATE song SET songname = (?),artistname = (?) WHERE songid = (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getSongName());
            stmt.setString(2, entity.getArtistName());
            stmt.setInt(3, entity.getSongId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
