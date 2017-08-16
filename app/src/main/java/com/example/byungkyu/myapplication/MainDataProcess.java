package com.example.byungkyu.myapplication;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by YJ on 2017-08-14.
 */

public class MainDataProcess implements DataProcessor{
    private static MainDataProcess mainDataProcess;

    private double[] analogParsedData;
    private double[] fuelParsedData;
    private String[][] currentErrorInfoParsedData;
    private final byte FUEL_USE_INFO = (byte) 0x12;
    private final byte CURRENT_ERROR_INFO = (byte) 0x21;
    private final byte ANALOG = (byte) 0x01;
    private HashMap<Byte,Object> processedData;

    private MainDataProcess(){
        analogParsedData = null;
        fuelParsedData = null;
        currentErrorInfoParsedData = null;
        processedData = null;
    }

    public static MainDataProcess getInstance(){
        if(mainDataProcess == null){
            mainDataProcess = new MainDataProcess();
        }
        return mainDataProcess;
    }

    public void analogDataProcessing(int[] analogParsedData) {
        if(analogParsedData == null || analogParsedData.length == 0){

        }
        this.analogParsedData = new double[analogParsedData.length];
        for(int i = 0; i < analogParsedData.length; i++){
            this.analogParsedData[i] = DBHelper.analogMap.get(i) * analogParsedData[i];
            processedData.put(ANALOG,this.analogParsedData);
        }

    }
    public void currentErrorInfoProcessing(int[][] parsingData){
        if(parsingData == null){

        }
        this.currentErrorInfoParsedData = new String[parsingData.length][3];

        //해당 값 투입
        for(int i=0; i<parsingData.length; i++){
            this.currentErrorInfoParsedData[i] = DBHelper.ceiMap.get(parsingData[i][0]+""+parsingData[i][1]);
            processedData.put(CURRENT_ERROR_INFO,this.currentErrorInfoParsedData);
        }
        //디비에 저장할것


    }
    @Override
    public void processingMsg(HashMap<Byte, Object> dataSet) {
        if(dataSet == null){
            Log.i("오류", "겠찌");
        }
        if(dataSet.containsKey(CURRENT_ERROR_INFO)){
            currentErrorInfoProcessing((int[][])dataSet.get(CURRENT_ERROR_INFO));
        }else if(dataSet.containsKey(ANALOG)){
            analogDataProcessing((int[])dataSet.get(ANALOG));
        }else if(dataSet.containsKey(FUEL_USE_INFO)){

        }

        CommunicationManager.socketActivity.receiveMsg(processedData);
    }
}
