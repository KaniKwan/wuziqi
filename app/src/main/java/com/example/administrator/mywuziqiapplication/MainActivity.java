package com.example.administrator.mywuziqiapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WuziqiPanel mWuziqiPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("aaaabbb", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWuziqiPanel = (WuziqiPanel) findViewById(R.id.main_wuziqi);

        Button btnRestart = (Button) findViewById(R.id.main_btnReset);
        if (btnRestart != null) {
            btnRestart.setOnClickListener(this);
        }

    }

    @Override
    protected void onStop() {
        Log.i("aaaabbb", "onStop");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("aaaabbb", "onDestroy");

        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i("aaaabbb", "onRestart");

        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("aaaabbb", "onResume");

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("aaaabbb", "onPause");

        super.onPause();
    }

    @Override
    protected void onStart() {
        Log.i("aaaabbb", "onStart");

        super.onStart();
    }

    @Override
    public void onClick(View v) {
        mWuziqiPanel.reStart();
    }
}
