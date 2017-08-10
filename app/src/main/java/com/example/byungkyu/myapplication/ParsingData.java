package com.example.byungkyu.myapplication;

import java.util.HashMap;

/**
 * Created by YJ on 2017-08-01.
 */

public class ParsingData {
    CommunicationManager communicationManager = CommunicationManager.getInstance();
    /*임시 LOCAL DATA READER data set*/

    private final byte LDR = (byte)0xE6;
    //private HashMap<Byte, Object> LDR;
    /*임시 LOCAL DATA WRITER data set*/
    private final byte LDW = (byte) 0xE7;
    //private Byte[] LDW = {(byte) 0xA6, (byte) 0xA7, (byte) 0xA8, (byte) 0xA9, (byte) 0xA1, (byte) 0xA2};

    /*임시 Periodic Send Stop */
    private final byte PSS = (byte) 0xE8;

    /*임시 EEPROM READ */
    private final byte ERR = (byte) 0xE9;

    /*임시 EEPROM WRITER */
    private final byte ERW = (byte) 0xE1;

    /*임시 ERROR INFORMATION READER */
    private final byte EIR = (byte) 0xE2;


    /*private Byte[] ANALOG = {(byte) 0x00, (byte) 0x01, (byte) 0x02};
    private Byte[] DIGITAL_IO = {(byte) 0x03, (byte) 0x04, (byte) 0x05};
    private Byte[] FUEL_USE_INFO = {(byte) 0x06, (byte) 0x07, (byte) 0x08};
    private Byte[] OPERATION_TIME = {(byte) 0x09, (byte) 0x10, (byte) 0x11};
    private Byte[] FILTER_USETIME = {(byte) 0x12, (byte) 0x13, (byte) 0x14};
    private Byte[] FILTER_INIT = {(byte) 0x15, (byte) 0x16, (byte) 0x17};
    private Byte[] FILTER_CHANGE = {(byte) 0x18, (byte) 0x19, (byte) 0x20};
    private HashMap<Byte, Byte[]> CURRENT_ERROR_INFO;*/
    private final byte ANALOG = (byte) 0x01;
    private final byte DIGITAL_IO = (byte) 0x0A;
    private final byte FUEL_USE_INFO = (byte) 0x12;
    private final byte OPERATION_TIME = (byte) 0x04;
    private final byte FILTER_USETIME = (byte) 0x08;
    private final byte FILTER_INIT = (byte) 0x09;
    private final byte FILTER_CHANGE = (byte) 0x20;
    private final byte CURRENT_ERROR_INFO = (byte) 0x21;


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
    private byte[] data;
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
       /* LDR = new HashMap<Byte, Object>();
        CURRENT_ERROR_INFO = new HashMap<Byte, Byte[]>();
        responseMap.put((byte) 0xE6, LDR);
        responseMap.put((byte) 0xE7, LDW);
        responseMap.put((byte) 0xE8, PSS);
        responseMap.put((byte) 0xE9, ERR);
        responseMap.put((byte) 0xE1, ERW);
        responseMap.put((byte) 0xE2, EIR);

        /*Local Data Reader 일 경우 HashMap*/
       /*
        LDR.put((byte) 0x01, ANALOG);
        LDR.put((byte) 0x0A, DIGITAL_IO);
        LDR.put((byte) 0x12, FUEL_USE_INFO);
        LDR.put((byte) 0x04, OPERATION_TIME);
        LDR.put((byte) 0x08, FILTER_USETIME);
        LDR.put((byte) 0x09, FILTER_INIT);
        LDR.put((byte) 0x20, FILTER_CHANGE);
        LDR.put((byte) 0x21, CURRENT_ERROR_INFO);

        /*CURRENT_ERROR_INFO 일 경우 HashMap*

        /
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
        CURRENT_ERROR_INFO.put((byte) 103, VPV8);*/
    }

    /*ParsingData 싱글톤 패턴*/
    public static ParsingData getInstance() {
        if (parsingData == null) {
            parsingData = new ParsingData();
        }
        return parsingData;
    }

    /*Parsing 할 data 처리를 위한 Method*/
    public void parsingMsg(byte[] recvMsg) throws Exception {
        if (recvMsg.length == 0 || recvMsg == null) {
            throw new Exception("잘못된 data입니다.");
        }
        int LSB,MSB;
        data = recvMsg;
        nextIndex = 0;
        groupCount = 0;
        /*
        Object obj = null;
        String className = null;
        Byte[] tmpArray;
        HashMap tmpMap;
        obj = responseMap.get(data[POSITIVE_RS_ID]);*/
        switch (data[POSITIVE_RS_ID]){
            case LDR:
                groupCount = data[++nextIndex];

                for(int i=0;i<groupCount;i++){
                    switch (data[++nextIndex]){
                        case ANALOG:
                            break;
                        case DIGITAL_IO:
                            break;
                        case FUEL_USE_INFO:
                            break;
                        case OPERATION_TIME:
                            break;
                        case FILTER_USETIME:
                            break;
                        case FILTER_INIT:
                            break;
                        case FILTER_CHANGE:
                            break;
                        case CURRENT_ERROR_INFO:
                            dataCount=data[++nextIndex];
                            LSB=data[++nextIndex];
                            MSB=data[++nextIndex];
                            int numberOfError=((MSB&0xff)<<8)+(LSB&0xff);
                            //Error, FMI code
                            int EFCode[][] = new int[numberOfError][2];
                            for(int j=0;j<dataCount-1;j++){
                                LSB=data[++nextIndex];
                                MSB=data[++nextIndex];
                                EFCode[j][0]=LSB&0xff;
                                EFCode[j][1]=(MSB>>3)&0x1f;
                            }
                            communicationManager.sendMsg(EFCode);
                            break;
                        default:
                            break;
                    }
                }
                break;
            case LDW:
                break;
            case PSS:
                break;
            case ERR:
                break;
            case ERW:
                break;
            case EIR:
                break;
            default:
                break;
        }
    }
}