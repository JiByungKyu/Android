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
    private HashMap<String, ArrayList<String>> FCL;
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

        for(int i=0; i<dataCount-1; i++){
            //index 추가
            indexAndFMI.append(recvMsg[++nextIndex] & 0xFF);
            Log.i("LSB", (recvMsg[nextIndex] & 0xFF)+"");
            indexAndFMI.append(recvMsg[++nextIndex] >> 3);
            Log.i("MSB", (recvMsg[nextIndex] >> 3)+"");
            Log.i("데이터 값", indexAndFMI.toString());
            indexAndFMI.setLength(0);
        }
        //실제 데이터 값

    }
}
