import java.util.ArrayList;
import java.util.stream.Stream;

public class Discount {

    public double calculate(ArrayList<OrderLine> lines) {
        Stream<OrderLine> nonVoided = filterVoided(lines.stream());
        return doCalculate(nonVoided);
    }

    protected double doCalculate(Stream<OrderLine> lines) {
        return 0;
    }

    Stream<OrderLine> filterVoided(Stream<OrderLine> list) {
        return list.filter(orderLine -> !orderLine.isVoided());
    }
}
