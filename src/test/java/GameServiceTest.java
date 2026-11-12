import com.example.excise.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    @Test
    void endRoomShouldNotRunVote() {
        Scanner input = new Scanner(" ");
        ConsoleView view = new ScannerConsoleView(input, System.out);
        Map<Player, Integer> votes = new HashMap<>();
        Room room = new Room("测试房间", 3);
        room.setStatus(GameStatus.FINISHED);
        GameService gameService = new GameService(new GameResultService(),view);
        gameService.runGame(room, votes);
        assertTrue(room.getStatus() == GameStatus.FINISHED);
    }

    @Test
    void wholeGameTest() {
        Player playerA = new NormalPlayer("小明", Role.CIVILIAN, 100, 1);
        Player playerB = new NormalPlayer("小红", Role.CIVILIAN, 100, 2);
        Player playerC = new NormalPlayer("小丽", Role.UNDERCOVER, 100, 3);

        Room room = new Room("测试房间", 3);
        room.addPlayer(playerA);
        room.addPlayer(playerB);
        room.addPlayer(playerC);

        Map<Player, Integer> votes = new HashMap<>();

        room.allPlayersReady();
        room.initVotes(votes);
        room.startGame();

        Scanner input = new Scanner("我不是卧底\n我也不是卧底\n我是卧底\n3\n3\n1");
        ConsoleView view = new ScannerConsoleView(input, System.out);
        GameService gameService = new GameService(new GameResultService(),view);
        gameService.runGame(room,votes);

        assertEquals(GameStatus.FINISHED, room.getStatus());
        assertFalse(playerC.isAlive());
        assertEquals(Role.UNDERCOVER, playerC.getRole());
    }

    @Test
    void shouldThrowNullPointerException() {
        GameResultHandler handler = new GameResultService();
        Scanner input = new Scanner(" ");
        ConsoleView view = new ScannerConsoleView(input, System.out);
        assertThrows(NullPointerException.class,
                () -> new GameService(null,view));
        assertThrows(NullPointerException.class,
                () -> new GameService(handler,null));
    }

    @Test
    void runGameShouldCallInjectedResultHandler() {
        class RecordingGameResultHandler implements GameResultHandler {
            private boolean called;

            @Override
            public void processResult(Room room, Player maxVotePlayer) {
                called = true;
                room.setStatus(GameStatus.FINISHED);
            }

            public boolean wasCalled() {
                return called;
            }
        }
        Player playerA = new NormalPlayer("小明", Role.CIVILIAN, 100, 1);
        Player playerB = new NormalPlayer("小红", Role.CIVILIAN, 100, 2);
        Player playerC = new NormalPlayer("小丽", Role.UNDERCOVER, 100, 3);

        Room room = new Room("测试房间", 3);
        room.addPlayer(playerA);
        room.addPlayer(playerB);
        room.addPlayer(playerC);

        Map<Player, Integer> votes = new HashMap<>();

        room.allPlayersReady();
        room.initVotes(votes);
        room.startGame();

        Scanner input = new Scanner("我不是卧底\n我也不是卧底\n我是卧底\n3\n3\n1");

        RecordingGameResultHandler handler = new RecordingGameResultHandler();
        ConsoleView view = new ScannerConsoleView(input, System.out);
        GameService gameService = new GameService(handler,view);
        gameService.runGame(room,votes);

        assertTrue(handler.wasCalled());
    }
}
