package lk.icbt.oceanview.oceanviewroomreservationsystem.service.impl;

import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.BillDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.factory.DAOFactory;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Bill;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Reservation;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.BillingService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.strategy.PricingStrategy;
import lk.icbt.oceanview.oceanviewroomreservationsystem.strategy.StandardPricingStrategy;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class BillingServiceImpl implements BillingService {

    private final BillDAO billDAO;

    public BillingServiceImpl() {
        this.billDAO = DAOFactory.getBillDAO();
    }

    @Override
    public Bill generateBill(Reservation reservation, int userId) {
        PricingStrategy strategy = new StandardPricingStrategy();
        return generateBillWithStrategy(reservation, userId, strategy);
    }


    @Override
    public Bill generateBillWithStrategy(Reservation reservation, int userId, PricingStrategy pricingStrategy) {
        try {
            if (reservation == null) {
                throw new IllegalArgumentException("Reservation cannot be null");
            }


            Bill existingBill = billDAO.findByReservationId(reservation.getReservationId());
            if (existingBill != null) {
                System.out.println("[BillingService] Bill already exists for reservation: " + reservation.getReservationNumber());
                return existingBill;
            }


            Bill bill = new Bill(reservation);


            bill.setGeneratedBy(userId);
            bill.setCreatedAt(LocalDateTime.now());
            bill.setUpdatedAt(LocalDateTime.now());


            if (pricingStrategy != null && reservation.getRoomType() != null) {
                int nights = (int) reservation.calculateNumberOfNights();
                bill.setRatePerNight(
                        pricingStrategy.calculatePrice(1, reservation.getRoomType().getPricePerNight())
                );
            }

            bill.calculateAmounts();

            int billId = billDAO.save(bill);

            return billDAO.findById(billId);

        } catch (SQLException e) {
            System.err.println("[BillingService] Error generating bill: " + e.getMessage());
            throw new RuntimeException("Failed to generate bill: " + e.getMessage());
        }
    }

    @Override
    public Bill getBillById(int billId) {
        try {
            return billDAO.findById(billId);
        } catch (SQLException e) {
            System.err.println("[BillingService] Error getting bill: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Bill getBillByReservationId(int reservationId) {
        try {
            return billDAO.findByReservationId(reservationId);
        } catch (SQLException e) {
            System.err.println("[BillingService] Error getting bill: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Bill> getAllBills() {
        try {
            return billDAO.findAll();
        } catch (SQLException e) {
            System.err.println("[BillingService] Error getting bills: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Bill> getPendingBills() {
        try {
            return billDAO.findAllPending();
        } catch (SQLException e) {
            System.err.println("[BillingService] Error getting pending bills: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean markBillAsPaid(int billId, String paymentMethod) {
        try {
            return billDAO.markAsPaid(billId, paymentMethod);
        } catch (SQLException e) {
            System.err.println("[BillingService] Error marking bill as paid: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Bill applyDiscount(int billId, double discountPercentage) {
        try {
            Bill bill = billDAO.findById(billId);

            if (bill == null) {
                return null;
            }

            bill.applyDiscountPercentage(discountPercentage);

            billDAO.update(bill);

            return bill;

        } catch (SQLException e) {
            System.err.println("[BillingService] Error applying discount: " + e.getMessage());
            return null;
        }
    }
}
