package com.example.excise;


import java.util.Map;
import java.util.Objects;

public class GameService {
    private final GameResultHandler gameResultHandler;
    private final ConsoleView consoleView;

    public GameService(GameResultHandler gameResultHandler,ConsoleView consoleView) {
        this.gameResultHandler = Objects.requireNonNull(gameResultHandler);
        this.consoleView = Objects.requireNonNull(consoleView);
    }

    public void runGame(Room room,Map<Player,Integer> votes){
        int round = 1;
        while(room.getStatus() == GameStatus.PLAYING){
            consoleView.print("第"+round + "回合开始");
            room.resetVotes(votes);
            consoleView.print("当前游戏状态为" + room.getStatus().getDescription());
            room.playerSay(consoleView);
            Player finalPlayer = room.playVotingRound(votes,consoleView);
            gameResultHandler.processResult(room,finalPlayer);
            if(room.getStatus() ==  GameStatus.FINISHED){
                consoleView.print("当前游戏状态为" + room.getStatus().getDescription());
            }
            round++;
        }
        consoleView.print("游戏结束");
    }
}
