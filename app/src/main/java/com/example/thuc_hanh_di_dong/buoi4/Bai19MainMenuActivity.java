package com.example.thuc_hanh_di_dong.buoi4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.Locale;

/**
 * Buổi 4: Menu chính sau đăng nhập
 * - Thống kê tổng quan trực tiếp (Lớp, Sinh viên, Nam/Nữ, Điểm TB)
 * - QL Lớp học, QL Sinh viên
 * - Đổi mật khẩu, Đăng xuất, Thoát
 */
@SuppressLint("SetTextI18n")
public class Bai19MainMenuActivity extends Activity {

    private DatabaseHelper dbHelper;
    private TextView tvStatsSummary;
    private String currentUsername = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi4_bai19_02_main_menu);

        dbHelper = new DatabaseHelper(this);
        tvStatsSummary = findViewById(R.id.tvStatsSummary);
        TextView tvWelcomeUser = findViewById(R.id.tvWelcomeUser);

        SharedPreferences prefs = getSharedPreferences(Bai19LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        String extraUser = getIntent().getStringExtra("username");
        if (extraUser != null && !extraUser.isEmpty()) {
            currentUsername = extraUser;
        } else {
            currentUsername = prefs.getString(Bai19LoginActivity.KEY_CURRENT_USER, "admin");
        }

        tvWelcomeUser.setText("👤 Tài khoản đang đăng nhập: " + currentUsername);

        // Mở QL Lớp học
        findViewById(R.id.btnOpenClass).setOnClickListener(v ->
                startActivity(new Intent(this, Bai19ClassListActivity.class)));

        // Mở QL Sinh viên
        findViewById(R.id.btnOpenStudent).setOnClickListener(v ->
                startActivity(new Intent(this, Bai19StudentListActivity.class)));

        // Đổi mật khẩu
        findViewById(R.id.btnChangePassword).setOnClickListener(v -> showChangePasswordDialog());

        // Đăng xuất
        findViewById(R.id.btnLogout).setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có muốn đăng xuất khỏi tài khoản '" + currentUsername + "'?")
                        .setPositiveButton("Đăng xuất", (dialog, which) -> {
                            startActivity(new Intent(this, Bai19LoginActivity.class));
                            finish();
                        })
                        .setNegativeButton("Hủy", null)
                        .show());

        // Thoát
        findViewById(R.id.btnExitApp).setOnClickListener(v -> Notify.exit(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDashboardStats();
    }

    private void updateDashboardStats() {
        DatabaseHelper.DashboardStats stats = dbHelper.getDashboardStats();
        String gpaStr = String.format(Locale.US, "%.2f", stats.avgGpa);
        tvStatsSummary.setText(
                "• Tổng số lớp học: " + stats.totalClasses + " lớp\n"
                + "• Tổng số sinh viên: " + stats.totalStudents + " SV (Nam: " + stats.maleCount + " | Nữ: " + stats.femaleCount + ")\n"
                + "• Điểm TB toàn trường: " + gpaStr + "\n"
                + "• Sinh viên Giỏi / Xuất sắc: " + stats.goodExcellentCount + " SV"
        );
    }

    private void showChangePasswordDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(48, 24, 48, 8);

        EditText edtOldPass = new EditText(this);
        edtOldPass.setHint("Nhập mật khẩu hiện tại");
        edtOldPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        EditText edtNewPass = new EditText(this);
        edtNewPass.setHint("Nhập mật khẩu mới (tối thiểu 4 ký tự)");
        edtNewPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        EditText edtConfirmPass = new EditText(this);
        edtConfirmPass.setHint("Nhập lại mật khẩu mới");
        edtConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        layout.addView(edtOldPass);
        layout.addView(edtNewPass);
        layout.addView(edtConfirmPass);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("🔑 Đổi mật khẩu (" + currentUsername + ")")
                .setView(layout)
                .setPositiveButton("Cập nhật", null)
                .setNegativeButton("Hủy", null)
                .create();

        dialog.setOnShowListener(d -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String oldPass = edtOldPass.getText().toString().trim();
            String newPass = edtNewPass.getText().toString().trim();
            String confirmPass = edtConfirmPass.getText().toString().trim();

            if (oldPass.isEmpty() || newPass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ mật khẩu!", Toast.LENGTH_SHORT).show();
            } else if (newPass.length() < 4) {
                Toast.makeText(this, "Mật khẩu mới phải có ít nhất 4 ký tự!", Toast.LENGTH_SHORT).show();
            } else if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
            } else if (dbHelper.changePassword(currentUsername, oldPass, newPass)) {
                SharedPreferences prefs = getSharedPreferences(Bai19LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
                if (prefs.getBoolean(Bai19LoginActivity.KEY_REMEMBER, true)
                        && currentUsername.equals(prefs.getString(Bai19LoginActivity.KEY_USERNAME, ""))) {
                    prefs.edit().putString(Bai19LoginActivity.KEY_PASSWORD, newPass).apply();
                }
                Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Mật khẩu hiện tại không đúng!", Toast.LENGTH_SHORT).show();
            }
        }));
        dialog.show();
    }
}
