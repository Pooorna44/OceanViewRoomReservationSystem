package lk.icbt.oceanview.oceanviewroomreservationsystem.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bill extends BaseEntity {


    private static final BigDecimal TAX_RATE = new BigDecimal("0.10");
    private int billId;
    private int reservationId;
    private String reservationNumber;
    private String guestName;
    private String roomType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfNights;
    private BigDecimal ratePerNight;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private int generatedBy;
    private String notes;


    public Bill() {
        super();
        this.paymentStatus = "PENDING";
        this.taxAmount = BigDecimal.ZERO;
        this.discountAmount = BigDecimal.ZERO;
        this.totalAmount = BigDecimal.ZERO;
    }


    public Bill(String reservationNumber, String guestName, String roomType, int numberOfNights, BigDecimal ratePerNight) {
        this();
        this.reservationNumber = reservationNumber;
        this.guestName = guestName;
        this.roomType = roomType;
        this.numberOfNights = numberOfNights;
        this.ratePerNight = ratePerNight;
        calculateAmounts();
    }

    public Bill(Reservation reservation) {
        this();
        if (reservation != null) {
            this.reservationId = reservation.getReservationId();
            this.reservationNumber = reservation.getReservationNumber();
            this.guestName = reservation.getGuestName();
            this.checkInDate = reservation.getCheckInDate();
            this.checkOutDate = reservation.getCheckOutDate();
            this.numberOfNights = (int) reservation.calculateNumberOfNights();

            if (reservation.getRoomType() != null) {
                this.roomType = reservation.getRoomType().getTypeName();
                this.ratePerNight = reservation.getRoomType().getPricePerNight();
                calculateAmounts();
            }
        }
    }

    public static BigDecimal getTaxRate() {
        return TAX_RATE;
    }

    @Override
    public boolean validate() {

        if (reservationNumber == null || reservationNumber.trim().isEmpty()) {
            return false;
        }


        if (guestName == null || guestName.trim().isEmpty()) {
            return false;
        }

        if (numberOfNights <= 0) {
            return false;
        }

        if (ratePerNight == null || ratePerNight.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        return paymentStatus != null && !paymentStatus.trim().isEmpty();
    }

    @Override
    public String getDisplayName() {
        return "Bill #" + billId + " - " + reservationNumber + " - " + guestName;
    }

    public void calculateAmounts() {
        if (numberOfNights > 0 && ratePerNight != null) {
            this.subtotal = ratePerNight.multiply(BigDecimal.valueOf(numberOfNights));

            this.taxAmount = subtotal.multiply(TAX_RATE);

            this.totalAmount = subtotal.add(taxAmount).subtract(discountAmount);

            if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
                totalAmount = BigDecimal.ZERO;
            }
        }
    }

    public void recalculate() {
        calculateAmounts();
        this.updateTimestamp();
    }

    public void applyDiscount(BigDecimal discount) {
        if (discount != null && discount.compareTo(BigDecimal.ZERO) > 0) {
            this.discountAmount = discount;
            calculateAmounts();
            this.updateTimestamp();
        }
    }

    public void applyDiscountPercentage(double percentage) {
        if (percentage > 0 && percentage <= 100 && subtotal != null) {
            BigDecimal discountPercent = BigDecimal.valueOf(percentage / 100);
            this.discountAmount = subtotal.multiply(discountPercent);
            calculateAmounts();
            this.updateTimestamp();
        }
    }

    public void markAsPaid(String paymentMethod) {
        this.paymentStatus = "PAID";
        this.paymentMethod = paymentMethod;
        this.paymentDate = LocalDateTime.now();
        this.updateTimestamp();
    }

    public void cancel() {
        this.paymentStatus = "CANCELLED";
        this.updateTimestamp();
    }

    public boolean isPaid() {
        return "PAID".equalsIgnoreCase(paymentStatus);
    }

    public boolean isPending() {
        return "PENDING".equalsIgnoreCase(paymentStatus);
    }

    public boolean isCancelled() {
        return "CANCELLED".equalsIgnoreCase(paymentStatus);
    }

    public BigDecimal getSavings() {
        return discountAmount != null ? discountAmount : BigDecimal.ZERO;
    }

    public String getFormattedTotal() {
        if (totalAmount == null) {
            return "LKR 0.00";
        }
        return String.format("LKR %,.2f", totalAmount);
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
        this.setId(billId);
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
        calculateAmounts();
    }

    public BigDecimal getRatePerNight() {
        return ratePerNight;
    }

    public void setRatePerNight(BigDecimal ratePerNight) {
        this.ratePerNight = ratePerNight;
        calculateAmounts();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
        calculateAmounts();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(int generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Bill{" + "billId=" + billId + ", reservationNumber='" + reservationNumber + '\'' + ", guestName='" + guestName + '\'' + ", roomType='" + roomType + '\'' + ", numberOfNights=" + numberOfNights + ", ratePerNight=" + ratePerNight + ", subtotal=" + subtotal + ", taxAmount=" + taxAmount + ", discountAmount=" + discountAmount + ", totalAmount=" + totalAmount + ", paymentStatus='" + paymentStatus + '\'' + ", paymentMethod='" + paymentMethod + '\'' + "} " + super.toString();
    }
}
