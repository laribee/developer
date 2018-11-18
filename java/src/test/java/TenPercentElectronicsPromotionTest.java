import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Discount: 10% Off Most Expensive Electronics")
class TenPercentElectronicsPromotionTest extends ModelTestFixture {

    @Test
    @DisplayName("10% off when there are two electronics items.")
    void applies_ten_percent_when_two_electronics() {
        Order order = new Order();

        Product boombox = createBoomboxProduct();
        boombox.setCost(100);

        order.addItem(boombox);
        order.addItem(boombox);

        TenPercentElectronicsPromotion subject = new TenPercentElectronicsPromotion();

        double total = order.calculateTotal(List.of(subject));

        assertEquals(190, total);
    }

    @Test
    @DisplayName("Minimum purchase of two electronics items for discount to apply.")
    void only_applies_to_two_or_more_items() {
        Order order = new Order();

        Product boombox = createBoomboxProduct();
        boombox.setCost(100);

        order.addItem(boombox);

        TenPercentElectronicsPromotion subject = new TenPercentElectronicsPromotion();

        double total = order.calculateTotal(List.of(subject));

        assertEquals(100, total);
    }


    @Test
    @DisplayName("Takes 10% off the highest priced item.")
    void takes_ten_percent_of_the_highest_price_item_off() {
        Order order = new Order();

        Product boombox = createBoomboxProduct();
        boombox.setCost(100);

        order.addItem(boombox);

        boombox.setCost(200);
        order.addItem(boombox);

        TenPercentElectronicsPromotion subject = new TenPercentElectronicsPromotion();

        double total = order.calculateTotal(List.of(subject));

        assertEquals(280, total);
    }

    @Test
    @DisplayName("Voided items are excluded from the discount.")
    void do_not_apply_to_voided_items() {
        Order order = new Order();

        Product boombox = createBoomboxProduct();
        boombox.setCost(100);

        order.addItem(boombox);

        OrderLine voidItem = order.addItem(boombox);
        voidItem.markVoid();

        TenPercentElectronicsPromotion subject = new TenPercentElectronicsPromotion();

        double total = order.calculateTotal(List.of(subject));

        assertEquals(100, total);
    }

}
