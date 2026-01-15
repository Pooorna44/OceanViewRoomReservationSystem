package lk.icbt.oceanview.oceanviewroomreservationsystem.service;

import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation createReservation(Reservation reservation, int userId);

    Reservation getReservationById(int reservationId);

    Reservation getReservationByNumber(String reservationNumber);

    List<Reservation> getAllReservations();

    List<Reservation> getActiveReservations();

    List<Reservation> searchByGuestName(String guestName);

    List<Reservation> getTodayCheckIns();

    List<Reservation> getTodayCheckOuts();

    List<Reservation> getUpcomingReservations(int days);

    String validateReservation(Reservation reservation);

    boolean updateReservation(Reservation reservation);

    boolean cancelReservation(int reservationId);

    int completePastReservations();

    String generateReservationNumber();
}
