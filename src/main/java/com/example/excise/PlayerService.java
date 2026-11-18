package com.example.excise;

public class PlayerService {
    public Player createPlayer(ConsoleView consoleView, int ID){
        consoleView.print("请输入你的姓名：");
        String name = consoleView.readLine();
        Role role;
        while(true){
            consoleView.print("请选择你的角色：");
            consoleView.print("1.平民");
            consoleView.print("2.卧底");
            try{
                int choice = consoleView.readInt();
                if(choice == 1){
                    role = Role.CIVILIAN;
                    break;
                }
                else if(choice == 2){
                    role = Role.UNDERCOVER;
                    break;
                }
                else{
                    consoleView.print("请输入1或2");
                }
            }catch(NumberFormatException e){
                consoleView.print("请输入整数");
            }
        }
        int score = 100;
        return new NormalPlayer(name,role,score,ID);
    }
}
