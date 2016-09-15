package com.victor_xiao.handlertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView textView = (TextView) findViewById(R.id.txtContent);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 == -1)
                    textView.setText("FINISH");
                else
                    textView.setText(msg.arg1 + "");
            }
        };


        final Runnable myWorker = new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress < 10) {
                    Message msg = new Message();

                    Random rad = new Random();

                    msg.arg1 = rad.nextInt(100) + 1;
                    handler.sendMessage(msg);
                    progress += 1;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = handler.obtainMessage();//同 new Message();
                msg.arg1 = -1;
                handler.sendMessage(msg);
            }
        };

        Button button = (Button) findViewById(R.id.btnStart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread = new Thread(null, myWorker, "WorkThread");
                workThread.start();


            }
        });
    }
}
