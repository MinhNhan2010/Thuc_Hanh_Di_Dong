package com.example.thuc_hanh_di_dong.buoi1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 1 - Bài 05: Xử lí sự kiện bằng lớp nặc danh (Inline anonymous listener)
 */
public class Bai05AnonymousListenerActivity extends Activity {

    @Override
    @SuppressWarnings("Convert2Lambda")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi1_bai05_anonymous_listener);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        getApplication(),
                        "Bạn đang Click vào Button Login",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        getApplication(),
                        "Bạn đang Click vào Button Logout",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}
