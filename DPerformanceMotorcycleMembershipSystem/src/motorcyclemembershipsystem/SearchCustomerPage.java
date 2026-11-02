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

public class SearchCustomerPage extends JFrame{
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<Customer> customers;

    public SearchCustomerPage() {
        setTitle("搜索会员");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000,650));
        customers = FileManager.loadCustomers();

        JPanel root = new JPanel(new BorderLayout(15,15));
        root.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        root.setBackground(Color.WHITE);

        JLabel title = new JLabel("动态搜索会员", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        title.setForeground(new Color(45,31,75));
        root.add(title, BorderLayout.NORTH);

        searchField = new JTextField();
        searchField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
        searchField.setBorder(BorderFactory.createTitledBorder("输入名字 / 电话 / 车牌"));
        root.add(searchField, BorderLayout.PAGE_START);

        String[] cols = {"名字", "国家号码", "电话号码", "车牌", "到访次数", "总消费"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        table.setRowHeight(28);
        root.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton back = makeButton("上一页");
        JPanel bottom = new JPanel(); bottom.setBackground(Color.WHITE); bottom.add(back);
        root.add(bottom, BorderLayout.SOUTH);
        setContentPane(root);

        refreshTable("");
        searchField.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { refreshTable(searchField.getText().trim()); }});
        back.addActionListener(e -> { dispose(); new MainMenu().setVisible(true); });
    }

    private JButton makeButton(String text) { JButton b = new JButton(text); b.setFont(new Font("Microsoft YaHei", Font.BOLD, 16)); b.setBackground(new Color(102,51,153)); b.setForeground(Color.WHITE); return b; }

    private void refreshTable(String keyword) {
        model.setRowCount(0);
        for (Customer c : customers) {
            if (keyword.isEmpty() || c.getName().toLowerCase().contains(keyword.toLowerCase()) || c.getFullPhone().contains(keyword) || c.getPlateNumber().toLowerCase().contains(keyword.toLowerCase())) {
                model.addRow(new Object[]{c.getName(), c.getCountryCode(), c.getPhoneNumber(), c.getPlateNumber(), c.getVisitCount(), String.format("RM%.2f", c.getTotalSpent())});
            }
        }
    }
}
