import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order")
class OrderTest {

    @Test
    @DisplayName("Has multiple items.")
    void An_order_can_have_multiple_order_items() throws Product.InvalidProductException {

        Product product1 = new Product("BOOMBOX", 99.99, "Pump up the jams.", Product.ProductType.Electronics);
        Product product2 = new Product("JUICERO", 400.00, "Make juice ta yer house!", Product.ProductType.Appliance);
        Product product3 = new Product("PPTRTS-PS", 3.89, "Pumpkin Space Pop Tarts", Product.ProductType.Miscellaneous);

        Order subject = new Order();
        assertEquals(0, subject.getLines().size());

        subject.addItem(product1);
        assertEquals(1, subject.getLines().size());

        subject.addItem(product2);
        assertEquals(2, subject.getLines().size());

        subject.addItem(product3);
        assertEquals(3, subject.getLines().size());
    }


    @DisplayName("When adding products,")
    @Nested
    class AddingItems {

        @Test
        @DisplayName("copy product data into the order item.")
        void Copy_product_data_into_the_order_item() throws Product.InvalidProductException {
            Product product1 = new Product("BOOMBOX", 99.99, "Pump up the jams.", Product.ProductType.Electronics);

            Order subject = new Order();
            OrderLine line = subject.addItem(product1);

            assertEquals(product1.getDescription(), line.getDescription());
            assertEquals(product1.getSku(), line.getSku());
            assertEquals(product1.getCost(), line.getCost());
            assertEquals(product1.getProductType(), line.getProductType());

            product1.setProductType(Product.ProductType.Miscellaneous);
            product1.setDescription("Fridge");
            product1.setCost(77.20);
            product1.setSku("FRIDGE01");

            assertEquals("Pump up the jams.", line.getDescription());
            assertEquals("BOOMBOX", line.getSku());
            assertEquals(99.99, line.getCost());
            assertEquals(Product.ProductType.Electronics, line.getProductType());
        }


        @Test
        @DisplayName("ensure the product is valid.")
        void Ensure_the_product_is_valid() {
            Product product1 = new Product("", 99.99, "Pump up the jams.", Product.ProductType.Electronics);

            Order subject = new Order();

            assertFalse(product1.isValid());
            assertThrows(Product.InvalidProductException.class, () -> subject.addItem(product1));
        }

    }

}
