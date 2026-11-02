/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorcyclemembershipsystem;

public class DiscountRule {
    private double minAmount;
    private double maxAmount;
    private double percentage;

    public DiscountRule(double minAmount, double maxAmount, double percentage) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.percentage = percentage;
    }

    public double getMinAmount() { return minAmount; }
    public double getMaxAmount() { return maxAmount; }
    public double getPercentage() { return percentage; }

    public boolean matches(double amount) {
        return amount >= minAmount && amount <= maxAmount;
    }

    public String toFileString() {
        return minAmount + "," + maxAmount + "," + percentage;
    }

    public static DiscountRule fromFileString(String line) {
        String[] p = line.split(",");
        if (p.length != 3) return null;
        try {
            return new DiscountRule(Double.parseDouble(p[0]), Double.parseDouble(p[1]), Double.parseDouble(p[2]));
        } catch (Exception e) {
            return null;
        }
    }
}
