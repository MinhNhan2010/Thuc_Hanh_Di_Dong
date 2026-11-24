package com.example.thuc_hanh_di_dong.buoi4;

import android.app.Activity;
import android.app.AlertDialog;

/**
 * Buổi 4: Lớp Notify hỗ trợ hiển thị thông báo và xác nhận thoát
 * Đúng theo giáo trình Android TDMU
 */
@SuppressWarnings("unused")
public class Notify {

    public static void exit(Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Xác nhận")
                .setMessage("Bạn có chắc chắn muốn thoát?")
                .setPositiveButton("Có", (dialog, which) -> activity.finish())
                .setNegativeButton("Không", null)
                .show();
    }

    public static void showDialog(Activity activity, String title, String msg) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Đồng ý", null)
                .show();
    }
}
