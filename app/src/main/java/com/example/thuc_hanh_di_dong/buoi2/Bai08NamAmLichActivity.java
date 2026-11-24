package com.example.thuc_hanh_di_dong.buoi2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 2 - Bài tập 08: Đổi năm Dương lịch qua năm Âm lịch
 * Can = Năm dương % 10, Chi = Năm dương % 12
 */
@SuppressLint("SetTextI18n")
public class Bai08NamAmLichActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi2_bai08_nam_am_lich);

        EditText edtNam = findViewById(R.id.edtNamDuong);
        TextView tvKetQua = findViewById(R.id.tvKetQua);

        findViewById(R.id.btnChuyenDoi).setOnClickListener(v -> {
            String input = edtNam.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập năm!", Toast.LENGTH_SHORT).show();
                return;
            }
            int nam;
            try {
                nam = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Năm nhập vào không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (nam < 1900) {
                Toast.makeText(this, "Năm phải >= 1900!", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] dsCan = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
            String[] dsChi = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi"};

            String can = dsCan[nam % 10];
            String chi = dsChi[nam % 12];

            tvKetQua.setText("Năm Âm lịch: " + can + " " + chi);
        });
    }
}
