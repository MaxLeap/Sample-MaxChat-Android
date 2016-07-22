package cn.maxleap.chatdemo.event;

public class MessageEvent {
    public   String uName;
    public   String uPwd;

    public  MessageEvent(String uName,String uPwd){
        this.uName=uName;
        this.uPwd=uPwd;
    }

    public  MessageEvent(String uName){
        this.uName=uName;
    }


}
