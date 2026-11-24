package com.example.thuc_hanh_di_dong.buoi3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 3 - Bài tập 17: Thêm Album (hiển thị dạng Dialog)
 */
public class Bai17AddAlbumActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_DeviceDefault_Light_Dialog);
        setContentView(R.layout.buoi3_bai17_02_add_album);

        EditText edtMa = findViewById(R.id.edtMaAlbum);
        EditText edtTen = findViewById(R.id.edtTenAlbum);
        EditText edtCaSi = findViewById(R.id.edtCaSi);

        // Lưu Album
        findViewById(R.id.btnLuuAlbum).setOnClickListener(v -> {
            String ma = edtMa.getText().toString().trim();
            String ten = edtTen.getText().toString().trim();
            String casi = edtCaSi.getText().toString().trim();

            if (ma.isEmpty() || ten.isEmpty() || casi.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            for (Album a : Bai17Data.albums) {
                if (a.getMa().equals(ma)) {
                    Toast.makeText(this, "Mã Album đã tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Bai17Data.albums.add(new Album(ma, ten, casi));
            Toast.makeText(this, "Đã lưu Album: " + ten, Toast.LENGTH_SHORT).show();

            edtMa.getText().clear();
            edtTen.getText().clear();
            edtCaSi.getText().clear();
            edtMa.requestFocus();
        });

        // Xóa trắng
        findViewById(R.id.btnXoaTrang).setOnClickListener(v -> {
            edtMa.getText().clear();
            edtTen.getText().clear();
            edtCaSi.getText().clear();
            edtMa.requestFocus();
        });
    }
}
