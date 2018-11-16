import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {

    static class CannotAddItemsToVoidedOrder extends RuntimeException {
    }

    private ArrayList<OrderLine> lines = new ArrayList<>();

    private boolean voided = false;

    public OrderLine addItem(Product product) {

        if (!product.isValid()) throw new Product.InvalidProductException();
        if (this.isVoided()) throw new CannotAddItemsToVoidedOrder();

        OrderLine line = new OrderLine(this, product);
        lines.add(line);

        return line;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public double getTotal() {
        if (isVoided()) return 0;

        double subTotal = calculateSubTotal();

        Map<String, Integer> skuMap = new HashMap<>();
        Map<String, Double> costMap = new HashMap<>();

        double discount = 0;

        for(OrderLine line : lines) {
            String sku = line.getSku();

            if (line.isVoided()) continue;

            if (!skuMap.containsKey(sku)) skuMap.put(sku, 0);
            if (!costMap.containsKey(sku)) costMap.put(sku, line.getCost());

            Integer currentCount = skuMap.get(sku);
            skuMap.replace(sku, currentCount + 1);
        }

        for (String sku : skuMap.keySet()) {
            if (skuMap.get(sku) == 5) {
                discount += costMap.get(sku);
            }
        }

        return subTotal - discount;
    }

    public void markVoid() {
        voided = true;
    }

    public boolean isVoided() {
        return voided;
    }

    private double calculateSubTotal() {
        double total = 0.0;
        for (OrderLine line : lines) {
            total += line.getCost();
        }
        return total;
    }
}
