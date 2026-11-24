package com.example.thuc_hanh_di_dong.buoi2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 2 - Bài tập 11: ListView - Sử dụng String-Array, ArrayAdapter
 */
@SuppressLint("SetTextI18n")
public class Bai11ListViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi2_bai11_listview);

        TextView tvSelected = findViewById(R.id.tvSelected);
        ListView lvMonHoc = findViewById(R.id.lvMonHoc);

        String[] monHocArray = getResources().getStringArray(R.array.mon_hoc_array);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, monHocArray);
        lvMonHoc.setAdapter(adapter);

        lvMonHoc.setOnItemClickListener((parent, view, position, id) ->
                tvSelected.setText("Vị trí: " + position + " - Giá trị: " + monHocArray[position]));
    }
}
