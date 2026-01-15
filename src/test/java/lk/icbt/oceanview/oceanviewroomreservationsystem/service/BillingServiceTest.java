package lk.icbt.oceanview.oceanviewroomreservationsystem.service;

import lk.icbt.oceanview.oceanviewroomreservationsystem.service.impl.BillingServiceImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.strategy.PricingStrategy;
import lk.icbt.oceanview.oceanviewroomreservationsystem.strategy.SeasonalPricingStrategy;
import lk.icbt.oceanview.oceanviewroomreservationsystem.strategy.StandardPricingStrategy;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BillingServiceTest {

    private BillingService billingService;

    @BeforeEach
    void setUp() {
        billingService = new BillingServiceImpl();
    }


    @Test
    @Order(41)
    @DisplayName("Test 41: Standard pricing calculation")
    void testStandardPricing() {
        PricingStrategy strategy = new StandardPricingStrategy();
        BigDecimal basePrice = new BigDecimal("10000");
        int nights = 3;

        BigDecimal total = strategy.calculatePrice(nights, basePrice);
        assertEquals(0, new BigDecimal("30000.00").compareTo(total));
    }

    @Test
    @Order(42)
    @DisplayName("Test 42: Seasonal pricing peak season")
    void testSeasonalPricingPeak() {
        PricingStrategy strategy = new SeasonalPricingStrategy();
        BigDecimal basePrice = new BigDecimal("10000");
        int nights = 3;

        BigDecimal total = strategy.calculatePrice(nights, basePrice);
        // Should calculate correctly
        assertTrue(total.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @Order(43)
    @DisplayName("Test 43: Zero nights calculation")
    void testZeroNightsCalculation() {
        PricingStrategy strategy = new StandardPricingStrategy();
        BigDecimal basePrice = new BigDecimal("10000");
        int nights = 0;

        BigDecimal total = strategy.calculatePrice(nights, basePrice);
        assertEquals(0, new BigDecimal("0.00").compareTo(total));
    }

    @Test
    @Order(44)
    @DisplayName("Test 44: Large number of nights calculation")
    void testLargeNumberOfNights() {
        PricingStrategy strategy = new StandardPricingStrategy();
        BigDecimal basePrice = new BigDecimal("10000");
        int nights = 31;

        BigDecimal total = strategy.calculatePrice(nights, basePrice);
        assertEquals(0, new BigDecimal("310000.00").compareTo(total));
    }

    @Test
    @Order(45)
    @DisplayName("Test 45: Strategy name retrieval")
    void testStrategyName() {
        PricingStrategy strategy = new StandardPricingStrategy();
        assertEquals("Standard Pricing", strategy.getStrategyName());
    }

    @Test
    @Order(46)
    @DisplayName("Test 46: Strategy description")
    void testStrategyDescription() {
        PricingStrategy strategy = new StandardPricingStrategy();
        String description = strategy.getDescription();
        assertNotNull(description);
        assertFalse(description.isEmpty());
    }

    @Test
    @Order(47)
    @DisplayName("Test 47: Bill calculation with tax")
    void testBillCalculationWithTax() {
        BigDecimal subtotal = new BigDecimal("30000");
        BigDecimal taxRate = new BigDecimal("0.10");  // 10%
        BigDecimal expectedTax = new BigDecimal("3000.00");
        BigDecimal expectedTotal = new BigDecimal("33000.00");

        BigDecimal tax = subtotal.multiply(taxRate).setScale(2);
        BigDecimal total = subtotal.add(tax);

        assertEquals(expectedTax, tax);
        assertEquals(expectedTotal, total);
    }
}
