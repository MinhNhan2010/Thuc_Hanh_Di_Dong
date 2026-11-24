package com.example.thuc_hanh_di_dong.buoi2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Buổi 2 - Bài tập 14: Custom ListView, kế thừa ArrayAdapter
 * Nếu nội dung <=3 ký tự: hình ngôi sao, còn lại: hình địa cầu
 */
@SuppressLint("SetTextI18n")
public class Bai14CustomListViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi2_bai14_custom_listview);

        TextView tvSelected = findViewById(R.id.tvSelected);
        ListView lvCustom = findViewById(R.id.lvCustom);

        ArrayList<String> data = new ArrayList<>(Arrays.asList(
                "PHP", "Java", "C++", "Python", "Kotlin",
                "JS", "C#", "Go", "Rust", "SQL",
                "HTML", "CSS", "Ruby", "Swift", "Dart"
        ));

        MyCustomAdapter adapter = new MyCustomAdapter(this, data);
        lvCustom.setAdapter(adapter);

        lvCustom.setOnItemClickListener((parent, view, position, id) ->
                tvSelected.setText("Vị trí: " + position + " - Giá trị: " + data.get(position)));
    }

    public static class MyCustomAdapter extends ArrayAdapter<String> {
        private final ArrayList<String> items;

        public MyCustomAdapter(Context context, ArrayList<String> items) {
            super(context, R.layout.buoi2_bai14_item_custom_list, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.buoi2_bai14_item_custom_list, parent, false);
            }

            ImageView imgIcon = view.findViewById(R.id.imgIcon);
            TextView txtContent = view.findViewById(R.id.txtContent);

            String item = items.get(position);
            txtContent.setText(item);

            if (item.length() <= 3) {
                imgIcon.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                imgIcon.setImageResource(android.R.drawable.ic_dialog_map);
            }

            return view;
        }
    }
}
