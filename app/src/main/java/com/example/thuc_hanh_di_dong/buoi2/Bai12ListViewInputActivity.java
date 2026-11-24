package com.example.thuc_hanh_di_dong.buoi2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;

/**
 * Buổi 2 - Bài tập 12: ListView - ArrayList, ArrayAdapter, notifyDataSetChanged
 */
@SuppressLint("SetTextI18n")
public class Bai12ListViewInputActivity extends Activity {

    private final ArrayList<String> danhSach = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi2_bai12_listview_input);

        EditText edtNhapTen = findViewById(R.id.edtNhapTen);
        TextView tvSelected = findViewById(R.id.tvSelected);
        ListView lvDanhSach = findViewById(R.id.lvDanhSach);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, danhSach);
        lvDanhSach.setAdapter(adapter);

        findViewById(R.id.btnNhap).setOnClickListener(v -> {
            String ten = edtNhapTen.getText().toString().trim();
            if (ten.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                return;
            }
            danhSach.add(ten);
            adapter.notifyDataSetChanged();
            edtNhapTen.getText().clear();
            edtNhapTen.requestFocus();
        });

        lvDanhSach.setOnItemClickListener((parent, view, position, id) ->
                tvSelected.setText("Vị trí: " + position + " - Giá trị: " + danhSach.get(position)));
    }
}
