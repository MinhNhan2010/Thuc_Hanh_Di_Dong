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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;

/**
 * Buổi 4: Danh sách lớp học
 * - Tìm kiếm lớp học theo mã hoặc tên lớp
 * - Hiển thị sĩ số thực tế / sĩ số tối đa
 * - Chạm vào lớp để xem danh sách sinh viên của lớp đó
 * - ContextMenu: Xem SV / Thêm SV vào lớp / Sửa / Xóa lớp
 */
@SuppressLint("SetTextI18n")
public class Bai19ClassListActivity extends Activity {

    public static final int REQUEST_INSERT = 300;
    public static final int REQUEST_EDIT = 301;
    public static final int REQUEST_ADD_STUDENT = 302;

    private DatabaseHelper dbHelper;
    private ArrayList<LopHoc> dsLop;
    private MyAdapterClass adapter;
    private TextView tvCount;
    private EditText edtSearchClass;
    private int contextPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi4_bai19_03_class_list);

        dbHelper = new DatabaseHelper(this);
        tvCount = findViewById(R.id.tvClassCount);
        edtSearchClass = findViewById(R.id.edtSearchClass);
        ListView lvClass = findViewById(R.id.lvClass);

        dsLop = dbHelper.getAllClasses();
        adapter = new MyAdapterClass(this, dsLop);
        lvClass.setAdapter(adapter);
        updateCount();

        edtSearchClass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refreshList();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        findViewById(R.id.btnClearSearchClass).setOnClickListener(v -> edtSearchClass.getText().clear());

        findViewById(R.id.btnAddClass).setOnClickListener(v -> {
            Intent intent = new Intent(this, Bai19InsertClassActivity.class);
            startActivityForResult(intent, REQUEST_INSERT);
        });

        lvClass.setOnItemClickListener((parent, view, position, id) -> {
            LopHoc lop = dsLop.get(position);
            Intent intent = new Intent(this, Bai19StudentListActivity.class);
            intent.putExtra("filterMaLop", lop.getMaLop());
            startActivity(intent);
        });

        registerForContextMenu(lvClass);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (!(menuInfo instanceof AdapterView.AdapterContextMenuInfo)) return;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        contextPosition = info.position;
        if (menu != null && contextPosition >= 0 && contextPosition < dsLop.size()) {
            LopHoc lop = dsLop.get(contextPosition);
            menu.setHeaderTitle(lop.getMaLop() + " - " + lop.getTenLop()
                    + " (" + lop.getSoSVHienTai() + "/" + lop.getSiSo() + " SV)");
            getMenuInflater().inflate(R.menu.buoi4_bai19_menu_class, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (contextPosition < 0 || contextPosition >= dsLop.size()) {
            return super.onContextItemSelected(item);
        }
        LopHoc lop = dsLop.get(contextPosition);
        int id = item.getItemId();

        if (id == R.id.mnuViewStudentsInClass) {
            Intent intent = new Intent(this, Bai19StudentListActivity.class);
            intent.putExtra("filterMaLop", lop.getMaLop());
            startActivity(intent);
        } else if (id == R.id.mnuAddStudentToClass) {
            if (lop.getSiSo() > 0 && lop.getSoSVHienTai() >= lop.getSiSo()) {
                Toast.makeText(this, "Lớp " + lop.getMaLop() + " đã đủ sĩ số ("
                        + lop.getSoSVHienTai() + "/" + lop.getSiSo() + ")!", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, Bai19InsertStudentActivity.class);
                intent.putExtra("preselectMaLop", lop.getMaLop());
                startActivityForResult(intent, REQUEST_ADD_STUDENT);
            }
        } else if (id == R.id.mnuEditClass) {
            Intent intent = new Intent(this, Bai19EditClassActivity.class);
            intent.putExtra("position", contextPosition);
            intent.putExtra("maLop", lop.getMaLop());
            intent.putExtra("tenLop", lop.getTenLop());
            intent.putExtra("siSo", lop.getSiSo());
            intent.putExtra("soSVHienTai", lop.getSoSVHienTai());
            startActivityForResult(intent, REQUEST_EDIT);
        } else if (id == R.id.mnuDeleteClass) {
            new AlertDialog.Builder(this)
                    .setTitle("Xóa lớp học")
                    .setMessage("Bạn có chắc muốn xóa lớp '" + lop.getTenLop() + "' (" + lop.getMaLop()
                            + ") và toàn bộ " + lop.getSoSVHienTai() + " sinh viên thuộc lớp này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        dbHelper.deleteClass(lop.getMaLop());
                        refreshList();
                        Toast.makeText(this, "Đã xóa lớp " + lop.getMaLop() + "!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", null)
                    .show();
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
        String query = edtSearchClass != null ? edtSearchClass.getText().toString() : "";
        dsLop.clear();
        dsLop.addAll(dbHelper.getAllClasses(query));
        adapter.notifyDataSetChanged();
        updateCount();
    }

    private void updateCount() {
        int totalSV = 0;
        for (LopHoc lop : dsLop) {
            totalSV += lop.getSoSVHienTai();
        }
        tvCount.setText("Danh sách: " + dsLop.size() + " lớp | Tổng: " + totalSV + " sinh viên");
    }
}
