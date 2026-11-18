package com.example.excise;

import java.util.NoSuchElementException;

public class RoomService {
    public Room createRoom(ConsoleView consoleView)
    {
        String name = readRoomName(consoleView);
        int people = readPlayerCount(consoleView);
        return new Room(name,people);
    }

    private String readRoomName(ConsoleView consoleView){
        while(true){
            consoleView.print("请创建房间名称:");
            try{
                String name = consoleView.readLine();
                if(name == null || name.isBlank()){
                    consoleView.print("房间创建失败：房间名称不能为空");
                    continue;
                }
                return name;
            }catch (NoSuchElementException e){
                throw new NoSuchElementException("房间创建失败，没有更多输入");
            }
        }

    }

    private int readPlayerCount(ConsoleView consoleView){
        while(true){
            consoleView.print("请输入房间最大人数:");
            try{
                int people = consoleView.readInt();
                if(people <= Room.MIN_PLAYER){
                    consoleView.print("房间创建失败：最大人数必须大于" + Room.MIN_PLAYER);
                    continue;
                }
                return people;
            }catch(NumberFormatException e){
                consoleView.print("房间创建失败：请输入整数");
            }
        }
    }
}
