/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorcyclemembershipsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CustomerDatabasePage extends JFrame{
    public CustomerDatabasePage() {
        setTitle("顾客资料库");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000,650));

        JPanel root = new JPanel(new BorderLayout(15,15));
        root.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        root.setBackground(Color.WHITE);

        JLabel title = new JLabel("顾客资料库", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        title.setForeground(new Color(45,31,75));
        root.add(title, BorderLayout.NORTH);

        String[] cols = {"名字", "国家号码", "电话号码", "车牌", "到访次数", "总消费"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        ArrayList<Customer> list = FileManager.loadCustomers();
        for (Customer c : list) model.addRow(new Object[]{c.getName(), c.getCountryCode(), c.getPhoneNumber(), c.getPlateNumber(), c.getVisitCount(), String.format("RM%.2f", c.getTotalSpent())});

        JTable table = new JTable(model);
        table.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        table.setRowHeight(28);
        root.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton back = makeButton("上一页");
        JPanel bottom = new JPanel(); bottom.setBackground(Color.WHITE); bottom.add(back);
        root.add(bottom, BorderLayout.SOUTH);
        setContentPane(root);
        back.addActionListener(e -> { dispose(); new MainMenu().setVisible(true); });
    }
    
    private JButton makeButton(String text) { JButton b = new JButton(text); b.setFont(new Font("Microsoft YaHei", Font.BOLD, 16)); b.setBackground(new Color(102,51,153)); b.setForeground(Color.WHITE); return b; }
}
