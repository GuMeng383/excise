import com.example.excise.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class RoomStartTest {

    @Test
    void shouldStartWhenAllRulesAreSatisfied() {
        Room room = new Room("测试房", 5);

        room.addPlayer(player(1, Role.CIVILIAN));
        room.addPlayer(player(2, Role.CIVILIAN));
        room.addPlayer(player(3, Role.CIVILIAN));
        room.addPlayer(player(4, Role.UNDERCOVER));
        room.allPlayersReady();

        assertEquals(GameStartResult.START,room.startGame());
        assertEquals(GameStatus.PLAYING,room.getStatus());
    }

    @Test
    void shouldNotStartWithoutUndercover() {
        Room room = new Room("测试房", 5);

        room.addPlayer(player(1, Role.CIVILIAN));
        room.addPlayer(player(2, Role.CIVILIAN));
        room.allPlayersReady();

        assertEquals(GameStartResult.UNDERCOVER,room.startGame());
        assertEquals(GameStatus.WAITING,room.getStatus());
    }

    @Test
    void shouldNotStartWhenPlayersAreNotReady() {
        Room room = new Room("测试房", 5);

        room.addPlayer(player(1, Role.CIVILIAN));
        room.addPlayer(player(2, Role.CIVILIAN));
        room.addPlayer(player(3, Role.UNDERCOVER));

        assertEquals(GameStartResult.PREPARE,room.startGame());
        assertEquals(GameStatus.WAITING,room.getStatus());
    }

    @Test
    void shouldNotStartWhenCiviliansDoNotOutnumberUndercover() {
        Room room = new Room("测试房", 5);

        room.addPlayer(player(1, Role.CIVILIAN));
        room.addPlayer(player(2, Role.UNDERCOVER));
        room.allPlayersReady();

        assertEquals(GameStartResult.MOUNTNOMATCH,room.startGame());
        assertEquals(GameStatus.WAITING,room.getStatus());
    }

    private Player player(int id, Role role) {
        return new NormalPlayer("玩家" + id, role, 100, id);
    }
}
