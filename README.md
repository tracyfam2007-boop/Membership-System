# Motorcycle Membership Management System

## Project Description

The Motorcycle Membership Management System is a desktop-based application developed for D PERFORMANCE MOTORCYCLES SDN BHD. It helps manage customer membership records, track customer visits and spending, and calculate discounts based on purchase amounts.

The system provides a graphical user interface (GUI) to make customer and membership management easier.

## Features

- **Customer Registration** – Register new members by entering their name, country code, phone number and motorcycle plate number.
- **Membership Checking & Payment Discount** – Check customer membership status, calculate discounts based on spending amounts and update customer visit records.
- **Customer Search** – Search for registered members using their name, phone number or motorcycle plate number.
- **Customer Database** – View registered customer information, including visit counts and total spending.
- **Discount Settings** – Allow managers to add, edit and remove discount ranges.
- **Manager Authentication** – Require a manager password before modifying discount settings.
- **Data Management** – Save and load customer information and discount rules using text files.

## Technologies Used

- Java
- Java Swing (GUI)
- Java AWT
- File Handling (Text File Storage)

## Project Structure

- `MembershipSystem.java` – Main entry point of the application.
- `MainMenu.java` – Main menu interface.
- `Customer.java` – Customer information management.
- `CustomerRegistrationPage.java` – Customer registration interface.
- `MembershipCheckPage.java` – Membership checking and discount calculation.
- `SearchCustomerPage.java` – Customer search functionality.
- `CustomerDatabasePage.java` – Customer database display.
- `DiscountSettingPage.java` – Discount management interface.
- `DiscountRule.java` – Discount rule management.
- `FileManager.java` – Handles customer and discount data storage.

## Data Storage

The system uses text files to store information:

- `customers.txt` – Stores customer records, visit counts and total spending.
- `discount.txt` – Stores discount rules and the number of times discount settings have been modified.

## How to Run

1. Open the project using Apache NetBeans IDE.
2. Ensure all Java source files are included in the `motorcyclemembershipsystem` package.
3. Run `MembershipSystem.java`.
4. The main menu will appear.
5. Select the required function from the menu.

## Project Purpose

This project was developed to practise Java programming, object-oriented programming, GUI development and file handling while creating a practical membership management solution for a motorcycle business.
