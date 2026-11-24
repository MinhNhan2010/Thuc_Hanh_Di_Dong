package com.example.thuc_hanh_di_dong.buoi4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Custom Adapter cho danh sách Sinh Viên (ImageView + thông tin chi tiết + Điểm TB/Xếp loại)
 */
@SuppressLint("SetTextI18n")
public class MyAdapterStudent extends ArrayAdapter<SinhVien> {

    private final ArrayList<SinhVien> dsSV;

    public MyAdapterStudent(Context context, ArrayList<SinhVien> dsSV) {
        super(context, R.layout.buoi4_bai19_08_item_student, dsSV);
        this.dsSV = dsSV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.buoi4_bai19_08_item_student, parent, false);
        }

        SinhVien sv = dsSV.get(position);

        ImageView imgStudent = view.findViewById(R.id.imgStudent);
        if ("Nam".equals(sv.getGioiTinh())) {
            imgStudent.setImageResource(android.R.drawable.ic_menu_myplaces);
        } else {
            imgStudent.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        ((TextView) view.findViewById(R.id.txtStudentName)).setText(sv.getMaSV() + " - " + sv.getTenSV());
        ((TextView) view.findViewById(R.id.txtStudentClass)).setText("🏫 Lớp: " + sv.getMaLop());
        ((TextView) view.findViewById(R.id.txtStudentGender)).setText("👤 Giới tính: " + sv.getGioiTinh());
        ((TextView) view.findViewById(R.id.txtStudentBirthday)).setText("🎂 NS: " + sv.getNgaySinh());
        ((TextView) view.findViewById(R.id.txtStudentAddress)).setText("📍 ĐC: " + sv.getDiaChi());

        TextView tvGPA = view.findViewById(R.id.txtStudentGPA);
        String gpaStr = String.format(Locale.US, "%.1f", sv.getDiemTB());
        tvGPA.setText("⭐ " + gpaStr + " (" + sv.getXepLoai() + ")");
        if (sv.getDiemTB() >= 8.0) {
            tvGPA.setTextColor(Color.parseColor("#2E7D32"));
        } else if (sv.getDiemTB() >= 6.5) {
            tvGPA.setTextColor(Color.parseColor("#1565C0"));
        } else if (sv.getDiemTB() >= 5.0) {
            tvGPA.setTextColor(Color.parseColor("#EF6C00"));
        } else {
            tvGPA.setTextColor(Color.parseColor("#C62828"));
        }

        TextView tvContact = view.findViewById(R.id.txtStudentContact);
        List<String> contactParts = new ArrayList<>();
        if (sv.getSoDienThoai() != null && !sv.getSoDienThoai().trim().isEmpty()) {
            contactParts.add("📞 " + sv.getSoDienThoai());
        }
        if (sv.getEmail() != null && !sv.getEmail().trim().isEmpty()) {
            contactParts.add("✉️ " + sv.getEmail());
        }
        if (!contactParts.isEmpty()) {
            tvContact.setVisibility(View.VISIBLE);
            tvContact.setText(TextUtils.join(" | ", contactParts));
        } else {
            tvContact.setVisibility(View.GONE);
        }

        return view;
    }
}
