package lk.icbt.oceanview.oceanviewroomreservationsystem.strategy;

import java.math.BigDecimal;

public interface PricingStrategy {


    BigDecimal calculatePrice(int numberOfNights, BigDecimal baseRatePerNight);

    String getStrategyName();

    String getDescription();
}
