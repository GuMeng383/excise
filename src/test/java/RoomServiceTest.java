import com.example.excise.ConsoleView;
import com.example.excise.Room;
import com.example.excise.RoomService;
import com.example.excise.ScannerConsoleView;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomServiceTest {
    @Test
    void rightCreatRoom(){
        Scanner input = new Scanner("测试房\n3");
        RoomService roomService = new RoomService();
        ConsoleView view = new ScannerConsoleView(input,System.out);
        Room room = roomService.createRoom(view);
        assertEquals("测试房",room.getRoomName());
        assertEquals(3,room.getMaxPlayerCount());
    }

    @Test
    void blankNameShouldReinput(){
        Scanner input = new Scanner(" \n测试房\n3");
        RoomService roomService = new RoomService();
        ConsoleView view = new ScannerConsoleView(input,System.out);
        Room room = roomService.createRoom(view);
        assertEquals("测试房",room.getRoomName());
        assertEquals(3,room.getMaxPlayerCount());
    }

    @Test
    void misPlayerCountShouldReinput(){
        Scanner input = new Scanner("测试房\n2\n3");
        RoomService roomService = new RoomService();
        ConsoleView view = new ScannerConsoleView(input,System.out);
        Room room = roomService.createRoom(view);
        assertEquals("测试房",room.getRoomName());
        assertEquals(3,room.getMaxPlayerCount());
    }

    @Test
    void inputIsEmptyCreatRoomShouldException(){
        Scanner input = new Scanner("");
        ConsoleView view = new ScannerConsoleView(input,System.out);
        RoomService roomService = new RoomService();
        assertThrows(NoSuchElementException.class,
                () -> roomService.createRoom(view));
    }

    @Test
    void noPlayerCount(){
        Scanner input = new Scanner("测试房间");
        ConsoleView view = new ScannerConsoleView(input,System.out);
        RoomService roomService = new RoomService();
        assertThrows(NoSuchElementException.class,
                () ->  roomService.createRoom(view));
    }

    @Test
    void misInputShouldRecreateRoom(){
        Scanner input = new Scanner("测试房\nabc\n3");
        ConsoleView view = new ScannerConsoleView(input,System.out);
        RoomService roomService = new RoomService();
        Room room = roomService.createRoom(view);
        assertEquals(3,room.getMaxPlayerCount());
    }
}
