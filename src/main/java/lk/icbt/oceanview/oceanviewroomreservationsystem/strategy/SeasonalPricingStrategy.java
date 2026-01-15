package lk.icbt.oceanview.oceanviewroomreservationsystem.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;


public class SeasonalPricingStrategy implements PricingStrategy {

    private static final BigDecimal PEAK_SEASON_MULTIPLIER = new BigDecimal("1.20");  // +20%


    @Override
    public BigDecimal calculatePrice(int numberOfNights, BigDecimal baseRatePerNight) {
        if (numberOfNights <= 0 || baseRatePerNight == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal subtotal = baseRatePerNight.multiply(BigDecimal.valueOf(numberOfNights));

        if (isPeakSeason()) {
            subtotal = subtotal.multiply(PEAK_SEASON_MULTIPLIER);
        }

        return subtotal;
    }

    private boolean isPeakSeason() {
        Month currentMonth = LocalDate.now().getMonth();

        return currentMonth == Month.DECEMBER ||
                currentMonth == Month.JANUARY ||
                currentMonth == Month.FEBRUARY ||
                currentMonth == Month.JULY ||
                currentMonth == Month.AUGUST;
    }

    @Override
    public String getStrategyName() {
        return "Seasonal Pricing";
    }

    @Override
    public String getDescription() {
        return "Peak season (Dec-Feb, Jul-Aug): +20% surcharge. Off-peak: standard rate";
    }
}
