package com.example.byungkyu.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SocketActivity{
    CommunicationManager communicationManager = CommunicationManager.getInstance();
    TextView rcvMsg;
    Message msg;
    Button btn;
    byte[] bytes;
    StringBuilder sb = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvMsg=(TextView)findViewById(R.id.textView);
        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });

        communicationManager.setSocketActivity(this);

        try{
            process();
        }
        catch (Exception e){

        }

    }
    public void receiveMsg(byte[] bytes){
        Log.d("받았냐??", "컥");this.bytes=bytes;
        for(byte bt:bytes){
            sb.append(String.format("%02x ", bt & 0xff));
        }
        msg = Message.obtain();
        msg.obj=sb.toString();
        msgHandler.sendMessage(msg);
    }
    public void receiveFaultMsg(String [][] faultMsg){
        msg = Message.obtain();
        msg.obj=sb.toString();
        msgHandler.sendMessage(msg);
    }
    public void process() throws IOException{
       communicationManager.sendMsg();
    }
    public void test(){
        try{
        communicationManager.disconnect();}
        catch(Exception e){

        }
        for(byte bt:bytes){
            sb.append(String.format("%02x ", bt & 0xff));
        }
        msg = Message.obtain();
        msg.obj=sb.toString();
        msgHandler.sendMessage(msg);
    }
    Handler msgHandler = new Handler(){
        public void handleMessage(Message msg){
            messageDisplay(msg.obj.toString());
        }
    };
    public void messageDisplay(String serverMsg){
        rcvMsg.setText(""+serverMsg);
    }
}
