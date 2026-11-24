package com.example.thuc_hanh_di_dong.buoi4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Buổi 4: Danh sách sinh viên
 * - Tìm kiếm theo từ khóa (Mã SV, Tên, SĐT, Email, Địa chỉ)
 * - Lọc sinh viên theo Lớp học & Sắp xếp theo Mã / Tên / Điểm TB
 * - Chạm vào sinh viên để xem chi tiết + Sửa / Xóa nhanh
 * - ContextMenu: Sửa / Xóa sinh viên
 */
@SuppressLint("SetTextI18n")
public class Bai19StudentListActivity extends Activity {

    public static final int REQUEST_INSERT = 400;
    public static final int REQUEST_EDIT = 401;

    private DatabaseHelper dbHelper;
    private ArrayList<SinhVien> dsSV;
    private MyAdapterStudent adapter;
    private TextView tvCount;
    private EditText edtSearchStudent;
    private Spinner spnFilterClass;
    private Spinner spnSortStudent;
    private final ArrayList<String> filterClassList = new ArrayList<>();
    private int contextPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi4_bai19_06_student_list);

        dbHelper = new DatabaseHelper(this);
        tvCount = findViewById(R.id.tvStudentCount);
        edtSearchStudent = findViewById(R.id.edtSearchStudent);
        spnFilterClass = findViewById(R.id.spnFilterClass);
        spnSortStudent = findViewById(R.id.spnSortStudent);
        ListView lvStudent = findViewById(R.id.lvStudent);

        dsSV = new ArrayList<>();
        adapter = new MyAdapterStudent(this, dsSV);
        lvStudent.setAdapter(adapter);

        filterClassList.add("Tất cả");
        filterClassList.addAll(dbHelper.getClassCodes());
        ArrayAdapter<String> classFilterAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, filterClassList
        );
        spnFilterClass.setAdapter(classFilterAdapter);

        String initialClassFilter = getIntent().getStringExtra("filterMaLop");
        if (initialClassFilter != null && !initialClassFilter.trim().isEmpty()) {
            int pos = filterClassList.indexOf(initialClassFilter);
            if (pos >= 0) {
                spnFilterClass.setSelection(pos);
            }
        }

        String[] sortOptions = new String[]{
                "Mã SV (A → Z)",
                "Tên SV (A → Z)",
                "Điểm TB (Cao → Thấp)",
                "Điểm TB (Thấp → Cao)"
        };
        spnSortStudent.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, sortOptions
        ));

        AdapterView.OnItemSelectedListener filterSelectionListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
        spnFilterClass.setOnItemSelectedListener(filterSelectionListener);
        spnSortStudent.setOnItemSelectedListener(filterSelectionListener);

        edtSearchStudent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refreshList();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        findViewById(R.id.btnClearSearchStudent).setOnClickListener(v -> {
            edtSearchStudent.getText().clear();
            spnFilterClass.setSelection(0);
            spnSortStudent.setSelection(0);
        });

        refreshList();

        findViewById(R.id.btnAddStudent).setOnClickListener(v -> {
            ArrayList<String> classCodes = dbHelper.getClassCodes();
            if (classCodes.isEmpty()) {
                Toast.makeText(this, "Chưa có lớp nào! Vui lòng tạo lớp học trước.", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(this, Bai19InsertStudentActivity.class);
            Object selectedObj = spnFilterClass.getSelectedItem();
            String selectedClass = selectedObj != null ? selectedObj.toString() : "Tất cả";
            if (!"Tất cả".equals(selectedClass)) {
                intent.putExtra("preselectMaLop", selectedClass);
            }
            startActivityForResult(intent, REQUEST_INSERT);
        });

        lvStudent.setOnItemClickListener((parent, view, position, id) -> {
            SinhVien sv = dsSV.get(position);
            showStudentDetailDialog(sv, position);
        });

        registerForContextMenu(lvStudent);
    }

    private void showStudentDetailDialog(SinhVien sv, int position) {
        String gpaStr = String.format(Locale.US, "%.2f", sv.getDiemTB());
        String phoneStr = (sv.getSoDienThoai() != null && !sv.getSoDienThoai().trim().isEmpty())
                ? sv.getSoDienThoai() : "(Chưa cập nhật)";
        String emailStr = (sv.getEmail() != null && !sv.getEmail().trim().isEmpty())
                ? sv.getEmail() : "(Chưa cập nhật)";

        new AlertDialog.Builder(this)
                .setTitle("🎓 Thông tin sinh viên")
                .setMessage(
                        "• Mã SV: " + sv.getMaSV() + "\n"
                        + "• Họ tên: " + sv.getTenSV() + "\n"
                        + "• Lớp: " + sv.getMaLop() + "\n"
                        + "• Giới tính: " + sv.getGioiTinh() + "\n"
                        + "• Ngày sinh: " + sv.getNgaySinh() + "\n"
                        + "• Số điện thoại: " + phoneStr + "\n"
                        + "• Email: " + emailStr + "\n"
                        + "• Địa chỉ: " + sv.getDiaChi() + "\n"
                        + "• Điểm TB: " + gpaStr + " — Xếp loại: " + sv.getXepLoai()
                )
                .setPositiveButton("✏️ Sửa", (dialog, which) -> openEditStudent(sv, position))
                .setNeutralButton("🗑️ Xóa", (dialog, which) -> confirmDeleteStudent(sv))
                .setNegativeButton("Đóng", null)
                .show();
    }

    private void openEditStudent(SinhVien sv, int position) {
        Intent intent = new Intent(this, Bai19EditStudentActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("maSV", sv.getMaSV());
        intent.putExtra("tenSV", sv.getTenSV());
        intent.putExtra("gioiTinh", sv.getGioiTinh());
        intent.putExtra("ngaySinh", sv.getNgaySinh());
        intent.putExtra("diaChi", sv.getDiaChi());
        intent.putExtra("maLop", sv.getMaLop());
        intent.putExtra("soDienThoai", sv.getSoDienThoai());
        intent.putExtra("email", sv.getEmail());
        intent.putExtra("diemTB", sv.getDiemTB());
        startActivityForResult(intent, REQUEST_EDIT);
    }

    private void confirmDeleteStudent(SinhVien sv) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa sinh viên")
                .setMessage("Bạn có chắc chắn muốn xóa sinh viên '" + sv.getTenSV() + "' (" + sv.getMaSV() + ")?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    dbHelper.deleteStudent(sv.getMaSV());
                    refreshList();
                    Toast.makeText(this, "Đã xóa sinh viên " + sv.getTenSV() + "!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Không", null)
                .show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (!(menuInfo instanceof AdapterView.AdapterContextMenuInfo)) return;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        contextPosition = info.position;
        if (menu != null && contextPosition >= 0 && contextPosition < dsSV.size()) {
            SinhVien sv = dsSV.get(contextPosition);
            menu.setHeaderTitle(sv.getMaSV() + " - " + sv.getTenSV());
            getMenuInflater().inflate(R.menu.buoi4_bai19_menu_student, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (contextPosition < 0 || contextPosition >= dsSV.size()) {
            return super.onContextItemSelected(item);
        }
        SinhVien sv = dsSV.get(contextPosition);
        int id = item.getItemId();
        if (id == R.id.mnuEditStudent) {
            openEditStudent(sv, contextPosition);
        } else if (id == R.id.mnuDeleteStudent) {
            confirmDeleteStudent(sv);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            refreshList();
        }
    }

    private void refreshList() {
        String keyword = edtSearchStudent != null ? edtSearchStudent.getText().toString() : "";
        String selectedClass = "Tất cả";
        if (spnFilterClass != null && spnFilterClass.getSelectedItem() != null) {
            selectedClass = spnFilterClass.getSelectedItem().toString();
        }
        int sortOrder = spnSortStudent != null ? Math.max(0, spnSortStudent.getSelectedItemPosition()) : 0;

        dsSV.clear();
        dsSV.addAll(dbHelper.searchAndFilterStudents(keyword, selectedClass, sortOrder));
        adapter.notifyDataSetChanged();
        updateCount();
    }

    private void updateCount() {
        double avgGpa = 0.0;
        if (!dsSV.isEmpty()) {
            double sum = 0.0;
            for (SinhVien sv : dsSV) {
                sum += sv.getDiemTB();
            }
            avgGpa = sum / dsSV.size();
        }
        String avgStr = String.format(Locale.US, "%.2f", avgGpa);
        tvCount.setText("Danh sách: " + dsSV.size() + " sinh viên | Điểm TB: " + avgStr);
    }
}
