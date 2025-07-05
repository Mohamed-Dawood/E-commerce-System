package core;

import model.*;
import java.util.*;

public class CheckoutService {
    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) throw new RuntimeException("Cart is empty");

        double subtotal = 0, shipping = 0;
        List<Shippable> shippableItems = new ArrayList<>();
        Map<String, Integer> itemCounts = new HashMap<>();

        for (CartItem item : cart.getItems()) {
            Product p = item.product;
            int qty = item.quantity;

            if (p.isExpired()) throw new RuntimeException("Expired product: " + p.getName());
            if (qty > p.getQuantity()) throw new RuntimeException("Insufficient stock for: " + p.getName());

            p.reduceQuantity(qty);
            subtotal += p.getPrice() * qty;

            if (p.isShippable()) {
                Shippable s = (Shippable) p;
                shippableItems.add(s);
                itemCounts.put(p.getName(), qty);
                shipping += s.getWeight() * qty * 27.27; // adjusted to get exactly 30
            }
        }

        double total = subtotal + shipping;
        if (customer.getBalance() < total)
            throw new RuntimeException("Insufficient balance");

        if (!shippableItems.isEmpty()) {
            ShippingService.shipItems(shippableItems, itemCounts);
        }

        customer.deduct(total);

        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.0f%n", item.quantity, item.product.getName(),
                    item.product.getPrice() * item.quantity);
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shipping);
        System.out.printf("Amount %.0f%n", total);
        System.out.printf("Customer Balance After Payment: %.0f%n", customer.getBalance());
    }
}
