package ttzg.com.smssenderdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ttzg.com.smssenderview.GetCaptchaButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetCaptchaButton g = findViewById(R.id.btn_getcaptcha);
        //g.setPhone("111111111111");
    }
}
