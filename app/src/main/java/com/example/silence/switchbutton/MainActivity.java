package com.example.silence.switchbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.library.SwitchButton;

public class MainActivity extends AppCompatActivity {

    private SwitchButton switchButton,switchButton2,switchButton3;

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

        switchButton2=findViewById(R.id.switchButton2);
        switchButton2.setTabTexts("第一项","第二项","第三项","第四项");
        switchButton3=findViewById(R.id.switchButton3);
        switchButton3.setTabTexts("hello","world");






    }




}
