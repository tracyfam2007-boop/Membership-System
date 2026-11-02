/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorcyclemembershipsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MembershipCheckPage extends JFrame{
    private JTextField searchField, amountField;
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<Customer> customers;

    public MembershipCheckPage() {
        setTitle("会员检查 / 付款折扣");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000,650));
        customers = FileManager.loadCustomers();

        JPanel root = new JPanel(new BorderLayout(15,15));
        root.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        root.setBackground(Color.WHITE);

        JLabel title = new JLabel("会员检查 / 付款折扣", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        title.setForeground(new Color(45,31,75));
        root.add(title, BorderLayout.NORTH);

        JPanel top = new JPanel(new GridLayout(2,2,10,10));
        top.setBackground(Color.WHITE);
        top.add(label("搜索名字 / 电话 / 车牌:"));
        searchField = new JTextField();
        top.add(searchField);
        top.add(label("输入消费金额 RM:"));
        amountField = new JTextField();
        top.add(amountField);

        String[] cols = {"名字", "电话", "车牌", "到访次数", "总消费"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        table.setRowHeight(28);

        JButton back = makeButton("上一页");
        JButton done = makeButton("完成");
        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.add(back); bottom.add(done);

        root.add(top, BorderLayout.PAGE_START);
        root.add(new JScrollPane(table), BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);
        setContentPane(root);

        refreshTable("");

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                if (row >= 0) {
                    String customerName = model.getValueAt(row, 0).toString();

                    searchField.setText(customerName);
                    amountField.setText(""); // leave amount blank
                }
            }
        });

        searchField.addKeyListener(new KeyAdapter() { 
            public void keyReleased(KeyEvent e) { 
                refreshTable(searchField.getText().trim()); 
            }
        });
        back.addActionListener(e -> { dispose(); new MainMenu().setVisible(true); });
        done.addActionListener(e -> processPayment());
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text); l.setFont(new Font("Microsoft YaHei", Font.BOLD, 16)); return l;
    }
    private JButton makeButton(String text) {
        JButton b = new JButton(text); b.setFont(new Font("Microsoft YaHei", Font.BOLD, 16)); b.setBackground(new Color(102,51,153)); b.setForeground(Color.WHITE); return b;
    }

    private void refreshTable(String keyword) {
        model.setRowCount(0);
        for (Customer c : customers) {
            if (keyword.isEmpty() || c.getName().toLowerCase().contains(keyword.toLowerCase()) || c.getFullPhone().contains(keyword) || c.getPlateNumber().toLowerCase().contains(keyword.toLowerCase())) {
                model.addRow(new Object[]{c.getName(), c.getFullPhone(), c.getPlateNumber(), c.getVisitCount(), String.format("RM%.2f", c.getTotalSpent())});
            }
        }
    }

    private void processPayment() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "请先选择会员。如果没有资料，代表顾客不是会员。"); return; }
        String amountText = amountField.getText().trim();
        if (amountText.isEmpty()) { JOptionPane.showMessageDialog(this, "消费金额不能为空!"); return; }
        double amount;
        try { amount = Double.parseDouble(amountText); if (amount <= 0) throw new Exception(); }
        catch (Exception e) { JOptionPane.showMessageDialog(this, "请输入正确的消费金额!"); return; }

        String phone = model.getValueAt(row, 1).toString();
        Customer selected = null;
        for (Customer c : customers) if (c.getFullPhone().equals(phone)) selected = c;
        if (selected == null) { JOptionPane.showMessageDialog(this, "顾客不是会员!"); return; }

        double percent = getDiscountPercent(amount);
        double discounted = amount - (amount * percent / 100.0);
        selected.setVisitCount(selected.getVisitCount() + 1);
        selected.setTotalSpent(selected.getTotalSpent() + amount);
        FileManager.saveCustomers(customers);
        refreshTable(searchField.getText().trim());

        JOptionPane.showMessageDialog(this,
            "会员: " + selected.getName() +
            "\n原价: RM" + String.format("%.2f", amount) +
            "\n折扣: " + String.format("%.0f", percent) + "%" +
            "\n折扣后总额: RM" + String.format("%.2f", discounted) +
            "\n到访次数: " + selected.getVisitCount());
    }

    private double getDiscountPercent(double amount) {
        for (DiscountRule r : FileManager.loadDiscountRules()) if (r.matches(amount)) return r.getPercentage();
        return 0;
    }
}
