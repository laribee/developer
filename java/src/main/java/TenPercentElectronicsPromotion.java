import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Stream;
public class TenPercentElectronicsPromotion implements Discount {
    @Override
    public double calculate(ArrayList<OrderLine> lines) {
        if (filterByElectronics(lines).count() < 2) return 0;

        double highestPrice = getHighestPriceElectronicsItem(lines);

        return tenPercentOf(highestPrice);
    }

    private double tenPercentOf(double highestPrice) {
        return highestPrice * .1;
    }

    private double getHighestPriceElectronicsItem(ArrayList<OrderLine> lines) {
        return filterByElectronics(lines).max(Comparator.comparing(OrderLine::getCost)).get().getCost();
    }

    private Stream<OrderLine> filterByElectronics(ArrayList<OrderLine> lines) {
        Predicate<OrderLine> filter = line -> line.getProductType() == Product.ProductType.Electronics;
        return lines.stream().filter(filter).filter(orderLine -> !orderLine.isVoided());
    }
}
