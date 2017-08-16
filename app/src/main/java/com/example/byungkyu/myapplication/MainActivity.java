package com.example.byungkyu.myapplication;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements SocketActivity{
    ParsingData parsingData = ParsingData.getInstance();
    RequestData requestData = new RequestData();
    ReceiveData receiveData = new ReceiveData();
    private Object obj;
    SQLiteDatabase db;

    CommunicationManager communicationManager = CommunicationManager.getInstance();
    TextView rcvMsg;
    Message msg;
    Button btn;
    String[][] strings;
    StringBuilder sb = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //데이터베이스 생성
        final DBHelper helper = new DBHelper(this);

        try{
            db = helper.getWritableDatabase();

        }catch(SQLException e){
            helper.onUpgrade(db,3,4);
            db = helper.getReadableDatabase();
        }

        try{
            //Positive_response insert 구문
           /*db.execSQL("insert into positive_response_tb(ID, CONTENT) values(0xE6, '로컬 데이터 읽기');");
            db.execSQL("insert into positive_response_tb(ID, CONTENT) values(0xE7, '로컬 데이터 쓰기');");
            db.execSQL("insert into positive_response_tb(ID, CONTENT) values(0xE8, '주기적 전송 중지');");
            db.execSQL("insert into positive_response_tb(ID, CONTENT) values(0xE9, 'EEPROM 읽기');");
            db.execSQL("insert into positive_response_tb(ID, CONTENT) values(0xE1, 'EEPROM 쓰기');");
            db.execSQL("insert into positive_response_tb(ID, CONTENT) values(0xE2, '고장 정보 읽기');");*/
            //Negative_response insert 구문
            //주로 사용
            /*db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x10, 'generalReject');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x11, 'serviceNotSupported');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x12, 'subFunctionNotSupported−invalidFormat');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x21, 'busy−repeatRequest');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x22, 'conditionsNotCorrectOrRequestSequenceError');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x23, 'routineNotComplete');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x31, 'requestOutOfRange');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x33, 'securityAccessDenied−securityAccessRequested');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x35, 'invalidKey');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x36, 'exceedNumberOfAttempts');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x37, 'requiredTimeDelayNotExpired');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x40, 'downloadNotAccepted');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x41, 'improperDownloadType');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x42, 'canNotDownloadToSpecifiedAddress');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x43, 'canNotDownloadNumberOfBytesRequested');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x50, 'uploadNotAccepted');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x51, 'improperUploadType');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x52, 'canNotUploadFromSpecifiedAddress');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x53, 'canNotUploadNumberOfBytesRequested');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x71, 'transferSuspended');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x72, 'transferAborted');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x74, 'illegalAddressInBlockTransfer');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x75, 'illegalByteCountInBlockTransfer');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x76, 'illegalBlockTransferType');");*/
            //주로 사용
           /*db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x77, 'blockTransferDataChecksumError');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x78, 'requestCorrectlyReceived−ResponsePending');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x79, 'incorrectlyByteCountDuringBlockTransfer');");
            db.execSQL("insert into negative_response_tb(ID, CONTENT) values(0x80, 'serviceNotSupportedInActiveDiagnosticMode');");
            */
            //로컬 데이터 그룹 insert 구문
            /*db.execSQL("insert into local_data_group_tb(ID, CONTENT) values(0x01, '아날로그');");
            db.execSQL("insert into local_data_group_tb(ID, CONTENT) values(0x0A, '디지털');");
            db.execSQL("insert into local_data_group_tb(ID, CONTENT) values(0x12, '연료 사용정보');");
            db.execSQL("insert into local_data_group_tb(ID, CONTENT) values(0x04, '가동시간');");
            db.execSQL("insert into local_data_group_tb(ID, CONTENT) values(0x08, '필터/오일 사용시간');");
            db.execSQL("insert into local_data_group_tb(ID, CONTENT) values(0x09, '필터/오일 사용시간 초기화');");
            db.execSQL("insert into local_data_group_tb(ID, CONTENT) values(0x20, '필터/오일 교환주기');");
            db.execSQL("insert into local_data_group_tb(ID, CONTENT) values(0x21, '현재 고장 정보');");
            */
            //아날로그 insert 구문
            /*db.execSQL("insert into analog_tb(ID, CONTENT, UNITVALUE,  UNIT) values(0x00, '엔진 회전수(Engine RPM)', 0.125, 'rpm');");
            db.execSQL("insert into analog_tb(ID, CONTENT, UNITVALUE,  UNIT) values(0x01, '엔진 오일 압력',  4, 'kPa');");
            db.execSQL("insert into analog_tb(ID, CONTENT, UNITVALUE,  UNIT) values(0x02, '연료 온도',  1, '℃');");
            db.execSQL("insert into analog_tb(ID, CONTENT, UNITVALUE,  UNIT) values(0x03, '프론트 펌프 압력', 0.0625, 'bar');");
            db.execSQL("insert into analog_tb(ID, CONTENT, UNITVALUE,  UNIT) values(0x04, '리어 펌프 압력', 0.0625, 'bar');");
            db.execSQL("insert into analog_tb(ID, CONTENT, UNITVALUE,  UNIT) values(0x05, '펌프 1 파일롯 압력', 0.0625, 'bar');");
            db.execSQL("insert into analog_tb(ID, CONTENT, UNITVALUE,  UNIT) values(0x06, '펌프 2 파일롯 압력', 0.0625, 'bar');");
            db.execSQL("insert into analog_tb(ID, CONTENT, UNITVALUE,  UNIT) values(0x07, '펌프 밸브1', 1, 'mA');");
            db.execSQL("insert into analog_tb(ID, CONTENT, UNITVALUE,  UNIT) values(0x08, '펌프 밸브2', 1, 'mA');");
            db.execSQL("insert into analog_tb(ID, CONTENT, UNITVALUE,  UNIT) values(0x09, '쿨링 팬 밸브', 1, 'mA');");
            db.execSQL("insert into analog_tb(ID, CONTENT, UNITVALUE,  UNIT) values(0x10, '유량제어 밸브', 1, 'mA');");*/

            //고장정보 insert 구문
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('E000046-01', '대기압센서(APS)로부터 낮은 " +
                    "공기압 신호 (E56)', 'Low air pressure signal from APS(E56)', 205, 1);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('E000046-19', '대기압센서(APS)로부터 CAN " +
                    "메시지 타임아웃 (E56)', 'CAN message timeout from APS(E56)', 205, 19);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('E000051-03', '쓰로틀 포지션 센서 1번 배터리와 단락', " +
                    "'Throttle Position Sensor 1, short circuit to battery.', 207, 3);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('E000051-04', '쓰로틀 포지션 센서 1번 그라운드와 단락', 'Throttle Position Sensor 1, Short " +
                    "Circuit to Ground', 207, 4);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('E000051-07', '조정된 쓰로틀 포지션센서 전 " +
                    "압이 Closed end에서 허용된 범위를 넘어섬', 'Adapted throttle position sensor " +
                    "voltage, at closed end position, " + "is outside permitted range.', 207, 7);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('E000051-08', '조정된 쓰로틀 포지션센서 전" +
                    "압이 Open end에서 허용된 범위를 넘어섬', 'Adapted throttle position sensor " +
                    "voltage, at open end position, is" + "outside permitted range', 207, 8);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('E000051-09', '쓰로틀 포지션센서(들) 동작이 잘못되거나 상호관계 오류', " +
                    "'Throttle Position Sensor(s) Bad Performance, Correlation Error', 207, 9);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('E000091-02', '보조 악셀페달이 페달고장이나 " +
                    "다른 결함으로 사용 중', 'Auxiliary accelerator pedal is used due to pedal faulty or other.', 210, 2);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('E000091-09', '악셀 페달 고장 혹은 오류 (via CAN)', 'Acc pedal faulty or error via can.', 210, 9);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('E000091-10', 악셀 페달 적절하지 않음, 고장', 'Accelerator pedal not plausible, faulty', 210, 10);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('E000091-19', '악셀 페달값이 적절한 범위에서 벗어남 (via CAN)', 'Acc pedal value out of valid range (via CAN)', 210, 19);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VCO001-11', '계기판 통신이상', 'GAUGE PANEL ERROR', 11, 11);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VCO002-11', 'E-ECU 통신이상', 'E-ECU ERROR', 23, 11);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV001-05', '펌프 비례 밸브(A) 전류가 정상 " +
                    "범위보다 낮음(개방)', 'PUMP P/V (A), Current below normal', 25, 5);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV001-06', '펌프 비례 밸브(A) 전류가 정상 " +
                    "범위보다 높음(단락)', 'PUMP P/V (A), Current above normal', 25, 6);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV002-05', '펌프 비례 밸브(B) 전류가 정상 " +
                    "범위보다 낮음(개방)', 'PUMP P/V (B), Current below normal', 34, 5);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV002-06', '펌프 비례 밸브(B) 전류가 정상 " +
                    "범위보다 높음(단락)', 'PUMP P/V (B), Current above normal', 34, 6);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV003-05', '유량제어 비례 감압 밸브 (G) " +
                    "전류가 정상 범위보다 낮음(개방)', 'FLOW CONTROL P/V (G), Current below normal', 56, 5);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV003-06', '유량제어 비례 감압 밸브 (G) " +
                    "전류가 정상 범위보다 높음(단락)', 'FLOW CONTROL P/V (G), Current above normal', 56, 6);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV004-05', '팬 제어 비례 감압 밸브 (J) 전" +
                    "류가 정상 범위보다 낮음(개방)', 'FAN CONTROL P/V (J), Current below normal', 42, 5);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV004-06', '팬 제어 비례 감압 밸브 (J) 전" +
                    "류가 정상 범위보다 높음(단락)', 'FAN CONTROL P/V (J), Current above normal', 42, 6);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV005-05', '압력 제어 비례 감압 밸브 (H) " +
                    "전류가 정상 범위보다 낮음(개방)', 'FAN CONTROL P/V (J), Current PRESS. CONTROL 1 P/V (H), Current below normal', 89, 5);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV005-06', '압력 제어 비례 감압 밸브 (H) " +
                    "전류가 정상 범위보다 높음(단락)', 'PRESS. CONTROL 1 P/V (H), Current above normal', 89, 6);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV006-05', '압력 제어 비례 감압 밸브 (I) " +
                    "전류가 정상 범위보다 낮음(개방)', 'PRESS. CONTROL 2 P/V (I), " +
                    "Current below normal', 77, 5);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV006-06', '압력 제어 비례 감압 밸브 (I) " +
                    "전류가 정상 범위보다 높음(단락)', 'FAN CONTROL P/V (J), Current above normal', 77, 6);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV007-05', '유량제어 비례 감압 밸브 (C) " +
                    "2-WAY 좌측고압 전류가 정상범위보다 낮음(개방)', 'FLOW CONTROL P/V (C) 2-WAY RH-OPEN, Current below normal', 102, 5);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV007-06', '유량제어 비례 감압 밸브 (C) " +
                    "2-WAY 좌측고압 전류가 정상 범위보다 높음(단락)', 'FLOW CONTROL P/V (C) 2-WAY RH-OPEN, Current above normal', 102, 6);");
            db.execSQL("insert into fault_code_list_tb(ID, CONTENT_KR, CONTENT_ER, FCL_INDEX, FMI) values('VPV008-05', '유량제어 비례 감압 밸브 (D) " +
                    "2-WAY 우측고압 전류가 정상 범위보다 낮음(개방)', 'FLOW CONTROL P/V (D) 2-WAY RH-CLOSE, Current below normal', 103, 5);");
          //  db.execSQL("delete from fault_code_list_tb");
        }catch (SQLException e){
            e.getMessage();
        }

        communicationManager.setSocketActivity(this);
        setContentView(R.layout.activity_main);
        rcvMsg=(TextView)findViewById(R.id.textView);
        btn =  (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] msg = {(byte)0xE6,0x01,0x21,0x1F,0x01,0x00,(byte)0xD2,0x10,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
                        ,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
                        ,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
                        ,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
                try {

                    helper.selectAnalog(db);
                   parsingData.parsingMsg(msg);

                } catch (Exception e) {
                    Log.i("Error: ",e.getMessage());
                }
            }
        });

        try{
            process();
            //
        }
        catch (Exception e){

        }

    }
    public void receiveMsg(HashMap<Byte,Object> dataSet){
        Log.d("받았냐??", "컥");
        //받아온 데이터셋을 알맞은 자리에 출력을 해야겠찌
        if(dataSet.containsKey()){

        }
        for(String[] str: this.strings){
            if(str!=null){
                sb.append("오류코드 : " + str[0] + ", 한글 : " + str[1] + ", 영어 : " + str[2] +"\n");
            }
        }
        msg = Message.obtain();
        msg.obj=sb.toString();
        msgHandler.sendMessage(msg);
    }

    public void process() throws IOException{
        communicationManager.sendMsg();
    }
    public void test(){
        try{
            communicationManager.disconnect();}
        catch(Exception e){

        }
      /*  for(byte bt:bytes){
            sb.append(String.format("%02x ", bt & 0xff));
        }*/
        msg = Message.obtain();
        msg.obj=sb.toString();
        msgHandler.sendMessage(msg);
    }
    Handler msgHandler = new Handler(){
        public void handleMessage(Message msg){
            messageDisplay(msg.obj.toString());
        }
    };
    public void messageDisplay(String serverMsg){
        rcvMsg.setText(""+serverMsg);
    }
}
