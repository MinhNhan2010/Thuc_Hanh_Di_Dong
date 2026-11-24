package com.example.thuc_hanh_di_dong.buoi3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 3 - Bài tập 17: Danh sách Album
 * - Click → Sửa Album (startActivityForResult)
 * - Long press → Xóa Album (AlertDialog xác nhận)
 */
public class Bai17AlbumListActivity extends Activity {

    private ArrayAdapter<Album> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi3_bai17_03_album_list);

        ListView lvAlbums = findViewById(R.id.lvAlbums);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Bai17Data.albums);
        lvAlbums.setAdapter(adapter);

        // Click → Sửa Album (Explicit Intent + startActivityForResult)
        lvAlbums.setOnItemClickListener((parent, view, position, id) -> {
            Album album = Bai17Data.albums.get(position);
            Intent intent = new Intent(this, Bai17EditAlbumActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("ma", album.getMa());
            intent.putExtra("ten", album.getTen());
            intent.putExtra("casi", album.getCaSi());
            startActivityForResult(intent, 100);
        });

        // Long press → Xóa Album
        lvAlbums.setOnItemLongClickListener((parent, view, position, id) -> {
            Album album = Bai17Data.albums.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Xóa Album")
                    .setMessage("Bạn có muốn xóa Album '" + album.getTen() + "'?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        Bai17Data.albums.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Đã xóa Album!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            int pos = data.getIntExtra("position", -1);
            if (pos >= 0 && pos < Bai17Data.albums.size()) {
                String ten = data.getStringExtra("ten");
                String casi = data.getStringExtra("casi");
                Bai17Data.albums.get(pos).setTen(ten != null ? ten : "");
                Bai17Data.albums.get(pos).setCaSi(casi != null ? casi : "");
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã cập nhật Album!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
