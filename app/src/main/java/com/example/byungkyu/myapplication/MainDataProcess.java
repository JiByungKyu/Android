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
    private HashMap<Byte,Object> processedData;

    private MainDataProcess(){
        analogParsedData = null;
        fuelParsedData = null;
        currentErrorInfoParsedData = null;
        processedData = new HashMap<Byte, Object>();
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

        }
        processedData.put(Data.ANALOG,this.analogParsedData);
    }
    public void currentErrorInfoProcessing(int[][] parsingData){
        if(parsingData == null){

        }
        this.currentErrorInfoParsedData = new String[parsingData.length][3];

        //해당 값 투입
        for(int i=0; i<parsingData.length; i++){
            this.currentErrorInfoParsedData[i] = DBHelper.ceiMap.get(parsingData[i][0]+""+parsingData[i][1]);
        }

        processedData.put(Data.CURRENT_ERROR_INFO,this.currentErrorInfoParsedData);
        //디비에 저장할것
    }
    @Override
    public void processingMsg(HashMap<Byte, Object> dataSet) {
        if(dataSet == null){
            Log.i("오류", "겠찌");
        }

        if(dataSet.containsKey(Data.CURRENT_ERROR_INFO)){
            currentErrorInfoProcessing((int[][])dataSet.get(Data.CURRENT_ERROR_INFO));
        }else if(dataSet.containsKey(Data.ANALOG)){
            analogDataProcessing((int[])dataSet.get(Data.ANALOG));
        }else if(dataSet.containsKey(Data.FUEL_USE_INFO)){

        }

        Log.i("메인으로","갔음");
        CommunicationManager.socketActivity.receiveMsg(processedData);
    }
}
