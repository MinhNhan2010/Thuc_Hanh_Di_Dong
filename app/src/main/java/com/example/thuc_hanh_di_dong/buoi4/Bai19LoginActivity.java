package com.example.thuc_hanh_di_dong.buoi4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 4: Login Activity
 * - Đăng nhập bằng SQLite (kiểm tra bảng User)
 * - Ghi nhớ đăng nhập bằng SharedPreferences
 * - Đăng ký tài khoản mới trực tiếp
 */
public class Bai19LoginActivity extends Activity {

    public static final String PREFS_NAME = "QLSV_PREFS";
    public static final String KEY_REMEMBER = "remember";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CURRENT_USER = "current_user";

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi4_bai19_01_login);

        dbHelper = new DatabaseHelper(this);
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        EditText edtUsername = findViewById(R.id.edtUsername);
        EditText edtPassword = findViewById(R.id.edtPassword);
        CheckBox chkRememberMe = findViewById(R.id.chkRememberMe);

        boolean isRemembered = prefs.getBoolean(KEY_REMEMBER, true);
        chkRememberMe.setChecked(isRemembered);
        if (isRemembered) {
            edtUsername.setText(prefs.getString(KEY_USERNAME, "admin"));
            edtPassword.setText(prefs.getString(KEY_PASSWORD, "admin"));
        } else {
            edtUsername.setText("");
            edtPassword.setText("");
        }

        // Đăng nhập
        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.checkUser(user, pass)) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(KEY_REMEMBER, chkRememberMe.isChecked());
                if (chkRememberMe.isChecked()) {
                    editor.putString(KEY_USERNAME, user);
                    editor.putString(KEY_PASSWORD, pass);
                } else {
                    editor.remove(KEY_USERNAME);
                    editor.remove(KEY_PASSWORD);
                }
                editor.putString(KEY_CURRENT_USER, user);
                editor.apply();

                Toast.makeText(this, "Đăng nhập thành công! Xin chào " + user, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Bai19MainMenuActivity.class);
                intent.putExtra("username", user);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });

        // Đăng ký tài khoản mới
        findViewById(R.id.btnRegister).setOnClickListener(v ->
                showRegisterDialog(edtUsername, edtPassword));

        // Đóng
        findViewById(R.id.btnCloseLogin).setOnClickListener(v -> finish());
    }

    private void showRegisterDialog(EditText edtLoginUser, EditText edtLoginPass) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(48, 24, 48, 8);

        EditText edtNewUser = new EditText(this);
        edtNewUser.setHint("Nhập tên tài khoản mới (tối thiểu 3 ký tự)");

        EditText edtNewPass = new EditText(this);
        edtNewPass.setHint("Nhập mật khẩu (tối thiểu 4 ký tự)");
        edtNewPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        EditText edtConfirmPass = new EditText(this);
        edtConfirmPass.setHint("Nhập lại mật khẩu");
        edtConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        layout.addView(edtNewUser);
        layout.addView(edtNewPass);
        layout.addView(edtConfirmPass);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("📝 Đăng ký tài khoản mới")
                .setView(layout)
                .setPositiveButton("Đăng ký", null)
                .setNegativeButton("Hủy", null)
                .create();

        dialog.setOnShowListener(d -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String newUser = edtNewUser.getText().toString().trim();
            String newPass = edtNewPass.getText().toString().trim();
            String confirmPass = edtConfirmPass.getText().toString().trim();

            if (newUser.length() < 3) {
                Toast.makeText(this, "Tài khoản phải có ít nhất 3 ký tự!", Toast.LENGTH_SHORT).show();
            } else if (newPass.length() < 4) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 4 ký tự!", Toast.LENGTH_SHORT).show();
            } else if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
            } else if (dbHelper.registerUser(newUser, newPass)) {
                Toast.makeText(this, "Đăng ký tài khoản '" + newUser + "' thành công!", Toast.LENGTH_SHORT).show();
                edtLoginUser.setText(newUser);
                edtLoginPass.setText(newPass);
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Tài khoản '" + newUser + "' đã tồn tại!", Toast.LENGTH_SHORT).show();
            }
        }));
        dialog.show();
    }
}
