public class OrderLine {

    private String description;
    private Order parentOrder;
    private String sku;
    private double cost;
    private Product.ProductType productType;
    private boolean voided;

    OrderLine(Order parentOrder, Product product) {
        this.parentOrder = parentOrder;

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
        if (voided) return 0;
        return cost;
    }

    public Product.ProductType getProductType() {
        return productType;
    }

    public boolean isVoided() {
        if (this.parentOrder.isVoided()) return true;
        return voided;
    }

    public void markVoid() {
        voided = true;
    }
}
