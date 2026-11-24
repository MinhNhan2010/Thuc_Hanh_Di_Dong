package com.example.thuc_hanh_di_dong.buoi4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 4: Sửa lớp học → SQLite
 * Nhận dữ liệu từ Intent, update DB, trả result
 */
@SuppressLint("SetTextI18n")
public class Bai19EditClassActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi4_bai19_04_insert_class);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        TextView txtLabel = findViewById(R.id.txtClassLabel);
        EditText edtCode = findViewById(R.id.edtClassCode);
        EditText edtName = findViewById(R.id.edtClassName);
        EditText edtNumber = findViewById(R.id.edtClassNumber);
        Button btnSave = findViewById(R.id.btnSave);

        txtLabel.setText("Cập nhật lớp học");
        btnSave.setText("✅ Cập nhật");

        String maLop = getIntent().getStringExtra("maLop");
        if (maLop == null) maLop = "";
        int soSVHienTai = getIntent().getIntExtra("soSVHienTai", 0);

        edtCode.setText(maLop);
        edtCode.setEnabled(false);
        edtName.setText(getIntent().getStringExtra("tenLop"));
        edtNumber.setText(String.valueOf(getIntent().getIntExtra("siSo", 0)));

        final String finalMaLop = maLop;
        btnSave.setOnClickListener(v -> {
            String ten = edtName.getText().toString().trim();
            String siSoStr = edtNumber.getText().toString().trim();

            if (ten.isEmpty() || siSoStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            int siSo = 0;
            try {
                siSo = Integer.parseInt(siSoStr);
            } catch (NumberFormatException ignored) {
            }

            if (siSo <= 0) {
                Toast.makeText(this, "Sĩ số tối đa phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (siSo < soSVHienTai) {
                Toast.makeText(this, "Sĩ số tối đa (" + siSo + ") không được nhỏ hơn số SV hiện có trong lớp (" + soSVHienTai + ")!", Toast.LENGTH_LONG).show();
                return;
            }

            LopHoc lop = new LopHoc(finalMaLop, ten, siSo);
            dbHelper.updateClass(lop);
            Toast.makeText(this, "Cập nhật lớp " + finalMaLop + " thành công!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            edtName.getText().clear();
            edtNumber.getText().clear();
            edtName.requestFocus();
        });

        findViewById(R.id.btnClose).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
