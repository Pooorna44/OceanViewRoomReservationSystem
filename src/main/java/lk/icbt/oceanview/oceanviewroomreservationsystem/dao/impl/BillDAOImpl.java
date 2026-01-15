package lk.icbt.oceanview.oceanviewroomreservationsystem.dao.impl;

import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.BillDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Bill;
import lk.icbt.oceanview.oceanviewroomreservationsystem.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class BillDAOImpl implements BillDAO {

    @Override
    public Bill findById(int billId) throws SQLException {
        String sql = "SELECT * FROM bills WHERE bill_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBill(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Bill findByReservationId(int reservationId) throws SQLException {
        String sql = "SELECT * FROM bills WHERE reservation_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBill(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Bill findByReservationNumber(String reservationNumber) throws SQLException {
        String sql = "SELECT * FROM bills WHERE reservation_number = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reservationNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBill(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Bill> findAll() throws SQLException {
        String sql = "SELECT * FROM bills ORDER BY generated_at DESC";
        List<Bill> bills = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bills.add(mapResultSetToBill(rs));
            }
        }

        return bills;
    }

    @Override
    public List<Bill> findByPaymentStatus(String paymentStatus) throws SQLException {
        String sql = "SELECT * FROM bills WHERE payment_status = ? ORDER BY generated_at DESC";
        List<Bill> bills = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paymentStatus);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        }

        return bills;
    }

    @Override
    public List<Bill> findAllPending() throws SQLException {
        return findByPaymentStatus("PENDING");
    }

    @Override
    public List<Bill> findAllPaid() throws SQLException {
        return findByPaymentStatus("PAID");
    }

    @Override
    public List<Bill> findByGuestName(String guestName) throws SQLException {
        String sql = "SELECT * FROM bills WHERE guest_name LIKE ? COLLATE utf8mb4_unicode_ci ORDER BY generated_at DESC";
        List<Bill> bills = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + guestName + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        }

        return bills;
    }

    @Override
    public List<Bill> findByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT * FROM bills WHERE DATE(generated_at) BETWEEN ? AND ? ORDER BY generated_at DESC";
        List<Bill> bills = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        }

        return bills;
    }

    @Override
    public int save(Bill bill) throws SQLException {
        String sql = "INSERT INTO bills (reservation_id, reservation_number, guest_name, room_type, " +
                "check_in_date, check_out_date, number_of_nights, rate_per_night, subtotal, " +
                "tax_amount, discount_amount, total_amount, payment_status, payment_method, " +
                "payment_date, generated_by, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, bill.getReservationId());
            stmt.setString(2, bill.getReservationNumber());
            stmt.setString(3, bill.getGuestName());
            stmt.setString(4, bill.getRoomType());
            stmt.setDate(5, bill.getCheckInDate() != null ? Date.valueOf(bill.getCheckInDate()) : null);
            stmt.setDate(6, bill.getCheckOutDate() != null ? Date.valueOf(bill.getCheckOutDate()) : null);
            stmt.setInt(7, bill.getNumberOfNights());
            stmt.setBigDecimal(8, bill.getRatePerNight());
            stmt.setBigDecimal(9, bill.getSubtotal());
            stmt.setBigDecimal(10, bill.getTaxAmount());
            stmt.setBigDecimal(11, bill.getDiscountAmount());
            stmt.setBigDecimal(12, bill.getTotalAmount());
            stmt.setString(13, bill.getPaymentStatus());
            stmt.setString(14, bill.getPaymentMethod());
            stmt.setTimestamp(15, bill.getPaymentDate() != null ? Timestamp.valueOf(bill.getPaymentDate()) : null);
            stmt.setInt(16, bill.getGeneratedBy());
            stmt.setString(17, bill.getNotes());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int billId = generatedKeys.getInt(1);
                    bill.setBillId(billId);
                    return billId;
                } else {
                    throw new SQLException("Creating bill failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public boolean update(Bill bill) throws SQLException {
        String sql = "UPDATE bills SET reservation_id = ?, reservation_number = ?, guest_name = ?, " +
                "room_type = ?, check_in_date = ?, check_out_date = ?, number_of_nights = ?, " +
                "rate_per_night = ?, subtotal = ?, tax_amount = ?, discount_amount = ?, " +
                "total_amount = ?, payment_status = ?, payment_method = ?, payment_date = ?, " +
                "notes = ? WHERE bill_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bill.getReservationId());
            stmt.setString(2, bill.getReservationNumber());
            stmt.setString(3, bill.getGuestName());
            stmt.setString(4, bill.getRoomType());
            stmt.setDate(5, bill.getCheckInDate() != null ? Date.valueOf(bill.getCheckInDate()) : null);
            stmt.setDate(6, bill.getCheckOutDate() != null ? Date.valueOf(bill.getCheckOutDate()) : null);
            stmt.setInt(7, bill.getNumberOfNights());
            stmt.setBigDecimal(8, bill.getRatePerNight());
            stmt.setBigDecimal(9, bill.getSubtotal());
            stmt.setBigDecimal(10, bill.getTaxAmount());
            stmt.setBigDecimal(11, bill.getDiscountAmount());
            stmt.setBigDecimal(12, bill.getTotalAmount());
            stmt.setString(13, bill.getPaymentStatus());
            stmt.setString(14, bill.getPaymentMethod());
            stmt.setTimestamp(15, bill.getPaymentDate() != null ? Timestamp.valueOf(bill.getPaymentDate()) : null);
            stmt.setString(16, bill.getNotes());
            stmt.setInt(17, bill.getBillId());

            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int billId) throws SQLException {
        String sql = "DELETE FROM bills WHERE bill_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean markAsPaid(int billId, String paymentMethod) throws SQLException {
        String sql = "UPDATE bills SET payment_status = 'PAID', payment_method = ?, payment_date = ? WHERE bill_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paymentMethod);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, billId);

            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public int countByPaymentStatus(String paymentStatus) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bills WHERE payment_status = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paymentStatus);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();

        bill.setBillId(rs.getInt("bill_id"));
        bill.setReservationId(rs.getInt("reservation_id"));
        bill.setReservationNumber(rs.getString("reservation_number"));
        bill.setGuestName(rs.getString("guest_name"));
        bill.setRoomType(rs.getString("room_type"));

        Date checkIn = rs.getDate("check_in_date");
        if (checkIn != null) bill.setCheckInDate(checkIn.toLocalDate());

        Date checkOut = rs.getDate("check_out_date");
        if (checkOut != null) bill.setCheckOutDate(checkOut.toLocalDate());

        bill.setNumberOfNights(rs.getInt("number_of_nights"));
        bill.setRatePerNight(rs.getBigDecimal("rate_per_night"));
        bill.setSubtotal(rs.getBigDecimal("subtotal"));
        bill.setTaxAmount(rs.getBigDecimal("tax_amount"));
        bill.setDiscountAmount(rs.getBigDecimal("discount_amount"));
        bill.setTotalAmount(rs.getBigDecimal("total_amount"));
        bill.setPaymentStatus(rs.getString("payment_status"));
        bill.setPaymentMethod(rs.getString("payment_method"));

        Timestamp paymentDate = rs.getTimestamp("payment_date");
        if (paymentDate != null) bill.setPaymentDate(paymentDate.toLocalDateTime());

        bill.setGeneratedBy(rs.getInt("generated_by"));
        bill.setNotes(rs.getString("notes"));

        Timestamp generatedAt = rs.getTimestamp("generated_at");
        if (generatedAt != null) bill.setCreatedAt(generatedAt.toLocalDateTime());

        return bill;
    }
}
