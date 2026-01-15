package lk.icbt.oceanview.oceanviewroomreservationsystem.dao;

import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Bill;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * BillDAO - Data Access Object interface for Bill entity
 * <p>
 * Academic Purpose:
 * - Demonstrates DAO PATTERN (MANDATORY DESIGN PATTERN)
 * - Demonstrates ABSTRACTION (interface defines contract)
 * - Demonstrates POLYMORPHISM (implementation flexibility)
 *
 * @author CIS6003 Student
 * @version 1.0
 * @since 2026-01-12
 */
public interface BillDAO {


    Bill findById(int billId) throws SQLException;

    Bill findByReservationId(int reservationId) throws SQLException;

    Bill findByReservationNumber(String reservationNumber) throws SQLException;

    List<Bill> findAll() throws SQLException;

    List<Bill> findByPaymentStatus(String paymentStatus) throws SQLException;

    List<Bill> findAllPending() throws SQLException;

    List<Bill> findAllPaid() throws SQLException;

    List<Bill> findByGuestName(String guestName) throws SQLException;

    List<Bill> findByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException;

    int save(Bill bill) throws SQLException;

    boolean update(Bill bill) throws SQLException;

    boolean delete(int billId) throws SQLException;

    boolean markAsPaid(int billId, String paymentMethod) throws SQLException;

    int countByPaymentStatus(String paymentStatus) throws SQLException;
}
