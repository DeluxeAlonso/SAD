/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.user;

import application.profile.ProfileApplication;
import application.user.UserApplication;
import entity.Usuario;
import java.awt.Color;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import util.EntityState;
import util.EntityType;
import util.InstanceFactory;
import util.Regex;
import util.Strings;
import util.Tools;

/**
 *
 * @author Nevermade
 */
public class NewUserView extends javax.swing.JDialog {

    UserApplication userApplication = InstanceFactory.Instance.getInstance("userApplication", UserApplication.class);
    ProfileApplication profileApplication = InstanceFactory.Instance.getInstance("profileApplication", ProfileApplication.class);
    Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);
    Border regularBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);

    /**
     * Creates new form NewUser
     */
    public NewUserView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //initialize user states combo
        fillCombos();

    }

    public void fillCombos() {
        comboState.setModel(new javax.swing.DefaultComboBoxModel(EntityState.getUsersState()));
        comboProfile.setModel(new javax.swing.DefaultComboBoxModel(EntityType.PROFILES_NAMES));
    }

    public void clearFields() {
        txtName.setText("");
        txtFirstName.setText("");
        txtSecondName.setText("");
        txtEmail.setText("");
        comboState.setSelectedIndex(0);
        txtPassword.setText("");
    }

    public boolean isValidForm() {
        String message = "";
        String name = txtName.getText().trim();
        String firstName = txtFirstName.getText().trim();
        String secondName = txtSecondName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText();
        //Validation name
        if (name.length() > 0 && name.length() <= 40) {
            if (!name.matches(Regex.ONLY_LETTERS)) {
                message += Strings.ERROR_NAME_ONLY_LETTERS + "\n";
                txtName.setBorder(errorBorder);
            } else {
                txtName.setBorder(regularBorder);
            }
        } else {
            if (name.length() == 0) {
                message += Strings.ERROR_NAME_REQUIRED + "\n";
            }
            if (name.length() > 40) {
                message += Strings.ERROR_NAME_LENGTH + "\n";
            }
            txtName.setBorder(errorBorder);
        }
        //Validation firstName
        if (firstName.length() > 0 && firstName.length() <= 40) {
            if (!firstName.matches(Regex.ONLY_LETTERS)) {
                message += Strings.ERROR_FIRSTNAME_ONLY_LETTERS + "\n";
                txtFirstName.setBorder(errorBorder);
            } else {
                txtFirstName.setBorder(regularBorder);
            }
        } else {
            if (firstName.length() == 0) {
                message += Strings.ERROR_FIRSTNAME_REQUIRED + "\n";
            }
            if (firstName.length() > 40) {
                message += Strings.ERROR_FIRSTNAME_LENGTH + "\n";
            }
            txtFirstName.setBorder(errorBorder);
        }
        //Validation secondName
        if (secondName.length() > 0 && secondName.length() <= 40) {
            if (!secondName.matches(Regex.ONLY_LETTERS)) {
                message += Strings.ERROR_SECONDNAME_ONLY_LETTERS + "\n";
                txtSecondName.setBorder(errorBorder);
            } else {
                txtSecondName.setBorder(regularBorder);
            }
        } else {
            if (secondName.length() == 0) {
                message += Strings.ERROR_SECONDNAME_REQUIRED + "\n";
            }
            if (secondName.length() > 40) {
                message += Strings.ERROR_SECONDNAME_LENGTH + "\n";
            }
            txtSecondName.setBorder(errorBorder);
        }

        //Validation email
        if (email.length() > 0 && email.length() <= 40) {
            if (!email.matches(Regex.EMAIL)) {
                message += Strings.ERROR_EMAIL_INVALID + "\n";
                txtEmail.setBorder(errorBorder);
            } else {
                if (userApplication.doesUserExist(email)) {
                    message += Strings.ERROR_USER_SAME_EMAIL + "\n";
                    txtEmail.setBorder(errorBorder);
                } else {
                    txtEmail.setBorder(regularBorder);
                }
            }

        } else {
            if (email.length() == 0) {
                message += Strings.ERROR_EMAIL_REQUIRED + "\n";
            }
            if (email.length() > 40) {
                message += Strings.ERROR_EMAIL_LENGTH + "\n";
            }
            txtEmail.setBorder(errorBorder);
        }
        //Validation password
        if (password.length() > 0 && password.length() <= 40) {
            if (password.length() >= 6) {

                txtPassword.setBorder(regularBorder);

            } else {
                message += Strings.ERROR_PASSWORD_MIN_LENGTH + "\n";
                txtPassword.setBorder(errorBorder);
            }
        }else{
            if (password.length() == 0) {
                message += Strings.ERROR_PASSWORD_REQUIRED + "\n";
            }
            if (password.length() > 40) {
                message += Strings.ERROR_EMAIL_LENGTH + "\n";
            }
            txtPassword.setBorder(errorBorder);
        }
        //validate State
        if (comboState.getSelectedIndex() == 0) {
            message += Strings.ERROR_USER_STATUS_REQUIRED + "\n";
            comboState.setBorder(errorBorder);
        } else {
            comboState.setBorder(regularBorder);
        }
        //Validate profile
        if (comboProfile.getSelectedIndex() == 0) {
            message += Strings.ERROR_USER_PROFILE_REQUIRED + "\n";
            comboProfile.setBorder(errorBorder);
        } else {
            comboProfile.setBorder(regularBorder);
        }
        if (!message.equals("")) {
            JOptionPane.showMessageDialog(this, message, "Mensaje", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancelBtn = new javax.swing.JButton();
        saveTxt = new javax.swing.JButton();
        comboProfile = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        comboState = new javax.swing.JComboBox();
        txtPassword = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        autoGeneratePassBtn = new javax.swing.JButton();
        txtEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtSecondName = new javax.swing.JTextField();
        txtFirstName = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo Usuario");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        cancelBtn.setText("Cancelar");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        saveTxt.setText("Guardar");
        saveTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTxtActionPerformed(evt);
            }
        });

        comboProfile.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("*Perfil:");

        jLabel9.setText("*Estado:");

        comboState.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("*Contraseña:");

        autoGeneratePassBtn.setText("Generar");
        autoGeneratePassBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoGeneratePassBtnActionPerformed(evt);
            }
        });

        jLabel11.setText("*Correo:");

        jLabel12.setText("*Apellido Materno:");

        jLabel13.setText("*Apellido Paterno:");

        jLabel14.setText("*Nombres:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel14)
                        .addGap(42, 42, 42)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel13)
                        .addGap(6, 6, 6)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel12)
                        .addGap(4, 4, 4)
                        .addComponent(txtSecondName, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel11)
                        .addGap(51, 51, 51)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel10)
                        .addGap(27, 27, 27)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(autoGeneratePassBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel9)
                        .addGap(51, 51, 51)
                        .addComponent(comboState, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel8)
                        .addGap(60, 60, 60)
                        .addComponent(comboProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(saveTxt)
                        .addGap(29, 29, 29)
                        .addComponent(cancelBtn)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel14))
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel13))
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel12))
                    .addComponent(txtSecondName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel11))
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel10))
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(autoGeneratePassBtn)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9))
                    .addComponent(comboState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(comboProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveTxt)
                    .addComponent(cancelBtn))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // TODO add your handling code here:
        UserView.userView.refreshGrid();
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void saveTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTxtActionPerformed
        // TODO add your handling code here:
        if (isValidForm()) {
            Usuario user = new Usuario();
            user.setId(UUID.randomUUID().toString().replace("-", ""));
            user.setNombre(txtName.getText().trim());
            user.setApellidoPaterno(txtFirstName.getText().trim());
            user.setApellidoMaterno(txtSecondName.getText().trim());
            user.setCorreo(txtEmail.getText().trim());
            user.setEstado(comboState.getSelectedIndex() - 1);
            user.setPassword(txtPassword.getText().trim());
            user.setPerfil(profileApplication.getProfileByName(comboProfile.getSelectedItem().toString()));
            userApplication.createUser(user);
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_NEW_USER_CREATED);
            clearFields();
        }
    }//GEN-LAST:event_saveTxtActionPerformed

    private void autoGeneratePassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoGeneratePassBtnActionPerformed
        // TODO add your handling code here:
        txtPassword.setText(Tools.generatePassword(10));
    }//GEN-LAST:event_autoGeneratePassBtnActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        UserView.userView.fillTableWithUsers();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton autoGeneratePassBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JComboBox comboProfile;
    private javax.swing.JComboBox comboState;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton saveTxt;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtSecondName;
    // End of variables declaration//GEN-END:variables
}
