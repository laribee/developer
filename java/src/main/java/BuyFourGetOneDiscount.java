import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
public class BuyFourGetOneDiscount extends Discount {

    @Override
    protected double doCalculate(Stream<OrderLine> lines) {
        double discount = 0;

        Map<String, Integer> skuMap = new HashMap<>();
        Map<String, Double> costMap = new HashMap<>();

        lines.forEach((line) -> {
            String sku = line.getSku();

            if (!skuMap.containsKey(sku)) skuMap.put(sku, 0);
            if (!costMap.containsKey(sku)) costMap.put(sku, line.getCost());

            Integer currentCount = skuMap.get(sku);
            skuMap.replace(sku, currentCount + 1);
        });

        for (String sku : skuMap.keySet()) {
            if (skuMap.get(sku) == 5) {
                discount += costMap.get(sku);
            }
        }
        return discount;

    }

}
