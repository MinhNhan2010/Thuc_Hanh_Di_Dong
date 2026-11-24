package com.example.thuc_hanh_di_dong.buoi2;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 2 - Bài tập 07: Đăng nhập + CheckBox + AlertDialog Thoát
 */
public class Bai07AlertLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi2_bai07_alert_login);

        CheckBox chkSaveInfo = findViewById(R.id.chkSaveInfo);

        // Đăng nhập
        findViewById(R.id.btnDangNhap).setOnClickListener(v -> {
            if (chkSaveInfo.isChecked()) {
                Toast.makeText(this, "Chào mừng bạn đăng nhập hệ thống, bạn đã lưu thông tin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Chào mừng bạn đăng nhập hệ thống, bạn không lưu thông tin", Toast.LENGTH_LONG).show();
            }
        });

        // Thoát - Alert Dialog
        findViewById(R.id.btnThoat).setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Xác nhận thoát")
                        .setMessage("Bạn có chắc chắn muốn thoát?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", (dialog, which) -> finish())
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .setCancelable(false)
                        .show());
    }
}
