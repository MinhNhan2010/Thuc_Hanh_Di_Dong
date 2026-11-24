package com.example.thuc_hanh_di_dong.buoi2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Buổi 2 - Bài tập 15: Spinner + ListView
 * Chọn danh mục trong Spinner → hiển thị sản phẩm trong ListView
 */
public class Bai15SpinnerActivity extends Activity {

    private final ArrayList<String> danhMuc = new ArrayList<>(Arrays.asList(
            "Điện thoại", "Laptop", "Tablet", "Phụ kiện"
    ));

    private final Map<String, ArrayList<String>> sanPham = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi2_bai15_spinner);

        sanPham.put("Điện thoại", new ArrayList<>(Arrays.asList(
                "Samsung Galaxy S24", "iPhone 15 Pro", "Xiaomi 14",
                "OPPO Find X7", "Google Pixel 8"
        )));
        sanPham.put("Laptop", new ArrayList<>(Arrays.asList(
                "MacBook Pro M3", "Dell XPS 15", "ThinkPad X1 Carbon",
                "ASUS ROG Strix", "HP Spectre x360"
        )));
        sanPham.put("Tablet", new ArrayList<>(Arrays.asList(
                "iPad Pro M2", "Samsung Tab S9", "Xiaomi Pad 6",
                "Lenovo Tab P12"
        )));
        sanPham.put("Phụ kiện", new ArrayList<>(Arrays.asList(
                "AirPods Pro 2", "Samsung Buds FE", "Cáp sạc Type-C",
                "Ốp lưng iPhone", "Bàn phím Bluetooth", "Sạc dự phòng 20000mAh"
        )));

        Spinner spnDanhMuc = findViewById(R.id.spnDanhMuc);
        ListView lvSanPham = findViewById(R.id.lvSanPham);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhMuc);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDanhMuc.setAdapter(spinnerAdapter);

        spnDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDanhMuc = danhMuc.get(position);
                ArrayList<String> dsSanPham = sanPham.get(selectedDanhMuc);
                if (dsSanPham == null) {
                    dsSanPham = new ArrayList<>();
                }
                ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                        Bai15SpinnerActivity.this,
                        android.R.layout.simple_list_item_1,
                        dsSanPham
                );
                lvSanPham.setAdapter(listAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lvSanPham.setOnItemClickListener((parent, view, position, id) -> {
            String selectedDanhMuc = danhMuc.get(spnDanhMuc.getSelectedItemPosition());
            ArrayList<String> ds = sanPham.get(selectedDanhMuc);
            String selectedSP = (ds != null && position < ds.size()) ? ds.get(position) : "";
            Toast.makeText(
                    this,
                    "Danh mục: " + selectedDanhMuc + "\nSản phẩm: " + selectedSP,
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}
