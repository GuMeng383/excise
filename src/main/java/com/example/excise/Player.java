package com.example.excise;

public abstract class Player {
    private String playerName;
    private Role role;
    private int score;
    private boolean ready;
    private boolean alive;
    private final int ID;

    public Player(String name, Role role, int score, int ID) {
        this.playerName = name;
        this.role = role;
        this.score = score;
        this.alive = true;
        this.ID = ID;
    }

    public String getName() {
        return this.playerName;
    }
    public Role getRole() {
        return this.role;
    }
    public int getScore() {
        return this.score;
    }
    public void ready() {
        ready = true;
    }
    public boolean isReady(){
        return ready;
    }
    public boolean isAlive(){
        return alive;
    }
    public int getID(){
        return this.ID;
    }

    public void voteFor(Player target){
        System.out.println(playerName + "投票给了" + target.getName());
    }

    public int addScore(int value){
        if(value <=0){
            System.out.println("分数必须是正数");
            return score;
        }
        score += value;
        return score;
    }
    public int deductScore(int value){
        if(!canDeductScore(value)){
            System.out.println("分数不够");
            return score;
        }
        score -= value;
        return score;
    }
    public void eliminate(){
        if(alive == false){
            System.out.println("玩家" + playerName + "已被淘汰");
            return;
        }
        System.out.println("玩家" + playerName+ "被淘汰");
        alive = false;
        deductScore(20);
        System.out.println("剩余分数为：" + score);
    }
    public boolean canDeductScore(int value){
        if(value <=0 || value > score){
            return false;
        }
        return true;
    }
    public abstract void showInfo();
    @Override
    public  boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Player)){
            return false;
        }
        Player other = (Player) obj;
        return this.ID == other.ID;
    }
    @Override
    public int hashCode(){
        return Integer.hashCode(ID);
    }
    @Override
    public String toString(){
        return "player{" +
                "姓名：" + playerName +
                ",角色：" + role +
                ",分数：" + score +
                ",ID：" + ID +
                "}";
    }

}


