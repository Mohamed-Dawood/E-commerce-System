import model.*;
import core.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            Customer customer = new Customer("Fawry User", 1000);

            Product cheese = new ShippableExpirableProduct("Cheese", 100, 5, LocalDate.now().plusDays(2), 0.2);
            Product biscuits = new ShippableExpirableProduct("Biscuits", 150, 2, LocalDate.now().plusDays(1), 0.7);

            Cart cart = new Cart();
            cart.add(cheese, 2);
            cart.add(biscuits, 1);
            // No Scratch Card here

            CheckoutService.checkout(customer, cart);

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}
