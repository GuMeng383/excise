import com.example.excise.*;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerServiceTest {
    @Test
    void createCivilian(){
        Scanner input = new Scanner("小明\n1");
        PrintStream output = System.out;
        ConsoleView view = new ScannerConsoleView(input,output);
        PlayerService playerService = new PlayerService();
        Player player = playerService.createPlayer(view,1);

        assertEquals("小明", player.getName());
        assertEquals(Role.CIVILIAN, player.getRole());
        assertEquals(1,player.getID());
        assertEquals(100,player.getScore());
    }

    @Test
    void createUndercover(){
        Scanner input = new Scanner("小红\n2\n");
        PrintStream output = System.out;
        ConsoleView view = new ScannerConsoleView(input,output);
        PlayerService playerService = new PlayerService();
        Player player = playerService.createPlayer(view,1);

        assertEquals("小红", player.getName());
        assertEquals(Role.UNDERCOVER, player.getRole());
        assertEquals(1,player.getID());
        assertEquals(100,player.getScore());
    }

    @Test
    void misInputNotShouldBeTrue(){
        Scanner input = new Scanner("小明\n3\n1\n");
        PrintStream output = System.out;
        ConsoleView view = new ScannerConsoleView(input,output);
        PlayerService playerService = new PlayerService();
        Player player = playerService.createPlayer(view,1);
        assertEquals(Role.CIVILIAN, player.getRole());
    }
}
