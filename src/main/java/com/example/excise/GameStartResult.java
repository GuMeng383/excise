package com.example.excise;

public enum GameStartResult {
    START("已开始"),
    ROOM("房间状态不对"),
    PEOPLE("人数不够"),
    UNDERCOVER("没有卧底"),
    PREPARE("有玩家未准备"),
    MOUNTNOMATCH("双方阵营人数不匹配");

    private final String description;
    GameStartResult(String description) {this.description = description;}
    public String getDescription(){
        return description;
    }

}
