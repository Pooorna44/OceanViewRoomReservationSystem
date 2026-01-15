package lk.icbt.oceanview.oceanviewroomreservationsystem.strategy;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;


public class WeekendPricingStrategy implements PricingStrategy {

    private static final BigDecimal WEEKEND_MULTIPLIER = new BigDecimal("1.15");  // +15%

    @Override
    public BigDecimal calculatePrice(int numberOfNights, BigDecimal baseRatePerNight) {
        if (numberOfNights <= 0 || baseRatePerNight == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal subtotal = baseRatePerNight.multiply(BigDecimal.valueOf(numberOfNights));

        if (isWeekend()) {
            subtotal = subtotal.multiply(WEEKEND_MULTIPLIER);
        }

        return subtotal;
    }

    private boolean isWeekend() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        return today == DayOfWeek.FRIDAY ||
                today == DayOfWeek.SATURDAY ||
                today == DayOfWeek.SUNDAY;
    }

    @Override
    public String getStrategyName() {
        return "Weekend Pricing";
    }

    @Override
    public String getDescription() {
        return "Weekend (Fri-Sun): +15% premium. Weekday (Mon-Thu): standard rate";
    }
}
