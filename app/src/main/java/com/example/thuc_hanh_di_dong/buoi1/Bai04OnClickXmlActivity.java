package com.example.thuc_hanh_di_dong.buoi1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 1 - Bài 04: Xử lý sự kiện trong Layout (Handle event in Layout)
 * Trong file layout: android:onClick="clickMe"
 */
public class Bai04OnClickXmlActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi1_bai04_onclick_xml);
    }

    public void clickMe(View v) {
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
