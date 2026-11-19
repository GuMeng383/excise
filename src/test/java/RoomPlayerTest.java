import com.example.excise.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    @Test
    void shouldPrintEliminationOnceAndShowScoreAfterDeduction() {
        Room room = new Room("测试房", 3);
        Player target = new NormalPlayer("小王", Role.UNDERCOVER, 100, 3);
        room.addPlayer(player(1));
        room.addPlayer(player(2));
        room.addPlayer(target);
        room.allPlayersReady();
        assertEquals(GameStartResult.START, room.startGame());

        Map<Player, Integer> votes = new HashMap<>();
        room.initVotes(votes);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner input = new Scanner("3\n3\n1");
             PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            ConsoleView view = new ScannerConsoleView(input, out);
            assertSame(target, room.playVotingRound(votes, view));
        }

        String output = buffer.toString(StandardCharsets.UTF_8);
        String expectedLine = "玩家小王被淘汰";
        long count = output.lines().filter(expectedLine::equals).count();
        assertEquals(1L, count, "淘汰提示应恰好出现一次");
        assertTrue(output.lines().anyMatch("玩家小王剩余分数:80"::equals), "应显示扣分后的分数");
    }

    private Player player(int id){
        return new NormalPlayer("玩家"+id,Role.CIVILIAN,100,id);
    }

}
