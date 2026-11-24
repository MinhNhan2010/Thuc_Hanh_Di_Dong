package com.example.thuc_hanh_di_dong.buoi3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Buổi 3 - Bài tập 16: Menu, ContextMenu
 * - Quản lý công việc + DatePicker + TimePicker
 * - ContextMenu (long press): Sửa, Xóa, Đếm CV
 * - Options Menu: Hoàn thành, Chưa HT, Tất cả, Xóa tất cả
 */
@SuppressLint("SetTextI18n")
public class Bai16MenuActivity extends Activity {

    public static class CongViec {
        public String ten;
        public String ngay;
        public String gio;
        public boolean hoanThanh;

        public CongViec(String ten, String ngay, String gio, boolean hoanThanh) {
            this.ten = ten;
            this.ngay = ngay;
            this.gio = gio;
            this.hoanThanh = hoanThanh;
        }

        @Override
        public String toString() {
            String status = hoanThanh ? "✅" : "⬜";
            return status + " " + ten + "\n   Hạn: " + ngay + " " + gio;
        }
    }

    private final ArrayList<CongViec> dsCongViec = new ArrayList<>();
    private final ArrayList<CongViec> dsHienThi = new ArrayList<>();
    private ArrayAdapter<CongViec> adapter;
    private TextView tvStatus;

    private int selectedDay = 0;
    private int selectedMonth = 0;
    private int selectedYear = 0;
    private int selectedHour = 0;
    private int selectedMinute = 0;
    private String ngayStr = "chưa chọn";
    private String gioStr = "chưa chọn";
    private int contextPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi3_bai16_menu);

        EditText edtCV = findViewById(R.id.edtCongViec);
        TextView tvDateTime = findViewById(R.id.tvDateTime);
        CheckBox chkHoanThanh = findViewById(R.id.chkHoanThanh);
        tvStatus = findViewById(R.id.tvStatus);
        ListView lvCV = findViewById(R.id.lvCongViec);

        Calendar cal = Calendar.getInstance();
        selectedYear = cal.get(Calendar.YEAR);
        selectedMonth = cal.get(Calendar.MONTH);
        selectedDay = cal.get(Calendar.DAY_OF_MONTH);
        selectedHour = cal.get(Calendar.HOUR_OF_DAY);
        selectedMinute = cal.get(Calendar.MINUTE);

        dsHienThi.addAll(dsCongViec);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dsHienThi);
        lvCV.setAdapter(adapter);

        // DatePicker
        findViewById(R.id.btnDate).setOnClickListener(v ->
                new DatePickerDialog(this, (view, y, m, d) -> {
                    selectedYear = y;
                    selectedMonth = m;
                    selectedDay = d;
                    ngayStr = String.format(Locale.US, "%02d/%02d/%d", d, m + 1, y);
                    tvDateTime.setText("Hạn: " + ngayStr + " " + gioStr);
                }, selectedYear, selectedMonth, selectedDay).show());

        // TimePicker
        findViewById(R.id.btnTime).setOnClickListener(v ->
                new TimePickerDialog(this, (view, h, m) -> {
                    selectedHour = h;
                    selectedMinute = m;
                    gioStr = String.format(Locale.US, "%02d:%02d", h, m);
                    tvDateTime.setText("Hạn: " + ngayStr + " " + gioStr);
                }, selectedHour, selectedMinute, true).show());

        // Thêm công việc
        findViewById(R.id.btnThemCV).setOnClickListener(v -> {
            String ten = edtCV.getText().toString().trim();
            if (ten.isEmpty()) {
                Toast.makeText(this, "Nhập tên công việc!", Toast.LENGTH_SHORT).show();
                return;
            }
            CongViec cv = new CongViec(ten, ngayStr, gioStr, chkHoanThanh.isChecked());
            dsCongViec.add(cv);
            refreshList();
            edtCV.getText().clear();
            chkHoanThanh.setChecked(false);
            edtCV.requestFocus();
        });

        registerForContextMenu(lvCV);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buoi3_bai16_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnuHoanThanh) {
            dsHienThi.clear();
            for (CongViec cv : dsCongViec) {
                if (cv.hoanThanh) dsHienThi.add(cv);
            }
            adapter.notifyDataSetChanged();
            tvStatus.setText("Đã hoàn thành: " + dsHienThi.size() + " CV");
        } else if (id == R.id.mnuChuaHoanThanh) {
            dsHienThi.clear();
            for (CongViec cv : dsCongViec) {
                if (!cv.hoanThanh) dsHienThi.add(cv);
            }
            adapter.notifyDataSetChanged();
            tvStatus.setText("Chưa hoàn thành: " + dsHienThi.size() + " CV");
        } else if (id == R.id.mnuTatCa) {
            refreshList();
        } else if (id == R.id.mnuXoaTatCa) {
            new AlertDialog.Builder(this)
                    .setTitle("Xóa tất cả")
                    .setMessage("Bạn có chắc muốn xóa toàn bộ công việc?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        dsCongViec.clear();
                        refreshList();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (!(menuInfo instanceof AdapterView.AdapterContextMenuInfo)) return;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        contextPosition = info.position;
        if (menu != null && contextPosition >= 0 && contextPosition < dsHienThi.size()) {
            menu.setHeaderTitle("Công việc: " + dsHienThi.get(contextPosition).ten);
            menu.add(0, 1, 0, "✏️ Sửa công việc");
            menu.add(0, 2, 1, "🗑️ Xóa công việc");
            menu.add(0, 3, 2, "📊 Đếm số CV");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (contextPosition < 0 || contextPosition >= dsHienThi.size()) {
            return super.onContextItemSelected(item);
        }
        int itemId = item.getItemId();
        if (itemId == 1) {
            CongViec cv = dsHienThi.get(contextPosition);
            EditText edtNew = new EditText(this);
            edtNew.setText(cv.ten);
            new AlertDialog.Builder(this)
                    .setTitle("Sửa công việc")
                    .setView(edtNew)
                    .setPositiveButton("Lưu", (dialog, which) -> {
                        cv.ten = edtNew.getText().toString().trim();
                        cv.hoanThanh = !cv.hoanThanh;
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        } else if (itemId == 2) {
            CongViec cv = dsHienThi.get(contextPosition);
            new AlertDialog.Builder(this)
                    .setTitle("Xóa công việc")
                    .setMessage("Bạn có chắc muốn xóa '" + cv.ten + "'?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        dsCongViec.remove(cv);
                        refreshList();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        } else if (itemId == 3) {
            int ht = 0;
            int chua = 0;
            for (CongViec c : dsCongViec) {
                if (c.hoanThanh) ht++;
                else chua++;
            }
            new AlertDialog.Builder(this)
                    .setTitle("Thống kê")
                    .setMessage("Tổng: " + dsCongViec.size() + "\nĐã hoàn thành: " + ht + "\nChưa hoàn thành: " + chua)
                    .setPositiveButton("OK", null)
                    .show();
        }
        return super.onContextItemSelected(item);
    }

    private void refreshList() {
        dsHienThi.clear();
        dsHienThi.addAll(dsCongViec);
        adapter.notifyDataSetChanged();
        tvStatus.setText("Tổng: " + dsCongViec.size() + " công việc");
    }
}
