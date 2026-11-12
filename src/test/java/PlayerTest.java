import com.example.excise.NormalPlayer;
import com.example.excise.Player;
import com.example.excise.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest{

    @Test
    void shouldAddScore(){
        Player player = new NormalPlayer("小明", Role.CIVILIAN,100,1);
        int result = player.addScore(20);
        assertEquals(120,result);
        assertEquals(120,player.getScore());
    }

    @Test
    void shouldNotDeductMoreThenCurrentScore(){
        Player player = new NormalPlayer("小明",Role.CIVILIAN,100,1);
        int result = player.deductScore(120);
        assertEquals(100,result);
        assertEquals(100,player.getScore());
    }

    @Test
    void shouldEliminatePlayer(){
        Player player = new NormalPlayer("小明",Role.CIVILIAN,100,1);
        player.eliminate();
        assertFalse(player.isAlive());
        assertEquals(80,player.getScore());
    }

    @Test
    void playerShouldBeReadyAfterReady(){
        Player player = new NormalPlayer("小明",Role.CIVILIAN,100,1);
        player.ready();
        assertTrue(player.isReady());
    }
}
