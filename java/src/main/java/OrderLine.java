public class OrderLine {

    private String description;
    private String sku;
    private double cost;
    private Product.ProductType productType;

    OrderLine(Product product) {
        this.sku = product.getSku();
        this.productType = product.getProductType();
        this.description = product.getDescription();
        this.cost = product.getCost();
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    public double getCost() {
        return cost;
    }

    public Product.ProductType getProductType() {
        return productType;
    }
}
