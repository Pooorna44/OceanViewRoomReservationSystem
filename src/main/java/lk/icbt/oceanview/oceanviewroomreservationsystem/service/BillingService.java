package lk.icbt.oceanview.oceanviewroomreservationsystem.service;

import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Bill;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Reservation;
import lk.icbt.oceanview.oceanviewroomreservationsystem.strategy.PricingStrategy;

import java.util.List;


public interface BillingService {


    Bill generateBill(Reservation reservation, int userId);

    Bill generateBillWithStrategy(Reservation reservation, int userId, PricingStrategy pricingStrategy);

    Bill getBillById(int billId);

    Bill getBillByReservationId(int reservationId);

    List<Bill> getAllBills();

    List<Bill> getPendingBills();

    boolean markBillAsPaid(int billId, String paymentMethod);

    Bill applyDiscount(int billId, double discountPercentage);
}
