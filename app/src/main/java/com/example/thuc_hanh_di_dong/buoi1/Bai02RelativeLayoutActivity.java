package com.example.thuc_hanh_di_dong.buoi1;

import android.app.Activity;
import android.os.Bundle;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 1 - Bài 02: RelativeLayout
 * - layout_below: view nằm dưới view khác
 * - layout_toRightOf: view nằm bên phải view khác
 * - layout_alignParentRight: căn phải theo parent
 */
public class Bai02RelativeLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi1_bai02_relative_layout);
    }
}
