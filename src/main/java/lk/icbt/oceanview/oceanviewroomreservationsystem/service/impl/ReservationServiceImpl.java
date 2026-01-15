package lk.icbt.oceanview.oceanviewroomreservationsystem.service.impl;

import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.ReservationDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.RoomTypeDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.factory.DAOFactory;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Reservation;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.RoomType;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.ReservationService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.util.DateValidator;
import lk.icbt.oceanview.oceanviewroomreservationsystem.util.InputSanitizer;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReservationServiceImpl implements ReservationService {

    private final ReservationDAO reservationDAO;
    private final RoomTypeDAO roomTypeDAO;

    public ReservationServiceImpl() {
        this.reservationDAO = DAOFactory.getReservationDAO();
        this.roomTypeDAO = DAOFactory.getRoomTypeDAO();
    }

    @Override
    public Reservation createReservation(Reservation reservation, int userId) {
        try {
            String validationError = validateReservation(reservation);
            if (!validationError.isEmpty()) {
                throw new IllegalArgumentException(validationError);
            }

            reservation.setGuestName(InputSanitizer.cleanInput(reservation.getGuestName()));
            reservation.setAddress(InputSanitizer.cleanInput(reservation.getAddress()));
            reservation.setEmail(InputSanitizer.cleanInput(reservation.getEmail()));

            String reservationNumber = reservationDAO.generateReservationNumber();
            reservation.setReservationNumber(reservationNumber);

            reservation.setCreatedBy(userId);
            reservation.setCreatedAt(LocalDateTime.now());
            reservation.setUpdatedAt(LocalDateTime.now());

            int reservationId = reservationDAO.save(reservation);

            return reservationDAO.findById(reservationId);

        } catch (SQLException e) {
            System.err.println("[ReservationService] Error creating reservation: " + e.getMessage());
            throw new RuntimeException("Failed to create reservation: " + e.getMessage());
        }
    }

    @Override
    public Reservation getReservationById(int reservationId) {
        try {
            Reservation reservation = reservationDAO.findById(reservationId);

            if (reservation != null && reservation.getRoomTypeId() > 0) {
                RoomType roomType = roomTypeDAO.findById(reservation.getRoomTypeId());
                reservation.setRoomType(roomType);
            }

            return reservation;
        } catch (SQLException e) {
            System.err.println("[ReservationService] Error getting reservation: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Reservation getReservationByNumber(String reservationNumber) {
        try {
            Reservation reservation = reservationDAO.findByReservationNumber(reservationNumber);

            if (reservation != null && reservation.getRoomTypeId() > 0) {
                RoomType roomType = roomTypeDAO.findById(reservation.getRoomTypeId());
                reservation.setRoomType(roomType);
            }

            return reservation;
        } catch (SQLException e) {
            System.err.println("[ReservationService] Error getting reservation: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        try {
            return reservationDAO.findAll();
        } catch (SQLException e) {
            System.err.println("[ReservationService] Error getting reservations: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Reservation> getActiveReservations() {
        try {
            List<Reservation> reservations = reservationDAO.findAllActive();

            for (Reservation reservation : reservations) {
                if (reservation.getRoomTypeId() > 0) {
                    RoomType roomType = roomTypeDAO.findById(reservation.getRoomTypeId());
                    reservation.setRoomType(roomType);
                }
            }

            return reservations;
        } catch (SQLException e) {
            System.err.println("[ReservationService] Error getting active reservations: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Reservation> searchByGuestName(String guestName) {
        try {
            if (InputSanitizer.isNullOrEmpty(guestName)) {
                return new ArrayList<>();
            }
            return reservationDAO.findByGuestName(guestName);
        } catch (SQLException e) {
            System.err.println("[ReservationService] Error searching reservations: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Reservation> getTodayCheckIns() {
        try {
            return reservationDAO.findTodayCheckIns();
        } catch (SQLException e) {
            System.err.println("[ReservationService] Error getting today's check-ins: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Reservation> getTodayCheckOuts() {
        try {
            return reservationDAO.findTodayCheckOuts();
        } catch (SQLException e) {
            System.err.println("[ReservationService] Error getting today's check-outs: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Reservation> getUpcomingReservations(int days) {
        try {
            return reservationDAO.findUpcoming(days);
        } catch (SQLException e) {
            System.err.println("[ReservationService] Error getting upcoming reservations: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public String validateReservation(Reservation reservation) {
        if (reservation == null) {
            return "Reservation cannot be null";
        }

        String nameError = validateGuestName(reservation.getGuestName());
        if (!nameError.isEmpty()) return nameError;

        String contactError = validateContactNumber(reservation.getContactNumber());
        if (!contactError.isEmpty()) return contactError;

        String dateError = DateValidator.getDateValidationError(
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        );
        if (!dateError.isEmpty()) return dateError;

        if (reservation.getRoomTypeId() <= 0) {
            return "Please select a room type";
        }

        if (reservation.getNumberOfGuests() <= 0) {
            return "Number of guests must be at least 1";
        }

        return "";
    }


    private String validateGuestName(String guestName) {
        if (InputSanitizer.isNullOrEmpty(guestName)) {
            return "Guest name is required";
        }
        if (guestName.length() < 3) {
            return "Guest name must be at least 3 characters";
        }
        return "";
    }


    private String validateContactNumber(String contactNumber) {
        if (InputSanitizer.isNullOrEmpty(contactNumber)) {
            return "Contact number is required";
        }
        if (!InputSanitizer.isValidPhoneNumber(contactNumber)) {
            return "Invalid contact number format";
        }
        return "";
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        try {
            String validationError = validateReservation(reservation);
            if (!validationError.isEmpty()) {
                throw new IllegalArgumentException(validationError);
            }

            reservation.setUpdatedAt(LocalDateTime.now());
            return reservationDAO.update(reservation);

        } catch (SQLException e) {
            System.err.println("[ReservationService] Error updating reservation: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean cancelReservation(int reservationId) {
        try {
            return reservationDAO.cancel(reservationId);
        } catch (SQLException e) {
            System.err.println("[ReservationService] Error cancelling reservation: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int completePastReservations() {
        try {
            return reservationDAO.completePastReservations();
        } catch (SQLException e) {
            System.err.println("[ReservationService] Error completing past reservations: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public String generateReservationNumber() {
        try {
            return reservationDAO.generateReservationNumber();
        } catch (SQLException e) {
            System.err.println("[ReservationService] Error generating reservation number: " + e.getMessage());
            return null;
        }
    }
}
