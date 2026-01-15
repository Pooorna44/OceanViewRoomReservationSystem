package lk.icbt.oceanview.oceanviewroomreservationsystem.dao;

import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Reservation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public interface ReservationDAO {


    Reservation findById(int reservationId) throws SQLException;

    Reservation findByReservationNumber(String reservationNumber) throws SQLException;

    List<Reservation> findAll() throws SQLException;

    List<Reservation> findByStatus(String status) throws SQLException;

    List<Reservation> findAllActive() throws SQLException;

    List<Reservation> findByGuestName(String guestName) throws SQLException;

    List<Reservation> findByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException;

    List<Reservation> findTodayCheckIns() throws SQLException;

    List<Reservation> findTodayCheckOuts() throws SQLException;

    List<Reservation> findUpcoming(int days) throws SQLException;

    int save(Reservation reservation) throws SQLException;

    boolean update(Reservation reservation) throws SQLException;

    boolean delete(int reservationId) throws SQLException;

    boolean cancel(int reservationId) throws SQLException;

    int completePastReservations() throws SQLException;

    String generateReservationNumber() throws SQLException;

    int countByStatus(String status) throws SQLException;
}
