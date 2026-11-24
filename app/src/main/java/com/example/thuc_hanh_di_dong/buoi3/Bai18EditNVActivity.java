package com.example.thuc_hanh_di_dong.buoi3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 3 - Bài tập 18: Thêm/Sửa nhân viên
 * Nhận mode (add/edit) và dữ liệu từ Intent
 * Trả kết quả về cho Activity gọi
 */
@SuppressLint("SetTextI18n")
public class Bai18EditNVActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi3_bai18_02_edit_nv);

        TextView tvTitle = findViewById(R.id.tvTitle);
        EditText edtMa = findViewById(R.id.edtMaNV);
        EditText edtHoTen = findViewById(R.id.edtHoTen);
        EditText edtChucVu = findViewById(R.id.edtChucVu);
        EditText edtLuong = findViewById(R.id.edtLuong);
        RadioGroup rgPhongBan = findViewById(R.id.rgPhongBan);

        String mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "add";
        int position = getIntent().getIntExtra("position", -1);

        if ("edit".equals(mode)) {
            tvTitle.setText("Chỉnh sửa nhân viên");
            edtMa.setText(getIntent().getStringExtra("ma"));
            edtMa.setEnabled(false);
            edtHoTen.setText(getIntent().getStringExtra("hoten"));
            edtChucVu.setText(getIntent().getStringExtra("chucvu"));
            edtLuong.setText(String.valueOf(getIntent().getLongExtra("luong", 0)));

            String pb = getIntent().getStringExtra("phongban");
            if ("IT".equals(pb)) {
                rgPhongBan.check(R.id.rbIT);
            } else if ("Kế toán".equals(pb)) {
                rgPhongBan.check(R.id.rbKeToan);
            } else if ("Nhân sự".equals(pb)) {
                rgPhongBan.check(R.id.rbNhanSu);
            } else if ("Kinh doanh".equals(pb)) {
                rgPhongBan.check(R.id.rbKinhDoanh);
            }
        }

        findViewById(R.id.btnLuu).setOnClickListener(v -> {
            String ma = edtMa.getText().toString().trim();
            String hoTen = edtHoTen.getText().toString().trim();
            String chucVu = edtChucVu.getText().toString().trim();
            String luongStr = edtLuong.getText().toString().trim();

            if (ma.isEmpty() || hoTen.isEmpty() || chucVu.isEmpty() || luongStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            long luong = 0L;
            try {
                luong = Long.parseLong(luongStr);
            } catch (NumberFormatException ignored) {
            }

            String phongBan;
            int checkedId = rgPhongBan.getCheckedRadioButtonId();
            if (checkedId == R.id.rbKeToan) {
                phongBan = "Kế toán";
            } else if (checkedId == R.id.rbNhanSu) {
                phongBan = "Nhân sự";
            } else if (checkedId == R.id.rbKinhDoanh) {
                phongBan = "Kinh doanh";
            } else {
                phongBan = "IT";
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("ma", ma);
            resultIntent.putExtra("hoten", hoTen);
            resultIntent.putExtra("chucvu", chucVu);
            resultIntent.putExtra("luong", luong);
            resultIntent.putExtra("phongban", phongBan);
            resultIntent.putExtra("position", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        findViewById(R.id.btnXoa).setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc muốn hủy?")
                        .setPositiveButton("Có", (dialog, which) -> {
                            setResult(RESULT_CANCELED);
                            finish();
                        })
                        .setNegativeButton("Không", null)
                        .show());
    }
}
