package lk.icbt.oceanview.oceanviewroomreservationsystem.strategy;


public class PricingStrategyFactory {

    private PricingStrategyFactory() {
        throw new IllegalStateException("Factory class - cannot be instantiated");
    }

    public static PricingStrategy getStrategy(StrategyType strategyType) {
        switch (strategyType) {
            case STANDARD:
                return new StandardPricingStrategy();
            case SEASONAL:
                return new SeasonalPricingStrategy();
            case WEEKEND:
                return new WeekendPricingStrategy();
            default:
                return new StandardPricingStrategy();  // Default to standard
        }
    }


    public static PricingStrategy getDefaultStrategy() {
        return new StandardPricingStrategy();
    }

    public enum StrategyType {
        STANDARD,
        SEASONAL,
        WEEKEND
    }
}
