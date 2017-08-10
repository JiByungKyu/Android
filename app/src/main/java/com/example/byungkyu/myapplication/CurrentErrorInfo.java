package com.example.byungkyu.myapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YJ on 2017-08-08.
 */

public class CurrentErrorInfo implements ParsingBehaivor {
    private int nextIndex;
    private static CurrentErrorInfo currentErrorInfo;
    private String[] errorInfo;
    private HashMap<String, ArrayList<String>> FCL;
    private CommunicationManager com;
    static{
        currentErrorInfo = null;
    }
    private CurrentErrorInfo(){
        nextIndex = 0;
    }
    public static CurrentErrorInfo getInstance() {
        if (currentErrorInfo == null) {
            currentErrorInfo = new CurrentErrorInfo();
        }
        return currentErrorInfo;
    }
    public void parsingMessage(Byte[] recvMsg){
        if(recvMsg == null){

        }

        int dataCount = 0;
        byte msgInfo = 0;
        StringBuilder indexAndFMI = new StringBuilder();
        String[] data = null;

        dataCount = recvMsg[nextIndex];
        Log.i("카운트", dataCount+"");
        msgInfo = recvMsg[++nextIndex];
        Log.i("오류정보", msgInfo+"");
        /*오류정보는 30개를 초과할수가 없다.*/
        if(msgInfo < 0 || msgInfo > 30){
            return ;
        }

        /*총 오류 개수를 나타내는 데이터*/
        ++nextIndex;

        /*데이터를 저장할 배열 생성*/
        errorInfo = new String[dataCount-1];
        for(int i=0; i<dataCount-1; i++){
            //index 추가
            errorInfo[i] = (recvMsg[++nextIndex] & 0xFF) + "" + (recvMsg[++nextIndex] >> 3);
           // Log.i("LSB", (recvMsg[nextIndex] & 0xFF)+"");
           // indexAndFMI.append(recvMsg[++nextIndex] >> 3);
            //Log.i("MSB", (recvMsg[nextIndex] >> 3)+"");

            indexAndFMI.setLength(0);
        }
        //실제 데이터 값
        com = CommunicationManager.getInstance();
        com.sendMsg(errorInfo);
    }
}
