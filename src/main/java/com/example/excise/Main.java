package com.example.excise;

import java.io.PrintStream;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PrintStream output = System.out;
        ConsoleView view = new ScannerConsoleView(input, output);

        Map<Player,Integer> votes = new HashMap<>();
        RoomService roomService = new RoomService();
        Room room = roomService.createRoom(view);
        String statusText = switch(room.getStatus()){
            case WAITING -> "房间准备中";
            case PLAYING -> "游戏进行中";
            case FINISHED -> "游戏已结束";
            default -> "未知状态";
        };
        view.print(statusText);

        PlayerService playerService = new PlayerService();
        for (int id = 1; id <= room.getMaxPlayerCount(); id++) {
            view.print("创建第" + id + "名玩家");
            Player player = playerService.createPlayer(view, id);
            room.addPlayer(player);
        }
        room.initVotes(votes);
        room.allPlayersReady();
        boolean start = room.startGame();
        if(!start){
            view.print("不能开始游戏");
            input.close();
            return;
        }

        GameService gameService = new GameService(new GameResultService(),view);
        gameService.runGame(room,votes);

        input.close();
    }
    public static void useInspector(RoleInspector inspector, Scanner input, Room room){
        inspector.inspectRole(input,room);
    }
    public static Optional<Player> findPlayerById(Map<Integer, Player> playersById, int id){
        return Optional.ofNullable(playersById.get(id));
    }
}


