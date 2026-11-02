/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorcyclemembershipsystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends JFrame{
    private JLabel discountLabel;

    public MainMenu() {
        setTitle("D PERFORMANCE MOTORCYCLES SDN BHD 会员管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245,245,245));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(45, 31, 75));
        header.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        JLabel logoLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/shop_logo-removebg-preview.png"));
            Image img = icon.getImage().getScaledInstance(110, 90, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            logoLabel.setText("LOGO");
            logoLabel.setForeground(Color.WHITE);
        }

        JLabel title = new JLabel("D PERFORMANCE MOTORCYCLES 会员管理系统");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Microsoft YaHei", Font.BOLD, 32));

        discountLabel = new JLabel(getDiscountText());
        discountLabel.setForeground(new Color(255, 230, 120));
        discountLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));

        JPanel titlePanel = new JPanel(new GridLayout(2,1));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(discountLabel);

        header.add(logoLabel, BorderLayout.WEST);
        header.add(titlePanel, BorderLayout.CENTER);

        JPanel menu = new JPanel(new GridLayout(6,1,12,12));
        menu.setBorder(BorderFactory.createEmptyBorder(30,25,30,25));
        menu.setBackground(new Color(235,235,245));
        menu.setPreferredSize(new Dimension(260, 0));

        JButton btnRegister = makeButton("顾客注册");
        JButton btnCheck = makeButton("会员检查 / 付款折扣");
        JButton btnSearch = makeButton("搜索会员");
        JButton btnDatabase = makeButton("顾客资料库");
        JButton btnDiscount = makeButton("折扣设置");
        JButton btnExit = makeButton("退出系统");

        menu.add(btnRegister); menu.add(btnCheck); menu.add(btnSearch);
        menu.add(btnDatabase); menu.add(btnDiscount); menu.add(btnExit);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.WHITE);
        JLabel welcome = new JLabel("欢迎使用会员管理系统");
        welcome.setFont(new Font("Microsoft YaHei", Font.BOLD, 34));
        welcome.setForeground(new Color(45,31,75));
        center.add(welcome);

        root.add(header, BorderLayout.NORTH);
        root.add(menu, BorderLayout.WEST);
        root.add(center, BorderLayout.CENTER);
        setContentPane(root);

        btnRegister.addActionListener(e -> { dispose(); new CustomerRegistrationPage().setVisible(true); });
        btnCheck.addActionListener(e -> { dispose(); new MembershipCheckPage().setVisible(true); });
        btnSearch.addActionListener(e -> { dispose(); new SearchCustomerPage().setVisible(true); });
        btnDatabase.addActionListener(e -> { dispose(); new CustomerDatabasePage().setVisible(true); });
        btnDiscount.addActionListener(e -> { dispose(); new DiscountSettingPage().setVisible(true); });
        btnExit.addActionListener(e -> System.exit(0));
    }

    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        b.setBackground(new Color(102,51,153));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private String getDiscountText() {
        ArrayList<DiscountRule> rules = FileManager.loadDiscountRules();
        StringBuilder sb = new StringBuilder("当前折扣: ");
        for (DiscountRule r : rules) {
            sb.append("RM").append(String.format("%.2f", r.getMinAmount()))
              .append(" - RM").append(String.format("%.2f", r.getMaxAmount()))
              .append(" = ").append(String.format("%.0f", r.getPercentage())).append("%   ");
        }
        return sb.toString();
    }
}
