#  Pizza Vending Machine – Java Project

This is a simple **Pizza Vending Machine** simulation project created using **Java**, developed in two phases:

-  **Essentials of Programming (EOP)** – Text-based CLI version  
-  **Object-Oriented Programming (OOP)** – GUI-based version using Java Swing

---

##  Project Overview

The Pizza Vending Machine project demonstrates how to build a food-ordering system in Java, allowing users to:

- Select pizza options (size, topping, sauce)
- Add extras (cheese, olives, etc.)
- Simulate inventory management
- Handle and validate payments
- Print order summaries and calculate change

---

##  EOP Version (Command-Line Interface)

The **EOP version** uses:
- Text-based menus
- Scanner input
- File I/O for reading/writing inventory
- ArrayLists for data storage

###  Features

- Reads pizza components (buns, fillings, toppings, sauces) from `.txt` files
- User selects items from a menu
- Inventory quantities are updated after each order
- Calculates total price and validates payment input
- Outputs customer order to a text file (`cuslog.txt`)
- Includes helper methods like:
  - `bun()`, `fill()`, `top()`, `sauce()` – for item selection
  - `payment()` – for payment handling
  - `update()` – to write inventory back to files
  - `burgerProg()` – to simulate food preparation

---

##  OOP Version (Graphical User Interface)

The **OOP version** implements the same system with a modern GUI using **Java Swing**.

###  GUI Features

- Drop-downs for size, topping, and sauce
- Checkboxes for extra cheese and olives
- Combo box to choose payment method (Cash, Card, QR)
- Text fields for entering order and payment amount
- Progress bar simulates pizza preparation
- Displays total price and status updates

###  Core OOP Concepts Applied

- **Encapsulation**: Logical grouping of methods and state
- **Inheritance**: Uses `JFrame` and `ActionListener`
- **Polymorphism**: `actionPerformed()` handles different button events
- **Abstraction**: GUI separates user interaction from logic

---

##  License

This project is for educational and non-commercial academic purposes.
