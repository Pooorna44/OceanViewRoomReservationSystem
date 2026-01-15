package lk.icbt.oceanview.oceanviewroomreservationsystem.dao;

import lk.icbt.oceanview.oceanviewroomreservationsystem.model.RoomType;

import java.sql.SQLException;
import java.util.List;


public interface RoomTypeDAO {


    RoomType findById(int roomTypeId) throws SQLException;

    RoomType findByName(String typeName) throws SQLException;

    List<RoomType> findAll() throws SQLException;

    List<RoomType> findAllAvailable() throws SQLException;

    List<RoomType> findByCapacity(int minCapacity) throws SQLException;

    int save(RoomType roomType) throws SQLException;

    boolean update(RoomType roomType) throws SQLException;

    boolean delete(int roomTypeId) throws SQLException;

    boolean typeNameExists(String typeName) throws SQLException;
}
