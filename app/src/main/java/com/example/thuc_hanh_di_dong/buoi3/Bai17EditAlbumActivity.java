package com.example.thuc_hanh_di_dong.buoi3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 3 - Bài tập 17: Chỉnh sửa Album
 * Nhận dữ liệu từ Intent, trả kết quả về Activity trước
 */
public class Bai17EditAlbumActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_DeviceDefault_Light_Dialog);
        setContentView(R.layout.buoi3_bai17_04_edit_album);

        EditText edtMa = findViewById(R.id.edtMaAlbum);
        EditText edtTen = findViewById(R.id.edtTenAlbum);
        EditText edtCaSi = findViewById(R.id.edtCaSi);

        int position = getIntent().getIntExtra("position", -1);
        edtMa.setText(getIntent().getStringExtra("ma"));
        edtTen.setText(getIntent().getStringExtra("ten"));
        edtCaSi.setText(getIntent().getStringExtra("casi"));

        findViewById(R.id.btnUpdate).setOnClickListener(v -> {
            String ten = edtTen.getText().toString().trim();
            String casi = edtCaSi.getText().toString().trim();
            if (ten.isEmpty() || casi.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("position", position);
            resultIntent.putExtra("ten", ten);
            resultIntent.putExtra("casi", casi);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
