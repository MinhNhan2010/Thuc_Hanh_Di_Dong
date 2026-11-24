package com.example.thuc_hanh_di_dong.buoi2;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Buổi 2 - Bài tập 09: TextView, EditText, Button, Checkbox, RadioButton
 * Form thông tin cá nhân với validation
 */
public class Bai09ThongTinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi2_bai09_thong_tin);

        EditText edtTen = findViewById(R.id.edtTen);
        EditText edtCMND = findViewById(R.id.edtCMND);
        RadioGroup rgBangCap = findViewById(R.id.rgBangCap);
        CheckBox chkDocSach = findViewById(R.id.chkDocSach);
        CheckBox chkTheThao = findViewById(R.id.chkTheThao);
        CheckBox chkDuLich = findViewById(R.id.chkDuLich);
        CheckBox chkAmNhac = findViewById(R.id.chkAmNhac);
        EditText edtBoSung = findViewById(R.id.edtBoSung);

        findViewById(R.id.btnGuiThongTin).setOnClickListener(v -> {
            String ten = edtTen.getText().toString().trim();
            if (ten.length() < 3) {
                Toast.makeText(this, "Tên phải có ít nhất 3 ký tự!", Toast.LENGTH_SHORT).show();
                return;
            }

            String cmnd = edtCMND.getText().toString().trim();
            if (cmnd.length() != 9) {
                Toast.makeText(this, "CMND phải có đúng 9 chữ số!", Toast.LENGTH_SHORT).show();
                return;
            }

            String bangCap;
            int checkedId = rgBangCap.getCheckedRadioButtonId();
            if (checkedId == R.id.rbCaoDang) {
                bangCap = "Cao đẳng";
            } else if (checkedId == R.id.rbDaiHoc) {
                bangCap = "Đại học";
            } else if (checkedId == R.id.rbThacSi) {
                bangCap = "Thạc sĩ";
            } else {
                bangCap = "Chưa chọn";
            }

            List<String> soThich = new ArrayList<>();
            if (chkDocSach.isChecked()) soThich.add("Đọc sách");
            if (chkTheThao.isChecked()) soThich.add("Thể thao");
            if (chkDuLich.isChecked()) soThich.add("Du lịch");
            if (chkAmNhac.isChecked()) soThich.add("Âm nhạc");

            if (soThich.isEmpty()) {
                Toast.makeText(this, "Phải chọn ít nhất 1 sở thích!", Toast.LENGTH_SHORT).show();
                return;
            }

            String boSung = edtBoSung.getText().toString().trim();

            StringBuilder message = new StringBuilder();
            message.append("Tên: ").append(ten).append("\n");
            message.append("CMND: ").append(cmnd).append("\n");
            message.append("Bằng cấp: ").append(bangCap).append("\n");
            message.append("Sở thích: ").append(TextUtils.join(", ", soThich)).append("\n");
            if (!boSung.isEmpty()) {
                message.append("Thông tin bổ sung: ").append(boSung).append("\n");
            }

            new AlertDialog.Builder(this)
                    .setTitle("Thông tin cá nhân")
                    .setMessage(message.toString())
                    .setPositiveButton("OK", null)
                    .show();
        });
    }
}
