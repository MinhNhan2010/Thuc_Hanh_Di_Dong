package com.example.thuc_hanh_di_dong.buoi3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Buổi 3 - Bài tập 18: Quản lý nhân sự
 * - Explicit Intent: mở Activity thêm/sửa nhân viên
 * - startActivityForResult: lấy kết quả trả về
 * - ContextMenu: Chỉnh sửa, Xóa
 */
@SuppressLint("SetTextI18n")
public class Bai18NhanSuActivity extends Activity {

    public static final int REQUEST_ADD = 200;
    public static final int REQUEST_EDIT = 201;

    public static class NhanVien {
        public String ma;
        public String hoTen;
        public String chucVu;
        public long luong;
        public String phongBan;

        public NhanVien(String ma, String hoTen, String chucVu, long luong, String phongBan) {
            this.ma = ma;
            this.hoTen = hoTen;
            this.chucVu = chucVu;
            this.luong = luong;
            this.phongBan = phongBan;
        }

        @Override
        public String toString() {
            return "[" + ma + "] " + hoTen + "\n   Chức vụ: " + chucVu
                    + " | PB: " + phongBan + " | Lương: " + String.format(Locale.US, "%,d", luong) + " VNĐ";
        }
    }

    private final ArrayList<NhanVien> dsNhanVien = new ArrayList<>();
    private ArrayAdapter<NhanVien> adapter;
    private TextView tvTongNV;
    private int contextPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi3_bai18_01_nhan_su);

        tvTongNV = findViewById(R.id.tvTongNV);
        ListView lvNV = findViewById(R.id.lvNhanVien);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dsNhanVien);
        lvNV.setAdapter(adapter);

        findViewById(R.id.btnThemNV).setOnClickListener(v -> {
            Intent intent = new Intent(this, Bai18EditNVActivity.class);
            intent.putExtra("mode", "add");
            startActivityForResult(intent, REQUEST_ADD);
        });

        registerForContextMenu(lvNV);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (!(menuInfo instanceof AdapterView.AdapterContextMenuInfo)) return;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        contextPosition = info.position;
        if (menu != null && contextPosition >= 0 && contextPosition < dsNhanVien.size()) {
            menu.setHeaderTitle(dsNhanVien.get(contextPosition).hoTen);
            menu.add(0, 1, 0, "✏️ Chỉnh sửa");
            menu.add(0, 2, 1, "🗑️ Xóa nhân viên");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (contextPosition < 0 || contextPosition >= dsNhanVien.size()) {
            return super.onContextItemSelected(item);
        }
        int itemId = item.getItemId();
        if (itemId == 1) {
            NhanVien nv = dsNhanVien.get(contextPosition);
            Intent intent = new Intent(this, Bai18EditNVActivity.class);
            intent.putExtra("mode", "edit");
            intent.putExtra("position", contextPosition);
            intent.putExtra("ma", nv.ma);
            intent.putExtra("hoten", nv.hoTen);
            intent.putExtra("chucvu", nv.chucVu);
            intent.putExtra("luong", nv.luong);
            intent.putExtra("phongban", nv.phongBan);
            startActivityForResult(intent, REQUEST_EDIT);
        } else if (itemId == 2) {
            NhanVien nv = dsNhanVien.get(contextPosition);
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa nhân viên '" + nv.hoTen + "'?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        dsNhanVien.remove(contextPosition);
                        adapter.notifyDataSetChanged();
                        updateCount();
                        Toast.makeText(this, "Đã xóa!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;

        String ma = data.getStringExtra("ma");
        String hoTen = data.getStringExtra("hoten");
        String chucVu = data.getStringExtra("chucvu");
        long luong = data.getLongExtra("luong", 0);
        String phongBan = data.getStringExtra("phongban");

        if (ma == null) ma = "";
        if (hoTen == null) hoTen = "";
        if (chucVu == null) chucVu = "";
        if (phongBan == null) phongBan = "";

        if (requestCode == REQUEST_ADD) {
            dsNhanVien.add(new NhanVien(ma, hoTen, chucVu, luong, phongBan));
            adapter.notifyDataSetChanged();
            updateCount();
            Toast.makeText(this, "Đã thêm: " + hoTen, Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_EDIT) {
            int pos = data.getIntExtra("position", -1);
            if (pos >= 0 && pos < dsNhanVien.size()) {
                dsNhanVien.set(pos, new NhanVien(ma, hoTen, chucVu, luong, phongBan));
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã cập nhật: " + hoTen, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateCount() {
        tvTongNV.setText("Tổng: " + dsNhanVien.size() + " nhân viên");
    }
}
