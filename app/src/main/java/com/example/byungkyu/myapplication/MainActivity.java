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

public class MainActivity extends AppCompatActivity {
    TextView rcvMsg;
    Button btn;
    ParsingData parsingData = ParsingData.getInstance();
    RequestData requestData = new RequestData();
    ReceiveData receiveData = new ReceiveData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvMsg=(TextView)findViewById(R.id.textView);
        btn =  (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Byte[] msg = {(byte)0xE6,0x01,0x21,0x01,0x01,0x00,(byte)0xD2,0x10};
                try {
                    parsingData.parsingMsg(msg);
                } catch (Exception e) {
                    Log.i("Error: ",e.getMessage());
                }
            }
        });

        try{
            process();
            //
        }
        catch (Exception e){

        }
        /*msg = Message.obtain();
        msg.obj=sb.toString();
        msgHandler.sendMessage(msg);*/
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
