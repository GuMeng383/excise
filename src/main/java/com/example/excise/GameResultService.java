package com.example.excise;

import java.util.Objects;

public class GameResultService implements GameResultHandler{
    private final ConsoleView consoleView;
    public GameResultService(ConsoleView consoleView) {
        this.consoleView = Objects.requireNonNull(consoleView);
    }
    @Override
    public void processResult(Room room,Player maxVotePlayer) {
        GameStatus status = room.getStatus();
        if(status == GameStatus.FINISHED){
            consoleView.print("游戏已经结束");
            return;
        }
        if(maxVotePlayer != null){
            if(maxVotePlayer.getRole() == Role.UNDERCOVER){
                boolean a = room.hasAliveUndercover();
                if(a){
                    consoleView.print("玩家" + maxVotePlayer.getName() + "死亡，其身份为卧底，游戏继续");
                }
                else{
                    consoleView.print("平民胜利");
                    room.setStatus(GameStatus.FINISHED);
                }
            }
            else{
                int b = room.countAliveCivilian();
                int c = room.countAliveUndercover();
                if(b<=c){
                    consoleView.print("卧底获胜");
                    room.setStatus(GameStatus.FINISHED);
                }
                else{
                    consoleView.print("玩家" + maxVotePlayer.getName() + "死亡，其身份为平民，游戏继续");
                }
            }
        }
        else{
            consoleView.print("没有可判断的投票结果");
        }
    }
}
