package com.wurq.dex.hotfix;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textview);

        String s = "SDK:" + Build.VERSION.SDK_INT +
                "\nABI:" + Build.CPU_ABI +
                "\nABI2:" + Build.CPU_ABI2 + "\n";
        textView.setText(s);

        bugTest bugClass = new bugTest();
        textView.append("\n" + bugClass.bugshow());
    }
}
