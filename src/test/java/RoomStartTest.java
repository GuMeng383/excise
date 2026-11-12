import com.example.excise.NormalPlayer;
import com.example.excise.Player;
import com.example.excise.Role;
import com.example.excise.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoomStartTest {

    @Test
    void shouldStartWhenAllRulesAreSatisfied() {
        Room room = new Room("测试房", 5);

        room.addPlayer(player(1, Role.CIVILIAN));
        room.addPlayer(player(2, Role.CIVILIAN));
        room.addPlayer(player(3, Role.CIVILIAN));
        room.addPlayer(player(4, Role.UNDERCOVER));
        room.allPlayersReady();

        assertTrue(room.canStartGame());
    }

    @Test
    void shouldNotStartWithoutUndercover() {
        Room room = new Room("测试房", 5);

        room.addPlayer(player(1, Role.CIVILIAN));
        room.addPlayer(player(2, Role.CIVILIAN));
        room.allPlayersReady();

        assertFalse(room.canStartGame());
    }

    @Test
    void shouldNotStartWhenPlayersAreNotReady() {
        Room room = new Room("测试房", 5);

        room.addPlayer(player(1, Role.CIVILIAN));
        room.addPlayer(player(2, Role.CIVILIAN));
        room.addPlayer(player(3, Role.UNDERCOVER));

        assertFalse(room.canStartGame());
    }

    @Test
    void shouldNotStartWhenCiviliansDoNotOutnumberUndercover() {
        Room room = new Room("测试房", 5);

        room.addPlayer(player(1, Role.CIVILIAN));
        room.addPlayer(player(2, Role.UNDERCOVER));
        room.allPlayersReady();

        assertFalse(room.canStartGame());
    }

    private Player player(int id, Role role) {
        return new NormalPlayer("玩家" + id, role, 100, id);
    }
}
