# Thực Hành Lập Trình Di Động (Android Java)

Ứng dụng Android viết **100% bằng Java thuần**, tổng hợp toàn bộ bài tập thực hành từ **Buổi 1 đến Buổi 4** (học phần Lập trình Di động — GV: Nguyễn Hữu Vĩnh, ĐH Thủ Dầu Một). Mỗi bài tập có thể truy cập trực tiếp từ menu điều hướng chính [`MainActivity`](app/src/main/java/com/example/thuc_hanh_di_dong/MainActivity.java).

> Toàn bộ các file layout XML, menu XML và `AndroidManifest.xml` đã được tổ chức theo quy chuẩn đặt tên `buoiX_baiXX_...` và gắn chú thích `<!-- ⭐ QUAN TRỌNG: ... -->` tại các thuộc tính trọng tâm để thuận tiện ôn tập và bảo vệ đồ án.

---

## Mục lục

- [1. Tính năng & Nội dung theo Buổi](#1-tính-năng--nội-dung-theo-buổi)
- [2. Công nghệ & Thông số kỹ thuật](#2-công-nghệ--thông-số-kỹ-thuật)
- [3. Yêu cầu môi trường & Cài đặt](#3-yêu-cầu-môi-trường--cài-đặt)
- [4. Cấu trúc thư mục](#4-cấu-trúc-thư-mục)
- [5. Kiến trúc & Cơ sở dữ liệu SQLite (Buổi 4)](#5-kiến-trúc--cơ-sở-dữ-liệu-sqlite-buổi-4)
- [6. Lệnh Build, Kiểm tra & Chạy ứng dụng](#6-lệnh-build-kiểm-tra--chạy-ứng-dụng)
- [7. Khắc phục sự cố thường gặp](#7-khắc-phục-sự-cố-thường-gặp)

---

## 1. Tính năng & Nội dung theo Buổi

| Buổi | Chủ đề chính | Các bài tập |
|---|---|---|
| **Buổi 1** | **Layouts & Bắt sự kiện cơ bản** | • **Bài 01**: `LinearLayout` (`vertical`, `horizontal`, `layout_weight`, `weightSum`)<br>• **Bài 02**: `RelativeLayout` (`layout_below`, `layout_toRightOf`, `layout_alignParentRight`)<br>• **Bài 03**: `TableLayout` (`stretchColumns`, `layout_span`, `layout_column`)<br>• **Bài 04**: Bắt sự kiện `android:onClick` trực tiếp trong XML<br>• **Bài 05**: Xử lý sự kiện bằng Inline Anonymous Listener<br>• **Bài 06**: Kế thừa Interface `View.OnClickListener` (`setOnClickListener(this)`) |
| **Buổi 2** | **Android Widgets, ListView & Spinner** | • **BT01**: `Toast` (SHORT, LONG, TOP, CENTER, Custom View qua `LayoutInflater`)<br>• **BT02**: `AlertDialog` xác nhận thoát ứng dụng<br>• **BT03**: 5 kiểu lập trình sự kiện trên phép tính 2 số (`+`, `-`, `*`, `/`, `ƯCLN`)<br>• **BT04**: Chuyển đổi nhiệt độ `°C ↔ °F`<br>• **BT07**: Form Đăng nhập kết hợp `CheckBox` & `AlertDialog`<br>• **BT08**: Đổi năm Dương lịch → năm Âm lịch (`Can` + `Chi`)<br>• **BT09**: Form Thông tin cá nhân (`EditText`, `RadioGroup`, `CheckBox`, Validation)<br>• **BT11**: `ListView` đọc dữ liệu tĩnh từ `string-array`<br>• **BT12**: `ListView` kết hợp `ArrayList` + thêm phần tử động (`notifyDataSetChanged`)<br>• **BT14**: Custom `ListView` với Adapter tùy biến (`ImageView` + `TextView`)<br>• **BT15**: `Spinner` kết hợp `ListView` theo mô hình Master–Detail |
| **Buổi 3** | **Menu, ContextMenu & Intent** | • **BT16**: Quản lý công việc với `DatePickerDialog`, `TimePickerDialog`, `OptionsMenu` & `ContextMenu`<br>• **BT17**: Quản lý Album & Bài hát sử dụng Explicit Intent, Dialog Activity Theme, `Spinner`, và Implicit Intent (mở Web)<br>• **BT18**: Quản lý Nhân sự giao tiếp hai chiều giữa Activity bằng `Intent` + `startActivityForResult` |
| **Buổi 4** | **SQLite & Quản lý Sinh viên (BT19)** | • Đăng nhập / Đăng ký tài khoản với bảng `User` (SQLite) & ghi nhớ mật khẩu (`SharedPreferences`)<br>• Menu chính hiển thị Dashboard thống kê trực tiếp (tổng lớp, tổng SV, Nam/Nữ, Điểm TB, SV Giỏi/Xuất sắc) & Đổi mật khẩu<br>• Quản lý Lớp học: CRUD đầy đủ, tìm kiếm theo mã/tên lớp, đếm sĩ số thực tế / tối đa (`soSVHienTai/siSo`), `ContextMenu`<br>• Quản lý Sinh viên: CRUD đầy đủ, tìm kiếm đa trường (Mã SV, Tên, SĐT, Email, Địa chỉ), lọc theo lớp, sắp xếp theo Mã/Tên/Điểm TB, `DatePickerDialog`, kiểm tra trùng khóa chính, kiểm tra sĩ số lớp, tính xếp loại học lực |

---

## 2. Công nghệ & Thông số kỹ thuật

- **Ngôn ngữ**: Java 11 (`sourceCompatibility = JavaVersion.VERSION_11`, `targetCompatibility = JavaVersion.VERSION_11`)
- **UI Framework**: Android SDK truyền thống (XML Views, `Activity`, `ListView`, `Spinner`, `AlertDialog`, `Menu`) — không dùng Jetpack Compose hay thư viện bên thứ ba phức tạp
- **Lưu trữ cục bộ**:
  - `SQLite` thông qua [`SQLiteOpenHelper`](app/src/main/java/com/example/thuc_hanh_di_dong/buoi4/DatabaseHelper.java) (`DB_NAME = "QLSinhVien.db"`, `DB_VERSION = 2`)
  - `SharedPreferences` (`QLSV_PREFS`) cho tính năng ghi nhớ đăng nhập
- **Cấu hình Build**:
  - Namespace / Application ID: `com.example.thuc_hanh_di_dong`
  - Android Gradle Plugin (AGP): `9.5.0`
  - Gradle Wrapper: `9.6.0`
  - `minSdk`: `24` (Android 7.0)
  - `compileSdk` / `targetSdk`: `37`
- **Kiểm thử**: `junit:junit:4.13.2`, `androidx.test.ext:junit:1.1.5`, `androidx.test.espresso:espresso-core:3.5.1`

---

## 3. Yêu cầu môi trường & Cài đặt

### Yêu cầu
- **Android Studio** (khuyến nghị phiên bản hỗ trợ AGP 9.5.0 / SDK 37)
- **Android SDK** với Platform API 37 & Build Tools tương ứng
- **JDK**: Gradle Daemon đang cấu hình `toolchainVersion=25` trong [`gradle/gradle-daemon-jvm.properties`](gradle/gradle-daemon-jvm.properties) (và biên dịch bytecode Java 11)
- Thiết bị Android thật (bật *USB Debugging*) hoặc Android Emulator (API >= 24)

### Cài đặt & Khởi chạy trên máy mới

1. **Clone repository**:
   ```bash
   git clone https://github.com/MinhNhan2010/Thuc_Hanh_Di_Dong.git
   cd Thuc_Hanh_Di_Dong
   ```

2. **Mở bằng Android Studio**:
   - Chọn **File → Open...** và trỏ tới thư mục `Thuc_Hanh_Di_Dong`.
   - Android Studio sẽ tự tạo file `local.properties` chứa đường dẫn `sdk.dir` trên máy của bạn và đồng bộ Gradle (**Sync Project with Gradle Files**).

3. **Chạy ứng dụng**:
   - Chọn thiết bị/máy ảo đích rồi nhấn **Run ▶** (`Shift + F10`).
   - Tài khoản đăng nhập mặc định cho **BT19 (Buổi 4)**:
     - **Tài khoản**: `admin`
     - **Mật khẩu**: `admin`

---

## 4. Cấu trúc thư mục

```text
Thuc_Hanh_Di_Dong/
├── app/
│   ├── build.gradle.kts                  # Cấu hình SDK, Java 11, Lint & dependencies
│   └── src/main/
│       ├── AndroidManifest.xml           # Đăng ký toàn bộ Activity & Dialog theme
│       ├── java/com/example/thuc_hanh_di_dong/
│       │   ├── MainActivity.java         # Menu điều hướng chính (Buổi 1 → Buổi 4)
│       │   ├── buoi1/                    # 6 bài tập Layout & Sự kiện cơ bản
│       │   ├── buoi2/                    # 11 bài tập Widget, ListView, Spinner
│       │   ├── buoi3/                    # BT16–BT18 (Menu, ContextMenu, Intent, Models)
│       │   └── buoi4/                    # BT19 (SQLite DAO, Adapters, Models, CRUD Screens)
│       └── res/
│           ├── layout/                   # 36 file layout đặt tiền tố buoiX_baiXX_...
│           ├── menu/                     # buoi3_bai16_menu, buoi4_bai19_menu_class/student
│           └── values/                   # strings.xml (mon_hoc_array), colors.xml, themes.xml
├── gradle/
│   ├── libs.versions.toml                # Quản lý phiên bản AGP & thư viện test
│   ├── gradle-daemon-jvm.properties      # Cấu hình JVM toolchain cho Gradle Daemon
│   └── wrapper/                          # Gradle Wrapper 9.6.0
├── build.gradle.kts
├── gradle.properties
├── settings.gradle.kts
└── README.md
```

---

## 5. Kiến trúc & Cơ sở dữ liệu SQLite (Buổi 4)

### Luồng điều hướng BT19

```text
Bai19LoginActivity (SQLite checkUser + SharedPreferences)
   │
   ▼
Bai19MainMenuActivity (Dashboard thống kê + Đổi mật khẩu)
   ├──► Bai19ClassListActivity (Tìm kiếm lớp + ContextMenu)
   │       ├──► Bai19InsertClassActivity
   │       ├──► Bai19EditClassActivity
   │       └──► Chạm lớp ──► Mở Bai19StudentListActivity lọc sẵn theo mã lớp
   │
   └──► Bai19StudentListActivity (Tìm kiếm + Lọc lớp + Sắp xếp + Chi tiết)
           ├──► Bai19InsertStudentActivity (Kiểm tra sĩ số & Validation)
           └──► Bai19EditStudentActivity   (Kiểm tra chuyển lớp)
```

### Lược đồ CSDL (`QLSinhVien.db`)

```text
User
├── id        (INTEGER, PK AUTOINCREMENT)
├── username  (TEXT, NOT NULL UNIQUE)     -- Tài khoản mặc định: admin
└── password  (TEXT, NOT NULL)            -- Mật khẩu mặc định: admin

LopHoc
├── maLop     (TEXT, PK)                  -- Mẫu: CNTT01, CNTT02, KTPM01, QTKD01
├── tenLop    (TEXT, NOT NULL)
└── siSo      (INTEGER, DEFAULT 0)        -- Sĩ số tối đa của lớp

SinhVien
├── maSV        (TEXT, PK)                -- Mẫu: SV001..SV006
├── tenSV       (TEXT, NOT NULL)
├── gioiTinh    (TEXT)
├── ngaySinh    (TEXT)                    -- Định dạng dd/MM/yyyy qua DatePickerDialog
├── diaChi      (TEXT)
├── maLop       (TEXT, FK → LopHoc.maLop)
├── soDienThoai (TEXT, DEFAULT '')
├── email       (TEXT, DEFAULT '')
└── diemTB      (REAL, DEFAULT 0.0)
```

- **Truy vấn an toàn**: Mọi truy vấn đọc đều sử dụng `db.rawQuery(sql, selectionArgs)` với tham số `?` để tránh SQL Injection và bọc `Cursor` trong khối `try-with-resources`.
- **Ràng buộc nghiệp vụ**:
  - Chặn trùng khóa chính `maLop` / `maSV`.
  - Kiểm tra sĩ số tối đa khi thêm sinh viên, chuyển lớp, hoặc hạ sĩ số tối đa của lớp.
  - Validate Điểm TB (`0.0` – `10.0`), SĐT (`^0\d{8,10}$`), và Email (`Patterns.EMAIL_ADDRESS`).

---

## 6. Lệnh Build, Kiểm tra & Chạy ứng dụng

Chạy qua **Gradle Wrapper** (trên Windows dùng `.\gradlew.bat`, trên macOS/Linux dùng `./gradlew`):

| Mục đích | Lệnh (Windows PowerShell) |
|---|---|
| Biên dịch mã nguồn Java | `.\gradlew.bat compileDebugJavaWithJavac` |
| Đóng gói file APK Debug | `.\gradlew.bat assembleDebug` |
| Kiểm tra Android Lint | `.\gradlew.bat lint` |
| Chạy Unit Test | `.\gradlew.bat test` |
| Cài APK & mở app qua ADB | `adb install -r app\build\outputs\apk\debug\app-debug.apk`<br>`adb shell am start -n com.example.thuc_hanh_di_dong/.MainActivity` |

File APK đầu ra sau khi build nằm tại: `app/build/outputs/apk/debug/app-debug.apk`.

---

## 7. Khắc phục sự cố thường gặp

- **Máy ảo hiển thị trạng thái `offline` hoặc báo lỗi `AVD is already running as process ...`**:
  1. Dừng tiến trình chạy hiện tại trên Android Studio (`Ctrl + F2`).
  2. Tắt tiến trình emulator bị treo (`qemu-system-x86_64.exe` / `emulator.exe`) và xóa các file `*.lock` bên trong thư mục `C:\Users\<USER>\.android\avd\<TEN_AVD>.avd\`.
  3. Mở **Device Manager → ⋮ → Cold Boot Now** để khởi động lại máy ảo sạch trước khi nhấn **Run ▶**.
- **Đã sửa cấu trúc bảng SQLite nhưng app không cập nhật bảng mới**:
  - Tăng giá trị `DB_VERSION` trong [`DatabaseHelper.java`](app/src/main/java/com/example/thuc_hanh_di_dong/buoi4/DatabaseHelper.java) để kích hoạt `onUpgrade(...)`, hoặc gỡ cài đặt app trên máy ảo rồi chạy lại.
