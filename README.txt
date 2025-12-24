================================================================================
               HỆ THỐNG ĐẶT VÉ SEA GAMES 2025
              HƯỚNG DẪN CÀI ĐẶT VÀ CHẠY ỨNG DỤNG
================================================================================

I. YÊU CẦU HỆ THỐNG
--------------------------------------------------------------------------------
1. Java Development Kit (JDK) 17 hoặc 21
   - Download: https://adoptium.net/
   
2. MySQL Server 8.0+
   - Download: https://dev.mysql.com/downloads/mysql/
   
3. Spring Tool Suite 4 (STS) hoặc Eclipse với Spring Tools plugin
   - Download: https://spring.io/tools

4. Maven (thường đã được tích hợp trong STS)

================================================================================

II. CÀI ĐẶT MySQL VÀ TẠO DATABASE
--------------------------------------------------------------------------------
Bước 1: Mở MySQL Command Line hoặc MySQL Workbench

Bước 2: Đăng nhập với tài khoản root

Bước 3: Chạy lệnh sau để tạo database:

   CREATE DATABASE seagame_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

Bước 4: (Tùy chọn) Nếu bạn có mật khẩu MySQL, mở file:
   src/main/resources/application.properties
   
   Tìm dòng:
   spring.datasource.password=
   
   Sửa thành:
   spring.datasource.password=YOUR_PASSWORD

================================================================================

III. IMPORT PROJECT VÀO SPRING TOOL SUITE
--------------------------------------------------------------------------------
Bước 1: Mở Spring Tool Suite

Bước 2: Chọn File -> Import...

Bước 3: Chọn Maven -> Existing Maven Projects -> Next

Bước 4: Click "Browse..." và chọn thư mục chứa project (thư mục có file pom.xml)

Bước 5: Đảm bảo file pom.xml được tick chọn -> Finish

Bước 6: Đợi STS tải các dependencies (có thể mất vài phút lần đầu)

================================================================================

IV. CHẠY ỨNG DỤNG
--------------------------------------------------------------------------------
Cách 1: Trong STS
   1. Click chuột phải vào project
   2. Chọn Run As -> Spring Boot App
   3. Đợi console hiện "Started Application in X seconds"

Cách 2: Bằng Maven (Terminal)
   1. Mở Terminal trong thư mục project
   2. Chạy lệnh: mvnw spring-boot:run (Windows) 
                 hoặc ./mvnw spring-boot:run (Linux/Mac)

Sau khi chạy thành công, mở trình duyệt và truy cập:
   http://localhost:8080

================================================================================

V. NHẬP DỮ LIỆU MẪU
--------------------------------------------------------------------------------
Sau khi ứng dụng chạy lần đầu, 5 bảng sẽ được tạo tự động trong MySQL.

Để có dữ liệu test, chạy file SQL:
   src/main/resources/data.sql

Cách chạy:
   1. Mở MySQL Workbench hoặc phpMyAdmin
   2. Chọn database "seagame_db"
   3. Mở file data.sql và chạy toàn bộ script

================================================================================

VI. TÀI KHOẢN TEST
--------------------------------------------------------------------------------
Sau khi nhập dữ liệu mẫu:

   Admin:
   - Username: admin
   - Password: admin123

   User thường:
   - Username: user1, user2, user3
   - Password: 123456

================================================================================

VII. CẤU TRÚC PROJECT (CLEAN ARCHITECTURE)
--------------------------------------------------------------------------------
edu.thanglong
├── domain                      # Lớp lõi - Không phụ thuộc gì
│   ├── entity                  # Các entity: User, Match, Seat, Ticket...
│   └── repository              # Interface repository (ports)
│
├── application                 # Lớp nghiệp vụ
│   ├── dto                     # Data Transfer Objects
│   └── service                 # Các service: AuthService, BookingService...
│
├── infrastructure              # Lớp hạ tầng
│   ├── persistence             # JPA Repository implementations
│   └── config                  # Cấu hình: WebConfig, AuthInterceptor
│
├── presentation                # Lớp giao diện
│   └── controller              # Controllers: AuthController, BookingController...
│
└── Application.java            # Main class

================================================================================

VIII. CÁC CHỨC NĂNG CHÍNH
--------------------------------------------------------------------------------
1. ĐĂNG KÝ / ĐĂNG NHẬP
   - Truy cập: /login, /register
   - Hỗ trợ phân quyền USER và ADMIN

2. XEM TRẬN ĐẤU
   - Trang chủ: /
   - Danh sách trận: /matches

3. ĐẶT VÉ
   - Chọn trận đấu -> Click "Đặt vé"
   - Chọn ghế trên sơ đồ sân vận động
   - Xem vé đã đặt: /my-tickets

4. QUẢN TRỊ (ADMIN)
   - Dashboard: /admin
   - Danh sách vé: /admin/tickets
   - Check-in vé: /admin/checkin

================================================================================

IX. XỬ LÝ LỖI THƯỜNG GẶP
--------------------------------------------------------------------------------
1. "Cannot connect to MySQL"
   -> Kiểm tra MySQL đã chạy chưa
   -> Kiểm tra username/password trong application.properties

2. "Port 8080 already in use"
   -> Đổi cổng trong application.properties:
      server.port=8081

3. "Table doesn't exist"
   -> Chạy lại ứng dụng, JPA sẽ tự tạo bảng
   -> Hoặc kiểm tra ddl-auto=update trong application.properties

4. "Access denied"
   -> Kiểm tra lại password MySQL
   -> Đảm bảo user có quyền trên database seagame_db

================================================================================

X. LIÊN HỆ
--------------------------------------------------------------------------------
Đại học Thăng Long - Bộ môn Công nghệ Thông tin
Project: Bài tập lớn Quản lý Dự án và Hệ thống Thông tin

================================================================================
                           CHÚC BẠN THÀNH CÔNG!
================================================================================
