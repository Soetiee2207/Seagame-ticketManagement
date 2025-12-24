-- =====================================================
-- SEA GAMES TICKET SYSTEM - SAMPLE DATA
-- Run this script after the application starts
-- to populate the database with test data
-- =====================================================

-- Clear existing data (careful in production!)
-- DELETE FROM tickets;
-- DELETE FROM seats;
-- DELETE FROM seat_categories;
-- DELETE FROM matches;
-- DELETE FROM users;

-- =====================================================
-- 1. INSERT USERS (Admin & Regular Users)
-- =====================================================
INSERT INTO users (username, password, full_name, role) VALUES
('admin', 'admin123', 'Administrator', 'ADMIN'),
('user1', '123456', 'Nguyễn Văn A', 'USER'),
('user2', '123456', 'Trần Thị B', 'USER'),
('user3', '123456', 'Lê Văn C', 'USER');

-- =====================================================
-- 2. INSERT SEAT CATEGORIES (VIP, Standard, etc.)
-- =====================================================
INSERT INTO seat_categories (category_name, price, description) VALUES
('Super VIP', 1000000.00, 'Vị trí đẹp nhất, có đồ ăn và nước uống miễn phí'),
('VIP', 500000.00, 'Vị trí tốt, có điều hòa và nước uống'),
('Standard', 200000.00, 'Vị trí thường, có mái che');

-- =====================================================
-- 3. INSERT MATCHES (Các trận đấu SEA Games)
-- =====================================================
INSERT INTO matches (match_name, start_time, location) VALUES
('Chung kết Bóng đá Nam: Việt Nam vs Thái Lan', '2025-12-10 19:00:00', 'Sân vận động Mỹ Đình'),
('Bán kết Bóng đá Nữ: Việt Nam vs Indonesia', '2025-12-08 15:00:00', 'Sân vận động Mỹ Đình'),
('Chung kết Cầu lông Đơn Nam', '2025-12-11 09:00:00', 'Nhà thi đấu Hoàng Mai'),
('Bơi lội - Chung kết 100m Tự do Nam', '2025-12-09 14:00:00', 'Trung tâm Thể thao Dưới nước'),
('Điền kinh - Chung kết 100m Nam', '2025-12-12 18:00:00', 'Sân vận động Quốc gia');

-- =====================================================
-- 4. INSERT SEATS (Ghế ngồi trong sân vận động)
-- =====================================================
-- Super VIP (Row A): A-01 to A-05
INSERT INTO seats (seat_code, category_id) VALUES
('A-01', 1), ('A-02', 1), ('A-03', 1), ('A-04', 1), ('A-05', 1);

-- VIP (Row B & C): B-01 to B-10, C-01 to C-10
INSERT INTO seats (seat_code, category_id) VALUES
('B-01', 2), ('B-02', 2), ('B-03', 2), ('B-04', 2), ('B-05', 2),
('B-06', 2), ('B-07', 2), ('B-08', 2), ('B-09', 2), ('B-10', 2),
('C-01', 2), ('C-02', 2), ('C-03', 2), ('C-04', 2), ('C-05', 2),
('C-06', 2), ('C-07', 2), ('C-08', 2), ('C-09', 2), ('C-10', 2);

-- Standard (Row D, E, F): D-01 to D-15, E-01 to E-15, F-01 to F-15
INSERT INTO seats (seat_code, category_id) VALUES
('D-01', 3), ('D-02', 3), ('D-03', 3), ('D-04', 3), ('D-05', 3),
('D-06', 3), ('D-07', 3), ('D-08', 3), ('D-09', 3), ('D-10', 3),
('D-11', 3), ('D-12', 3), ('D-13', 3), ('D-14', 3), ('D-15', 3),
('E-01', 3), ('E-02', 3), ('E-03', 3), ('E-04', 3), ('E-05', 3),
('E-06', 3), ('E-07', 3), ('E-08', 3), ('E-09', 3), ('E-10', 3),
('E-11', 3), ('E-12', 3), ('E-13', 3), ('E-14', 3), ('E-15', 3),
('F-01', 3), ('F-02', 3), ('F-03', 3), ('F-04', 3), ('F-05', 3),
('F-06', 3), ('F-07', 3), ('F-08', 3), ('F-09', 3), ('F-10', 3),
('F-11', 3), ('F-12', 3), ('F-13', 3), ('F-14', 3), ('F-15', 3);

-- =====================================================
-- END OF SAMPLE DATA
-- =====================================================
