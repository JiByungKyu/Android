package com.example.byungkyu.myapplication;

import android.util.Log;

import static com.example.byungkyu.myapplication.Data.ANALOG;
import static com.example.byungkyu.myapplication.Data.CURRENT_ERROR_INFO;
import static com.example.byungkyu.myapplication.Data.DIGITAL_IO;
import static com.example.byungkyu.myapplication.Data.EIR;
import static com.example.byungkyu.myapplication.Data.ERR;
import static com.example.byungkyu.myapplication.Data.ERW;
import static com.example.byungkyu.myapplication.Data.FILTER_CHANGE;
import static com.example.byungkyu.myapplication.Data.FILTER_INIT;
import static com.example.byungkyu.myapplication.Data.FILTER_USETIME;
import static com.example.byungkyu.myapplication.Data.FUEL_USE_INFO;
import static com.example.byungkyu.myapplication.Data.LDW;
import static com.example.byungkyu.myapplication.Data.OPERATION_TIME;
import static com.example.byungkyu.myapplication.Data.PTS;

/**
 * Created by YJ on 2017-08-01.
 */

public class ParsingData {

    /*배열의 첫번째 방은 PRI 지정*/
    private final byte POSITIVE_RS_ID = 0;



    private byte nextIndex;
    private byte groupCount;
    private byte dataCount;
    private byte msgInfo;
    private byte[] data;
    private MainDataProcess mainDataProcess;
    private static ParsingData parsingData;
    /*ParsingData class 변수 초기화*/
    static {
        parsingData = null;
    }

    /*데이터 초기화*/
    private ParsingData() {
        data = null;
        mainDataProcess = new MainDataProcess();
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
            case Data.LDR: {
                groupCount = data[++nextIndex];
                if(groupCount == 0)
                    return ;
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
                                UNIT = (data[++nextIndex] << 8) | (data[++nextIndex] & 0xff);
                                analogParsedData[j] = UNIT;
                            }
                            mainDataProcess.analogDataProcessing(analogParsedData);
                        break;
                        case DIGITAL_IO :

                        case FUEL_USE_INFO :
                            /*LSB=data[++nextIndex];
                            MSB=data[++nextIndex];
                            for(int j=0;j<dataCount;j++){
                                ID=data[++nextIndex];
                                UNIT=data[++nextIndex];
                                parsedData[j][0] = ID;
                                parsedData[j][1] = UNIT;
                            } break;*/

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
                            mainDataProcess.currentErrorInfoProcessing(ceiParsedData);
                            break;

                    }
                }

            }
            case  LDW: break;
            case  PTS: break;
            case  ERR: break;
            case  ERW: break;
            case  EIR: break;
            default: Log.i("여기로","오는가");//throw new Exception("잘못된 데이터 입니다.");
        }
        if(CommunicationManager.socketActivity instanceof MainActivity)
            ;
        return ;
    }
}
