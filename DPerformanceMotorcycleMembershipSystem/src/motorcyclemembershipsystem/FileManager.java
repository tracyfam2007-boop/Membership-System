/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorcyclemembershipsystem;

import java.io.*;
import java.util.*;

public class FileManager {
    public static final String CUSTOMER_FILE = "customers.txt";
    public static final String DISCOUNT_FILE = "discount.txt";

    public static ArrayList<Customer> loadCustomers() {
        ArrayList<Customer> list = new ArrayList<>();
        File file = new File(CUSTOMER_FILE);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Customer c = Customer.fromFileString(line);
                if (c != null) list.add(c);
            }
        } catch (IOException e) {
            System.out.println("读取顾客资料失败: " + e.getMessage());
        }
        return list;
    }

    public static void saveCustomers(ArrayList<Customer> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CUSTOMER_FILE))) {
            for (Customer c : list) pw.println(c.toFileString());
        } catch (IOException e) {
            System.out.println("保存顾客资料失败: " + e.getMessage());
        }
    }

    public static ArrayList<DiscountRule> loadDiscountRules() {
        ArrayList<DiscountRule> rules = new ArrayList<>();
        File file = new File(DISCOUNT_FILE);

        if (!file.exists()) {
            rules.add(new DiscountRule(0, 99.99, 0));
            rules.add(new DiscountRule(100, 299.99, 5));
            rules.add(new DiscountRule(300, 599.99, 10));
            rules.add(new DiscountRule(600, 999999, 15));
            saveDiscountRules(rules, 0);
            return rules;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("ModifiedTimes=")) continue;
                DiscountRule r = DiscountRule.fromFileString(line);
                if (r != null) rules.add(r);
            }
        } catch (IOException e) {
            System.out.println("读取折扣资料失败: " + e.getMessage());
        }
        return rules;
    }

    public static int loadModifiedTimes() {
        File file = new File(DISCOUNT_FILE);
        if (!file.exists()) return 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line != null && line.startsWith("ModifiedTimes=")) {
                return Integer.parseInt(line.substring(14));
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    public static void saveDiscountRules(ArrayList<DiscountRule> rules, int modifiedTimes) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DISCOUNT_FILE))) {
            pw.println("ModifiedTimes=" + modifiedTimes);
            for (DiscountRule r : rules) pw.println(r.toFileString());
        } catch (IOException e) {
            System.out.println("保存折扣资料失败: " + e.getMessage());
        }
    }
}
