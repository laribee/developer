import java.util.ArrayList;
import java.util.List;

public class Order {

    private ArrayList<OrderLine> lines = new ArrayList<>();

    public OrderLine addItem(Product product) throws Product.InvalidProductException {
        if (!product.isValid()) throw new Product.InvalidProductException();

        OrderLine line = new OrderLine(product);
        lines.add(line);

        return line;
    }

    public List<OrderLine> getLines() {
        return lines;
    }
}