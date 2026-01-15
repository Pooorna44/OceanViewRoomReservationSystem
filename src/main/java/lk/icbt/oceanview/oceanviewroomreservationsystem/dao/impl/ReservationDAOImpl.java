package lk.icbt.oceanview.oceanviewroomreservationsystem.dao.impl;

import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.ReservationDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Reservation;
import lk.icbt.oceanview.oceanviewroomreservationsystem.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public Reservation findById(int reservationId) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE reservation_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToReservation(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Reservation findByReservationNumber(String reservationNumber) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE reservation_number = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reservationNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToReservation(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Reservation> findAll() throws SQLException {
        String sql = "SELECT * FROM reservations ORDER BY check_in_date DESC";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }

        return reservations;
    }

    @Override
    public List<Reservation> findByStatus(String status) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE status = ? ORDER BY check_in_date DESC";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapResultSetToReservation(rs));
                }
            }
        }

        return reservations;
    }

    @Override
    public List<Reservation> findAllActive() throws SQLException {
        String sql = "SELECT * FROM reservations WHERE status != 'CANCELLED' ORDER BY check_in_date DESC";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }

        return reservations;
    }

    @Override
    public List<Reservation> findByGuestName(String guestName) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE guest_name LIKE ? COLLATE utf8mb4_unicode_ci ORDER BY check_in_date DESC";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + guestName + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapResultSetToReservation(rs));
                }
            }
        }

        return reservations;
    }

    @Override
    public List<Reservation> findByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE check_in_date BETWEEN ? AND ? ORDER BY check_in_date";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapResultSetToReservation(rs));
                }
            }
        }

        return reservations;
    }

    @Override
    public List<Reservation> findTodayCheckIns() throws SQLException {
        String sql = "SELECT * FROM reservations WHERE check_in_date = CURDATE() AND status = 'CONFIRMED' ORDER BY guest_name";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }

        return reservations;
    }

    @Override
    public List<Reservation> findTodayCheckOuts() throws SQLException {
        String sql = "SELECT * FROM reservations WHERE check_out_date = CURDATE() AND status = 'CONFIRMED' ORDER BY guest_name";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }

        return reservations;
    }

    @Override
    public List<Reservation> findUpcoming(int days) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE check_in_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY) " +
                "AND status = 'CONFIRMED' ORDER BY check_in_date";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, days);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapResultSetToReservation(rs));
                }
            }
        }

        return reservations;
    }

    @Override
    public int save(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (reservation_number, guest_name, address, contact_number, email, " +
                "room_type_id, check_in_date, check_out_date, number_of_guests, status, special_requests, " +
                "created_by, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, reservation.getReservationNumber());
            stmt.setString(2, reservation.getGuestName());
            stmt.setString(3, reservation.getAddress());
            stmt.setString(4, reservation.getContactNumber());
            stmt.setString(5, reservation.getEmail());
            stmt.setInt(6, reservation.getRoomTypeId());
            stmt.setDate(7, Date.valueOf(reservation.getCheckInDate()));
            stmt.setDate(8, Date.valueOf(reservation.getCheckOutDate()));
            stmt.setInt(9, reservation.getNumberOfGuests());
            stmt.setString(10, reservation.getStatus());
            stmt.setString(11, reservation.getSpecialRequests());
            stmt.setInt(12, reservation.getCreatedBy());
            stmt.setTimestamp(13, Timestamp.valueOf(reservation.getCreatedAt()));
            stmt.setTimestamp(14, Timestamp.valueOf(reservation.getUpdatedAt()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating reservation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int reservationId = generatedKeys.getInt(1);
                    reservation.setReservationId(reservationId);
                    return reservationId;
                } else {
                    throw new SQLException("Creating reservation failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public boolean update(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservations SET guest_name = ?, address = ?, contact_number = ?, email = ?, " +
                "room_type_id = ?, check_in_date = ?, check_out_date = ?, number_of_guests = ?, " +
                "status = ?, special_requests = ?, updated_at = ? WHERE reservation_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reservation.getGuestName());
            stmt.setString(2, reservation.getAddress());
            stmt.setString(3, reservation.getContactNumber());
            stmt.setString(4, reservation.getEmail());
            stmt.setInt(5, reservation.getRoomTypeId());
            stmt.setDate(6, Date.valueOf(reservation.getCheckInDate()));
            stmt.setDate(7, Date.valueOf(reservation.getCheckOutDate()));
            stmt.setInt(8, reservation.getNumberOfGuests());
            stmt.setString(9, reservation.getStatus());
            stmt.setString(10, reservation.getSpecialRequests());
            stmt.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(12, reservation.getReservationId());

            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int reservationId) throws SQLException {
        String sql = "DELETE FROM reservations WHERE reservation_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean cancel(int reservationId) throws SQLException {
        String sql = "UPDATE reservations SET status = 'CANCELLED', updated_at = ? WHERE reservation_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, reservationId);

            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public int completePastReservations() throws SQLException {
        String sql = "UPDATE reservations SET status = 'COMPLETED', updated_at = ? " +
                "WHERE status = 'CONFIRMED' AND check_out_date < CURDATE()";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            int updated = stmt.executeUpdate();

            if (updated > 0) {
                System.out.println("[ReservationDAO] Auto-completed " + updated + " past reservation(s)");
            }

            return updated;
        }
    }

    @Override
    public String generateReservationNumber() throws SQLException {
        String sql = "{CALL sp_generate_reservation_number(?)}";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, Types.VARCHAR);
            stmt.execute();

            return stmt.getString(1);
        }
    }

    @Override
    public int countByStatus(String status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservations WHERE status = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();

        reservation.setReservationId(rs.getInt("reservation_id"));
        reservation.setReservationNumber(rs.getString("reservation_number"));
        reservation.setGuestName(rs.getString("guest_name"));
        reservation.setAddress(rs.getString("address"));
        reservation.setContactNumber(rs.getString("contact_number"));
        reservation.setEmail(rs.getString("email"));
        reservation.setRoomTypeId(rs.getInt("room_type_id"));
        reservation.setCheckInDate(rs.getDate("check_in_date").toLocalDate());
        reservation.setCheckOutDate(rs.getDate("check_out_date").toLocalDate());
        reservation.setNumberOfGuests(rs.getInt("number_of_guests"));
        reservation.setStatus(rs.getString("status"));
        reservation.setSpecialRequests(rs.getString("special_requests"));
        reservation.setCreatedBy(rs.getInt("created_by"));
        reservation.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        reservation.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        return reservation;
    }
}
