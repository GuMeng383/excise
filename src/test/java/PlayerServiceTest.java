import com.example.excise.Player;
import com.example.excise.PlayerService;
import com.example.excise.Role;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerServiceTest {
    @Test
    void createCivilian(){
        Scanner input = new Scanner("小明\n1\n");
        PlayerService playerService = new PlayerService();
        Player player = playerService.createPlayer(input,1);

        assertEquals("小明", player.getName());
        assertEquals(Role.CIVILIAN, player.getRole());
        assertEquals(1,player.getID());
        assertEquals(100,player.getScore());
    }

    @Test
    void createUndercover(){
        Scanner input = new Scanner("小红\n2\n");
        PlayerService playerService = new PlayerService();
        Player player = playerService.createPlayer(input,1);

        assertEquals("小红", player.getName());
        assertEquals(Role.UNDERCOVER, player.getRole());
        assertEquals(1,player.getID());
        assertEquals(100,player.getScore());
    }

    @Test
    void misInputNotShouldBeTrue(){
        Scanner input = new Scanner("小明\n3\n1\n");
        PlayerService playerService = new PlayerService();
        Player player = playerService.createPlayer(input,1);
        assertEquals(Role.CIVILIAN, player.getRole());
    }
}
