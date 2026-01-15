package lk.icbt.oceanview.oceanviewroomreservationsystem.dao.impl;

import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.RoomTypeDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.RoomType;
import lk.icbt.oceanview.oceanviewroomreservationsystem.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class RoomTypeDAOImpl implements RoomTypeDAO {

    @Override
    public RoomType findById(int roomTypeId) throws SQLException {
        String sql = "SELECT * FROM room_types WHERE room_type_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomTypeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoomType(rs);
                }
            }
        }

        return null;
    }

    @Override
    public RoomType findByName(String typeName) throws SQLException {
        String sql = "SELECT * FROM room_types WHERE type_name = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, typeName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoomType(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<RoomType> findAll() throws SQLException {
        String sql = "SELECT * FROM room_types ORDER BY type_name";
        List<RoomType> roomTypes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                roomTypes.add(mapResultSetToRoomType(rs));
            }
        }

        return roomTypes;
    }

    @Override
    public List<RoomType> findAllAvailable() throws SQLException {
        String sql = "SELECT * FROM room_types WHERE is_available = TRUE ORDER BY type_name";
        List<RoomType> roomTypes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                roomTypes.add(mapResultSetToRoomType(rs));
            }
        }

        return roomTypes;
    }

    @Override
    public List<RoomType> findByCapacity(int minCapacity) throws SQLException {
        String sql = "SELECT * FROM room_types WHERE capacity >= ? ORDER BY capacity";
        List<RoomType> roomTypes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, minCapacity);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roomTypes.add(mapResultSetToRoomType(rs));
                }
            }
        }

        return roomTypes;
    }

    @Override
    public int save(RoomType roomType) throws SQLException {
        String sql = "INSERT INTO room_types (type_name, description, price_per_night, capacity, features, is_available, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, roomType.getTypeName());
            stmt.setString(2, roomType.getDescription());
            stmt.setBigDecimal(3, roomType.getPricePerNight());
            stmt.setInt(4, roomType.getCapacity());
            stmt.setString(5, roomType.getFeatures());
            stmt.setBoolean(6, roomType.isAvailable());
            stmt.setTimestamp(7, Timestamp.valueOf(roomType.getCreatedAt()));
            stmt.setTimestamp(8, Timestamp.valueOf(roomType.getUpdatedAt()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating room type failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int roomTypeId = generatedKeys.getInt(1);
                    roomType.setRoomTypeId(roomTypeId);
                    return roomTypeId;
                } else {
                    throw new SQLException("Creating room type failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public boolean update(RoomType roomType) throws SQLException {
        String sql = "UPDATE room_types SET type_name = ?, description = ?, price_per_night = ?, " +
                "capacity = ?, features = ?, is_available = ?, updated_at = ? WHERE room_type_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, roomType.getTypeName());
            stmt.setString(2, roomType.getDescription());
            stmt.setBigDecimal(3, roomType.getPricePerNight());
            stmt.setInt(4, roomType.getCapacity());
            stmt.setString(5, roomType.getFeatures());
            stmt.setBoolean(6, roomType.isAvailable());
            stmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(8, roomType.getRoomTypeId());

            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int roomTypeId) throws SQLException {
        String sql = "DELETE FROM room_types WHERE room_type_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomTypeId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean typeNameExists(String typeName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM room_types WHERE type_name = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, typeName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    private RoomType mapResultSetToRoomType(ResultSet rs) throws SQLException {
        RoomType roomType = new RoomType();

        roomType.setRoomTypeId(rs.getInt("room_type_id"));
        roomType.setTypeName(rs.getString("type_name"));
        roomType.setDescription(rs.getString("description"));
        roomType.setPricePerNight(rs.getBigDecimal("price_per_night"));
        roomType.setCapacity(rs.getInt("capacity"));
        roomType.setFeatures(rs.getString("features"));
        roomType.setAvailable(rs.getBoolean("is_available"));
        roomType.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        roomType.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        return roomType;
    }
}
