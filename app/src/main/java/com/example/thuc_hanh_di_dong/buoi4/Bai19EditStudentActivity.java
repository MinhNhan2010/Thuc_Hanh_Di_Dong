package com.example.thuc_hanh_di_dong.buoi4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Buổi 4: Sửa sinh viên trong SQLite
 * - Tái sử dụng layout buoi4_bai19_07_insert_student
 * - Điền sẵn dữ liệu nhận từ Intent (bao gồm Điểm TB, SĐT, Email)
 * - Kiểm tra sĩ số nếu chuyển sang lớp khác
 * - Cập nhật thay đổi vào SQLite
 */
@SuppressLint("SetTextI18n")
public class Bai19EditStudentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi4_bai19_07_insert_student);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        TextView txtLabel = findViewById(R.id.txtLabel);
        Button btnSave = findViewById(R.id.btnSave);

        txtLabel.setText("Cập nhật sinh viên");
        btnSave.setText("✅ Cập nhật");

        Spinner spnClassCode = findViewById(R.id.spnClassCode);
        EditText edtStudentCode = findViewById(R.id.edtStudentCode);
        EditText edtStudentName = findViewById(R.id.edtStudentName);
        RadioButton rdiMale = findViewById(R.id.rdiMale);
        RadioButton rdiFemale = findViewById(R.id.rdiFemale);
        EditText edtStudentBirthday = findViewById(R.id.edtStudentBirthday);
        EditText edtStudentGPA = findViewById(R.id.edtStudentGPA);
        EditText edtStudentPhone = findViewById(R.id.edtStudentPhone);
        EditText edtStudentEmail = findViewById(R.id.edtStudentEmail);
        EditText edtStudentAddress = findViewById(R.id.edtStudentAddress);

        edtStudentCode.setEnabled(false);

        ArrayList<String> classCodes = dbHelper.getClassCodes();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, classCodes
        );
        spnClassCode.setAdapter(spinnerAdapter);

        String maSV = getIntent().getStringExtra("maSV");
        if (maSV == null) maSV = "";
        String tenSV = getIntent().getStringExtra("tenSV");
        if (tenSV == null) tenSV = "";
        String gioiTinh = getIntent().getStringExtra("gioiTinh");
        if (gioiTinh == null) gioiTinh = "Nam";
        String ngaySinh = getIntent().getStringExtra("ngaySinh");
        if (ngaySinh == null) ngaySinh = "";
        String diaChi = getIntent().getStringExtra("diaChi");
        if (diaChi == null) diaChi = "";
        String oldMaLop = getIntent().getStringExtra("maLop");
        if (oldMaLop == null) oldMaLop = "";
        String soDienThoai = getIntent().getStringExtra("soDienThoai");
        if (soDienThoai == null) soDienThoai = "";
        String email = getIntent().getStringExtra("email");
        if (email == null) email = "";
        double diemTB = getIntent().getDoubleExtra("diemTB", 0.0);

        edtStudentCode.setText(maSV);
        edtStudentName.setText(tenSV);
        edtStudentBirthday.setText(ngaySinh);
        edtStudentGPA.setText(String.valueOf(diemTB));
        edtStudentPhone.setText(soDienThoai);
        edtStudentEmail.setText(email);
        edtStudentAddress.setText(diaChi);

        if ("Nữ".equals(gioiTinh)) {
            rdiFemale.setChecked(true);
        } else {
            rdiMale.setChecked(true);
        }

        int classPos = classCodes.indexOf(oldMaLop);
        if (classPos >= 0) {
            spnClassCode.setSelection(classPos);
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

        final String finalMaSV = maSV;
        final String finalOldMaLop = oldMaLop;

        btnSave.setOnClickListener(v -> {
            String newTen = edtStudentName.getText().toString().trim();
            String newNgaySinh = edtStudentBirthday.getText().toString().trim();
            String newGpaStr = edtStudentGPA.getText().toString().trim();
            String newPhone = edtStudentPhone.getText().toString().trim();
            String newEmail = edtStudentEmail.getText().toString().trim();
            String newDiaChi = edtStudentAddress.getText().toString().trim();
            String newGioiTinh = rdiMale.isChecked() ? "Nam" : "Nữ";
            String newMaLop = spnClassCode.getSelectedItem() != null
                    ? spnClassCode.getSelectedItem().toString() : "";

            if (newTen.isEmpty() || newNgaySinh.isEmpty() || newDiaChi.isEmpty() || newGpaStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ Họ tên, Ngày sinh, Điểm TB và Địa chỉ!", Toast.LENGTH_SHORT).show();
                return;
            }

            double newDiemTB;
            try {
                newDiemTB = Double.parseDouble(newGpaStr);
            } catch (NumberFormatException e) {
                newDiemTB = -1;
            }
            if (newDiemTB < 0.0 || newDiemTB > 10.0) {
                Toast.makeText(this, "Điểm trung bình phải nằm trong khoảng 0.0 đến 10.0!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPhone.isEmpty() && !newPhone.matches("^0\\d{8,10}$")) {
                Toast.makeText(this, "Số điện thoại không hợp lệ (bắt đầu bằng 0, từ 9-11 chữ số)!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newEmail.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                Toast.makeText(this, "Địa chỉ Email không đúng định dạng!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newMaLop.equals(finalOldMaLop)) {
                LopHoc targetLop = dbHelper.getClassByCode(newMaLop);
                if (targetLop != null && targetLop.getSiSo() > 0 && targetLop.getSoSVHienTai() >= targetLop.getSiSo()) {
                    Toast.makeText(this, "Lớp " + newMaLop + " đã đủ sĩ số tối đa ("
                            + targetLop.getSoSVHienTai() + "/" + targetLop.getSiSo() + ")!", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            SinhVien sv = new SinhVien(
                    finalMaSV, newTen, newGioiTinh, newNgaySinh, newDiaChi, newMaLop,
                    newPhone, newEmail, newDiemTB
            );
            dbHelper.updateStudent(sv);

            Toast.makeText(this, "Cập nhật sinh viên " + newTen + " thành công!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            edtStudentName.getText().clear();
            edtStudentBirthday.getText().clear();
            edtStudentGPA.getText().clear();
            edtStudentPhone.getText().clear();
            edtStudentEmail.getText().clear();
            edtStudentAddress.getText().clear();
            rdiMale.setChecked(true);
            edtStudentName.requestFocus();
        });

        findViewById(R.id.btnClose).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
