package com.example.thuc_hanh_di_dong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.thuc_hanh_di_dong.buoi1.Bai01LinearLayoutActivity;
import com.example.thuc_hanh_di_dong.buoi1.Bai02RelativeLayoutActivity;
import com.example.thuc_hanh_di_dong.buoi1.Bai03TableLayoutActivity;
import com.example.thuc_hanh_di_dong.buoi1.Bai04OnClickXmlActivity;
import com.example.thuc_hanh_di_dong.buoi1.Bai05AnonymousListenerActivity;
import com.example.thuc_hanh_di_dong.buoi1.Bai06InterfaceOnClickActivity;
import com.example.thuc_hanh_di_dong.buoi2.Bai01ToastActivity;
import com.example.thuc_hanh_di_dong.buoi2.Bai02AlertDialogActivity;
import com.example.thuc_hanh_di_dong.buoi2.Bai03EventTypesActivity;
import com.example.thuc_hanh_di_dong.buoi2.Bai04TemperatureActivity;
import com.example.thuc_hanh_di_dong.buoi2.Bai07AlertLoginActivity;
import com.example.thuc_hanh_di_dong.buoi2.Bai08NamAmLichActivity;
import com.example.thuc_hanh_di_dong.buoi2.Bai09ThongTinActivity;
import com.example.thuc_hanh_di_dong.buoi2.Bai11ListViewActivity;
import com.example.thuc_hanh_di_dong.buoi2.Bai12ListViewInputActivity;
import com.example.thuc_hanh_di_dong.buoi2.Bai14CustomListViewActivity;
import com.example.thuc_hanh_di_dong.buoi2.Bai15SpinnerActivity;
import com.example.thuc_hanh_di_dong.buoi3.Bai16MenuActivity;
import com.example.thuc_hanh_di_dong.buoi3.Bai17MainActivity;
import com.example.thuc_hanh_di_dong.buoi3.Bai18NhanSuActivity;
import com.example.thuc_hanh_di_dong.buoi4.Bai19LoginActivity;

/**
 * MainActivity - Menu chính điều hướng đến các bài tập
 * Buổi 1 -> Buổi 4
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ===== BUỔI 1: LAYOUTS & BẮT SỰ KIỆN =====

        // 1. LinearLayout
        findViewById(R.id.btnLinearLayout).setOnClickListener(v ->
                startActivity(new Intent(this, Bai01LinearLayoutActivity.class)));

        // 2. RelativeLayout
        findViewById(R.id.btnRelativeLayout).setOnClickListener(v ->
                startActivity(new Intent(this, Bai02RelativeLayoutActivity.class)));

        // 3. TableLayout
        findViewById(R.id.btnTableLayout).setOnClickListener(v ->
                startActivity(new Intent(this, Bai03TableLayoutActivity.class)));

        // 4. OnClick in XML
        findViewById(R.id.btnOnClickXml).setOnClickListener(v ->
                startActivity(new Intent(this, Bai04OnClickXmlActivity.class)));

        // 5. Inline Anonymous Listener
        findViewById(R.id.btnAnonymousListener).setOnClickListener(v ->
                startActivity(new Intent(this, Bai05AnonymousListenerActivity.class)));

        // 6. Interface OnClickListener
        findViewById(R.id.btnInterfaceOnClick).setOnClickListener(v ->
                startActivity(new Intent(this, Bai06InterfaceOnClickActivity.class)));

        // ===== BUỔI 2: ANDROID WIDGETS =====

        // BT01. Toast
        findViewById(R.id.btnBai1Toast).setOnClickListener(v ->
                startActivity(new Intent(this, Bai01ToastActivity.class)));

        // BT02. Alert Dialog
        findViewById(R.id.btnBai2AlertDialog).setOnClickListener(v ->
                startActivity(new Intent(this, Bai02AlertDialogActivity.class)));

        // BT03. Các kiểu sự kiện
        findViewById(R.id.btnBai3EventTypes).setOnClickListener(v ->
                startActivity(new Intent(this, Bai03EventTypesActivity.class)));

        // BT04. Chuyển đổi nhiệt độ
        findViewById(R.id.btnBai4Temperature).setOnClickListener(v ->
                startActivity(new Intent(this, Bai04TemperatureActivity.class)));

        // BT07. Alert Dialog + Đăng nhập
        findViewById(R.id.btnBai7AlertLogin).setOnClickListener(v ->
                startActivity(new Intent(this, Bai07AlertLoginActivity.class)));

        // BT08. Đổi năm Âm lịch
        findViewById(R.id.btnBai8NamAmLich).setOnClickListener(v ->
                startActivity(new Intent(this, Bai08NamAmLichActivity.class)));

        // BT09. Thông tin cá nhân
        findViewById(R.id.btnBai9ThongTin).setOnClickListener(v ->
                startActivity(new Intent(this, Bai09ThongTinActivity.class)));

        // BT11. ListView (String-Array)
        findViewById(R.id.btnBai11ListView).setOnClickListener(v ->
                startActivity(new Intent(this, Bai11ListViewActivity.class)));

        // BT12. ListView (ArrayList + Nhập)
        findViewById(R.id.btnBai12ListViewInput).setOnClickListener(v ->
                startActivity(new Intent(this, Bai12ListViewInputActivity.class)));

        // BT14. Custom ListView
        findViewById(R.id.btnBai14CustomListView).setOnClickListener(v ->
                startActivity(new Intent(this, Bai14CustomListViewActivity.class)));

        // BT15. Spinner + ListView
        findViewById(R.id.btnBai15Spinner).setOnClickListener(v ->
                startActivity(new Intent(this, Bai15SpinnerActivity.class)));

        // ===== BUỔI 3: MENU, CONTEXTMENU VÀ INTENT =====

        // BT16. Menu + ContextMenu
        findViewById(R.id.btnBai16Menu).setOnClickListener(v ->
                startActivity(new Intent(this, Bai16MenuActivity.class)));

        // BT17. Intent - QL Album/Bài hát
        findViewById(R.id.btnBai17Intent).setOnClickListener(v ->
                startActivity(new Intent(this, Bai17MainActivity.class)));

        // BT18. Intent - QL Nhân sự
        findViewById(R.id.btnBai18NhanSu).setOnClickListener(v ->
                startActivity(new Intent(this, Bai18NhanSuActivity.class)));

        // ===== BUỔI 4: KẾT NỐI CƠ SỞ DỮ LIỆU SQLITE =====

        // BT19. Quản lý sinh viên
        findViewById(R.id.btnBai19Database).setOnClickListener(v ->
                startActivity(new Intent(this, Bai19LoginActivity.class)));
    }
}
