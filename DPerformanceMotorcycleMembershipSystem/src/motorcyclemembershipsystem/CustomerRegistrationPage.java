/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorcyclemembershipsystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CustomerRegistrationPage extends JFrame{
    private JTextField nameField, codeField, phoneField, plateField;

    public CustomerRegistrationPage() {
        setTitle("顾客注册");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900,600));

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12,12,12,12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("顾客注册", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        title.setForeground(new Color(45,31,75));
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        main.add(title, gbc);

        nameField = new JTextField(25);
        codeField = new JTextField("+60", 25);
        phoneField = new JTextField(25);
        plateField = new JTextField(25);

        addRow(main, gbc, 1, "输入顾客名字:", nameField);
        addRow(main, gbc, 2, "输入国家号码:", codeField);
        addRow(main, gbc, 3, "输入电话号码:", phoneField);
        addRow(main, gbc, 4, "输入摩托车车牌:", plateField);

        JButton back = makeButton("上一页");
        JButton done = makeButton("完成");
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(back); btnPanel.add(done);
        gbc.gridx=0; gbc.gridy=5; gbc.gridwidth=2;
        main.add(btnPanel, gbc);

        setContentPane(main);
        back.addActionListener(e -> { dispose(); new MainMenu().setVisible(true); });
        done.addActionListener(e -> saveCustomer());
    }

    private void addRow(JPanel p, GridBagConstraints gbc, int y, String label, JTextField field) {
        gbc.gridwidth=1;
        gbc.gridx=0; gbc.gridy=y;
        JLabel l = new JLabel(label);
        l.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        p.add(l, gbc);
        gbc.gridx=1;
        field.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        p.add(field, gbc);
    }

    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        b.setBackground(new Color(102,51,153));
        b.setForeground(Color.WHITE);
        return b;
    }

    private void saveCustomer() {
        String name = nameField.getText().trim();
        String code = codeField.getText().trim();
        String phone = phoneField.getText().trim();
        String plate = plateField.getText().trim().toUpperCase();

        if (name.isEmpty() || code.isEmpty() || phone.isEmpty() || plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有资料都不能为空!"); return;
        }
        if (!code.startsWith("+") || code.length() < 2) {
            JOptionPane.showMessageDialog(this, "国家号码必须以 + 开始，例如 +60"); return;
        }
        if (!phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "电话号码只能输入数字!"); return;
        }
        if (!plate.matches("[A-Z0-9]+")) {
            JOptionPane.showMessageDialog(this, "车牌只能输入英文字母和数字!"); return;
        }

        ArrayList<Customer> list = FileManager.loadCustomers();
        for (Customer c : list) {
            if (c.getFullPhone().equalsIgnoreCase(code + phone)) {
                JOptionPane.showMessageDialog(this, "这个电话号码已经注册过!"); return;
            }
            if (c.getPlateNumber().equalsIgnoreCase(plate)) {
                JOptionPane.showMessageDialog(this, "这个车牌已经注册过!"); return;
            }
        }

        list.add(new Customer(name, code, phone, plate, 0, 0));
        FileManager.saveCustomers(list);
        JOptionPane.showMessageDialog(this, "资料已安全保存!");
        dispose(); new MainMenu().setVisible(true);
    }
}
