package com.example.thuc_hanh_di_dong.buoi2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 2 - Bài tập 03: Thực hành các kiểu lập trình sự kiện trong Android
 * - Tổng: OnClick in XML
 * - Hiệu: Inline anonymous listener
 * - Tích: Activity is listener (implements OnClickListener)
 * - Thương: Listener in variable
 * - ƯCLN: Explicit listener class
 * - Thoát: View Subclassing / Lambda
 */
@SuppressLint("SetTextI18n")
public class Bai03EventTypesActivity extends Activity implements View.OnClickListener {

    private EditText edtA;
    private EditText edtB;
    private TextView tvKetQua;

    @Override
    @SuppressWarnings("Convert2Lambda")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi2_bai03_event_types);

        edtA = findViewById(R.id.edtSoA);
        edtB = findViewById(R.id.edtSoB);
        tvKetQua = findViewById(R.id.tvKetQua);

        // 2. Hiệu - Inline anonymous listener
        findViewById(R.id.btnHieu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] nums = getNumbers();
                if (nums == null) return;
                tvKetQua.setText("Kết quả: " + nums[0] + " - " + nums[1] + " = " + (nums[0] - nums[1]));
            }
        });

        // 3. Tích - Activity is listener
        findViewById(R.id.btnTich).setOnClickListener(this);

        // 4. Thương - Listener in variable
        View.OnClickListener thuongListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] nums = getNumbers();
                if (nums == null) return;
                if (nums[1] == 0) {
                    Toast.makeText(Bai03EventTypesActivity.this, "Không thể chia cho 0!", Toast.LENGTH_SHORT).show();
                } else {
                    double kq = (double) nums[0] / nums[1];
                    tvKetQua.setText("Kết quả: " + nums[0] + " / " + nums[1] + " = " + kq);
                }
            }
        };
        findViewById(R.id.btnThuong).setOnClickListener(thuongListener);

        // 5. ƯCLN - Explicit listener class
        findViewById(R.id.btnUCSLN).setOnClickListener(new UclnClickListener());

        // 6. Thoát
        findViewById(R.id.btnThoat).setOnClickListener(v -> finish());
    }

    // 1. Tổng - OnClick in XML
    public void onClickTong(View v) {
        int[] nums = getNumbers();
        if (nums == null) return;
        tvKetQua.setText("Kết quả: " + nums[0] + " + " + nums[1] + " = " + (nums[0] + nums[1]));
    }

    // 3. Tích - Activity is listener (OnClickListener)
    @Override
    public void onClick(View v) {
        if (v != null && v.getId() == R.id.btnTich) {
            int[] nums = getNumbers();
            if (nums == null) return;
            tvKetQua.setText("Kết quả: " + nums[0] + " × " + nums[1] + " = " + (nums[0] * nums[1]));
        }
    }

    // 5. ƯCLN - Explicit listener class
    private class UclnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int[] nums = getNumbers();
            if (nums == null) return;
            int ucln = gcd(nums[0], nums[1]);
            tvKetQua.setText("Kết quả: ƯCLN(" + nums[0] + ", " + nums[1] + ") = " + ucln);
        }

        private int gcd(int a, int b) {
            int x = Math.abs(a);
            int y = Math.abs(b);
            while (y != 0) {
                int temp = y;
                y = x % y;
                x = temp;
            }
            return x;
        }
    }

    private int[] getNumbers() {
        String aStr = edtA.getText().toString().trim();
        String bStr = edtB.getText().toString().trim();
        if (aStr.isEmpty() || bStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ 2 số!", Toast.LENGTH_SHORT).show();
            return null;
        }
        try {
            return new int[]{Integer.parseInt(aStr), Integer.parseInt(bStr)};
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số nhập vào không hợp lệ!", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
