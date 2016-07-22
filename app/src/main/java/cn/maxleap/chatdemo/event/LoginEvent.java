package cn.maxleap.chatdemo.event;

public class LoginEvent {

    public  String username;
    public  String password;

    public  LoginEvent(String username,String password){
        this.username=username;
        this.password=password;
    }

}
