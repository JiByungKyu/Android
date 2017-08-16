package com.example.byungkyu.myapplication;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static android.content.ContentValues.TAG;

/**
 * Created by ByungKyu on 2017-08-03.
 */

public class CommunicationManager {
    RequestData requestData = new RequestData();
    Thread connectThread;
    Thread receiveThread;
    public static SocketActivity socketActivity;
    private String ip = "192.168.2.99";
    private int port = 5000;
    private Socket socket;
    byte[] buffer = new byte[500];
    byte[] bytes;
    int gap=250;
    private InputStream inputStream;
    private OutputStream outputStream;
    public boolean Isconnected=false;
    private ParsingData parsingData = ParsingData.getInstance();
    private static final CommunicationManager communicationManager = new CommunicationManager();
    private CommunicationManager(){
        try {
            start();
        }
        catch(Exception e){
            Log.e("생성자","에러 : "+e);
        }
    }
    public void setSocketActivity(SocketActivity socketActivity){
        this.socketActivity=socketActivity;
    }
    public static CommunicationManager getInstance(){return communicationManager;}
    public boolean start(){
        connectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //if (socket == null) {
                    //ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
                    //setThreadPolicy(policy);
                    socket = new Socket(ip, port);
                    //}
                    //if(socket.isConnected()==false) {
                    //    socket.connect(new InetSocketAddress(ip, port));
                    //}
                    outputStream = socket.getOutputStream();
                    inputStream = socket.getInputStream();
                    Isconnected = true;
                    Log.d("보임?", ""+Isconnected);
                    receive();
                } catch (Exception e) {
                    Log.e(TAG, "Error: " + e);
                }
            }
        });
        connectThread.start();
        return true;
    }
    public void disconnect()throws IOException{
        if(socket!=null) {
            socket.getOutputStream().close();
            socket.getInputStream().close();
            socket.close();
        }
    }
    public void sendMsg(){
        try {
            if(socketActivity instanceof MainActivity) {
                outputStream.write(requestData.reqTest());
                Thread.sleep(gap);
                // outputStream.flush();
                outputStream.write(requestData.reqFault());
                Thread.sleep(gap);
                outputStream.write(requestData.reqFuel());
                Thread.sleep(gap);
                outputStream.write(requestData.reqOpTime());
                Thread.sleep(gap);
                outputStream.write(requestData.reqPump());
                Log.d("보낸다, 연결됨?", "" + Isconnected);
            }
        }
        catch(Exception e){
            Log.e(TAG,"ERROR : "+e);
        }
    }
    public byte[] recvMsg()throws IOException {
        byte sum;
        inputStream.read(buffer);
        byte[] bytes=new byte[buffer[0]];
        sum=buffer[0];

        System.arraycopy(buffer,1,bytes,0,buffer[0]);
        for(int i=1;i<=buffer[0];i++) {
            //bytes[i-1]=buffer[i]; 복사 방법2
            sum+=buffer[i];

        }
        Log.d("받은 것: "+buffer[buffer[0]+1],"더한 것:"+sum);
        if(sum==buffer[buffer[0]+1])
            return bytes;
        else {
            Log.d("안됩니다?", "checksum 오류");
            return null;
        }
        /*
        else
        return sb.toString();
         */
    }
    public void receive(){
        receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("진입중?", "컥");
                    while(Isconnected) {
                        Log.d("연결됨", "컥");
                        bytes=recvMsg();
                        if(bytes!=null){
                            Log.d("됩니다?", "컥");
                            //parsingData.parsingMsg(bytes);
                        }
                        else
                            Log.d("receive thread:","byte가 null입니다. ");
                    }
                }
                catch(Exception e)  {

                }
            }
        });
        receiveThread.start();
    }
}