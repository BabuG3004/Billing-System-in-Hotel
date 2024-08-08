package com.Hotel;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class BillingSystem {
    String itemName;
    int price, quantity;
    double subtotal;

    BillingSystem(String itemName, int price, int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = price * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-10d %-10d %-10.2f", itemName, price, quantity, subtotal);
    }
}

public class BillGenerator {
    private static Scanner scan = new Scanner(System.in);
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private static final Map<Integer, String> itemNames = new HashMap<>();
    private static final Map<Integer, Integer> itemPrices = new HashMap<>();

    static {
        itemNames.put(1, "Coffee");
        itemPrices.put(1, 10);
        itemNames.put(2, "Coke");
        itemPrices.put(2, 40);
        itemNames.put(3, "Peri peri Fries");
        itemPrices.put(3, 69);
        itemNames.put(4, "Panner Sandwich");
        itemPrices.put(4, 109);
        itemNames.put(5, "Water");
        itemPrices.put(5, 20);
        itemNames.put(6, "Pizza");
        itemPrices.put(6, 80);
        itemNames.put(7, "Burger");
        itemPrices.put(7, 90);
        itemNames.put(8, "Gobi");
        itemPrices.put(8, 75);
        itemNames.put(9, "Rose Milk");
        itemPrices.put(9, 50);
        itemNames.put(10, "Mango Milk shake");
        itemPrices.put(10, 60);
    }

    private static String getCustomerName() {
        System.out.println("Welcome to Delicious Delights! 🍔🍣🍕");
        System.out.println("        South Indian Foods");
        String name = "Customer";
        return name;
    }

    private static void displayMenu() {
        System.out.println("_______________________________________________");
        System.out.println(String.format("%-10s %-20s %-10s", "Item ID", "Item Name", "Item Cost"));
        System.out.println("_______________________________________________");
        for (Map.Entry<Integer, String> entry : itemNames.entrySet()) {
            int id = entry.getKey();
            String name = entry.getValue();
            int price = itemPrices.get(id);
            System.out.printf("%-10d %-20s %-10d%n", id, name, price);
        }
        System.out.println("_______________________________________________");
    }

    private static int getItemId() {
        System.out.println("Enter the Item ID which you want to buy:");
        while (!scan.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid Item ID:");
            scan.next();
        }
        return scan.nextInt();
    }

    private static int getQuantity() {
        System.out.println("Enter the Quantity:");
        while (!scan.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid Quantity:");
            scan.next();
        }
        return scan.nextInt();
    }

    private static BillingSystem createItem(int itemId, int quantity) {
        String itemName = itemNames.get(itemId);
        int price = itemPrices.get(itemId);
        return new BillingSystem(itemName, price, quantity);
    }

    private static char shouldContinue() {
        System.out.println("Do you want to continue (Y/N)?");
        String c = scan.next().toUpperCase();
        return c.charAt(0);
    }

    private static boolean askForDiscount() {
        System.out.println("Do you want to apply a 10% discount? (Y/N)");
        String response = scan.next().toUpperCase();
        return response.charAt(0) == 'Y';
    }

    private static double applyDiscounts(double grandTotal) {
        double discount = grandTotal * 0.10;
        System.out.println("A 10% discount has been applied! Discount: " + df.format(discount));
        return discount;
    }

    private static void printBill(String name, ArrayList<BillingSystem> items, double grandTotal, double discount) {
        System.out.println("HI " + name + ", THIS IS YOUR BILL");
        System.out.println("Customer name: " + name);
        System.out.println("______________________________________________________");
        System.out.println(String.format("%-20s %-10s %-10s %-10s", "Item Name", "Price", "Quantity", "Subtotal"));
        System.out.println("______________________________________________________");

        for (BillingSystem item : items) {
            System.out.println(item);
        }

        double finalTotal = grandTotal - discount;

        System.out.println("______________________________________________________");
        System.out.println("Grand Total                                     " + df.format(grandTotal));
        if (discount > 0) {
            System.out.println("Discount                                        -" + df.format(discount));
            System.out.println("______________________________________________________");
            System.out.println("Final Amount to be paid                         " + df.format(finalTotal));
        } else {
            System.out.println("______________________________________________________");
            System.out.println("Amount to be paid                               " + df.format(finalTotal));
        }
        System.out.println("______________________________________________________");
    }

    public static void main(String[] args) {
        ArrayList<BillingSystem> newItem = new ArrayList<>();
        String name = getCustomerName();
        displayMenu();

        char cont = 'Y';
        double grandTotal = 0;

        while (cont == 'Y') {
            int itemId = getItemId();
            if (itemNames.containsKey(itemId)) {
                int quantity = getQuantity();
                BillingSystem item = createItem(itemId, quantity);
                newItem.add(item);
                grandTotal += item.subtotal;
            } else {
                System.out.println("Invalid Item ID. Please try again.");
            }
            cont = shouldContinue();
        }

        boolean applyDiscount = askForDiscount();
        double discount = applyDiscount ? applyDiscounts(grandTotal) : 0.0;

        printBill(name, newItem, grandTotal, discount);
        System.out.println("THANK YOU! VISIT AGAIN.");
    }
}
