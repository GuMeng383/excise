import com.example.excise.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameResultServiceTest {
    private ConsoleView view;
    private Player p1;
    private Player p2;
    private Player p3;
    private Room room;
    private Map<Player, Integer> votes;

    @BeforeEach
    void setUp() {
        Scanner input = new Scanner("");
        view = new ScannerConsoleView(input,System.out);

        p1 = new NormalPlayer("p1",Role.CIVILIAN,100,1);
        p2 = new NormalPlayer("p2",Role.CIVILIAN,100,2);
        p3 = new NormalPlayer("p3",Role.UNDERCOVER,100,3);

        room = new Room("测试房", 4);
        room.addPlayer(p1);
        room.addPlayer(p2);
        room.addPlayer(p3);

        votes = new HashMap<>();
        room.initVotes(votes);
        room.allPlayersReady();
        room.startGame();
    }

    @Test
    void noUndercoverCivilianShouldWin() {
        Scanner input = new Scanner(" \n \n \n3\n3\n1");
        PrintStream out = System.out;
        view = new ScannerConsoleView(input,out);

        GameService gameService = new GameService(new GameResultService(view),view);
        gameService.runGame(room,votes);

        assertTrue(room.getStatus() == GameStatus.FINISHED);
        assertFalse(p3.isAlive());
        assertFalse(room.hasAliveUndercover());
    }

    @Test
    void underCoverMoreThanCivilianUnderCoverShouldWin() {
        Scanner input = new Scanner(" \n \n \n3\n1\n1");
        PrintStream out = System.out;
        view = new ScannerConsoleView(input,out);


        GameService gameService = new GameService(new GameResultService(view),view);
        gameService.runGame(room, votes);

        assertTrue(room.getStatus() == GameStatus.FINISHED);
        assertFalse(p1.isAlive());
        assertEquals(room.countAliveUndercover(),room.countAliveCivilian());
    }

    @Test
    void otherConsequencesShouldContinue(){

        Room room2 = new Room("测试房2",4);
        p1 = new NormalPlayer("p1",Role.CIVILIAN,100,1);
        p2 = new NormalPlayer("p2",Role.CIVILIAN,100,2);
        p3 = new NormalPlayer("p3",Role.UNDERCOVER,100,3);
        Player p4 = new NormalPlayer("p4",Role.CIVILIAN,100,4);
        Scanner input = new Scanner("");
        ConsoleView view = new ScannerConsoleView(input,System.out);
        room2.addPlayer(p1);
        room2.addPlayer(p2);
        room2.addPlayer(p3);
        room2.addPlayer(p4);

        Map<Player,Integer> votes2 = new HashMap<>();

        room2.initVotes(votes2);
        room2.allPlayersReady();
        room2.startGame();
        p4.eliminate();
        GameResultService gameResultService = new GameResultService(view);
        gameResultService.processResult(room2,p4);

        assertTrue(room2.getStatus() == GameStatus.PLAYING);
        assertFalse(p4.isAlive());
    }
}
