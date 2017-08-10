package com.example.byungkyu.myapplication;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by YJ on 2017-08-01.
 */

public class ParsingData {
    private ParsingBehaivor parsingBehaivor;
    /*배열의 첫번째 방은 PRI 지정*/
    private final byte POSITIVE_RS_ID = 0;
    private static ParsingData parsingData;
    /*각각 activity에 보낼 데이터 셋 */
    private byte nextIndex;
    private byte groupCount;
    private byte dataCount;
    private byte msgInfo;
    private Byte[] data;
    private Object obj;

    /*ParsingData class 변수 초기화*/
    static {
        parsingData = null;
    }

    /*데이터 초기화*/
    private ParsingData() {
        data = null;
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
    public Object parsingMsg(Byte[] recvMsg) throws Exception {
        if (recvMsg.length == 0 || recvMsg == null) {
            throw new Exception("잘못된 data입니다.");
        }
        data = recvMsg;
        nextIndex = 0;
        groupCount = 0;

        msgInfo = data[POSITIVE_RS_ID];

        switch (msgInfo){
            case  (byte)0xE6: {
                groupCount = data[++nextIndex];
                if(groupCount == 0)
                    return null;
                for(int i = 0; i < groupCount; i++) {
                    //LDR 종류 체크 데이터
                    msgInfo = data[++nextIndex];
                    //LDR에 따른 parsing 알고리즘 적용
                    setBehaivor(msgInfo);
                    //해당 LDR이 존재한다면 data 개수 만큼 배열전달
                    if(parsingBehaivor != null){
                        dataCount = data[++nextIndex];
                        parsingBehaivor.parsingMessage(Arrays.copyOfRange(data,nextIndex, nextIndex + (dataCount * 2)+1));
                    }
                    //다음 그룹이 존재한다면 nextIndex setting
                    if(nextIndex + dataCount+1 < data.length){
                        nextIndex += dataCount;
                    }
                }

            } return obj;
            case  (byte)0xE7: break;
            case  (byte)0xE8: break;
            case  (byte)0xE9: break;
            case  (byte)0xE1: break;
            case  (byte)0xE2: break;
            default: Log.i("여기로","오는가");//throw new Exception("잘못된 데이터 입니다.");
        }
        return null;
    }

    private void setBehaivor(byte id){

        switch (id){
            case  0x01: break;
            case  0x0A: break;
            case  0x12: break;
            case  0x04: break;
            case  0x08: break;
            case  0x09: break;
            case  0x20: break;
            case  0x21: parsingBehaivor = CurrentErrorInfo.getInstance(); break;
            default: parsingBehaivor = null;
        }
    }
    public ParsingBehaivor getParsingBehaivor(){
        return parsingBehaivor;
    }
}
