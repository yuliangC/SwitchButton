package com.example.silence.switchbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.library.SwitchButton;

public class MainActivity extends AppCompatActivity {

    private SwitchButton switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchButton=findViewById(R.id.switchButton);
        switchButton.setListener(new SwitchButton.OnSwitchCheckListener() {
            @Override
            public void checkButton(int tabIndex, String selectedString) {
                String string="选中了第"+tabIndex+"个tab，选中文字为："+selectedString;
                Toast.makeText(MainActivity.this,string,Toast.LENGTH_SHORT).show();
            }
        });







    }




}
