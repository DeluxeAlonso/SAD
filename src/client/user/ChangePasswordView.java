/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.user;

import application.user.UserApplication;
import client.base.BaseDialogView;
import entity.Usuario;
import javax.swing.JOptionPane;
import util.Icons;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author Nevermade
 */
public class ChangePasswordView extends BaseDialogView {

    /**
     * Creates new form ChangePasswordForm
     */
    UserApplication userApplication = InstanceFactory.Instance.getInstance("userApplication", UserApplication.class);
    Usuario user;

    public ChangePasswordView(java.awt.Frame parent, boolean modal, Usuario user) {
        super(parent, modal);
        initComponents();
        super.initialize();
        Icons.setMainIcon(this);
        Icons.setButton(btnSavePass, Icons.ICONOS.SAVE.ordinal());
        this.user = user;
    }

    public boolean isValidForm() {
        String currentPass = new String(txtCurrentPass.getPassword());
        String newPassword = new String(txtNewPass.getPassword());
        String confirmPassword = new String(txtNewConfirmPass.getPassword());
        String message = "";

        if (!userApplication.decrypt(user.getPassword()).equals(currentPass)) {
            message += "La contraseña ingresada es incorrecta." + "\n";
            txtCurrentPass.setBorder(errorBorder);
        } else {
            txtCurrentPass.setBorder(null);
        }

        if (newPassword.length() < 6) {
            message += Strings.ERROR_PASSWORD_MIN_LENGTH + "\n";
            txtNewPass.setBorder(errorBorder);
        } else {
            if (!newPassword.equals(confirmPassword)) {
                message += "Las contraseñas no coinciden";
                txtNewPass.setBorder(errorBorder);
                txtNewConfirmPass.setBorder(errorBorder);
            } else {
                txtNewPass.setBorder(null);
                txtNewConfirmPass.setBorder(null);
            }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnSavePass = new javax.swing.JButton();
        txtCurrentPass = new javax.swing.JPasswordField();
        txtNewPass = new javax.swing.JPasswordField();
        txtNewConfirmPass = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Actualizar contraseña");
        setResizable(false);

        jLabel1.setText("Contraseña actual:");

        jLabel2.setText("Nueva contraseña:");

        jLabel3.setText("Confirmar nueva contraseña:");

        btnSavePass.setText("Cambiar Contraseña");
        btnSavePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavePassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCurrentPass)
                            .addComponent(txtNewPass)
                            .addComponent(txtNewConfirmPass, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(btnSavePass)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCurrentPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNewConfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(btnSavePass)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSavePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePassActionPerformed
        // TODO add your handling code here:
        if (isValidForm()){
            String pass= new String(txtNewPass.getPassword());
            user.setPassword(userApplication.encrypt(pass));
            userApplication.updateUser(user);
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_RECOVER_PASSWORD);
            this.dispose();
        }
        
    }//GEN-LAST:event_btnSavePassActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSavePass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField txtCurrentPass;
    private javax.swing.JPasswordField txtNewConfirmPass;
    private javax.swing.JPasswordField txtNewPass;
    // End of variables declaration//GEN-END:variables
}
