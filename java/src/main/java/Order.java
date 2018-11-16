import java.util.ArrayList;
import java.util.List;

public class Order {

    static class CannotAddItemsToVoidedOrder extends RuntimeException { }

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

        double total = 0.0;
        for (OrderLine line : lines) {
            total += line.getCost();
        }
        return total;
    }

    public void markVoid() {
        voided = true;
    }

    public boolean isVoided() {
        return voided;
    }
}
