import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Stream;
public class TenPercentElectronicsPromotion extends Discount {

    @Override
    protected double doCalculate(Stream<OrderLine> lines) {
        Stream<OrderLine> nonVoidedElectronicItems = filterByElectronics(lines);

        if (nonVoidedElectronicItems.count() < 2) return 0;

        double highestPrice = getHighestPriceElectronicsItem(lines);

        return tenPercentOf(highestPrice);
    }

    private double tenPercentOf(double highestPrice) {
        return highestPrice * .1;
    }

    private double getHighestPriceElectronicsItem(Stream<OrderLine> lines) {
        return filterByElectronics(lines).max(Comparator.comparing(OrderLine::getCost)).get().getCost();
    }

    private Stream<OrderLine> filterByElectronics(Stream<OrderLine> lines) {
        Predicate<OrderLine> electronicsOnly = line -> line.getProductType() == Product.ProductType.Electronics;
        return filterVoided(lines.filter(electronicsOnly));
    }

}
