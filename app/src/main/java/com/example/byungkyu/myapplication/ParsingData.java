package com.example.byungkyu.myapplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by YJ on 2017-08-01.
 */

public class ParsingData {
    /*임시 LOCAL DATA READER data set*/

    private HashMap<Byte, Object> LDR;
    private Byte[] ANALOG = {(byte) 0x00, (byte) 0x01, (byte) 0x02};
    private Byte[] DIGITAL_IO = {(byte) 0x03, (byte) 0x04, (byte) 0x05};
    private Byte[] FUEL_USE_INFO = {(byte) 0x06, (byte) 0x07, (byte) 0x08};
    private Byte[] OPERATION_TIME = {(byte) 0x09, (byte) 0x10, (byte) 0x11};
    private Byte[] FILTER_USETIME = {(byte) 0x12, (byte) 0x13, (byte) 0x14};
    private Byte[] FILTER_INIT = {(byte) 0x15, (byte) 0x16, (byte) 0x17};
    private Byte[] FILTER_CHANGE = {(byte) 0x18, (byte) 0x19, (byte) 0x20};
    private HashMap<Byte, Byte[]> CURRENT_ERROR_INFO;

    /*임시 LOCAL DATA WRITER data set*/
    private Byte[] LDW = {(byte) 0xA6, (byte) 0xA7, (byte) 0xA8, (byte) 0xA9, (byte) 0xA1, (byte) 0xA2};

    /*임시 Periodic Send Stop */
    private byte PSS = (byte) 0xE8;

    /*임시 EEPROM READ */
    private byte ERR = (byte) 0xE9;

    /*임시 EEPROM WRITER */
    private byte ERW = (byte) 0xE1;

    /*임시 ERROR INFORMATION READER */
    private byte EIR = (byte) 0xE2;

    /*임시 CURRENT_ERROR_INFO data set*/
    private Byte[] E046 = {(byte) 1, (byte) 19};
    private Byte[] E051 = {(byte) 3, (byte) 4, (byte) 7, (byte) 8, (byte) 9};
    private Byte[] E091 = {(byte) 2, (byte) 9, (byte) 10, (byte) 19};
    private Byte[] VC01 = {(byte) 11};
    private Byte[] VC02 = {(byte) 11};
    private Byte[] VPV1 = {(byte) 5, (byte) 6};
    private Byte[] VPV2 = {(byte) 5, (byte) 6};
    private Byte[] VPV3 = {(byte) 5, (byte) 6};
    private Byte[] VPV4 = {(byte) 5, (byte) 6};
    private Byte[] VPV5 = {(byte) 5, (byte) 6};
    private Byte[] VPV6 = {(byte) 5, (byte) 6};
    private Byte[] VPV7 = {(byte) 5, (byte) 6};
    private Byte[] VPV8 = {(byte) 5};


    /*배열의 첫번째 방은 PRI 지정*/
    private final byte POSITIVE_RS_ID = 0;
    private static ParsingData parsingData;
    /*각각 activity에 보낼 데이터 셋 */
    private HashMap<Byte, String> sendMsg;
    private byte nextIndex;
    private byte groupCount;
    private byte dataCount;
    private byte msgInfo;
    private Byte[] data;
    HashMap<Byte, Object> responseMap;

    /*ParsingData class 변수 초기화*/
    static {
        parsingData = null;
    }

    /*데이터 초기화*/
    private ParsingData() {
        data = null;
        /*PRI를 파악하기 위한 HashMap*/
        responseMap = new HashMap<Byte, Object>();
        responseMap.put((byte) 0xE6, LDR);
        responseMap.put((byte) 0xE7, LDW);
        responseMap.put((byte) 0xE8, PSS);
        responseMap.put((byte) 0xE9, ERR);
        responseMap.put((byte) 0xE1, ERW);
        responseMap.put((byte) 0xE2, EIR);

        /*Local Data Reader 일 경우 HashMap*/
        LDR.put((byte) 0x01, ANALOG);
        LDR.put((byte) 0x0A, DIGITAL_IO);
        LDR.put((byte) 0x12, FUEL_USE_INFO);
        LDR.put((byte) 0x04, OPERATION_TIME);
        LDR.put((byte) 0x08, FILTER_USETIME);
        LDR.put((byte) 0x09, FILTER_INIT);
        LDR.put((byte) 0x20, FILTER_CHANGE);
        LDR.put((byte) 0x21, CURRENT_ERROR_INFO);

        /*CURRENT_ERROR_INFO 일 경우 HashMap*/
        CURRENT_ERROR_INFO.put((byte) 205, E046);
        CURRENT_ERROR_INFO.put((byte) 207, E051);
        CURRENT_ERROR_INFO.put((byte) 210, E091);
        CURRENT_ERROR_INFO.put((byte) 11, VC01);
        CURRENT_ERROR_INFO.put((byte) 23, VC02);
        CURRENT_ERROR_INFO.put((byte) 25, VPV1);
        CURRENT_ERROR_INFO.put((byte) 34, VPV2);
        CURRENT_ERROR_INFO.put((byte) 56, VPV3);
        CURRENT_ERROR_INFO.put((byte) 42, VPV4);
        CURRENT_ERROR_INFO.put((byte) 89, VPV5);
        CURRENT_ERROR_INFO.put((byte) 77, VPV6);
        CURRENT_ERROR_INFO.put((byte) 102, VPV7);
        CURRENT_ERROR_INFO.put((byte) 103, VPV8);
    }

    private ParsingData(Byte[] data) {
        this.data = data;
    }

    /*ParsingData 싱글톤 패턴*/
    public static ParsingData getInstance() {
        if (parsingData == null) {
            parsingData = new ParsingData();
        }
        return parsingData;
    }

    /*Parsing 할 data 처리를 위한 Method*/
    public void parsingMsg(Byte[] recvMsg) throws Exception {
        if (recvMsg.length == 0 || recvMsg == null) {
            throw new Exception("잘못된 data입니다.");
        }
        data = recvMsg;
        nextIndex = 0;
        groupCount = 0;
        Object obj = null;
        String className = null;
        Byte[] tmpArray;
        HashMap tmpMap;

        responseMap.get(data[POSITIVE_RS_ID]);

        if(obj == null){
            throw new Exception("실패한 응답 MSG입니다.");
        }

        className = obj.getClass().getName();

        if(className.equals("java.util.HashMap")){
            groupCount = data[++nextIndex];
        }
        else if(className.equals("java.lang.Byte")){
            msgInfo = data[++nextIndex];
        }
        else{
            Arrays.sort(LDW);
            msgInfo = LDW[Arrays.binarySearch(LDW, data[++nextIndex])];
        }


        if(groupCount == 0)
            return ;


        for(int i = 0; i < groupCount; i++){
            obj = LDR.get(data[++nextIndex]);
            className = obj.getClass().getName();

            if(className.equals("java.util.HashMap")){
                tmpMap = (HashMap)obj;
                dataCount = data[++nextIndex];
                /* 정확히 무슨 역할을 하는지 알수 없었던 2byte */
                ++nextIndex;
                ++nextIndex;
                //실제 데이터 값
                for(int j = 0; j < dataCount ; j++){
                    msgInfo = data[++nextIndex];
                    tmpArray = (Byte[])tmpMap.get(msgInfo);
                    Arrays.sort(tmpArray);
                    msgInfo = data[++nextIndex] << 3;
                    msgInfo = tmpArray[Arrays.binarySearch(tmpArray,msgInfo)];
                    sendMsg.put(msgInfo, "오류값");
                }
            }
            else{
                tmpArray = (Byte[])obj;
                Arrays.sort(tmpArray);
                sendMsg.put(tmpArray[Arrays.binarySearch(tmpArray,data[++nextIndex])], "해당값");

            }

        }


    }



   /* private final byte POSITIVE_RS_ID  = 0;
    private byte GROUP_COUNT  = 1;
    private final byte LOCAL_DATA_GROUP_ID  = 2;
    private byte DATA_COUNT  = 3;
    private byte group_ID_Index;
    private byte data_Index;
    private byte nextData;

    public Byte[] data;
    private static ParsingData parsingData;
    Byte[] PRS = {(byte)0xE6, (byte)0xE7,(byte)0xE8,(byte)0xE9,(byte)0xE1,(byte)0xE2};

    HashMap<Byte, Object> responseMap;
    HashMap<Byte, Object> SID;


    Byte[] CFI = {(byte)0x00, (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,
            (byte)0x09,(byte)0x10,(byte)0x11,(byte)0x12,(byte)0x13,(byte)0x14,(byte)0x15,(byte)0x16,
            (byte)0x17,(byte)0x18,(byte)0x19,(byte)0x20,(byte)0x21,(byte)0x22,(byte)0x23,(byte)0x24,
            (byte)0x25,(byte)0x26,(byte)0x27,(byte)0x28,(byte)0x29,(byte)0x30};
    //Byte[] MSB = { (byte)};

    static{
        parsingData = null;
    }
    private ParsingData(){
        data = null;
        responseMap = new HashMap<Byte, Object>();
        responseMap.put((byte)0xE6, SID);

        SID.put((byte)0x01, ANALOG);
        SID.put((byte)0x21, CFI);
        SID.put((byte)0x0A, DIGITAL_IO);

        nextData = 0;
    }
    private ParsingData(Byte[] data) {
        this.data = data;
    }
    public static ParsingData getInstance(){
        if(parsingData == null){
            parsingData = new ParsingData();
        }
        return parsingData;
    }

    public void parsingMsg(Byte[]  recvMsg) throws Exception{
        if(recvMsg.length == 0 || recvMsg == null){
            throw new Exception("잘못된 data입니다.");
        }
        data = recvMsg;

        Object obj = responseMap.get(data[POSITIVE_RS_ID]);
        if(obj == null){
            return ;
        }
        byte gc = data[GROUP_COUNT];

        group_ID_Index  = LOCAL_DATA_GROUP_ID;
        data_Index = group_ID_Index;
        data_Index++;

        for(int i = 0; i < gc; i++){
            if(obj.getClass().getName().equals("java.util.HashMap")){
                HashMap<Byte, Object> tmp = (HashMap)obj;
                obj = tmp.get(data[group_ID_Index]);
            }else {

                Byte[] tmp = (Byte[]) obj;
            }
            DATA_COUNT = 0;
            for(int j = DATA_COUNT+1; j < data[DATA_COUNT]+DATA_COUNT; j++){
                i % 2 == 0 ? data[j] : data[j] << 3;
            }
            group_ID_Index = LOCAL_DATA_GROUP_ID + DATA_COUNT;
            data_Index = group_ID_Index;
            data_Index++;
        }



    }*/
}