/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pizza.vending.machine.oop;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
/**
 *
 * @author sundu
 */
public class PizzaVendingMachine extends JFrame implements ActionListener {

    /**
     * @param args the command line arguments
     */
   private static final long serialVersionUID = 1L;

    JComboBox<String> size, topping, sauce, paymentMethod;
    JCheckBox extraCheese, extraOlives;
    JButton addToOrder, makePayment;
    JTextField order, paymentField;
    JLabel statusLabel, priceLabel;
    JProgressBar progressBar;
    HashMap<String, Double> prices;
    HashMap<String, Integer> quantities;
    double totalPrice;

    public PizzaVendingMachine() {
        setSize(600, 500);
        setTitle("Pizza Vending Machine");

        quantities = new HashMap<>();
        initializeQuantities();

        prices = new HashMap<>();
        initializePrices();

        size = new JComboBox<>(new String[]{"personal", "regular", "large"});
        topping = new JComboBox<>(new String[]{"chicken pepperoni", "beef pepperoni", "hawaiian", "seafood", "cheese"});
        sauce = new JComboBox<>(new String[]{"tomato", "barbeque", "garlic"});
        paymentMethod = new JComboBox<>(new String[]{"Cash", "Card", "QR"});
        extraCheese = new JCheckBox("Extra Cheese (+$1.00)");
        extraOlives = new JCheckBox("Extra Olives (+$0.50)");
        addToOrder = new JButton("Add to Order");
        makePayment = new JButton("Make Payment");
        order = new JTextField();
        paymentField = new JTextField();
        statusLabel = new JLabel("Status: ");
        priceLabel = new JLabel("Price: $0.0");
        progressBar = new JProgressBar();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Size:"));
        panel.add(size);
        panel.add(new JLabel("Topping:"));
        panel.add(topping);
        panel.add(new JLabel("Sauce:"));
        panel.add(sauce);
        panel.add(extraCheese);
        panel.add(extraOlives);
        panel.add(addToOrder);
        panel.add(new JLabel(""));
        panel.add(new JLabel("Order:"));
        panel.add(order);
        panel.add(new JLabel("Payment Method:"));
        panel.add(paymentMethod);
        panel.add(new JLabel("Payment Amount:"));
        panel.add(paymentField);
        panel.add(new JLabel("Price:"));
        panel.add(priceLabel);
        panel.add(makePayment);
        panel.add(new JLabel("Status:"));
        panel.add(statusLabel);
        panel.add(new JLabel("Progress:"));
        panel.add(progressBar);

        addToOrder.addActionListener(this);
        makePayment.addActionListener(this);

        add(panel, BorderLayout.CENTER);
    }

    private void initializeQuantities() {
        quantities.put("personal", 10);
        quantities.put("regular", 10);
        quantities.put("large", 10);
        quantities.put("tomato", 10);
        quantities.put("barbeque", 10);
        quantities.put("garlic", 10);
        quantities.put("chicken pepperoni", 10);
        quantities.put("beef pepperoni", 10);
        quantities.put("hawaiian", 10);
        quantities.put("seafood", 10);
        quantities.put("cheese", 10);
    }

    private void initializePrices() {
        prices.put("personal", 5.0);
        prices.put("regular", 8.0);
        prices.put("large", 10.0);
        prices.put("chicken pepperoni", 1.5);
        prices.put("beef pepperoni", 1.5);
        prices.put("hawaiian", 2.0);
        prices.put("seafood", 2.5);
        prices.put("cheese", 1.0);
        prices.put("tomato", 0.5);
        prices.put("barbeque", 0.7);
        prices.put("garlic", 0.6);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addToOrder) {
            String sizeChoice = (String) size.getSelectedItem();
            String toppingChoice = (String) topping.getSelectedItem();
            String sauceChoice = (String) sauce.getSelectedItem();
            boolean hasExtraCheese = extraCheese.isSelected();
            boolean hasExtraOlives = extraOlives.isSelected();

            if (quantities.get(sizeChoice) > 0 && quantities.get(toppingChoice) > 0 && quantities.get(sauceChoice) > 0) {
                order.setText(order.getText() + String.format("Size: %s, Topping: %s, Sauce: %s\n", sizeChoice, toppingChoice, sauceChoice));
                quantities.put(sizeChoice, quantities.get(sizeChoice) - 1);
                quantities.put(toppingChoice, quantities.get(toppingChoice) - 1);
                quantities.put(sauceChoice, quantities.get(sauceChoice) - 1);

                double itemPrice = calculatePrice(sizeChoice, toppingChoice, sauceChoice, hasExtraCheese, hasExtraOlives);
                totalPrice += itemPrice;
                priceLabel.setText("Price: $" + totalPrice);

                // Simulate pizza making stages
                simulatePizzaMaking();

                updateStatusLabel("Pizza added to order.");
            } else {
                updateStatusLabel("One or more of the selected items is out of stock.");
            }
        } else if (e.getSource() == makePayment) {
            try {
                double paymentAmount = Double.parseDouble(paymentField.getText());
                String paymentMethodChoice = (String) paymentMethod.getSelectedItem();

                if (totalPrice > 0 && validatePayment(paymentAmount, paymentMethodChoice)) {
                    double change = makePayment(totalPrice, paymentAmount);
                    order.setText("");
                    totalPrice = 0;
                    paymentField.setText("");
                    priceLabel.setText("Price: $0.0");
                    progressBar.setValue(0);
                    updateStatusLabel(String.format("Payment successful. Change: $%.2f", change));
                } else {
                    updateStatusLabel("Invalid payment amount or method.");
                }
            } catch (NumberFormatException ex) {
                updateStatusLabel("Invalid payment amount.");
            }
        }
    }

    private double calculatePrice(String size, String topping, String sauce, boolean extraCheese, boolean extraOlives) {
        double basePrice = prices.get(size);
        double toppingPrice = prices.get(topping);
        double saucePrice = prices.get(sauce);
        double extraCheesePrice = extraCheese ? 1.0 : 0.0;
        double extraOlivesPrice = extraOlives ? 0.5 : 0.0;
        return basePrice + toppingPrice + saucePrice + extraCheesePrice + extraOlivesPrice;
    }

    private double makePayment(double totalPrice, double paymentAmount) {
        return paymentAmount - totalPrice;
    }

    private boolean validatePayment(double paymentAmount, String paymentMethod) {
        // You can add more sophisticated validation logic here based on your requirements
        return paymentAmount >= totalPrice;
    }

    private void updateStatusLabel(String message) {
        statusLabel.setText("Status: " + message);
    }

    private void simulatePizzaMaking() {
        // Simulate pizza making stages
        int progress = 0;
        while (progress <= 100) {
            try {
                Thread.sleep(100); // Simulate time for each stage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressBar.setValue(progress);
            progress += 10;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PizzaVendingMachine().setVisible(true);
        });
    }
}
