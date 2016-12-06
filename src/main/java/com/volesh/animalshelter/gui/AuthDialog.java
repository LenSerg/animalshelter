package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.UserDAO;
import com.volesh.animalshelter.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthDialog extends JDialog implements ActionListener {
    private static final String ENTER = "ENTER";
    private static final String REGISTRATION = "REGISTRATION";
    private boolean auth = false;

    private UserDAO userManager = new UserDAO();
    private JTextField loginField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JPanel authPanel = new JPanel();
    private GridBagLayout gridBag = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    public AuthDialog() {
        userManager.findUser();
        setTitle("Авторизация");
        setLayout(new BorderLayout());

        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        authPanel.setLayout(gridBag);

        addField(new JLabel("Логин:"), loginField, 200, 25);
        addField(new JLabel("Пароль:"), passwordField, 200, 25);

        add(authPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("Войти", ENTER));
        buttonPanel.add(createButton("Регистрация", REGISTRATION));

        add(buttonPanel, BorderLayout.SOUTH);

        setModal(true);
        setResizable(false);
        setBounds(400, 300, 360, 160);
        setVisible(true);
    }

    private JButton createButton(String title, String action) {
        JButton button = new JButton(title);
        button.setIcon(new ImageIcon(action + ".png"));
        button.setActionCommand(action);
        button.addActionListener(this);
        return button;
    }

    private void addField(JLabel jLabel, JComponent component, int width, int high) {
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gridBag.setConstraints(jLabel, gbc);
        authPanel.add(jLabel);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        component.setPreferredSize(new Dimension(width, high));
        gridBag.setConstraints(component, gbc);
        authPanel.add(component);
    }

    public boolean isAuthorization() {
        return auth;
    }


    public String getHash(char[] strMass) {
        String str = new String(strMass);

        MessageDigest md5 ;
        StringBuffer  hexString = new StringBuffer();

        try {
            md5 = MessageDigest.getInstance("md5");
            md5.reset();
            md5.update(str.getBytes());
            byte messageDigest[] = md5.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }

        }
        catch (NoSuchAlgorithmException e) {
            return e.toString();
        }
        return hexString.toString();
    }


    private void authorization() {
        User user = userManager.findUserForAuth(loginField.getText(), getHash(passwordField.getPassword()));
        if (user != null) {
            auth = true;
            setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(this, "Неверное имя пользователя или пароль", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registration() {
        RegistrationDialog dialog = new RegistrationDialog(null, "Регистрация пользователя");
        if (dialog.isSave()) {
            userManager.addUser(dialog.getNewUser());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case ENTER:
                authorization();
                break;
            case REGISTRATION:
                registration();
                break;
            default:
                setVisible(false);
                break;
        }
    }
}
