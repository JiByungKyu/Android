package com.example.byungkyu.myapplication;

/**
 * Created by YJ on 2017-08-01.
 */

public class ParsingData {
    private final int
    public Byte[] data;
    private static ParsingData parsingData;
    Byte[] PRS = {(byte)0xE6, (byte)0xE7,(byte)0xE8,(byte)0xE9,(byte)0xE1,(byte)0xE2};
    Byte[] SID = {(byte)0x01, (byte)0x0A,(byte)0x12,(byte)0x04,(byte)0x08,(byte)0x09,
            (byte)0x20, (byte)0x21};
    Byte[] GC = {};
    Byte[] DC = {};
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

        data[0]




    }
}
