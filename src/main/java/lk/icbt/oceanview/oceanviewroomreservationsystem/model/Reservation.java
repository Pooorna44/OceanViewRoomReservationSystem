package lk.icbt.oceanview.oceanviewroomreservationsystem.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation extends BaseEntity {

    private int reservationId;
    private String reservationNumber;
    private String guestName;
    private String address;
    private String contactNumber;
    private String email;
    private int roomTypeId;
    private RoomType roomType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private String status;
    private String specialRequests;
    private int createdBy;

    public Reservation() {
        super();
        this.status = "CONFIRMED";
        this.numberOfGuests = 1;
    }

    public Reservation(String guestName, String contactNumber,
                       LocalDate checkInDate, LocalDate checkOutDate) {
        this();
        this.guestName = guestName;
        this.contactNumber = contactNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }


    public Reservation(String reservationNumber, String guestName, String address,
                       String contactNumber, String email, int roomTypeId,
                       LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests) {
        this();
        this.reservationNumber = reservationNumber;
        this.guestName = guestName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        this.roomTypeId = roomTypeId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
    }


    @Override
    public boolean validate() {
        if (guestName == null || guestName.trim().isEmpty()) {
            return false;
        }

        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            return false;
        }
        if (!validateContactNumber(contactNumber)) {
            return false;
        }

        if (roomTypeId <= 0) {
            return false;
        }

        if (checkInDate == null || checkOutDate == null) {
            return false;
        }
        if (!validateDates(checkInDate, checkOutDate)) {
            return false;
        }

        if (numberOfGuests <= 0) {
            return false;
        }

        if (status == null || status.trim().isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public String getDisplayName() {
        return reservationNumber + " - " + guestName;
    }

    public boolean validateContactNumber(String contactNumber) {
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            return false;
        }
        String cleaned = contactNumber.replaceAll("[\\s-]", "");
        return cleaned.matches("^0\\d{9}$") || cleaned.matches("^\\d{10}$");
    }


    public boolean validateDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            return false;
        }
        if (!checkOut.isAfter(checkIn)) {
            return false;
        }
        if (isNew() && checkIn.isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }

    public boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }


    public long calculateNumberOfNights() {
        if (checkInDate == null || checkOutDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    public boolean isConfirmed() {
        return "CONFIRMED".equalsIgnoreCase(status);
    }

    public boolean isCancelled() {
        return "CANCELLED".equalsIgnoreCase(status);
    }

    public boolean isCompleted() {
        return "COMPLETED".equalsIgnoreCase(status);
    }

    public boolean isActive() {
        return !isCancelled();
    }

    public void confirm() {
        this.status = "CONFIRMED";
        this.updateTimestamp();
    }

    public void cancel() {
        this.status = "CANCELLED";
        this.updateTimestamp();
    }

    public void complete() {
        this.status = "COMPLETED";
        this.updateTimestamp();
    }

    public void markNoShow() {
        this.status = "NO_SHOW";
        this.updateTimestamp();
    }

    public boolean isCheckingInToday() {
        return checkInDate != null && checkInDate.equals(LocalDate.now());
    }

    public boolean isCheckingOutToday() {
        return checkOutDate != null && checkOutDate.equals(LocalDate.now());
    }

    public long getDaysUntilCheckIn() {
        if (checkInDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), checkInDate);
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
        this.setId(reservationId);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
        if (roomType != null) {
            this.roomTypeId = roomType.getRoomTypeId();
        }
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

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", reservationNumber='" + reservationNumber + '\'' +
                ", guestName='" + guestName + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", roomTypeId=" + roomTypeId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", status='" + status + '\'' +
                ", nights=" + calculateNumberOfNights() +
                "} " + super.toString();
    }
}
