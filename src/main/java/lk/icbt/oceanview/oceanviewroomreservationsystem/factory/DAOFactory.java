package lk.icbt.oceanview.oceanviewroomreservationsystem.factory;

import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.BillDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.ReservationDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.RoomTypeDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.UserDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.impl.BillDAOImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.impl.ReservationDAOImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.impl.RoomTypeDAOImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.impl.UserDAOImpl;

public class DAOFactory {

    private DAOFactory() {
        throw new IllegalStateException("Factory class - cannot be instantiated");
    }


    public static UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    public static RoomTypeDAO getRoomTypeDAO() {
        return new RoomTypeDAOImpl();
    }

    public static ReservationDAO getReservationDAO() {
        return new ReservationDAOImpl();
    }

    public static BillDAO getBillDAO() {
        return new BillDAOImpl();
    }

    public static Object getDAO(DAOType daoType) {
        switch (daoType) {
            case USER:
                return new UserDAOImpl();
            case ROOM_TYPE:
                return new RoomTypeDAOImpl();
            case RESERVATION:
                return new ReservationDAOImpl();
            case BILL:
                return new BillDAOImpl();
            default:
                throw new IllegalArgumentException("Unknown DAO type: " + daoType);
        }
    }


    public enum DAOType {
        USER,
        ROOM_TYPE,
        RESERVATION,
        BILL
    }
}
