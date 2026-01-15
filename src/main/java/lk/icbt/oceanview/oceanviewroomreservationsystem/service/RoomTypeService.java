package lk.icbt.oceanview.oceanviewroomreservationsystem.service;

import lk.icbt.oceanview.oceanviewroomreservationsystem.model.RoomType;

import java.util.List;

public interface RoomTypeService {
    List<RoomType> getAllAvailableRoomTypes();

    RoomType getRoomTypeById(int roomTypeId);

    List<RoomType> getAllRoomTypes();

    List<RoomType> findSuitableRoomTypes(int numberOfGuests);
}
