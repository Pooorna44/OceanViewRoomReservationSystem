package lk.icbt.oceanview.oceanviewroomreservationsystem.model;

import java.math.BigDecimal;

public class RoomType extends BaseEntity {


    private int roomTypeId;
    private String typeName;
    private String description;
    private BigDecimal pricePerNight;
    private int capacity;
    private String features;
    private boolean isAvailable;


    public RoomType() {
        super();
        this.isAvailable = true;
        this.capacity = 2;
        this.pricePerNight = BigDecimal.ZERO;
    }


    public RoomType(String typeName, BigDecimal pricePerNight) {
        this();
        this.typeName = typeName;
        this.pricePerNight = pricePerNight;
    }


    public RoomType(String typeName, String description, BigDecimal pricePerNight,
                    int capacity, String features) {
        this();
        this.typeName = typeName;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.features = features;
    }

    @Override
    public boolean validate() {
        if (typeName == null || typeName.trim().isEmpty()) {
            return false;
        }

        if (pricePerNight == null || pricePerNight.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (capacity <= 0 || capacity > 10) {
            return false;
        }

        return true;
    }

    @Override
    public String getDisplayName() {
        return typeName + " (LKR " + pricePerNight + "/night)";
    }

    public boolean validate(BigDecimal price) {
        return price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean validate(int capacity) {
        return capacity > 0 && capacity <= 10;
    }

    public boolean validate(BigDecimal price, int capacity) {
        return validate(price) && validate(capacity);
    }

    public BigDecimal calculateTotalPrice(int numberOfNights) {
        if (numberOfNights <= 0) {
            throw new IllegalArgumentException("Number of nights must be positive");
        }
        return pricePerNight.multiply(BigDecimal.valueOf(numberOfNights));
    }

    public boolean canAccommodate(int numberOfGuests) {
        return numberOfGuests > 0 && numberOfGuests <= capacity;
    }

    public void makeAvailable() {
        this.isAvailable = true;
        this.updateTimestamp();
    }

    public void makeUnavailable() {
        this.isAvailable = false;
        this.updateTimestamp();
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
        this.setId(roomTypeId);
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "roomTypeId=" + roomTypeId +
                ", typeName='" + typeName + '\'' +
                ", description='" + description + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", capacity=" + capacity +
                ", features='" + features + '\'' +
                ", isAvailable=" + isAvailable +
                "} " + super.toString();
    }
}
