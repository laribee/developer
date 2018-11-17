import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Discount: Buy Four, Get One")
class BuyFourGetOneDiscountTest
{
    private Product createBoomboxProduct() {
        return new Product("BOOMBOX", 99.99, "Pump up the jams.", Product.ProductType.Electronics);
    }

    @Test
    @DisplayName("Buy four items of with the same SKU, get the fifth free.")
    void five_items_of_same_sku_get_one_for_free() {

        Order subject = new Order();

        ArrayList<Discount> theDiscounts = new ArrayList<>();
        theDiscounts.add(new BuyFourGetOneDiscount());

        Product boombox = createBoomboxProduct();
        boombox.setSku("BOOMBOX"); // Just so we know
        boombox.setCost(100); // Keep it simple

        for (int i = 0; i < 5; i++) {
            subject.addItem(boombox);
        }

        assertEquals(400, subject.calculateTotal(theDiscounts));
    }

    @Test
    @DisplayName("Voided items do not qualify.")
    void voided_line_items_do_not_contribute_to_discount()
    {
        ArrayList<Discount> theDiscounts = new ArrayList<>();
        theDiscounts.add(new BuyFourGetOneDiscount());

        Order subject = new Order();

        Product boombox = createBoomboxProduct();
        boombox.setSku("BOOMBOX"); // Just so we know
        boombox.setCost(100); // Keep it simple

        for (int i = 0; i < 5; i++) {
            subject.addItem(boombox);
        }

        subject.getLines().get(0).markVoid();

        assertEquals(5, subject.getLines().size());
        assertEquals(400, subject.calculateTotal(theDiscounts));

        subject.addItem(boombox);

        assertEquals(6, subject.getLines().size());
        assertEquals(400, subject.calculateTotal(theDiscounts));
    }

}