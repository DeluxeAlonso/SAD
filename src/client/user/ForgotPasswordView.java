/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.user;

import application.user.UserApplication;
import entity.Usuario;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import util.EntityType;
import util.Icons;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author Nevermade
 */
public class ForgotPasswordView extends javax.swing.JFrame {

    UserApplication userApplication = InstanceFactory.Instance.getInstance("userApplication", UserApplication.class);
    Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);

    /**
     * Creates new form ForgotPasswordForm
     */
    public ForgotPasswordView() {
        initComponents();
        Icons.setMainIcon(this);
        Icons.setButton(btnChPass, Icons.ICONOS.RESET.ordinal());
        comboQuestions.setModel(new DefaultComboBoxModel(EntityType.USER_QUESTIONS));
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblQuestion = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAnswer = new javax.swing.JTextField();
        comboQuestions = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnChPass = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Olvidé mi contraseña");
        setResizable(false);

        lblQuestion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblQuestion.setText("Cuál es su pregunta secreta?");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Respuesta:");

        comboQuestions.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Correo:");

        btnChPass.setText("Restaurar Contraseña");
        btnChPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChPassActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 255));
        jLabel4.setText("<< Volver al inicio de sesión");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAnswer)
                            .addComponent(comboQuestions, 0, 252, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)))
                .addContainerGap(38, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnChPass)
                .addGap(89, 89, 89))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(lblQuestion)
                .addGap(18, 18, 18)
                .addComponent(comboQuestions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnChPass)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChPassActionPerformed
        // TODO add your handling code here:
        Usuario user = userApplication.getUserByEmail(txtEmail.getText().trim());
        if (user != null) {
            String userQuestion = user.getPreguntaSecreta().getPregunta();
            String userAnsw = user.getRespuesta().toLowerCase();
            txtEmail.setBorder(null);
            if (comboQuestions.getSelectedItem().toString().equals(userQuestion) && txtAnswer.getText().trim().toLowerCase().equals(userAnsw)) {
                if (!userApplication.recoverPasswordAndSendEmail(user).equals("")) {
                    JOptionPane.showMessageDialog(this, Strings.MESSAGE_RECOVER_PASSWORD);
                } else {
                    JOptionPane.showMessageDialog(this, "No se puedo enviar el correo. Asegúrese que el puerto 587 esté habilitado.");
                }
                jLabel4MouseClicked(null);
            } else {
                comboQuestions.setBorder(errorBorder);
                txtAnswer.setBorder(errorBorder);
                JOptionPane.showMessageDialog(this,"La pregunta o la respuesta son incorrectos." , "Mensaje", JOptionPane.WARNING_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(this, "El correo no está registrado en nuestro sistema.", "Mensaje", JOptionPane.WARNING_MESSAGE);
            txtEmail.setBorder(errorBorder);
        }

    }//GEN-LAST:event_btnChPassActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        new LoginView().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChPass;
    private javax.swing.JComboBox comboQuestions;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblQuestion;
    private javax.swing.JTextField txtAnswer;
    private javax.swing.JTextField txtEmail;
    // End of variables declaration//GEN-END:variables
}
