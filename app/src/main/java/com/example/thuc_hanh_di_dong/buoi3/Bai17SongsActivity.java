package com.example.thuc_hanh_di_dong.buoi3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Buổi 3 - Bài tập 17: Quản lý bài hát
 * - Spinner load danh sách Album
 * - DatePickerDialog chọn ngày ra đĩa
 * - Thêm bài hát vào ListView
 */
@SuppressLint("SetTextI18n")
public class Bai17SongsActivity extends Activity {

    private String ngayRaDia = "chưa chọn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi3_bai17_05_songs);

        Spinner spnAlbum = findViewById(R.id.spnAlbum);
        EditText edtTenBH = findViewById(R.id.edtTenBaiHat);
        EditText edtTacGia = findViewById(R.id.edtTacGia);
        TextView tvNgay = findViewById(R.id.tvNgayRaDia);
        ListView lvBaiHat = findViewById(R.id.lvBaiHat);

        ArrayList<String> albumNames = new ArrayList<>();
        for (Album a : Bai17Data.albums) {
            albumNames.add(a.toString());
        }
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, albumNames);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAlbum.setAdapter(spnAdapter);

        ArrayAdapter<BaiHat> songAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Bai17Data.songs);
        lvBaiHat.setAdapter(songAdapter);

        Calendar cal = Calendar.getInstance();
        findViewById(R.id.btnChonNgay).setOnClickListener(v ->
                new DatePickerDialog(this, (view, y, m, d) -> {
                    ngayRaDia = String.format(Locale.US, "%02d/%02d/%d", d, m + 1, y);
                    tvNgay.setText("Ngày: " + ngayRaDia);
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show());

        findViewById(R.id.btnThemBaiHat).setOnClickListener(v -> {
            String tenBH = edtTenBH.getText().toString().trim();
            String tacGia = edtTacGia.getText().toString().trim();
            if (tenBH.isEmpty() || tacGia.isEmpty()) {
                Toast.makeText(this, "Nhập đầy đủ tên bài hát và tác giả!", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedAlbumPos = spnAlbum.getSelectedItemPosition();
            String albumMa = (selectedAlbumPos >= 0 && selectedAlbumPos < Bai17Data.albums.size())
                    ? Bai17Data.albums.get(selectedAlbumPos).getMa()
                    : "N/A";

            BaiHat baiHat = new BaiHat(tenBH, tacGia, ngayRaDia, albumMa);
            Bai17Data.songs.add(baiHat);
            songAdapter.notifyDataSetChanged();

            edtTenBH.getText().clear();
            edtTacGia.getText().clear();
            edtTenBH.requestFocus();
            Toast.makeText(this, "Đã thêm: " + tenBH, Toast.LENGTH_SHORT).show();
        });

        lvBaiHat.setOnItemClickListener((parent, view, position, id) -> {
            BaiHat bh = Bai17Data.songs.get(position);
            Toast.makeText(
                    this,
                    "Bài: " + bh.getTen() + "\nTác giả: " + bh.getTacGia() + "\nNgày: " + bh.getNgayRaDia() + "\nAlbum: " + bh.getAlbumMa(),
                    Toast.LENGTH_LONG
            ).show();
        });
    }
}
