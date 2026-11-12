package com.example.excise;

public enum GameStatus {
    WAITING("等待中"),
    PLAYING("游戏中"),
    FINISHED("结束");
    private final String description;
    //构造方法
    GameStatus(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public boolean isWaiting(){
        return this == WAITING;
    }
    public boolean isPlaying(){
        return this == PLAYING;
    }
}
