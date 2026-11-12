package com.example.excise;

import java.util.Scanner;

public class SpecialPlayer extends Player implements RoleInspector {
    private String specialAbility;
    public SpecialPlayer(String name, Role role, int score, int ID, String specialAbility) {
        super(name,role,score,ID);
        this.specialAbility =specialAbility;
    }
    public void showAbility(){
        System.out.println("特殊能力为："  + specialAbility);
    }
    @Override
    public Role inspectRole (Scanner input, Room room){
        while(true){
            try{
                System.out.println("请选择想查看身份的玩家编号");
                int id = Integer.parseInt(input.nextLine());
                Player player = room.findPlayer(id);
                if(player==null){
                    System.out.println("玩家不存在，请重新输入");
                    continue;
                }
                return player.getRole();
            }
            catch(NumberFormatException e){
                System.out.println("请输入正确编号");
            }
        }
    }
    @Override
    public void showInfo(){
        System.out.println("姓名："+ getName());
        System.out.println("角色："+ getRole().getDescription());
        System.out.println("编号：" + getID());
        System.out.println("该玩家是特殊玩家");
        System.out.println("能力：" + specialAbility);
    }

}
