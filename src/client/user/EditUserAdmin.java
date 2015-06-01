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
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import util.EntityState;
import util.EntityType;
import util.Icons;
import util.InstanceFactory;
import util.Regex;
import util.Strings;

/**
 *
 * @author Nevermade
 */
public class EditUserAdmin extends javax.swing.JDialog {

    /**
     * Creates new form EditUserAdmin
     */
    Usuario user = null;
    ProfileApplication profileApplication = InstanceFactory.Instance.getInstance("profileApplication", ProfileApplication.class);
    UserApplication userApplication = InstanceFactory.Instance.getInstance("userApplication", UserApplication.class);
    Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);
    Border regularBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);

    public EditUserAdmin(java.awt.Frame parent, boolean modal, Usuario user) {
        super(parent, modal);
        initComponents();
        addImagesToButton();
        this.user = user;
        fillCombos();
        fillUserFields();
    }
    public void addImagesToButton(){
        btnSave.setText("");
        btnCancel.setText("");
        Icons.setButton(btnSave, Icons.ICONOS.SAVE.ordinal());
        Icons.setButton(btnCancel, Icons.ICONOS.CANCEL.ordinal());        
    }
    public void fillCombos() {
        comboState.setModel(new javax.swing.DefaultComboBoxModel(EntityState.getUsersState()));
        comboProfile.setModel(new javax.swing.DefaultComboBoxModel(EntityType.PROFILES_NAMES));
    }

    public void fillUserFields() {
        txtUserId.setText(user.getId());
        txtName.setText(user.getNombre());
        txtFirstName.setText(user.getApellidoPaterno());
        txtSecondName.setText(user.getApellidoMaterno());
        txtEmail.setText(user.getCorreo());
        comboState.setSelectedIndex(user.getEstado() + 1);
        if (user.getPerfil() != null) {
            comboProfile.setSelectedItem(user.getPerfil().getNombrePerfil());
        }
    }

    public boolean isValidForm() {
        String message = "";
        String name = txtName.getText().trim();
        String firstName = txtFirstName.getText().trim();
        String secondName = txtSecondName.getText().trim();
        String email = txtEmail.getText().trim();
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
                    if (!email.equals(this.user.getCorreo())) {
                        message += Strings.ERROR_USER_SAME_EMAIL + "\n";
                        txtEmail.setBorder(errorBorder);
                    }
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

        label2 = new java.awt.Label();
        txtEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtSecondName = new javax.swing.JTextField();
        comboProfile = new javax.swing.JComboBox();
        txtFirstName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        comboState = new javax.swing.JComboBox();
        txtName = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        label1 = new java.awt.Label();
        jLabel6 = new javax.swing.JLabel();
        txtUserId = new javax.swing.JTextField();

        label2.setText("label1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Usuario");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel11.setText("*Correo:");

        jLabel12.setText("*Apellido Materno:");

        comboProfile.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("*Perfil:");

        jLabel13.setText("*Apellido Paterno:");

        jLabel9.setText("*Estado:");

        jLabel14.setText("*Nombres:");

        comboState.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        label1.setText("label1");

        jLabel6.setText("Código:");

        txtUserId.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addComponent(btnSave)
                        .addGap(29, 29, 29)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel13)
                        .addGap(6, 6, 6)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel6))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(txtUserId))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtUserId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if (isValidForm()) {
            user.setNombre(txtName.getText().trim());
            user.setApellidoPaterno(txtFirstName.getText().trim());
            user.setApellidoMaterno(txtSecondName.getText().trim());
            user.setCorreo(txtEmail.getText().trim());
            user.setEstado(comboState.getSelectedIndex() - 1);
            user.setPerfil(profileApplication.getProfileByName(comboProfile.getSelectedItem().toString()));
            userApplication.updateUser(user);
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_USER_UPDATED);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
        UserView.userView.refreshGrid();

    }//GEN-LAST:event_btnCancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        UserView.userView.refreshGrid();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox comboProfile;
    private javax.swing.JComboBox comboState;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSecondName;
    private javax.swing.JTextField txtUserId;
    // End of variables declaration//GEN-END:variables
}
