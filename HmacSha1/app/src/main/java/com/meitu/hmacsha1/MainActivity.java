package com.meitu.hmacsha1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.meitu.library.hmacsha1.HmacSHA1Sign;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_sign = findViewById(R.id.tv_sign);

        tv_sign.setText("" + HmacSHA1Sign.getSignResult("rF5GIhp5ReLKgLV91CKj5BO1q2FTLMmc", "5wGaQX_jsau6lcWGuENJHXn8zpWH8coY", System.currentTimeMillis() / 1000));

    }
}
