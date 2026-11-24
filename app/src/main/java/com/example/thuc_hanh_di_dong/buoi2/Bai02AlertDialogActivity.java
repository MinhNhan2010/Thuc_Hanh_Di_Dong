package com.example.thuc_hanh_di_dong.buoi2;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 2 - Bài tập 02: Alert Dialog (Hộp thoại cảnh báo) trong Android
 */
public class Bai02AlertDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi2_bai02_alert_dialog);

        findViewById(R.id.button).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận để thoát..!!!");
            builder.setMessage("Bạn có muốn thoát?");
            builder.setCancelable(false);

            builder.setPositiveButton("Đồng ý", (dialog, which) -> finish());

            builder.setNegativeButton("Không đồng ý", (dialog, which) ->
                    Toast.makeText(this, "Bạn đã click vào nút không đồng ý", Toast.LENGTH_SHORT).show());

            builder.setNeutralButton("Hủy", (dialog, which) ->
                    Toast.makeText(this, "Bạn đã click vào nút hủy", Toast.LENGTH_SHORT).show());

            builder.create().show();
        });
    }
}
