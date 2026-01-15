package lk.icbt.oceanview.oceanviewroomreservationsystem.strategy;

import java.math.BigDecimal;


public class StandardPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(int numberOfNights, BigDecimal baseRatePerNight) {
        if (numberOfNights <= 0 || baseRatePerNight == null) {
            return BigDecimal.ZERO;
        }

        return baseRatePerNight.multiply(BigDecimal.valueOf(numberOfNights));
    }

    @Override
    public String getStrategyName() {
        return "Standard Pricing";
    }

    @Override
    public String getDescription() {
        return "Standard flat-rate pricing with no discounts or surcharges";
    }
}
