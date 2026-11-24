package com.example.thuc_hanh_di_dong.buoi4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;

/**
 * Custom Adapter cho danh sách Lớp Học (3 cột: Mã, Tên, Sĩ số thực tế / tối đa)
 */
@SuppressLint("SetTextI18n")
public class MyAdapterClass extends ArrayAdapter<LopHoc> {

    private final ArrayList<LopHoc> dsLop;

    public MyAdapterClass(Context context, ArrayList<LopHoc> dsLop) {
        super(context, R.layout.buoi4_bai19_05_item_class, dsLop);
        this.dsLop = dsLop;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.buoi4_bai19_05_item_class, parent, false);
        }

        LopHoc lop = dsLop.get(position);
        ((TextView) view.findViewById(R.id.txtclasscode)).setText(lop.getMaLop());
        ((TextView) view.findViewById(R.id.txtclassname)).setText(lop.getTenLop());

        TextView tvNumber = view.findViewById(R.id.txtclassnumber);
        tvNumber.setText("SV: " + lop.getSoSVHienTai() + "/" + lop.getSiSo());
        if (lop.getSiSo() > 0 && lop.getSoSVHienTai() >= lop.getSiSo()) {
            tvNumber.setTextColor(Color.parseColor("#D32F2F"));
        } else {
            tvNumber.setTextColor(Color.parseColor("#2E7D32"));
        }

        return view;
    }
}
