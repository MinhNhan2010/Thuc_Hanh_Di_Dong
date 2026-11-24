package com.example.thuc_hanh_di_dong.buoi4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Buổi 4: SQLite Database Helper
 * Quản lý CSDL sinh viên: bảng User, LopHoc, SinhVien
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static class DashboardStats {
        public final int totalClasses;
        public final int totalStudents;
        public final int maleCount;
        public final int femaleCount;
        public final double avgGpa;
        public final int goodExcellentCount;

        public DashboardStats(int totalClasses, int totalStudents, int maleCount,
                              int femaleCount, double avgGpa, int goodExcellentCount) {
            this.totalClasses = totalClasses;
            this.totalStudents = totalStudents;
            this.maleCount = maleCount;
            this.femaleCount = femaleCount;
            this.avgGpa = avgGpa;
            this.goodExcellentCount = goodExcellentCount;
        }
    }

    public static final String DB_NAME = "QLSinhVien.db";
    public static final int DB_VERSION = 2;

    // Bảng User
    public static final String TABLE_USER = "User";
    public static final String COL_USER_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";

    // Bảng LopHoc
    public static final String TABLE_LOPHOC = "LopHoc";
    public static final String COL_CLASS_CODE = "maLop";
    public static final String COL_CLASS_NAME = "tenLop";
    public static final String COL_CLASS_NUMBER = "siSo";

    // Bảng SinhVien
    public static final String TABLE_SINHVIEN = "SinhVien";
    public static final String COL_SV_CODE = "maSV";
    public static final String COL_SV_NAME = "tenSV";
    public static final String COL_SV_GENDER = "gioiTinh";
    public static final String COL_SV_BIRTHDAY = "ngaySinh";
    public static final String COL_SV_ADDRESS = "diaChi";
    public static final String COL_SV_CLASS = "maLop";
    public static final String COL_SV_PHONE = "soDienThoai";
    public static final String COL_SV_EMAIL = "email";
    public static final String COL_SV_GPA = "diemTB";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng User
        db.execSQL("CREATE TABLE " + TABLE_USER + " ("
                + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USERNAME + " TEXT NOT NULL UNIQUE, "
                + COL_PASSWORD + " TEXT NOT NULL)");

        // Tạo bảng LopHoc
        db.execSQL("CREATE TABLE " + TABLE_LOPHOC + " ("
                + COL_CLASS_CODE + " TEXT PRIMARY KEY, "
                + COL_CLASS_NAME + " TEXT NOT NULL, "
                + COL_CLASS_NUMBER + " INTEGER DEFAULT 0)");

        // Tạo bảng SinhVien
        db.execSQL("CREATE TABLE " + TABLE_SINHVIEN + " ("
                + COL_SV_CODE + " TEXT PRIMARY KEY, "
                + COL_SV_NAME + " TEXT NOT NULL, "
                + COL_SV_GENDER + " TEXT, "
                + COL_SV_BIRTHDAY + " TEXT, "
                + COL_SV_ADDRESS + " TEXT, "
                + COL_SV_CLASS + " TEXT, "
                + COL_SV_PHONE + " TEXT DEFAULT '', "
                + COL_SV_EMAIL + " TEXT DEFAULT '', "
                + COL_SV_GPA + " REAL DEFAULT 0.0, "
                + "FOREIGN KEY (" + COL_SV_CLASS + ") REFERENCES " + TABLE_LOPHOC + "(" + COL_CLASS_CODE + "))");

        // Thêm tài khoản mặc định: admin/admin
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, "admin");
        cv.put(COL_PASSWORD, "admin");
        db.insert(TABLE_USER, null, cv);

        // Dữ liệu mẫu Lớp học (siSo là sĩ số tối đa của lớp)
        insertSampleClass(db, "CNTT01", "Công nghệ thông tin 01", 35);
        insertSampleClass(db, "CNTT02", "Công nghệ thông tin 02", 30);
        insertSampleClass(db, "KTPM01", "Kỹ thuật phần mềm 01", 30);
        insertSampleClass(db, "QTKD01", "Quản trị kinh doanh 01", 40);

        // Dữ liệu mẫu Sinh viên
        insertSampleStudent(db, new SinhVien("SV001", "Nguyễn Văn An", "Nam", "15/05/2004", "Thủ Dầu Một, Bình Dương", "CNTT01", "0901234567", "annv@student.edu.vn", 8.6));
        insertSampleStudent(db, new SinhVien("SV002", "Trần Thị Bích Ngọc", "Nữ", "22/08/2004", "Thuận An, Bình Dương", "CNTT01", "0912345678", "ngocttb@student.edu.vn", 9.2));
        insertSampleStudent(db, new SinhVien("SV003", "Lê Hoàng Minh", "Nam", "10/11/2004", "Dĩ An, Bình Dương", "CNTT02", "0987654321", "minhlh@student.edu.vn", 7.4));
        insertSampleStudent(db, new SinhVien("SV004", "Phạm Thu Hà", "Nữ", "03/02/2004", "Bến Cát, Bình Dương", "KTPM01", "0933445566", "hapt@student.edu.vn", 8.1));
        insertSampleStudent(db, new SinhVien("SV005", "Võ Quốc Tuấn", "Nam", "19/07/2004", "Quận 9, TP.HCM", "KTPM01", "0977889900", "tuanvq@student.edu.vn", 6.8));
        insertSampleStudent(db, new SinhVien("SV006", "Đặng Ngọc Mai", "Nữ", "28/12/2004", "Tân Uyên, Bình Dương", "QTKD01", "0966554433", "maidn@student.edu.vn", 7.9));
    }

    private void insertSampleClass(SQLiteDatabase db, String ma, String ten, int siSo) {
        ContentValues cv = new ContentValues();
        cv.put(COL_CLASS_CODE, ma);
        cv.put(COL_CLASS_NAME, ten);
        cv.put(COL_CLASS_NUMBER, siSo);
        db.insert(TABLE_LOPHOC, null, cv);
    }

    private void insertSampleStudent(SQLiteDatabase db, SinhVien sv) {
        ContentValues cv = new ContentValues();
        cv.put(COL_SV_CODE, sv.getMaSV());
        cv.put(COL_SV_NAME, sv.getTenSV());
        cv.put(COL_SV_GENDER, sv.getGioiTinh());
        cv.put(COL_SV_BIRTHDAY, sv.getNgaySinh());
        cv.put(COL_SV_ADDRESS, sv.getDiaChi());
        cv.put(COL_SV_CLASS, sv.getMaLop());
        cv.put(COL_SV_PHONE, sv.getSoDienThoai());
        cv.put(COL_SV_EMAIL, sv.getEmail());
        cv.put(COL_SV_GPA, sv.getDiemTB());
        db.insert(TABLE_SINHVIEN, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SINHVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOPHOC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // ===== USER =====
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password}
        )) {
            return cursor.getCount() > 0;
        }
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + "=?",
                new String[]{username}
        )) {
            return cursor.getCount() > 0;
        }
    }

    public boolean registerUser(String username, String password) {
        if (isUsernameExists(username)) return false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, username);
        cv.put(COL_PASSWORD, password);
        return db.insert(TABLE_USER, null, cv) > 0;
    }

    public boolean changePassword(String username, String oldPass, String newPass) {
        if (!checkUser(username, oldPass)) return false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PASSWORD, newPass);
        int updated = db.update(TABLE_USER, cv, COL_USERNAME + "=?", new String[]{username});
        return updated > 0;
    }

    // ===== LỚP HỌC =====
    public ArrayList<LopHoc> getAllClasses() {
        return getAllClasses("");
    }

    public ArrayList<LopHoc> getAllClasses(String searchQuery) {
        ArrayList<LopHoc> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder query = new StringBuilder(
                "SELECT l." + COL_CLASS_CODE + ", l." + COL_CLASS_NAME + ", l." + COL_CLASS_NUMBER + ", "
                + "(SELECT COUNT(*) FROM " + TABLE_SINHVIEN + " sv WHERE sv." + COL_SV_CLASS + " = l." + COL_CLASS_CODE + ") AS soSVHienTai "
                + "FROM " + TABLE_LOPHOC + " l"
        );
        List<String> args = new ArrayList<>();
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            query.append(" WHERE l.").append(COL_CLASS_CODE).append(" LIKE ? OR l.").append(COL_CLASS_NAME).append(" LIKE ?");
            String kw = "%" + searchQuery.trim() + "%";
            args.add(kw);
            args.add(kw);
        }
        query.append(" ORDER BY l.").append(COL_CLASS_CODE).append(" ASC");

        try (Cursor cursor = db.rawQuery(query.toString(), args.toArray(new String[0]))) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(new LopHoc(
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_CLASS_CODE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_CLASS_NAME)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COL_CLASS_NUMBER)),
                            cursor.getInt(cursor.getColumnIndexOrThrow("soSVHienTai"))
                    ));
                } while (cursor.moveToNext());
            }
        }
        return list;
    }

    public LopHoc getClassByCode(String maLop) {
        SQLiteDatabase db = getReadableDatabase();
        LopHoc lop = null;
        try (Cursor cursor = db.rawQuery(
                "SELECT l." + COL_CLASS_CODE + ", l." + COL_CLASS_NAME + ", l." + COL_CLASS_NUMBER + ", "
                + "(SELECT COUNT(*) FROM " + TABLE_SINHVIEN + " sv WHERE sv." + COL_SV_CLASS + " = l." + COL_CLASS_CODE + ") AS soSVHienTai "
                + "FROM " + TABLE_LOPHOC + " l WHERE l." + COL_CLASS_CODE + " = ?",
                new String[]{maLop}
        )) {
            if (cursor.moveToFirst()) {
                lop = new LopHoc(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CLASS_CODE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CLASS_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_CLASS_NUMBER)),
                        cursor.getInt(cursor.getColumnIndexOrThrow("soSVHienTai"))
                );
            }
        }
        return lop;
    }

    public long insertClass(LopHoc lopHoc) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_CLASS_CODE, lopHoc.getMaLop());
        cv.put(COL_CLASS_NAME, lopHoc.getTenLop());
        cv.put(COL_CLASS_NUMBER, lopHoc.getSiSo());
        return db.insert(TABLE_LOPHOC, null, cv);
    }

    public int updateClass(LopHoc lopHoc) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_CLASS_NAME, lopHoc.getTenLop());
        cv.put(COL_CLASS_NUMBER, lopHoc.getSiSo());
        return db.update(TABLE_LOPHOC, cv, COL_CLASS_CODE + "=?", new String[]{lopHoc.getMaLop()});
    }

    public int deleteClass(String maLop) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SINHVIEN, COL_SV_CLASS + "=?", new String[]{maLop});
        return db.delete(TABLE_LOPHOC, COL_CLASS_CODE + "=?", new String[]{maLop});
    }

    // ===== SINH VIÊN =====
    @SuppressWarnings("unused")
    public ArrayList<SinhVien> getAllStudents() {
        return searchAndFilterStudents("", "", 0);
    }

    public ArrayList<SinhVien> searchAndFilterStudents(String keyword, String maLopFilter, int sortOrder) {
        ArrayList<SinhVien> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        List<String> conditions = new ArrayList<>();
        List<String> args = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            conditions.add("(" + COL_SV_CODE + " LIKE ? OR " + COL_SV_NAME + " LIKE ? OR "
                    + COL_SV_ADDRESS + " LIKE ? OR " + COL_SV_PHONE + " LIKE ? OR " + COL_SV_EMAIL + " LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            for (int i = 0; i < 5; i++) {
                args.add(kw);
            }
        }

        if (maLopFilter != null && !maLopFilter.trim().isEmpty() && !"Tất cả".equals(maLopFilter)) {
            conditions.add(COL_SV_CLASS + " = ?");
            args.add(maLopFilter);
        }

        String whereClause = conditions.isEmpty() ? "" : "WHERE " + TextUtils.join(" AND ", conditions);
        String orderClause;
        if (sortOrder == 1) {
            orderClause = "ORDER BY " + COL_SV_NAME + " COLLATE NOCASE ASC";
        } else if (sortOrder == 2) {
            orderClause = "ORDER BY " + COL_SV_GPA + " DESC, " + COL_SV_CODE + " ASC";
        } else if (sortOrder == 3) {
            orderClause = "ORDER BY " + COL_SV_GPA + " ASC, " + COL_SV_CODE + " ASC";
        } else {
            orderClause = "ORDER BY " + COL_SV_CODE + " ASC";
        }

        try (Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_SINHVIEN + " " + whereClause + " " + orderClause,
                args.toArray(new String[0])
        )) {
            if (cursor.moveToFirst()) {
                do {
                    int phoneIdx = cursor.getColumnIndex(COL_SV_PHONE);
                    int emailIdx = cursor.getColumnIndex(COL_SV_EMAIL);
                    int gpaIdx = cursor.getColumnIndex(COL_SV_GPA);

                    String gender = cursor.getString(cursor.getColumnIndexOrThrow(COL_SV_GENDER));
                    String birthday = cursor.getString(cursor.getColumnIndexOrThrow(COL_SV_BIRTHDAY));
                    String address = cursor.getString(cursor.getColumnIndexOrThrow(COL_SV_ADDRESS));
                    String classCode = cursor.getString(cursor.getColumnIndexOrThrow(COL_SV_CLASS));
                    String phone = phoneIdx >= 0 ? cursor.getString(phoneIdx) : "";
                    String email = emailIdx >= 0 ? cursor.getString(emailIdx) : "";
                    double gpa = gpaIdx >= 0 ? cursor.getDouble(gpaIdx) : 0.0;

                    list.add(new SinhVien(
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_SV_CODE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_SV_NAME)),
                            gender != null ? gender : "Nam",
                            birthday != null ? birthday : "",
                            address != null ? address : "",
                            classCode != null ? classCode : "",
                            phone != null ? phone : "",
                            email != null ? email : "",
                            gpa
                    ));
                } while (cursor.moveToNext());
            }
        }
        return list;
    }

    public long insertStudent(SinhVien sv) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_SV_CODE, sv.getMaSV());
        cv.put(COL_SV_NAME, sv.getTenSV());
        cv.put(COL_SV_GENDER, sv.getGioiTinh());
        cv.put(COL_SV_BIRTHDAY, sv.getNgaySinh());
        cv.put(COL_SV_ADDRESS, sv.getDiaChi());
        cv.put(COL_SV_CLASS, sv.getMaLop());
        cv.put(COL_SV_PHONE, sv.getSoDienThoai());
        cv.put(COL_SV_EMAIL, sv.getEmail());
        cv.put(COL_SV_GPA, sv.getDiemTB());
        return db.insert(TABLE_SINHVIEN, null, cv);
    }

    public int updateStudent(SinhVien sv) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_SV_NAME, sv.getTenSV());
        cv.put(COL_SV_GENDER, sv.getGioiTinh());
        cv.put(COL_SV_BIRTHDAY, sv.getNgaySinh());
        cv.put(COL_SV_ADDRESS, sv.getDiaChi());
        cv.put(COL_SV_CLASS, sv.getMaLop());
        cv.put(COL_SV_PHONE, sv.getSoDienThoai());
        cv.put(COL_SV_EMAIL, sv.getEmail());
        cv.put(COL_SV_GPA, sv.getDiemTB());
        return db.update(TABLE_SINHVIEN, cv, COL_SV_CODE + "=?", new String[]{sv.getMaSV()});
    }

    public int deleteStudent(String maSV) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_SINHVIEN, COL_SV_CODE + "=?", new String[]{maSV});
    }

    public ArrayList<String> getClassCodes() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT " + COL_CLASS_CODE + " FROM " + TABLE_LOPHOC + " ORDER BY " + COL_CLASS_CODE + " ASC", null)) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }
        return list;
    }

    public DashboardStats getDashboardStats() {
        SQLiteDatabase db = getReadableDatabase();
        int totalClasses = 0;
        try (Cursor c1 = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_LOPHOC, null)) {
            if (c1.moveToFirst()) {
                totalClasses = c1.getInt(0);
            }
        }

        int totalStudents = 0;
        int maleCount = 0;
        int femaleCount = 0;
        double avgGpa = 0.0;
        int goodExcellentCount = 0;

        try (Cursor c2 = db.rawQuery(
                "SELECT COUNT(*), "
                + "SUM(CASE WHEN " + COL_SV_GENDER + " = 'Nam' THEN 1 ELSE 0 END), "
                + "SUM(CASE WHEN " + COL_SV_GENDER + " = 'Nữ' THEN 1 ELSE 0 END), "
                + "AVG(" + COL_SV_GPA + "), "
                + "SUM(CASE WHEN " + COL_SV_GPA + " >= 8.0 THEN 1 ELSE 0 END) "
                + "FROM " + TABLE_SINHVIEN, null
        )) {
            if (c2.moveToFirst()) {
                totalStudents = c2.getInt(0);
                maleCount = c2.getInt(1);
                femaleCount = c2.getInt(2);
                avgGpa = totalStudents > 0 ? c2.getDouble(3) : 0.0;
                goodExcellentCount = c2.getInt(4);
            }
        }

        return new DashboardStats(totalClasses, totalStudents, maleCount, femaleCount, avgGpa, goodExcellentCount);
    }
}
