import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.joshua.repository.SongRepository;
import com.joshua.repository.entities.SongEntity;
import com.joshua.service.SongService;
import com.joshua.service.model.Playlist;
import com.joshua.service.model.Song;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {
    @Mock 
    private SongRepository songRepository;

    @InjectMocks
    private SongService songService;
    
    private List<SongEntity> songs;
    private List<Song> SONGS;
    private SongEntity songEntity;
    private Song SONG;
    private Integer playlistId, songId;
    private Playlist playlist;

    @BeforeEach
    void setup() {
        songEntity = new SongEntity();
        songEntity.setSongId(1);
        songEntity.setSongName("Where Are You Christmas");
        songEntity.setArtistName("Elena");

        SONG = new Song();
        SONG.setId(1);
        SONG.setName("Where Are You Christmas");
        SONG.setArtist("ELena");

        SONGS = new ArrayList<>();
        SONGS.add(SONG);

        songs = new ArrayList<>();
        songs.add(songEntity);    

        playlistId = 1;
        songId = 1;

        playlist = new Playlist();
        playlist.setId(playlistId);
        playlist.setName("Panda");
        playlist.setSongs(SONGS);
    }

    @Test
    void findByNames_Success_ReturnSongId() throws SQLException{
        when(songRepository.findByNames(any(SongEntity.class))).thenReturn(songEntity.getSongId());
        Integer songId = songService.addSong(songEntity.getSongName(), songEntity.getArtistName());
        assertEquals(songId, songEntity.getSongId());
        verify(songRepository, times(1)).findByNames(any(SongEntity.class));
        verify(songRepository, never()).addSong(any(SongEntity.class));
    }

    @Test
    void findByNames_Failure_ReturnNullAndCallsAddSong() throws SQLException{
        when(songRepository.findByNames(any(SongEntity.class))).thenReturn(null);
        when(songRepository.addSong(any(SongEntity.class))).thenReturn(songEntity.getSongId());
        Integer result = songService.addSong(songEntity.getSongName(), songEntity.getArtistName());
        assertEquals(result, songEntity.getSongId());
        verify(songRepository, times(1)).findByNames(any(SongEntity.class));
        verify(songRepository, times(1)).addSong(any(SongEntity.class));
    }

    @Test
    void findByNames_Failure_ThrowsSQLException () throws SQLException{
        when(songRepository.findByNames(any(SongEntity.class))).thenThrow(new SQLException("DB error"));
        Integer result = songService.addSong(songEntity.getSongName(), songEntity.getArtistName());
        assertEquals(result, null);
        verify(songRepository, times(1)).findByNames(any(SongEntity.class));
        verify(songRepository, never()).addSong(any(SongEntity.class));
    }

    @Test
    void addSong_Failure_ThrowsSQLException () throws SQLException {
        when(songRepository.findByNames(any(SongEntity.class))).thenReturn(null);
        when(songRepository.addSong(any(SongEntity.class))).thenThrow(new SQLException("DB error"));
        Integer result = songService.addSong(songEntity.getSongName(), songEntity.getArtistName());
        assertEquals(result, null);
        verify(songRepository, times(1)).findByNames(any(SongEntity.class));
        verify(songRepository, times(1)).addSong(any(SongEntity.class));
    }

    @Test
    void addSongToPlaylist_Success_ReturnsTrue () throws SQLException {
        when(songRepository.addSongtoPlaylist(playlistId, songId)).thenReturn(true);
        boolean result = songService.addSongtoPlaylist(playlistId, songId);
        assertTrue(result);
        verify(songRepository, times(1)).addSongtoPlaylist(playlistId,songId);
    }

    @Test
    void addSongToPlaylist_Failure_ReturnsFalse () throws SQLException {
        when(songRepository.addSongtoPlaylist(playlistId, songId)).thenReturn(false);
        boolean result = songService.addSongtoPlaylist(playlistId, songId);
        assertFalse(result);
        verify(songRepository, times(1)).addSongtoPlaylist(playlistId,songId);
    }

    @Test
    void getAllModles_Success_ReturnList () throws SQLException {
        when(songRepository.getAll()).thenReturn(songs);
        List<Song> S = songService.getAllModels();
        assertFalse(S.isEmpty());
        verify(songRepository, times(1)).getAll();
    }

    @Test 
    void getAllModels_failure_ThrowSQLExcpetion () throws SQLException {
        when(songRepository.getAll()).thenThrow(new SQLException("DB error"));
        List<Song> S = songService.getAllModels();
        assertTrue(S.isEmpty());
        verify(songRepository, times(1)).getAll();
    }

    @Test
    void getModelById_Success_ReturnSong () throws SQLException {
        when(songRepository.getById(songId)).thenReturn(Optional.of(songEntity));
        Optional <Song> song = songService.getModelById(songId);
        assertFalse(song.isEmpty());
        verify(songRepository, times(1)).getById(songId);
    }

    @Test 
    void getModelById_failure_ThrowsSQLExcpetion () throws SQLException {
        when(songRepository.getById(songId)).thenThrow(new SQLException("DB error"));
        Optional <Song> song = songService.getModelById(songId);
        assertTrue(song.isEmpty());
        verify(songRepository, times(1)).getById(songId);
    }

    @Test
    void getSongsByPlaylsitId_Success_ReturnsListOfSongs () throws SQLException {
        when(songRepository.getSongsByPlaylsitId(playlistId)).thenReturn(songs);
        Playlist P = songService.getSongsByPlaylsitId(playlistId);
        assertFalse(P.getSongs().isEmpty());
        verify(songRepository, times(1)).getSongsByPlaylsitId(playlistId);
    }

    @Test
    void getSongsByPlaylsitId_Failure_ThrowSQLException () throws SQLException {
        when(songRepository.getSongsByPlaylsitId(playlistId)).thenThrow(new SQLException("DB error"));
        Playlist P = songService.getSongsByPlaylsitId(playlistId);
        assertTrue(P.getSongs().isEmpty());
        verify(songRepository, times(1)).getSongsByPlaylsitId(playlistId);
    }

    @Test 
    void update_Success_ReturnTrue () throws SQLException {
        when(songRepository.update(any(SongEntity.class))).thenReturn(true);
        boolean result = songService.update(playlistId, "Fly", "Billy");
        assertTrue(result);
        verify(songRepository, times(1)).update(any(SongEntity.class));
    }

    @Test
    void update_Failure_returnFalse () throws SQLException {
         when(songRepository.update(any(SongEntity.class))).thenThrow(new SQLException("DB error"));
        boolean result = songService.update(playlistId, "Fly", "Billy");
        assertFalse(result);
        verify(songRepository, times(1)).update(any(SongEntity.class));
    }

    @Test 
    void delete_success_ReturnTrue () throws SQLException {
        when(songRepository.deletebyId(songId)).thenReturn(true);
        boolean result = songService.deletebyId(songId);
        assertTrue(result);
        verify(songRepository,times(1)).deletebyId(songId);
    }

    @Test 
    void delete_Failure_ReturnFalse () throws SQLException {
        when(songRepository.deletebyId(songId)).thenThrow(new SQLException("DB error"));
        boolean result = songService.deletebyId(songId);
        assertFalse(result);
        verify(songRepository,times(1)).deletebyId(songId);
    }

    @Test
    void deleteSongInPlaylist_Success_ReturnTrue () throws SQLException {
        when(songRepository.deleteSongInPlaylist(songId, playlistId)).thenReturn(true);
        boolean result = songService.deleteSongInPlaylist(songId, playlistId);
        assertTrue(result);
        verify(songRepository,times(1)).deleteSongInPlaylist(songId, playlistId);
    }

    @Test
    void deleteSongInPlaylist_Failure_ReturnFalse () throws SQLException {
        when(songRepository.deleteSongInPlaylist(songId, playlistId)).thenThrow(new SQLException("DB error"));
        boolean result = songService.deleteSongInPlaylist(songId, playlistId);
        assertFalse(result);
        verify(songRepository,times(1)).deleteSongInPlaylist(songId, playlistId);
    }
}
