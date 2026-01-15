package lk.icbt.oceanview.oceanviewroomreservationsystem.service.impl;

import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.RoomTypeDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.factory.DAOFactory;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.RoomType;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.RoomTypeService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeDAO roomTypeDAO;

    public RoomTypeServiceImpl() {
        this.roomTypeDAO = DAOFactory.getRoomTypeDAO();
    }

    @Override
    public List<RoomType> getAllAvailableRoomTypes() {
        try {
            return roomTypeDAO.findAllAvailable();
        } catch (SQLException e) {
            System.err.println("[RoomTypeService] Error getting available room types: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public RoomType getRoomTypeById(int roomTypeId) {
        try {
            return roomTypeDAO.findById(roomTypeId);
        } catch (SQLException e) {
            System.err.println("[RoomTypeService] Error getting room type: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<RoomType> getAllRoomTypes() {
        try {
            return roomTypeDAO.findAll();
        } catch (SQLException e) {
            System.err.println("[RoomTypeService] Error getting room types: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<RoomType> findSuitableRoomTypes(int numberOfGuests) {
        try {
            return roomTypeDAO.findByCapacity(numberOfGuests);
        } catch (SQLException e) {
            System.err.println("[RoomTypeService] Error finding suitable room types: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
