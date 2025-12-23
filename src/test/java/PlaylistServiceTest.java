import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.joshua.repository.PlaylistRepository;
import com.joshua.repository.entities.PlaylistEntity;
import com.joshua.service.PlaylistService;
import com.joshua.service.model.Playlist;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @InjectMocks
    private PlaylistService playlistService;

    private List<PlaylistEntity> mockList;
    private PlaylistEntity playlistEntity;

    @BeforeEach 
    void setup() {
        
        playlistEntity = new PlaylistEntity();
        playlistEntity.setPlaylistId(1);
        playlistEntity.setPlaylistName("my_playlist");

        mockList = new ArrayList<>();
        mockList.add(playlistEntity);
    }

    @Test
    void create_Success_ReturnsTrue() throws SQLException {
        when(playlistRepository.create(playlistEntity.getPlaylistName())).thenReturn(true);
        boolean result = playlistService.create(playlistEntity.getPlaylistName());
        assertTrue(result);
        verify(playlistRepository, times(1)).create(playlistEntity.getPlaylistName());
    }

    @Test
    void create_Failure_ReturnFalse() throws SQLException {
        when(playlistRepository.create(playlistEntity.getPlaylistName())).thenThrow(new SQLException("DB error"));
        boolean result = playlistService.create(playlistEntity.getPlaylistName());
        assertFalse(result);
        verify(playlistRepository, times(1)).create(playlistEntity.getPlaylistName());
    }

    @Test
    void create_reallyLongname_ReturnsTrue() throws SQLException {
        when(playlistRepository.create("hihisndisndisndsknsjdkjsn7s9dnsds90dsbdsu0dsjhd8js0d9sjd9hs89hds89hds9hdsjdhsuidhsuid")).thenReturn(true);
        boolean result = playlistService.create("hihisndisndisndsknsjdkjsn7s9dnsds90dsbdsu0dsjhd8js0d9sjd9hs89hds89hds9hdsjdhsuidhsuid");
        assertTrue(result);
        verify(playlistRepository, times(1)).create("hihisndisndisndsknsjdkjsn7s9dnsds90dsbdsu0dsjhd8js0d9sjd9hs89hds89hds9hdsjdhsuidhsuid");
    }

    @Test
    void get_Success_ReturnList() throws SQLException {
        when(playlistRepository.getAll()).thenReturn(mockList);
        List<Playlist> result = playlistService.getAllModels();
        assertFalse(result.isEmpty());
        verify(playlistRepository, times(1)).getAll();
    }

    @Test
    void get_Failure_ThrowSQLException() throws SQLException {
        when(playlistRepository.getAll()).thenThrow(new SQLException("DB error"));
        List<Playlist> result = playlistService.getAllModels();
        assertTrue(result.isEmpty());
        verify(playlistRepository, times(1)).getAll();
    }

    @Test
    void delete_success_ReturnTrue() throws SQLException {
        when(playlistRepository.deletebyId(playlistEntity.getPlaylistId())).thenReturn(true);
        boolean result = playlistService.deletebyId(playlistEntity.getPlaylistId());
        assertTrue(result);
        verify(playlistRepository, times(1)).deletebyId(playlistEntity.getPlaylistId());
    }

    @Test
    void delete_Failure_ReturnFalse() throws SQLException {
        when(playlistRepository.deletebyId(playlistEntity.getPlaylistId())).thenReturn(false);
        boolean result = playlistService.deletebyId(playlistEntity.getPlaylistId());
        assertFalse(result);
        verify(playlistRepository, times(1)).deletebyId(playlistEntity.getPlaylistId());
    }

    @Test 
    void update_Success_ReturnTrue() throws SQLException {
        when(playlistRepository.update(any(PlaylistEntity.class))).thenReturn(true);
        boolean result = playlistService.update(playlistEntity.getPlaylistId(), playlistEntity.getPlaylistName());
        assertTrue(result);
        verify(playlistRepository, times(1)).update(any(PlaylistEntity.class));
    }

    @Test
    void update_Failure_returnFalse() throws SQLException {
        when(playlistRepository.update(any(PlaylistEntity.class))).thenReturn(false);
        boolean result = playlistService.update(playlistEntity.getPlaylistId(), playlistEntity.getPlaylistName());
        assertFalse(result);
        verify(playlistRepository, times(1)).update(any(PlaylistEntity.class));
    }
}
