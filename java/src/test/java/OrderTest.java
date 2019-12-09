import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order")
class OrderTest extends ModelTestFixture {

    @Test
    @DisplayName("Can have multiple line items.")
    void have_multiple_line_items() {

        Product product1 = createBoomboxProduct();
        Product product2 = new Product("JUICERO", 400.00, "Make juice ta yer house!", Product.ProductType.Appliance);
        Product product3 = new Product("PPTRTS-PS", 3.89, "Pumpkin Spice Pop Tarts", Product.ProductType.Miscellaneous);

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
        void Copy_product_data_into_the_order_line() {
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

    @DisplayName("When calculating the order total,")
    @Nested
    class CalculatingTotal {

        @Test
        @DisplayName("a new order with no products has a total of zero.")
        void new_order_has_zero_cost() {
            assertEquals(0, new Order().calculateTotal());
        }

        @Test
        @DisplayName("sum all order line costs.")
        void sum_all_order_line_costs() {
            Product p1 = createBoomboxProduct();
            p1.setCost(129.99);

            Order subject = new Order();
            subject.addItem(p1);

            assertEquals(129.99, subject.calculateTotal());

            subject.addItem(p1);
            assertEquals(259.98, subject.calculateTotal());
        }

        @Test
        @DisplayName("negative prices are impossible.")
        void negative_prices_are_impossible() {
            Product p1 = createBoomboxProduct();
            p1.setCost(-10);

            // It's impossible because you can't add a product
            // with negative total to an order, like, ever...

            Order subject = new Order();
            assertThrows(Product.InvalidProductException.class, () -> subject.addItem(p1));
        }

    }

    @DisplayName("When voiding an order,")
    @Nested
    class WhenVoiding {

        @Test
        @DisplayName("mark it as voided.")
        void the_order_is_marked_void() {
            Order subject = new Order();
            subject.markVoid();
            assertTrue(subject.isVoided());
        }

        @Test
        @DisplayName("prevent new items from being added.")
        void prevent_new_items_from_being_added() {
            Order subject = new Order();
            subject.markVoid();

            assertThrows(Order.CannotAddItemsToVoidedOrder.class, () -> subject.addItem(createBoomboxProduct()));
        }

        @Test
        @DisplayName("total is zero.")
        void a_voided_order_has_a_zero_total() {
            Order subject = new Order();

            subject.addItem(createBoomboxProduct());
            subject.addItem(createBoomboxProduct());
            subject.addItem(createBoomboxProduct());

            assertTrue(subject.calculateTotal() > 0);

            subject.markVoid();

            assertEquals(0, subject.calculateTotal());
        }

        @Test
        @DisplayName("mark all line items as voided.")
        void mark_all_line_items_as_voided() {
            Order subject = new Order();

            subject.addItem(createBoomboxProduct());
            subject.addItem(createBoomboxProduct());

            for(OrderLine line : subject.getLines()) {
                assertFalse(line.isVoided());
            }

            subject.markVoid();

            for(OrderLine line : subject.getLines()) {
                assertTrue(line.isVoided());
            }
        }

        @Test
        @DisplayName("it's possible to void individual lines.")
        void individual_lines_can_be_voided_without_the_order_being_voided() {
            Order subject = new Order();
            OrderLine line = subject.addItem(createBoomboxProduct());

            line.markVoid();

            assertTrue(line.isVoided());
            assertFalse(subject.isVoided());
        }

        @Test
        @DisplayName("voided individual line items have a zero cost.")
        void voided_lines_have_a_cost_of_zero() {
            Order subject = new Order();
            OrderLine line = subject.addItem(createBoomboxProduct());

            line.markVoid();

            assertEquals(0, line.getCost());
        }

        @Test
        void an_order_with_one_voided_line_is_not_voided_itself() {
            Order subject = new Order();

            OrderLine line = subject.addItem(createBoomboxProduct());
            line.markVoid();

            assertFalse(subject.isVoided());
        }

        @Test
        @DisplayName("an order with a voided line can have new items added.")
        void add_more_items_to_an_order_with_an_already_voided_item() {
            Order subject = new Order();

            OrderLine line = subject.addItem(createBoomboxProduct());

            line.markVoid();
            assertEquals(0, subject.calculateTotal());

            subject.addItem(createBoomboxProduct());

            assertTrue(subject.calculateTotal() > 0);
        }

    }

    @Nested
    @DisplayName("When discounting,")
    class WhenDiscounting {

        @Test
        @DisplayName("deduct discounted amount from the order.")
        void apply_the_discount_amount() {
            TestDiscount testDiscount = new TestDiscount(10);
            Order subject = new Order();

            Product boombox = createBoomboxProduct();
            boombox.setCost(100);

            subject.addItem(boombox);

            ArrayList<Discount> discounts = new ArrayList<>();
            discounts.add(testDiscount);

            assertEquals(90, subject.calculateTotal(discounts));
        }

        @Test
        @DisplayName("it is possible to apply multiple discounts.")
        void apply_multiple_discounts() {
            TestDiscount testDiscount1 = new TestDiscount(3.22);
            TestDiscount testDiscount2 = new TestDiscount(6.22);

            Order subject = new Order();

            Product boombox = createBoomboxProduct();
            boombox.setCost(100.00);

            subject.addItem(boombox);

            ArrayList<Discount> discounts = new ArrayList<>();
            discounts.add(testDiscount1);
            discounts.add(testDiscount2);

            assertEquals(90.56, subject.calculateTotal(discounts));
        }

        @Test
        @DisplayName("it is impossible for discounts to create a negative order total.")
        void discounts_will_not_result_in_a_negative_total() {
            TestDiscount testDiscount1 = new TestDiscount(101);

            Product boombox = createBoomboxProduct();
            boombox.setCost(100);

            Order subject = new Order();

            subject.addItem(boombox);

            ArrayList<Discount> discounts = new ArrayList<>();
            discounts.add(testDiscount1);

            assertEquals(0, subject.calculateTotal(discounts));
        }

        class TestDiscount extends Discount {

            double amount;

            TestDiscount(double amount) {
                this.amount = amount;
            }

            @Override
            public double calculate(ArrayList<OrderLine> lines) {
                return amount;
            }
        }

    }
}
