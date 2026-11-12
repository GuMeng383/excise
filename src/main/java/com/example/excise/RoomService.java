package com.example.excise;

import java.util.Scanner;

public class RoomService {
    public Room createRoom(Scanner input)
    {
        String name = readRoomName(input);
        int people = readPlayerCount(input);
        return new Room(name,people);
    }

    private String readRoomName(Scanner input){
        while(true){
            System.out.println("请创建房间名称:");
            String name = input.nextLine();
            if(name == null || name.isBlank()){
                System.out.println("房间创建失败：房间名称不能为空");
                continue;
            }
            return name;
        }
    }

    private int readPlayerCount(Scanner input){
        while(true){
            System.out.println("请输入房间最大人数:");
            try{
                int people = Integer.parseInt(input.nextLine());
                if(people <= Room.MIN_PLAYER){
                    System.out.println("房间创建失败：最大人数必须大于" + Room.MIN_PLAYER);
                    continue;
                }
                return people;
            }catch(NumberFormatException e){
                System.out.println("房间创建失败：请输入整数");
            }
        }
    }
}
