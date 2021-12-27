package com.uratio.testdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPClientActivity extends AppCompatActivity {
    private TextView tvShowMsg;
    private EditText etText;

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private Socket mClientSocket;
    private PrintWriter mPrintWriter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    tvShowMsg.setText(tvShowMsg.getText() + (String)msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);

        tvShowMsg = findViewById(R.id.tv_show_msg);
        etText = findViewById(R.id.edit_text);

        Intent service = new Intent(this, TCPClientActivity.class);
        startService(service);
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectTCPServer();
            }
        }).start();
    }

    public void onClickView(View view) {
        //发送消息
        String sendMsg = etText.getText().toString();
        if (!"".equals(sendMsg) && mPrintWriter != null) {
            mPrintWriter.println(sendMsg);
            etText.setText("");
            String time = formatDateTime(System.currentTimeMillis());
            String showMsg = "self" + time + ":" + sendMsg + '\n';
            tvShowMsg.setText(tvShowMsg.getText() + showMsg);
        }
    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket(Utils.LOCALHOST, Utils.PORT);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                System.out.println("connect server success");
            } catch (IOException e) {
                e.printStackTrace();
                SystemClock.sleep(1000);
                System.out.println("connect tcp sever failed, retry...");
            }
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TCPClientActivity.this.isFinishing()) {
                String msg = br.readLine();
                System.out.println("receive :" + msg);
                if (msg != null) {
                    String time = formatDateTime(System.currentTimeMillis());
                    String showedMeg = "server" + time + ":" + msg + '\n';
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showedMeg).sendToTarget();
                }
                System.out.println("quit ...");
                //关闭流
                if (mPrintWriter != null) {
                    mPrintWriter.close();
                }
                br.close();
                socket.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String formatDateTime(long time) {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(time));
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
