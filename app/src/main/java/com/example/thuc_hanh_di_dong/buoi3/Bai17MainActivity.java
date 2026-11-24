package com.example.thuc_hanh_di_dong.buoi3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import com.example.thuc_hanh_di_dong.R;

/**
 * Buổi 3 - Bài tập 17: Main - Quản lý Album/Bài hát
 * Explicit Intent: mở các Activity trong app
 * Implicit Intent: mở trình duyệt web
 */
public class Bai17MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi3_bai17_01_main);

        // Explicit Intent → Thêm Album (dạng Dialog)
        findViewById(R.id.btnThemAlbum).setOnClickListener(v -> {
            Intent intent = new Intent(this, Bai17AddAlbumActivity.class);
            startActivity(intent);
        });

        // Explicit Intent → Xem danh sách Album
        findViewById(R.id.btnXemAlbum).setOnClickListener(v -> {
            if (Bai17Data.albums.isEmpty()) {
                Toast.makeText(this, "Chưa có Album nào! Hãy thêm Album trước.", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(this, Bai17AlbumListActivity.class));
        });

        // Explicit Intent → Quản lý bài hát
        findViewById(R.id.btnQuanLyBaiHat).setOnClickListener(v -> {
            if (Bai17Data.albums.isEmpty()) {
                Toast.makeText(this, "Phải tạo Album trước khi quản lý bài hát!", Toast.LENGTH_LONG).show();
                return;
            }
            startActivity(new Intent(this, Bai17SongsActivity.class));
        });

        // Implicit Intent → Mở trình duyệt
        findViewById(R.id.btnImplicitIntent).setOnClickListener(v -> {
            String url = "https://www.google.com";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }
}
