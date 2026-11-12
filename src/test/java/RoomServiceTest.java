import com.example.excise.Room;
import com.example.excise.RoomService;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomServiceTest {
    @Test
    void rightCreatRoom(){
        Scanner input = new Scanner("测试房\n3");
        RoomService roomService = new RoomService();
        Room room = roomService.createRoom(input);
        assertEquals("测试房",room.getRoomName());
        assertEquals(3,room.getMaxPlayerCount());
    }

    @Test
    void blankNameShouldReinput(){
        Scanner input = new Scanner(" \n测试房\n3");
        RoomService roomService = new RoomService();
        Room room = roomService.createRoom(input);
        assertEquals("测试房",room.getRoomName());
        assertEquals(3,room.getMaxPlayerCount());
    }

    @Test
    void misPlayerCountShouldReinput(){
        Scanner input = new Scanner("测试房\n2\n3");
        RoomService roomService = new RoomService();
        Room room = roomService.createRoom(input);
        assertEquals("测试房",room.getRoomName());
        assertEquals(3,room.getMaxPlayerCount());
    }
}
