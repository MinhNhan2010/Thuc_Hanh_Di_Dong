package com.example.thuc_hanh_di_dong.buoi2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.Locale;

/**
 * Buổi 2 - Bài tập 04: Chuyển đổi nhiệt độ C ↔ F
 * C → F: C × 9/5 + 32
 * F → C: (F - 32) × 5/9
 */
@SuppressLint("SetTextI18n")
public class Bai04TemperatureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi2_bai04_temperature);

        EditText edtNhietDo = findViewById(R.id.edtNhietDo);
        TextView tvKetQua = findViewById(R.id.tvKetQua);

        // Convert to Celsius (F → C)
        findViewById(R.id.btnToC).setOnClickListener(v -> {
            String input = edtNhietDo.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập nhiệt độ!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double f = Double.parseDouble(input);
                double c = (f - 32) * 5.0 / 9.0;
                tvKetQua.setText(String.format(Locale.US, "Kết quả: %.2f°F = %.2f°C", f, c));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Nhiệt độ không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });

        // Convert to Fahrenheit (C → F)
        findViewById(R.id.btnToF).setOnClickListener(v -> {
            String input = edtNhietDo.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập nhiệt độ!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double c = Double.parseDouble(input);
                double f = c * 9.0 / 5.0 + 32;
                tvKetQua.setText(String.format(Locale.US, "Kết quả: %.2f°C = %.2f°F", c, f));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Nhiệt độ không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });

        // Clear
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            edtNhietDo.getText().clear();
            tvKetQua.setText("Kết quả: ");
            edtNhietDo.requestFocus();
        });
    }
}
