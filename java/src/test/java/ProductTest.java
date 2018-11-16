import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product")
class ProductTest {

    @Nested
    @DisplayName("Validation")
    class Validation {

        @Test
        @DisplayName("SKU cannot be an empty string.")
        void Sku_cannot_be_an_empty_string() {
            Product product = new Product("", 99.99, "Top rockin'", Product.ProductType.Electronics);
            assertTrue(product.getErrors().contains(Product.ValidationError.MissingSku));
        }

        @Test
        @DisplayName("SKU cannot be null.")
        void Sku_cannot_be_null() {
            Product product = new Product(null, 99.99, "Top rockin'", Product.ProductType.Electronics);
            assertTrue(product.getErrors().contains(Product.ValidationError.MissingSku));
        }

        @Test
        @DisplayName("Description cannot be empty.")
        void Description_cannot_be_an_empty_string() {
            Product product = new Product("BOOMBOX", 99.99, "", Product.ProductType.Electronics);
            assertTrue(product.getErrors().contains(Product.ValidationError.MissingDescription));
        }

        @Test
        @DisplayName("Description cannot be null.")
        void Description_cannot_be_null() {
            Product product = new Product("BOOMBOX", 99.99, null, Product.ProductType.Electronics);
            assertTrue(product.getErrors().contains(Product.ValidationError.MissingDescription));
        }

        @Test
        @DisplayName("Cost cannot be negative.")
        void Cost_cannot_be_negative() {
            Product product = new Product("BOOMBOX", -00.01, null, Product.ProductType.Electronics);
            assertTrue(product.getErrors().contains(Product.ValidationError.CostCannotBeNegative));
        }

        @Test
        @DisplayName("Product type is required.")
        void Product_type_is_required() {
            Product product = new Product();
            assertTrue(product.getErrors().contains(Product.ValidationError.ProductTypeIsRequired));
        }

    }




}
