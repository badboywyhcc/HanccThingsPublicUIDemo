package com.developer.hancc.hanccthingspublicuidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.developer.hancc.hanccthingspublicuisdk.HanccThingsPublicToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toastBtn = (Button)findViewById(R.id.toastBtn);
        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                HanccThingsPublicToast.showToast(MainActivity.this,"回家过年了");
//                HanccThingsPublicToast.showToast(MainActivity.this,"回家过年了",R.mipmap.ic_launcher_round);
                HanccThingsPublicToast.showToastInThread(MainActivity.this,"走走走,去逛去");
            }
        });
    }
}
