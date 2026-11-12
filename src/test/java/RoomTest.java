import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.excise.GameStatus;
import com.example.excise.Room;

class RoomTest {

    @Test
    void shouldCreateRoomWithValidArguments() {
        Room room = new Room("测试房", 5);

        assertEquals("测试房", room.getRoomName());
        assertEquals(5, room.getMaxPlayerCount());
        assertEquals(GameStatus.WAITING, room.getStatus());
    }

    @Test
    void shouldRejectBlankRoomName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room("", 5)
        );
    }

    @Test
    void shouldRejectTooSmallMaxPlayer() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room("测试房", 2)
        );
    }
}