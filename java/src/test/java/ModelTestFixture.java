public class ModelTestFixture {
    protected Product createBoomboxProduct() {
        return new Product("BOOMBOX", 99.99, "Pump up the jams.", Product.ProductType.Electronics);
    }
}
