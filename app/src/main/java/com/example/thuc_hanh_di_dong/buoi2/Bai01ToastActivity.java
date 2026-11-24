package com.example.thuc_hanh_di_dong.buoi2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 2 - Bài tập 01: Toast và tùy chỉnh Toast trong Android
 */
public class Bai01ToastActivity extends Activity {

    @Override
    @SuppressLint("SetTextI18n")
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi2_bai01_toast);

        // Toast SHORT
        findViewById(R.id.btnToastShort).setOnClickListener(v ->
                Toast.makeText(this, "Ví dụ Toast SHORT - 2 giây", Toast.LENGTH_SHORT).show());

        // Toast LONG
        findViewById(R.id.btnToastLong).setOnClickListener(v ->
                Toast.makeText(this, "Ví dụ Toast LONG - 3.5 giây", Toast.LENGTH_LONG).show());

        // Toast tùy chỉnh (Custom)
        findViewById(R.id.btnToastCustom).setOnClickListener(v -> {
            LayoutInflater inflater = getLayoutInflater();
            ViewGroup root = findViewById(android.R.id.content);
            View layout = inflater.inflate(R.layout.buoi2_bai01_item_toast, root, false);
            TextView txtMsg = layout.findViewById(R.id.txtToastMessage);
            txtMsg.setText("🎉 Đây là Toast tùy chỉnh!");

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(layout);
            toast.show();
        });

        // Toast ở trên (TOP)
        findViewById(R.id.btnToastTop).setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, "Toast hiển thị ở TOP", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        });

        // Toast ở giữa (CENTER)
        findViewById(R.id.btnToastCenter).setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, "Toast hiển thị ở CENTER", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
    }
}
