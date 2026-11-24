package com.example.thuc_hanh_di_dong.buoi4;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Buổi 4: Thêm sinh viên vào SQLite
 * - Chọn lớp từ Spinner (hỗ trợ chọn sẵn lớp khi mở từ màn hình Lớp học)
 * - Kiểm tra sĩ số lớp trước khi thêm
 * - Nhập Mã SV, Họ tên, Giới tính, Ngày sinh, Điểm TB, SĐT, Email, Địa chỉ
 */
public class Bai19InsertStudentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi4_bai19_07_insert_student);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        Spinner spnClassCode = findViewById(R.id.spnClassCode);
        EditText edtStudentCode = findViewById(R.id.edtStudentCode);
        EditText edtStudentName = findViewById(R.id.edtStudentName);
        RadioButton rdiMale = findViewById(R.id.rdiMale);
        EditText edtStudentBirthday = findViewById(R.id.edtStudentBirthday);
        EditText edtStudentGPA = findViewById(R.id.edtStudentGPA);
        EditText edtStudentPhone = findViewById(R.id.edtStudentPhone);
        EditText edtStudentEmail = findViewById(R.id.edtStudentEmail);
        EditText edtStudentAddress = findViewById(R.id.edtStudentAddress);

        ArrayList<String> classCodes = dbHelper.getClassCodes();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, classCodes
        );
        spnClassCode.setAdapter(spinnerAdapter);

        String preselectMaLop = getIntent().getStringExtra("preselectMaLop");
        if (preselectMaLop != null && !preselectMaLop.trim().isEmpty()) {
            int pos = classCodes.indexOf(preselectMaLop);
            if (pos >= 0) {
                spnClassCode.setSelection(pos);
            }
        }

        edtStudentBirthday.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR) - 20;
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            String currentText = edtStudentBirthday.getText().toString().trim();
            String[] parts = currentText.split("/");
            if (parts.length == 3) {
                try {
                    day = Integer.parseInt(parts[0]);
                    month = Integer.parseInt(parts[1]) - 1;
                    year = Integer.parseInt(parts[2]);
                } catch (NumberFormatException ignored) {
                }
            }

            new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
                String formatted = String.format(Locale.US, "%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                edtStudentBirthday.setText(formatted);
            }, year, month, day).show();
        });

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String maSV = edtStudentCode.getText().toString().trim().toUpperCase(Locale.ROOT);
            String tenSV = edtStudentName.getText().toString().trim();
            String ngaySinh = edtStudentBirthday.getText().toString().trim();
            String gpaStr = edtStudentGPA.getText().toString().trim();
            String phone = edtStudentPhone.getText().toString().trim();
            String email = edtStudentEmail.getText().toString().trim();
            String diaChi = edtStudentAddress.getText().toString().trim();
            String gioiTinh = rdiMale.isChecked() ? "Nam" : "Nữ";

            if (spnClassCode.getSelectedItem() == null) {
                Toast.makeText(this, "Vui lòng tạo lớp học trước!", Toast.LENGTH_SHORT).show();
                return;
            }
            String maLop = spnClassCode.getSelectedItem().toString();

            if (maSV.isEmpty() || tenSV.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty() || gpaStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ Mã SV, Họ tên, Ngày sinh, Điểm TB và Địa chỉ!", Toast.LENGTH_SHORT).show();
                return;
            }

            double diemTB;
            try {
                diemTB = Double.parseDouble(gpaStr);
            } catch (NumberFormatException e) {
                diemTB = -1;
            }
            if (diemTB < 0.0 || diemTB > 10.0) {
                Toast.makeText(this, "Điểm trung bình phải nằm trong khoảng 0.0 đến 10.0!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!phone.isEmpty() && !phone.matches("^0\\d{8,10}$")) {
                Toast.makeText(this, "Số điện thoại không hợp lệ (bắt đầu bằng 0, từ 9-11 chữ số)!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Địa chỉ Email không đúng định dạng!", Toast.LENGTH_SHORT).show();
                return;
            }

            LopHoc lop = dbHelper.getClassByCode(maLop);
            if (lop != null && lop.getSiSo() > 0 && lop.getSoSVHienTai() >= lop.getSiSo()) {
                Toast.makeText(this, "Lớp " + maLop + " đã đủ sĩ số tối đa ("
                        + lop.getSoSVHienTai() + "/" + lop.getSiSo() + ")!", Toast.LENGTH_LONG).show();
                return;
            }

            SinhVien sv = new SinhVien(maSV, tenSV, gioiTinh, ngaySinh, diaChi, maLop, phone, email, diemTB);
            long result = dbHelper.insertStudent(sv);

            if (result > 0) {
                Toast.makeText(this, "Thêm sinh viên " + tenSV + " thành công!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Mã sinh viên '" + maSV + "' đã tồn tại!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            edtStudentCode.getText().clear();
            edtStudentName.getText().clear();
            edtStudentBirthday.getText().clear();
            edtStudentGPA.getText().clear();
            edtStudentPhone.getText().clear();
            edtStudentEmail.getText().clear();
            edtStudentAddress.getText().clear();
            rdiMale.setChecked(true);
            edtStudentCode.requestFocus();
        });

        findViewById(R.id.btnClose).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
