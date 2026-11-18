package com.example.excise;

import java.util.*;

public class Room {
    public static final int MIN_PLAYER = 2;

    private String roomName;
    private final int maxPlayer;
    private List<Player> players;
    private GameStatus status;
    private final VotingService votingService = new  VotingService();

    public Room(String name, int maxPlayer) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("房间名称不能为空");
        }

        if (maxPlayer <= MIN_PLAYER) {
            throw new IllegalArgumentException("最大人数必须大于" + MIN_PLAYER);
        }
        this.roomName = name;
        this.maxPlayer = maxPlayer;
        this.players = new ArrayList<>();
        this.status = GameStatus.WAITING;
    }

    public String getRoomName() {
        return this.roomName;
    }
    public int getMaxPlayerCount() {
        return this.maxPlayer;
    }
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
    public List<Player> getReadonlyPlayers(){
        return Collections.unmodifiableList(players);
    }
    public GameStatus getStatus() {
        return status;
    }
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public boolean addPlayer(Player player) {
        if(player == null){
            return false;
        }
        if(!status.isWaiting()){
            return false;
        }
        if (players.size() >= maxPlayer) {
            return false;
        }
        if(findPlayer(player.getID()) != null){
            return false;
        }
        players.add(player);
        return true;
    }

    public void allPlayersReady() {
        for (Player player : players) {
            player.ready();
        }
    }
    public boolean canStartGame() {
        if(!status.isWaiting()){
            System.out.println("当前不是准备状态，无法开始游戏");
            return false;
        }
        if(players.size()<2){
            System.out.println("人数不足，无法开始");
            return false;
        }
        if(countAliveUndercover() == 0){
            System.out.println("卧底人数为0,无法开始");
            return false;
        }
        if(countAliveCivilian()<=countAliveUndercover()){
            System.out.println("卧底和平民人数不匹配,无法开始");
            return false;
        }
        boolean allReady = players
                .stream()
                .allMatch(p->p.isReady());
        if(!allReady){
            System.out.println("还有玩家没有准备");
            return false;
        }
        return true;
    }
    public boolean startGame(){
        boolean result = canStartGame();
        if(result){
            status = GameStatus.PLAYING;
            System.out.println("游戏开始");
            return true;
        }
        return false;
    }
    public String say(Player player ,ConsoleView consoleView){
        consoleView.print("玩家" + player.getName() + "正在发言:");
        String message = consoleView.readLine();
        return message;
    }
    public void playerSay(ConsoleView consoleView){
        if(status.isPlaying()){
            for(Player player : players){
                if(!player.isAlive()){
                    consoleView.print("玩家" + player.getName() + "死亡，跳过发言阶段");
                    continue;
                }
                say(player,consoleView);
            }
        }
        else{
            consoleView.print("游戏不在进行中，不能发言");
        }
    }
    public void initVotes(Map<Player,Integer> votes){
        for(Player player : players){
            votes.put(player,0);
        }
    }

    public Player realVoteChoice(Player voter, ConsoleView consoleView){
        for(Player player : players){
            if(player.isAlive()){
                consoleView.print(player.getID() + "号玩家是" + player.getName());
            }
        }
        while(true){
            consoleView.print("请选择投票的玩家:");
            try{
                int choice = consoleView.readInt();
                Player target = findPlayer(choice);
                if(target == null){
                    consoleView.print("不存在该玩家，请重新输入");
                    continue;
                }
                if(!target.isAlive()){
                    consoleView.print("玩家" +target.getName() + "死亡，不能投给他");
                    continue;
                }
                if(voter.equals(target)){
                    consoleView.print("不能投给自己");
                    continue;
                }
                return target;
            }catch(NumberFormatException e){
                consoleView.print("请输入合法数字");
            }
        }
    }
    public void addVote(Player voter, ConsoleView consoleView,Map<Player,Integer> votes){
        Player target = realVoteChoice(voter,consoleView);
        voter.voteFor(target);
        int Vote = votes.getOrDefault(target,0);
        votes.put(target,Vote + 1);
    }
    public void proAddVote(ConsoleView consoleView,Map<Player,Integer> votes){
        for(Player player:players){
            if(!player.isAlive()){
                consoleView.print("玩家" + player.getName() + "死亡，跳过投票阶段");
                continue;
            }
            addVote(player,consoleView,votes);
        }
    }
    public void showVotes(Map<Player,Integer> votes,ConsoleView consoleView) {
        for(Map.Entry<Player,Integer> entry : votes.entrySet()){
            Player player = entry.getKey();
            if(!player.isAlive()){
                continue;
            }
            int vote = entry.getValue();
            consoleView.print("玩家" + player.getName() + "获得" + vote + "票");
        }
    }

    public void resetVotes(Map<Player,Integer> votes) {
        for(Player player : players){
            votes.put(player,0);
        }
    }
    public Player playVotingRound(Map<Player,Integer> votes,ConsoleView consoleView){
        Player maxVotePlayer;
        while(true){
            if(!status.isPlaying()){
                return null;
            }
            proAddVote(consoleView,votes);
            showVotes(votes,consoleView);
            List<Player> result = votingService.findMaxVotePlayers(players,votes);
            if(result.size()==1){
                maxVotePlayer = result.get(0);
                consoleView.print("票数最多的人是" + maxVotePlayer.getName());
                consoleView.print("票数为" +votes.get(maxVotePlayer));
                maxVotePlayer.eliminate();
                break;
            }
            else if(result.size()==0){
                consoleView.print("没有投票结果");
                return null;
            }
            else{
                consoleView.print("出现平票，请重新投票");
                resetVotes(votes);
            }
        }
        return maxVotePlayer;
    }

    public boolean hasAliveUndercover(){
        boolean result = players
                .stream()
                .anyMatch(player -> player.getRole() == Role.UNDERCOVER
                && player.isAlive());
        return result;
    }
    public long  countAlivePlayer(){
        return players.stream()
                .filter(Player :: isAlive)
                .count();
    }
    public int countAliveCivilian(){
        int count = 0;
        for(Player player : players){
            if(player.isAlive()&&player.getRole()==Role.CIVILIAN){
                count++;
            }
        }
        return count;
    }
    public int countAliveUndercover(){
        int count = 0;
        for(Player player : players){
            if(player.isAlive()&&player.getRole()==Role.UNDERCOVER){
                count++;
            }
        }
        return count;
    }
    public Player findPlayer(int id){
        for(Player player : players){
            if(player.getID() == id){
                return player;
            }
        }
        return null;
    }
    public Optional<Player> findPlayerOptional(int id){
        return players.stream()
                .filter(player ->player.getID() == id)
                .findFirst();
    }
}


