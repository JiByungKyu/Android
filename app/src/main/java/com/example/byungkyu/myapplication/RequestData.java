package com.example.byungkyu.myapplication;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by ByungKyu on 2017-07-26.
 */

public class RequestData {
    SocketManager socketManager = SocketManager.singleton();
    BigInteger reqReadTest= new BigInteger("06a60001010101b0",16);
    BigInteger reqReadFault= new BigInteger("05a600012100cd",16);
    BigInteger reqReadFuel=new BigInteger("05a600011200be",16);
    BigInteger reqReadOpTime=new BigInteger("05a600010400b0",16);
    BigInteger reqReadPump=new BigInteger("07a6000101020304b8",16);
    //요청 메시지- Length, SID, 주기, 로컬 데이터 그룹 갯수, 갯수에 따른 ID, ID에 따른 데이터 갯수, 데이터 ID

    public void reqFault() throws IOException {
        socketManager.sendMsg(reqReadFault);
    }
    public void reqTest() throws IOException {
        socketManager.sendMsg(reqReadTest);
    }
    public void reqFuel()throws IOException{
        socketManager.sendMsg(reqReadFuel);
    }
    public void reqOpTime() throws IOException{
        socketManager.sendMsg(reqReadOpTime);
    }
    public void reqPump()throws IOException{
        socketManager.sendMsg(reqReadPump);
    }
}
