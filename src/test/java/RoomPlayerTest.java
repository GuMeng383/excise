import com.example.excise.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomPlayerTest {
    @Test
    void shouldAddPlayerUntilRoomIsFull(){
        Room room = new Room("测试房",3);
        assertTrue(room.addPlayer(player(1)));
        assertTrue(room.addPlayer(player(2)));
        assertTrue(room.addPlayer(player(3)));
        assertFalse(room.addPlayer(player(4)));
        assertEquals(3,room.getPlayers().size());
    }

    @Test
    void getPlayersShouldReturnCopy(){
        Room room = new Room("测试房间",3);
        room.addPlayer(player(1));
        room.addPlayer(player(2));

        List<Player> copyPlayers = room.getPlayers();
        copyPlayers.clear();
        assertEquals(2,room.getPlayers().size());
    }

    @Test
    void shouldNotAddPlayerWithDuplicateId() {
        Room room = new Room("测试房间",3);
        assertTrue(room.addPlayer(player(1)));
        assertFalse(room.addPlayer(player(1)));
        assertEquals(1,room.getPlayers().size());
    }

    @Test
    void shouldNotAddNullPlayer() {
        Room room = new Room("测试房间",3);
        Player player = null;
        assertFalse(room.addPlayer(player));
        assertEquals(0,room.getPlayers().size());
    }

    @Test
    void playingNotJoinRoom(){
        Room room = new Room("测试房",3);
        room.setStatus(GameStatus.PLAYING);
        assertFalse(room.addPlayer(player(1)));
    }
    private Player player(int id){
        return new NormalPlayer("玩家"+id,Role.CIVILIAN,100,id);
    }

}
