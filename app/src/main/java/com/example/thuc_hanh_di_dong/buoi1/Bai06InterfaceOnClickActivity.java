package com.example.thuc_hanh_di_dong.buoi1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 1 - Bài 06: Kế thừa Interface OnClickListener
 */
public class Bai06InterfaceOnClickActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi1_bai06_interface_onclick);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnLogin.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == null) return;
        int id = v.getId();
        if (id == R.id.btnLogin) {
            Toast.makeText(
                    getApplication(),
                    "Bạn đang Click vào Button Login",
                    Toast.LENGTH_LONG
            ).show();
        } else if (id == R.id.btnLogout) {
            Toast.makeText(
                    getApplication(),
                    "Bạn đang Click vào Button Logout",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
