package lk.icbt.oceanview.oceanviewroomreservationsystem.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


public class DateValidator {


    private static final DateTimeFormatter ISO_DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter US_DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter UK_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final int MAX_ADVANCE_BOOKING_DAYS = 365;
    private static final int MIN_STAY_NIGHTS = 1;
    private static final int MAX_STAY_NIGHTS = 30;


    private DateValidator() {
        throw new IllegalStateException("Utility class - cannot be instantiated");
    }


    public static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        try {
            return LocalDate.parse(dateString, ISO_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }


    public static LocalDate parseDate(String dateString, String format) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return null;
        }
    }


    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(ISO_DATE_FORMAT);
    }


    public static boolean isValidDateFormat(String dateString) {
        return parseDate(dateString) != null;
    }

    public static boolean isNotPast(LocalDate date) {
        if (date == null) {
            return false;
        }
        return !date.isBefore(LocalDate.now());
    }

    public static boolean isFuture(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isAfter(LocalDate.now());
    }


    public static boolean isWithinBookingLimit(LocalDate date) {
        if (date == null) {
            return false;
        }

        LocalDate maxDate = LocalDate.now().plusDays(MAX_ADVANCE_BOOKING_DAYS);
        return !date.isAfter(maxDate);
    }


    public static boolean isValidReservationDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            return false;
        }

        if (checkIn.isBefore(LocalDate.now())) {
            return false;
        }

        if (!checkOut.isAfter(checkIn)) {
            return false;
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);

        if (nights < MIN_STAY_NIGHTS || nights > MAX_STAY_NIGHTS) {
            return false;
        }

        if (!isWithinBookingLimit(checkIn)) {
            return false;
        }

        return true;
    }


    public static String getDateValidationError(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null) {
            return "Check-in date is required";
        }

        if (checkOut == null) {
            return "Check-out date is required";
        }

        if (checkIn.isBefore(LocalDate.now())) {
            return "Check-in date cannot be in the past";
        }

        if (!checkOut.isAfter(checkIn)) {
            return "Check-out date must be after check-in date";
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);

        if (nights < MIN_STAY_NIGHTS) {
            return "Minimum stay is " + MIN_STAY_NIGHTS + " night(s)";
        }

        if (nights > MAX_STAY_NIGHTS) {
            return "Maximum stay is " + MAX_STAY_NIGHTS + " nights";
        }

        if (!isWithinBookingLimit(checkIn)) {
            return "Cannot book more than " + MAX_ADVANCE_BOOKING_DAYS + " days in advance";
        }

        return "";
    }

    public static long calculateNights(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }


    public static long daysUntil(LocalDate futureDate) {
        if (futureDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), futureDate);
    }

    public static LocalDate today() {
        return LocalDate.now();
    }

    public static LocalDate tomorrow() {
        return LocalDate.now().plusDays(1);
    }

    public static LocalDate addDays(LocalDate date, int days) {
        if (date == null) {
            return null;
        }
        return date.plusDays(days);
    }
}
