package com.maplejaw.emojikeyboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void btnClick(View view){
        switch (view.getId()){
            case R.id.btn_single:
                startActivity(new Intent(this,SingleActivity.class));
                break;
            case R.id.btn_mutil:
                startActivity(new Intent(this,MutilActivity.class));
                break;
        }
    }

}
