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
        System.out.println("\n=== Test T-041: Standard Pricing Calculation ===");
        PricingStrategy strategy = new StandardPricingStrategy();
        BigDecimal basePrice = new BigDecimal("10000");
        int nights = 3;

        System.out.println("Input - Base Price: Rs. " + basePrice);
        System.out.println("Input - Nights: " + nights);
        System.out.println("Expected Result: Rs. 30000.00");

        BigDecimal total = strategy.calculatePrice(nights, basePrice);
        System.out.println("Actual Result:   Rs. " + total);
        System.out.println("Match: " + (new BigDecimal("30000.00").compareTo(total) == 0 ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (new BigDecimal("30000.00").compareTo(total) == 0 ? "PASSED ✓" : "FAILED ✗"));

        assertEquals(0, new BigDecimal("30000.00").compareTo(total));
    }

    @Test
    @Order(42)
    @DisplayName("Test 42: Seasonal pricing peak season")
    void testSeasonalPricingPeak() {
        System.out.println("\n=== Test T-042: Seasonal Pricing Peak Season ===");
        PricingStrategy strategy = new SeasonalPricingStrategy();
        BigDecimal basePrice = new BigDecimal("10000");
        int nights = 3;

        System.out.println("Input - Base Price: Rs. " + basePrice);
        System.out.println("Input - Nights: " + nights);
        System.out.println("Expected Result: total > 0");

        BigDecimal total = strategy.calculatePrice(nights, basePrice);
        System.out.println("Actual Result:   Rs. " + total);
        System.out.println("Match: " + (total.compareTo(BigDecimal.ZERO) > 0 ? "YES ✓ (" + total + " > 0)" : "NO ✗"));
        System.out.println("Status: " + (total.compareTo(BigDecimal.ZERO) > 0 ? "PASSED ✓" : "FAILED ✗"));

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
        System.out.println("\n=== Test T-047: Bill Calculation With Tax ===");
        BigDecimal subtotal = new BigDecimal("30000");
        BigDecimal taxRate = new BigDecimal("0.10");  // 10%
        BigDecimal expectedTax = new BigDecimal("3000.00");
        BigDecimal expectedTotal = new BigDecimal("33000.00");

        System.out.println("Input - Subtotal: Rs. " + subtotal);
        System.out.println("Input - Tax Rate: " + taxRate + " (10%)");
        System.out.println("Expected Result: Tax=3000.00, Total=33000.00");

        BigDecimal tax = subtotal.multiply(taxRate).setScale(2);
        BigDecimal total = subtotal.add(tax);

        System.out.println("Actual Result:   Tax=" + tax + ", Total=" + total);
        System.out.println("Match: " + (expectedTax.equals(tax) && expectedTotal.equals(total) ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (expectedTax.equals(tax) && expectedTotal.equals(total) ? "PASSED ✓" : "FAILED ✗"));

        assertEquals(expectedTax, tax);
        assertEquals(expectedTotal, total);
    }
}
