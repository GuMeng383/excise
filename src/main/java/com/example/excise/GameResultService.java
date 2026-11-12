package com.example.excise;

public class GameResultService implements GameResultHandler{
    @Override
    public void processResult(Room room,Player maxVotePlayer) {
        GameStatus status = room.getStatus();
        if(status == GameStatus.FINISHED){
            System.out.println("游戏已经结束");
            return;
        }
        if(maxVotePlayer != null){
            if(maxVotePlayer.getRole() == Role.UNDERCOVER){
                boolean a = room.hasAliveUndercover();
                if(a){
                    System.out.println("玩家" + maxVotePlayer.getName() + "死亡，其身份为卧底，游戏继续");
                }
                else{
                    System.out.println("平民胜利");
                    room.setStatus(GameStatus.FINISHED);
                }
            }
            else{
                int b = room.countAliveCivilian();
                int c = room.countAliveUndercover();
                if(b<=c){
                    System.out.println("卧底获胜");
                    room.setStatus(GameStatus.FINISHED);
                }
                else{
                    System.out.println("玩家" + maxVotePlayer.getName() + "死亡，其身份为平民，游戏继续");
                }
            }
        }
        else{
            System.out.println("没有可判断的投票结果");
        }
    }
}
