-- ============================================================================
-- Ocean View Resort - Initial Data for Room Reservation System
-- ============================================================================
-- Course: CIS6003 Advanced Programming
-- Purpose: Populate tables with initial/sample data for testing
-- ============================================================================

USE oceanview_db;

-- ============================================================================
-- Insert Users (Staff Credentials)
-- ============================================================================
-- Password: 'admin123' - SHA-256 hash: 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
-- Password: 'staff123' - SHA-256 hash: 10176e7b7b24d317acfcf8d2064cfd2f24e154f7b5a96603077d5ef813d6a6b6
-- Note: In production, use proper password hashing (BCrypt recommended)

INSERT INTO users (username, password, full_name, email, role, is_active)
VALUES ('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'System Administrator',
        'admin@oceanview.lk', 'ADMIN', TRUE),
       ('staff1', '10176e7b7b24d317acfcf8d2064cfd2f24e154f7b5a96603077d5ef813d6a6b6', 'Saman Perera',
        'saman@oceanview.lk', 'STAFF', TRUE),
       ('staff2', '10176e7b7b24d317acfcf8d2064cfd2f24e154f7b5a96603077d5ef813d6a6b6', 'Nimal Silva',
        'nimal@oceanview.lk', 'STAFF', TRUE),
       ('manager', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Kamala Fernando',
        'kamala@oceanview.lk', 'ADMIN', TRUE);

-- ============================================================================
-- Insert Room Types
-- ============================================================================
INSERT INTO room_types (type_name, description, price_per_night, capacity, features, is_available)
VALUES ('Standard',
        'Comfortable room with essential amenities. Perfect for budget-conscious travelers.',
        8000.00,
        2,
        'Air Conditioning, Free WiFi, Cable TV, Mini Refrigerator, Tea/Coffee Maker, Private Bathroom',
        TRUE),

       ('Deluxe',
        'Spacious room with premium amenities and partial ocean view. Ideal for couples and small families.',
        15000.00,
        3,
        'Air Conditioning, Free WiFi, Cable TV, Mini Bar, Tea/Coffee Maker, Private Balcony, Partial Ocean View, Safe Box, Bathrobe & Slippers',
        TRUE),

       ('Suite',
        'Luxurious suite with separate living area and stunning ocean view. Perfect for special occasions.',
        25000.00,
        4,
        'Air Conditioning, Free WiFi, Cable TV, Full Mini Bar, Coffee Machine, Large Private Balcony, Full Ocean View, Jacuzzi, Safe Box, Premium Bathroom Amenities, Room Service',
        TRUE),

       ('Family Room',
        'Large room designed for families with children. Includes extra beds and kid-friendly amenities.',
        18000.00,
        5,
        'Air Conditioning, Free WiFi, Cable TV, Mini Bar, Tea/Coffee Maker, Extra Beds, Kids Play Area, Private Balcony, Garden View',
        TRUE),

       ('Honeymoon Suite',
        'Romantic suite with special decorations and amenities. Exclusively for honeymooners.',
        30000.00,
        2,
        'Air Conditioning, Free WiFi, Cable TV, Champagne on Arrival, Jacuzzi, King Size Bed, Ocean View, Flower Decoration, Romantic Lighting, Premium Toiletries, Private Balcony',
        TRUE);

-- ============================================================================
-- Insert Sample Reservations
-- ============================================================================
-- Note: Adjust dates relative to current date for realistic testing

-- Past reservation (Completed)
INSERT INTO reservations
(reservation_number, guest_name, address, contact_number, email, room_type_id,
 check_in_date, check_out_date, number_of_guests, status, special_requests, created_by)
VALUES ('RES-20260101-0001',
        'John Smith',
        '123 Main Street, Colombo 03',
        '0771234567',
        'john.smith@email.com',
        1,
        '2026-01-01',
        '2026-01-05',
        2,
        'COMPLETED',
        'Late check-in requested',
        1);

-- Current reservation (Confirmed)
INSERT INTO reservations
(reservation_number, guest_name, address, contact_number, email, room_type_id,
 check_in_date, check_out_date, number_of_guests, status, special_requests, created_by)
VALUES ('RES-20260110-0001',
        'Priya Jayawardena',
        '45 Galle Road, Mount Lavinia',
        '0772345678',
        'priya.j@email.com',
        2,
        '2026-01-10',
        '2026-01-15',
        3,
        'CONFIRMED',
        'Extra bed required, vegetarian meals',
        2);

-- Upcoming reservation
INSERT INTO reservations
(reservation_number, guest_name, address, contact_number, email, room_type_id,
 check_in_date, check_out_date, number_of_guests, status, special_requests, created_by)
VALUES ('RES-20260115-0001',
        'David Williams',
        '789 Beach Avenue, Negombo',
        '0773456789',
        'david.w@email.com',
        3,
        '2026-01-15',
        '2026-01-20',
        2,
        'CONFIRMED',
        'Anniversary celebration, flowers and cake required',
        1);

-- Future reservation
INSERT INTO reservations
(reservation_number, guest_name, address, contact_number, email, room_type_id,
 check_in_date, check_out_date, number_of_guests, status, special_requests, created_by)
VALUES ('RES-20260125-0001',
        'Aisha Mohammed',
        '321 Kandy Road, Peradeniya',
        '0774567890',
        'aisha.m@email.com',
        4,
        '2026-01-25',
        '2026-01-30',
        5,
        'CONFIRMED',
        'Family with 2 children (ages 5 and 8), high chair needed',
        2);

-- Honeymoon booking
INSERT INTO reservations
(reservation_number, guest_name, address, contact_number, email, room_type_id,
 check_in_date, check_out_date, number_of_guests, status, special_requests, created_by)
VALUES ('RES-20260201-0001',
        'Robert & Emily Taylor',
        '567 Hill Street, Kandy',
        '0775678901',
        'taylor.couple@email.com',
        5,
        '2026-02-01',
        '2026-02-07',
        2,
        'CONFIRMED',
        'Honeymoon - romantic setup, champagne, rose petals',
        1);

-- Cancelled reservation (for testing)
INSERT INTO reservations
(reservation_number, guest_name, address, contact_number, email, room_type_id,
 check_in_date, check_out_date, number_of_guests, status, special_requests, created_by)
VALUES ('RES-20260112-0001',
        'Michael Brown',
        '890 Queens Road, Colombo 07',
        '0776789012',
        'michael.b@email.com',
        2,
        '2026-01-18',
        '2026-01-22',
        2,
        'CANCELLED',
        'Travel plans changed',
        2);

-- ============================================================================
-- Insert Sample Bills
-- ============================================================================

-- Bill for completed reservation
INSERT INTO bills
(reservation_id, reservation_number, guest_name, room_type, check_in_date, check_out_date,
 number_of_nights, rate_per_night, subtotal, tax_amount, discount_amount, total_amount,
 payment_status, payment_method, payment_date, generated_by, notes)
VALUES (1,
        'RES-20260101-0001',
        'John Smith',
        'Standard',
        '2026-01-01',
        '2026-01-05',
        4,
        8000.00,
        32000.00,
        3200.00,
        0.00,
        35200.00,
        'PAID',
        'CARD',
        '2026-01-05 11:30:00',
        1,
        'Paid by Visa card ending in 1234');

-- Bill for current reservation (pending)
INSERT INTO bills
(reservation_id, reservation_number, guest_name, room_type, check_in_date, check_out_date,
 number_of_nights, rate_per_night, subtotal, tax_amount, discount_amount, total_amount,
 payment_status, generated_by, notes)
VALUES (2,
        'RES-20260110-0001',
        'Priya Jayawardena',
        'Deluxe',
        '2026-01-10',
        '2026-01-15',
        5,
        15000.00,
        75000.00,
        7500.00,
        0.00,
        82500.00,
        'PENDING',
        2,
        'Bill generated, payment pending at checkout');

-- ============================================================================
-- Insert Sample Audit Logs
-- ============================================================================

INSERT INTO audit_log (user_id, action, table_name, record_id, description, ip_address)
VALUES (1, 'LOGIN', NULL, NULL, 'User admin logged in successfully', '192.168.1.100'),
       (1, 'CREATE', 'reservations', 1, 'Created reservation RES-20260101-0001', '192.168.1.100'),
       (2, 'LOGIN', NULL, NULL, 'User staff1 logged in successfully', '192.168.1.101'),
       (2, 'CREATE', 'reservations', 2, 'Created reservation RES-20260110-0001', '192.168.1.101'),
       (1, 'VIEW', 'reservations', 1, 'Viewed reservation details', '192.168.1.100'),
       (1, 'CREATE', 'bills', 1, 'Generated bill for reservation RES-20260101-0001', '192.168.1.100');

-- ============================================================================
-- Data Statistics
-- ============================================================================
SELECT 'Sample data inserted successfully!' AS status;
SELECT 'Users: 4 (1 admin, 3 staff)' AS summary
UNION ALL
SELECT 'Room Types: 5 (Standard to Honeymoon Suite)'
UNION ALL
SELECT 'Reservations: 6 (Various statuses)'
UNION ALL
SELECT 'Bills: 2 (1 paid, 1 pending)'
UNION ALL
SELECT 'Audit Logs: 6 entries';

-- ============================================================================
-- Test Queries to Verify Data
-- ============================================================================

-- Show all users
SELECT 'Users Table:' AS info;
SELECT user_id, username, full_name, role, is_active
FROM users;

-- Show all room types
SELECT 'Room Types Table:' AS info;
SELECT room_type_id, type_name, price_per_night, capacity
FROM room_types;

-- Show all reservations
SELECT 'Reservations Table:' AS info;
SELECT reservation_number, guest_name, check_in_date, check_out_date, status
FROM reservations;

-- Show active reservations view
SELECT 'Active Reservations View:' AS info;
SELECT *
FROM v_active_reservations;

-- Show upcoming check-ins
SELECT 'Upcoming Check-ins (Next 7 Days):' AS info;
SELECT *
FROM v_upcoming_checkins;

-- ============================================================================
-- Login Credentials for Testing
-- ============================================================================
SELECT '=== TEST LOGIN CREDENTIALS ===' AS info
UNION ALL
SELECT 'Username: admin | Password: admin123 | Role: ADMIN'
UNION ALL
SELECT 'Username: staff1 | Password: staff123 | Role: STAFF'
UNION ALL
SELECT 'Username: staff2 | Password: staff123 | Role: STAFF'
UNION ALL
SELECT 'Username: manager | Password: admin123 | Role: ADMIN'
UNION ALL
SELECT '=== IMPORTANT: Passwords are SHA-256 hashed ===';

-- ============================================================================
-- End of data.sql
-- ============================================================================
