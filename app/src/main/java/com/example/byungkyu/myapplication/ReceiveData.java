package com.example.byungkyu.myapplication;

import android.view.View;

/**
 * Created by ByungKyu on 2017-07-28.
 */

public class ReceiveData {
    byte [] bytes;
    Thread thread;
    SocketManager socketManager = SocketManager.singleton();
    SocketActivity activity;
    public ReceiveData(SocketActivity activity){
        this.activity=activity;
    }
    public void start(View view){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(socketManager.Isconnected) {
                        bytes=socketManager.recvMsg();
                        if(bytes!=null){
                            activity.receiveMsg(bytes);
                        }
                        else;//checksum 오류
                    }
                }
                catch(Exception e)  {

                }
            }
        });
        thread.start();
   }
}
