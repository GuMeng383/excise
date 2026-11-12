package com.example.excise;

import java.util.Scanner;

public class PlayerService {
    public Player createPlayer(Scanner input, int ID){
        System.out.print("请输入玩家姓名:");
        String name = input.nextLine();
        Role role;
        while(true){
            System.out.println("请选择你的角色：");
            System.out.println("1.平民");
            System.out.println("2.卧底");
            try{
                int choice = Integer.parseInt(input.nextLine());
                if(choice == 1){
                    role = Role.CIVILIAN;
                    break;
                }
                else if(choice == 2){
                    role = Role.UNDERCOVER;
                    break;
                }
                else{
                    System.out.println("请输入1或2");
                }
            }catch(NumberFormatException e){
                System.out.println("请输入整数");
            }
        }
        int score = 100;
        return new NormalPlayer(name,role,score,ID);
    }
}
