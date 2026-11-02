/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorcyclemembershipsystem;


public class Customer {
    private String name;
    private String countryCode;
    private String phoneNumber;
    private String plateNumber;
    private int visitCount;
    private double totalSpent;

    public Customer(String name, String countryCode, String phoneNumber, String plateNumber, int visitCount, double totalSpent) {
        this.name = name;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.plateNumber = plateNumber;
        this.visitCount = visitCount;
        this.totalSpent = totalSpent;
    }

    public String getName() { return name; }
    public String getCountryCode() { return countryCode; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getFullPhone() { return countryCode + phoneNumber; }
    public String getPlateNumber() { return plateNumber; }
    public int getVisitCount() { return visitCount; }
    public double getTotalSpent() { return totalSpent; }

    public void setVisitCount(int visitCount) { this.visitCount = visitCount; }
    public void setTotalSpent(double totalSpent) { this.totalSpent = totalSpent; }

    public String toFileString() {
        return name + "," + countryCode + "," + phoneNumber + "," + plateNumber + "," + visitCount + "," + totalSpent;
    }

    public static Customer fromFileString(String line) {
        String[] p = line.split(",");
        if (p.length != 6) return null;
        try {
            return new Customer(p[0], p[1], p[2], p[3], Integer.parseInt(p[4]), Double.parseDouble(p[5]));
        } catch (Exception e) {
            return null;
        }
    }
}
