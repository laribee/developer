import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class BuyFourGetOneDiscount implements Discount {

    @Override
    public double calculate(ArrayList<OrderLine> lines) {
        double discount = 0;

        Map<String, Integer> skuMap = new HashMap<>();
        Map<String, Double> costMap = new HashMap<>();

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
        return discount;
    }

}
