package com.example.byungkyu.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView rcvMsg;

    RequestData requestData = new RequestData();
    ReceiveData receiveData = new ReceiveData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvMsg=(TextView)findViewById(R.id.textView);
        try{
            process();
            //
        }
        catch (Exception e){

        }
        msg = Message.obtain();
        msg.obj=sb.toString();
        msgHandler.sendMessage(msg);
    }
    public void process() throws IOException{
        requestData.reqFault();
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
