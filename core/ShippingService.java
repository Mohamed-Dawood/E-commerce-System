package core;

import model.Shippable;
import java.util.*;

public class ShippingService {
    public static void shipItems(List<Shippable> items, Map<String, Integer> itemCounts) {
        System.out.println("** Shipment notice **");
        double totalWeight = 0;
        for (Shippable item : items) {
            double itemWeight = item.getWeight() * itemCounts.get(item.getName());
            System.out.printf("%dx %s %.0fg%n", itemCounts.get(item.getName()), item.getName(), itemWeight * 1000);
            totalWeight += itemWeight;
        }
        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }
}
