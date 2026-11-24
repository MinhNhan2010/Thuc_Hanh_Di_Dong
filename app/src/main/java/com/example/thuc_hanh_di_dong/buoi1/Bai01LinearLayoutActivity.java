package com.example.thuc_hanh_di_dong.buoi1;

import android.app.Activity;
import android.os.Bundle;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 1 - Bài 01: LinearLayout
 * - Vertical / Horizontal orientation
 * - layout_weight không có weightSum
 * - layout_weight có weightSum
 */
public class Bai01LinearLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi1_bai01_linear_layout);
    }
}
