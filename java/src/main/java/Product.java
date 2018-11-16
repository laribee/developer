import java.util.ArrayList;
import java.util.List;

public class Product {

    private ArrayList<ValidationError> errors = new ArrayList<>();
    private String sku;
    private String description;
    private double cost;
    private ProductType productType;

    public static class InvalidProductException extends Exception { }

    public enum ValidationError {
        MissingSku,
        CostCannotBeNegative,
        ProductTypeIsRequired,
        MissingDescription;
    }

    public enum ProductType {
        Appliance,
        Miscellaneous,
        Electronics;
    }

    public Product() {

    }

    public Product(String sku, double cost, String description, ProductType productType) {
        this.sku = sku;
        this.cost = cost;
        this.productType = productType;
        this.description = description;
    }

    public boolean isValid() {
        return getErrors().size() == 0;
    }

    public List<ValidationError> getErrors() {
        errors.clear();

        if ((getSku() == null) || (getSku().isEmpty())) errors.add(ValidationError.MissingSku);
        if ((getDescription() == null) || (getDescription().isEmpty())) errors.add(ValidationError.MissingDescription);
        if (getCost() < 0) errors.add(ValidationError.CostCannotBeNegative);
        if (getProductType() == null) errors.add(ValidationError.ProductTypeIsRequired);

        return errors;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
