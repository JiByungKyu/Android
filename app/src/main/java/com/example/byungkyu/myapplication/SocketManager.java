package com.example.byungkyu.myapplication;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;

import static android.content.ContentValues.TAG;

/**
 * Created by ByungKyu on 2017-07-26.
 */

public class SocketManager {
    Thread thread;
    private String ip = "192.168.2.99";
    private int port = 5000;
    private Socket socket;
    byte[] buffer = new byte[500];
    private InputStream inputStream;
    private OutputStream outputStream;
    public boolean Isconnected=false;
    private SocketManager(){
        try {
            connect();
        }
        catch(Exception e){
            Log.e("생성자","에러 : "+e);
        }
    }
    private static SocketManager socketManager = new SocketManager();
    public static SocketManager singleton(){return socketManager;}
    public Socket getSocket(){
        return socket;
    }
    public boolean connect(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //if (socket == null) {
                    //ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
                    //setThreadPolicy(policy);
                    socket = new Socket(ip, port);
                    Log.d("보임?", "컥");
                    //}
                    //if(socket.isConnected()==false) {
                    //    socket.connect(new InetSocketAddress(ip, port));
                    //}
                    outputStream = socket.getOutputStream();
                    inputStream = socket.getInputStream();
                    Isconnected = true;
                } catch (Exception e) {
                    Log.e(TAG, "Error: " + e);
                }
            }
     });
        thread.start();
        return true;
    }
    public void disconnect()throws IOException{
            if(socket!=null) {
                socket.getOutputStream().close();
                socket.getInputStream().close();
                socket.close();
            }
    }
    public void sendMsg(BigInteger bInt){
            Log.d("소켓 메니져","메시지보내기 전");
        try {
            socket.getOutputStream().write(bInt.toByteArray());
            Log.d("소켓 메니져", "메시지보낸 후");
        }
        catch(Exception e){
            Log.e(TAG,"ERROR : "+e);

        }
    }
    public byte[] recvMsg()throws IOException{
        int sum;
        inputStream.read(buffer);
        byte[] bytes=new byte[buffer[0]];
        sum=buffer[0];

        System.arraycopy(buffer,1,bytes,0,buffer[0]);
        for(int i=1;i<=buffer[0];i++) {
            //bytes[i-1]=buffer[i]; 복사 방법2
            sum+=buffer[i];

        }
        if(sum==buffer[buffer[0]]+1)
            return bytes;
        else
            return null;
        /*
        else
        return sb.toString();

         */
    }
}