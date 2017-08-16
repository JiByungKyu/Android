package com.example.byungkyu.myapplication;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by YJ on 2017-08-01.
 */

public class ParsingData {
    /*배열의 첫번째 방은 PRI 지정*/
    private final byte POSITIVE_RS_ID = 0;
    private static ParsingData parsingData;
    /*각각 activity에 보낼 데이터 셋 */
    private byte nextIndex;
    private byte groupCount;
    private byte dataCount;
    private byte msgInfo;
    private byte[] data;
    private DataProcessor dataProcessor;
    private HashMap<Byte, Object> dataSet;

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
    private final byte ANALOG = (byte) 0x01;
    private final byte DIGITAL_IO = (byte) 0x0A;
    private final byte FUEL_USE_INFO = (byte) 0x12;
    private final byte OPERATION_TIME = (byte) 0x04;
    private final byte FILTER_USETIME = (byte) 0x08;
    private final byte FILTER_INIT = (byte) 0x09;
    private final byte FILTER_CHANGE = (byte) 0x20;
    private final byte CURRENT_ERROR_INFO = (byte) 0x21;




    /*ParsingData class 변수 초기화*/
    static {
        parsingData = null;
    }

    /*데이터 초기화*/
    private ParsingData() {
        data = null;
        dataProcessor = null;
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
        data = recvMsg;
        nextIndex = 0;
        groupCount = 0;

        int LSB,MSB,ID,UNIT;

        msgInfo = data[POSITIVE_RS_ID];

        switch (msgInfo){
            case  LDR: {
                groupCount = data[++nextIndex];
                if(groupCount == 0)
                    return ;

                if(CommunicationManager.socketActivity instanceof MainActivity){
                    dataProcessor = MainDataProcess.getInstance();
                }
                for(int i = 0; i < groupCount; i++) {
                    //LDR 종류 체크 데이터
                    msgInfo = data[++nextIndex];
                    //data 개수 파악
                    dataCount = data[++nextIndex];
                    switch (msgInfo){
                        case ANALOG :
                            int[] analogParsedData = new int[dataCount];
                            //data 값만 가져오기
                            for(int j=0;j<dataCount;j++){
                                LSB = data[++nextIndex] & 0xff ;
                                MSB = data[++nextIndex] & 0xff ;
                                UNIT = (LSB << 8) | (MSB & 0xff);
                                analogParsedData[j] = UNIT;
                            }
                            dataSet.put(ANALOG, analogParsedData);
                        break;
                        case DIGITAL_IO :

                        case FUEL_USE_INFO :
                            int[] fuelParsedData = new int[dataCount];
                            for(int j=0;j<dataCount;j++){
                                UNIT = (data[++nextIndex] << 8) | (data[++nextIndex] & 0xff);
                            }
                            dataSet.put(FUEL_USE_INFO, fuelParsedData);

                            break;

                        case OPERATION_TIME :
                        case FILTER_USETIME :
                        case FILTER_INIT :
                        case FILTER_CHANGE :
                        case CURRENT_ERROR_INFO :
                            LSB=data[++nextIndex];
                            MSB=data[++nextIndex];
                            int numberOfError=((MSB&0xff)<<8)+(LSB&0xff);
                            int[][] ceiParsedData = new int[numberOfError][2];
                            //Error, FMI code
                            for(int j=0;j<dataCount-1;j++){
                                LSB=data[++nextIndex];
                                MSB=data[++nextIndex];
                                if(LSB != 0 && MSB !=0){
                                    ceiParsedData[j][0]=LSB&0xff;
                                    ceiParsedData[j][1]=(MSB>>3)&0x1f;
                                }
                            }
                            dataSet.put(CURRENT_ERROR_INFO,ceiParsedData);
                            break;

                    }
                }

            }

            case  (byte)0xE7: break;
            case  (byte)0xE8: break;
            case  (byte)0xE9: break;
            case  (byte)0xE1: break;
            case  (byte)0xE2: break;
            default: Log.i("여기로","오는가");//throw new Exception("잘못된 데이터 입니다.");
        }
        dataProcessor.receiveMsg(dataSet);
    }
}
