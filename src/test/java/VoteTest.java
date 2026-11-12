import com.example.excise.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VoteTest {
    private Map<Player, Integer> votes;
    private Player playerA;
    private Player playerB;
    private Player playerC;
    private Room room;
    private VotingService votingService;

    @BeforeEach
    void setUp() {
        votes = new HashMap<>();

        playerA = new NormalPlayer("A", Role.CIVILIAN, 100, 1);
        playerB = new NormalPlayer("B", Role.CIVILIAN, 100, 2);
        playerC = new NormalPlayer("C", Role.UNDERCOVER, 100, 3);

        votingService = new VotingService();

        room = new Room("测试房", 3);
        room.addPlayer(playerA);
        room.addPlayer(playerB);
        room.addPlayer(playerC);
        room.initVotes(votes);
    }
    @Test
    void testVote(){
        assertEquals(3,votes.size());
        for(Map.Entry<Player,Integer> entry : votes.entrySet()){
            int voteCount = entry.getValue();
            assertEquals (0,voteCount);
        }
    }

    @Test
    void maxVoteCountPlayer(){
        room.resetVotes(votes);
        votes.put(playerA,1);
        List<Player> maxVoteCountPlayers = votingService.findMaxVotePlayers(room.getPlayers(),votes);
        assertEquals(1,maxVoteCountPlayers.size());
        assertTrue(maxVoteCountPlayers.contains(playerA));
    }
    @Test
    void maxVoteCountPlayers() {
        room.resetVotes(votes);
        votes.put(playerA, 1);
        votes.put(playerB, 1);
        List<Player> maxVoteCountPlayers = votingService.findMaxVotePlayers(room.getPlayers(),votes);
        assertEquals(2, maxVoteCountPlayers.size());
    }
    @Test
    void noVoteShouldNoPlayer(){
        room.resetVotes(votes);
        List<Player> maxVoteCountPlayers = votingService.findMaxVotePlayers(room.getPlayers(),votes);
        assertEquals(0,maxVoteCountPlayers.size());
    }
    @Test
    void diedPlayerNotShouldInResult(){
        votes.put(playerA,1);
        votes.put(playerB,1);
        playerA.eliminate();
        List<Player> result = votingService.findMaxVotePlayers(room.getPlayers(),votes);
        assertFalse(result.contains(playerA));
        assertTrue(result.contains(playerB));
        assertEquals(1,result.size());
    }
}
