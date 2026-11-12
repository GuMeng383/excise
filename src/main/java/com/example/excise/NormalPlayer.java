package com.example.excise;

public class NormalPlayer extends Player {
    public NormalPlayer(String name, Role role, int score, int ID){
        super(name,role,score,ID);
    }
    @Override
    public void showInfo(){
        System.out.println("姓名：" + getName());
        System.out.println("角色：" + getRole().getDescription());
        System.out.println("编号：" + getID());
        System.out.println("该玩家是普通玩家");
    }
}
