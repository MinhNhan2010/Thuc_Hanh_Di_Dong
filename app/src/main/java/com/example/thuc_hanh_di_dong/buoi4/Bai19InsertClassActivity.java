package com.example.thuc_hanh_di_dong.buoi4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.Locale;

/**
 * Buổi 4: Thêm lớp học → SQLite
 */
public class Bai19InsertClassActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi4_bai19_04_insert_class);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        EditText edtCode = findViewById(R.id.edtClassCode);
        EditText edtName = findViewById(R.id.edtClassName);
        EditText edtNumber = findViewById(R.id.edtClassNumber);

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String ma = edtCode.getText().toString().trim().toUpperCase(Locale.ROOT);
            String ten = edtName.getText().toString().trim();
            String siSoStr = edtNumber.getText().toString().trim();

            if (ma.isEmpty() || ten.isEmpty() || siSoStr.isEmpty()) {
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

            LopHoc lop = new LopHoc(ma, ten, siSo);
            long result = dbHelper.insertClass(lop);
            if (result > 0) {
                Toast.makeText(this, "Thêm lớp " + ma + " thành công!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Mã lớp '" + ma + "' đã tồn tại!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            edtCode.getText().clear();
            edtName.getText().clear();
            edtNumber.getText().clear();
            edtCode.requestFocus();
        });

        findViewById(R.id.btnClose).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
