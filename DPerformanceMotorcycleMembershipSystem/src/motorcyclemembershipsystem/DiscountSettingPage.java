/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorcyclemembershipsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DiscountSettingPage extends JFrame{
    private DefaultTableModel model;
    private JTable table;

    public DiscountSettingPage() {
        setTitle("折扣设置");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000,650));
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                new MainMenu().setVisible(true);
            }
        });

        String password = JOptionPane.showInputDialog(this, "请输入经理密码:");
        if (password == null || !password.equals("123456")) {
            JOptionPane.showMessageDialog(this, "密码错误，不能修改折扣!");
            dispose(); new MainMenu().setVisible(true); return;
        }

        JPanel root = new JPanel(new BorderLayout(15,15));
        root.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        root.setBackground(Color.WHITE);

        JLabel title = new JLabel("折扣设置", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        title.setForeground(new Color(45,31,75));
        root.add(title, BorderLayout.NORTH);

        String[] cols = {"最低消费 RM", "最高消费 RM", "折扣 %"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        table.setRowHeight(28);
        loadRulesToTable();
        root.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton add = makeButton("增加范围");
        JButton remove = makeButton("删除选择");
        JButton back = makeButton("上一页");
        JButton done = makeButton("完成");
        JPanel bottom = new JPanel(); bottom.setBackground(Color.WHITE);
        bottom.add(add); bottom.add(remove); bottom.add(back); bottom.add(done);
        root.add(bottom, BorderLayout.SOUTH);
        setContentPane(root);

        add.addActionListener(e -> model.addRow(new Object[]{"0", "0", "0"}));
        remove.addActionListener(e -> { int row = table.getSelectedRow(); if (row >= 0) model.removeRow(row); });
        back.addActionListener(e -> { dispose(); new MainMenu().setVisible(true); });
        done.addActionListener(e -> saveRules());
    }

    private JButton makeButton(String text) { JButton b = new JButton(text); b.setFont(new Font("Microsoft YaHei", Font.BOLD, 16)); b.setBackground(new Color(102,51,153)); b.setForeground(Color.WHITE); return b; }

    private void loadRulesToTable() {
        model.setRowCount(0);
        for (DiscountRule r : FileManager.loadDiscountRules()) {
            model.addRow(new Object[]{r.getMinAmount(), r.getMaxAmount(), r.getPercentage()});
        }
    }

    private void saveRules() {
        ArrayList<DiscountRule> rules = new ArrayList<>();

        try {
            for (int i = 0; i < model.getRowCount(); i++) {

                String minText = model.getValueAt(i, 0).toString().trim();
                String maxText = model.getValueAt(i, 1).toString().trim();
                String percentText = model.getValueAt(i, 2).toString().trim();

                // Check empty
                if (minText.isEmpty() || maxText.isEmpty() || percentText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "第 " + (i + 1) + " 行不能留空!");
                    return;
                }

                // Check data type: only number or decimal
                if (!minText.matches("\\d+(\\.\\d+)?") ||
                    !maxText.matches("\\d+(\\.\\d+)?") ||
                    !percentText.matches("\\d+(\\.\\d+)?")) {

                    JOptionPane.showMessageDialog(this, "第 " + (i + 1) + " 行只能输入数字，例如 100 或 100.50!");
                    return;
                }

                double min = Double.parseDouble(minText);
                double max = Double.parseDouble(maxText);
                double percent = Double.parseDouble(percentText);

                // Check range logic
                if (min < 0) {
                    JOptionPane.showMessageDialog(this, "第 " + (i + 1) + " 行最低消费不能小过 0!");
                    return;
                }

                if (max <= min) {
                    JOptionPane.showMessageDialog(this, "第 " + (i + 1) + " 行最高消费必须大过最低消费!");
                    return;
                }

                if (percent < 0 || percent > 100) {
                    JOptionPane.showMessageDialog(this, "第 " + (i + 1) + " 行折扣必须在 0 到 100 之间!");
                    return;
                }

                rules.add(new DiscountRule(min, max, percent));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "请输入正确折扣资料!",
                "输入错误",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int times = FileManager.loadModifiedTimes() + 1;
        FileManager.saveDiscountRules(rules, times);

        JOptionPane.showMessageDialog(this, "折扣已安全保存! 已修改次数: " + times);
        dispose();
        new MainMenu().setVisible(true);
    }
}
