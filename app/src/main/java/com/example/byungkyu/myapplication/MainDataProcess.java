package com.example.byungkyu.myapplication;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by YJ on 2017-08-14.
 */

public class MainDataProcess {
    private static MainDataProcess mainDataProcess;

    private double[] analogParsedData;
    private double[] fuelParsedData;
    private String[][] currentErrorInfoParsedData;
    private HashMap<String, String[]> totalData;

    public MainDataProcess(){
    }



    public void analogDataProcessing(int[] analogParsedData) {
        if(analogParsedData == null || analogParsedData.length == 0){

        }
        this.analogParsedData = new double[analogParsedData.length];
        for(int i = 0; i < analogParsedData.length; i++){
            this.analogParsedData[i] = DBHelper.analogMap.get(i) * analogParsedData[i];
        }

    }
    public void currentErrorInfoProcessing(int[][] parsingData){
        if(parsingData == null){

        }
        this.currentErrorInfoParsedData = new String[parsingData.length][3];

        //해당 값 투입
        for(int i=0; i<parsingData.length; i++){
           this.currentErrorInfoParsedData[i] = DBHelper.ceiMap.get(parsingData[i][0]+""+parsingData[i][1]);
        }
        //디비에 저장할것

    }
}
