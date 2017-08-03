package com.example.byungkyu.myapplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by ByungKyu on 2017-07-26.
 */

public class SocketManager {
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

        }
    }
    private static SocketManager socketManager = new SocketManager();
    public static SocketManager singleton(){
        return socketManager;
    }
    public Socket getSocket(){
        return socket;
    }
    public boolean connect(){
        try{
            if (socket == null) {
                socket = new Socket(ip, port);
            }
            if(!socket.isConnected())
                socket.connect(new InetSocketAddress(ip,port));
            outputStream= socket.getOutputStream();
            inputStream = socket.getInputStream();
            Isconnected=true;
        }
        catch(Exception e) {
            return false;
        }

        return true;
    }
    public void disconnect()throws IOException{
            if(socket!=null) {
                socket.getOutputStream().close();
                socket.getInputStream().close();
                socket.close();
            }
    }
    public void sendMsg(BigInteger bInt)throws IOException{
            outputStream.write(bInt.toByteArray());
    }
    public byte[] recvMsg()throws IOException{
        int sum;
        inputStream.read(buffer);
        byte[] bytes=new byte[buffer[0]];
        sum=buffer[0];
        //StringBuilder sb = new StringBuilder();
        System.arraycopy(buffer,1,bytes,0,buffer[0]);
        for(int i=1;i<=buffer[0];i++) {
            //bytes[i-1]=buffer[i]; 복사 방법2
            sum+=buffer[i];
            //sb.append(String.format("%02x ", buffer[i] & 0xff));
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