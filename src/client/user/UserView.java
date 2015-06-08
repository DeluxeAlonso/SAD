/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.user;

import application.action.ActionApplication;
import application.profile.ProfileApplication;
import application.user.UserApplication;
import client.base.BaseView;
import entity.Accion;
import entity.Perfil;
import entity.Usuario;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import util.EntityState;
import util.EntityType;
import util.Icons;
import util.InstanceFactory;
import util.Regex;
import util.Strings;

/**
 *
 * @author dabarca
 */
public class UserView extends BaseView {

    UserApplication userApplication = InstanceFactory.Instance.getInstance("userApplicaiton", UserApplication.class);
    ProfileApplication profileApplication = InstanceFactory.Instance.getInstance("profileApplication", ProfileApplication.class);
    ActionApplication actionApplication = InstanceFactory.Instance.getInstance("actionApplication", ActionApplication.class);
    public static UserView userView;
    String newProfileName = "";
    boolean treeFilled = false;
    Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);
    Border regularBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);

    /**
     * Creates new form UserForm
     */
    public UserView(int tab) {
        super();
        initComponents();
        super.initialize();
        Icons.setMainIcon(this);
        addImagesToButton();
        tabbedUP.setSelectedIndex(tab);
        //actionOriginList.setModel(new DefaultListModel());
        //actionDestList.setModel(new DefaultListModel());
        //Initialize profileComboBox
        userView = this;
        profileApplication.refreshProfiles();
        fillCombos();
        refreshGrid();
        addListenerToProfileName();
        clearTree();

    }
    public void addImagesToButton(){
        btnAddProfile.setText("");
        btnCancelEdit.setText("");
        btnDeleteProfile.setText("");
        btnEditUser.setText("");
        btnEditProfile.setText("");
        btnNewUser.setText("");
        btnReset.setText("");
        btnSaveProfile.setText("");
        btnSearchUser.setText("");
        Icons.setButton(btnAddProfile, Icons.ICONOS.CREATE.ordinal());
        Icons.setButton(btnCancelEdit, Icons.ICONOS.CANCEL.ordinal());   
        Icons.setButton(btnDeleteProfile, Icons.ICONOS.DELETE.ordinal());
        Icons.setButton(btnEditUser, Icons.ICONOS.MODIFY.ordinal());
        Icons.setButton(btnEditProfile, Icons.ICONOS.MODIFY.ordinal());
        Icons.setButton(btnNewUser, Icons.ICONOS.CREATE.ordinal());
        Icons.setButton(btnReset, Icons.ICONOS.RESET.ordinal());
        Icons.setButton(btnSaveProfile, Icons.ICONOS.SAVE.ordinal());
        Icons.setButton(btnSearchUser, Icons.ICONOS.SEARCH.ordinal());        
    }
    public void clearTree() {
        DefaultTreeModel model = (DefaultTreeModel) treeActions.getModel();
        model.setRoot(null);
    }

    public void fillJTree() {

        ArrayList<Accion> pActions = actionApplication.getParents();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Acciones");
        //Set<Accion> profileActions= p.getAccions();
        for (Accion a : pActions) {
            DefaultMutableTreeNode parent = new DefaultMutableTreeNode(a.getNombre());

            root.add(parent);
            ArrayList<Accion> children = actionApplication.getChildsByParent(a.getId());
            for (Accion h : children) {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(h.getNombre());
                parent.add(child);
            }
        }
        DefaultTreeModel model = (DefaultTreeModel) new DefaultTreeModel(root);
        treeActions.setModel(model);
        model.reload();

    }

    public void addListenerToProfileName() {
        txtProfileName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                if (!txtProfileName.getText().equals("")) {
                    btnSaveProfile.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                if (txtProfileName.getText().equals("")) {
                    btnSaveProfile.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
            }
        });
    }

    public void fillCombos() {

        profileApplication.refreshProfiles();
        profileCombo1.setModel(new javax.swing.DefaultComboBoxModel(EntityType.PROFILES_NAMES));
        comboProfile2.setModel(new javax.swing.DefaultComboBoxModel(EntityType.PROFILES_NAMES));
        comboState.setModel(new javax.swing.DefaultComboBoxModel(EntityState.getUsersState()));
    }

    public void clearUserGrid() {
        DefaultTableModel model = (DefaultTableModel) usersGrid.getModel();
        model.setRowCount(0);
    }

    public void fillTableWithUsers() {
        clearUserGrid();
        DefaultTableModel model = (DefaultTableModel) usersGrid.getModel();
        ArrayList<Usuario> users = userApplication.getAllUsers();
        for (Usuario u : users) {
            Usuario user = userApplication.getUserById(u.getId());
            String profileName = user.getPerfil() != null ? user.getPerfil().getNombrePerfil() : "";
            String state = user.getEstado() != null ? EntityState.getUsersState()[user.getEstado() + 1] : "";
            model.addRow(new Object[]{
                user.getId(),
                user.getNombre() + " " + user.getApellidoPaterno() + " " + user.getApellidoMaterno(),
                user.getCorreo(),
                state,
                profileName
            });
        }

    }

    public void fillTableWithUsers(ArrayList<Usuario> users) {
        clearUserGrid();
        DefaultTableModel model = (DefaultTableModel) usersGrid.getModel();
        for (Usuario u : users) {
            Usuario user = userApplication.getUserById(u.getId());
            String profileName = user.getPerfil() != null ? user.getPerfil().getNombrePerfil() : "";
            String state = user.getEstado() != null ? EntityState.getUsersState()[user.getEstado() + 1] : "";
            model.addRow(new Object[]{
                user.getId(),
                user.getNombre() + " " + user.getApellidoPaterno() + " " + user.getApellidoMaterno(),
                user.getCorreo(),
                state,
                profileName
            });
        }

    }

    public void refreshGrid() {
        clearUserGrid();
        fillTableWithUsers();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedUP = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        btnNewUser = new javax.swing.JButton();
        btnEditUser = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        profileCombo1 = new javax.swing.JComboBox();
        btnSearchUser = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboState = new javax.swing.JComboBox();
        txtName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        usersGrid = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnCancelEdit = new javax.swing.JButton();
        btnSaveProfile = new javax.swing.JButton();
        btnEditProfile = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        treeActions = new client.user.JCheckBoxTree();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        comboProfile2 = new javax.swing.JComboBox();
        btnDeleteProfile = new javax.swing.JButton();
        btnAddProfile = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtProfileName = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Usuarios");

        btnNewUser.setText("Nuevo");
        btnNewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewUserActionPerformed(evt);
            }
        });

        btnEditUser.setText("Editar");
        btnEditUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUserActionPerformed(evt);
            }
        });

        btnReset.setText("Restablecer Contraseña");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Búsqueda", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("Correo:");

        profileCombo1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSearchUser.setText("Buscar");
        btnSearchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchUserActionPerformed(evt);
            }
        });

        jLabel2.setText("Perfil:");

        jLabel4.setText("Estado:");

        comboState.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Nombre:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(428, Short.MAX_VALUE)
                        .addComponent(btnSearchUser))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboState, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(profileCombo1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(31, 31, 31))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(profileCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(comboState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearchUser)
                .addContainerGap())
        );

        usersGrid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Correo", "Estado", "Perfil"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        usersGrid.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(usersGrid);
        if (usersGrid.getColumnModel().getColumnCount() > 0) {
            usersGrid.getColumnModel().getColumn(0).setMinWidth(100);
            usersGrid.getColumnModel().getColumn(1).setMinWidth(150);
            usersGrid.getColumnModel().getColumn(2).setMinWidth(150);
            usersGrid.getColumnModel().getColumn(3).setMinWidth(50);
            usersGrid.getColumnModel().getColumn(4).setMinWidth(150);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnNewUser)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditUser)
                        .addGap(18, 18, 18)
                        .addComponent(btnReset))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNewUser)
                        .addComponent(btnEditUser)
                        .addComponent(btnReset)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedUP.addTab("Usuarios", jPanel1);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones"));

        btnCancelEdit.setText("Cancelar");
        btnCancelEdit.setEnabled(false);
        btnCancelEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelEditActionPerformed(evt);
            }
        });

        btnSaveProfile.setText("Guardar");
        btnSaveProfile.setEnabled(false);
        btnSaveProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveProfileActionPerformed(evt);
            }
        });

        btnEditProfile.setText("Modificar");
        btnEditProfile.setEnabled(false);
        btnEditProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProfileActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(treeActions);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btnEditProfile)
                        .addGap(18, 18, 18)
                        .addComponent(btnSaveProfile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelEdit))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveProfile)
                    .addComponent(btnEditProfile)
                    .addComponent(btnCancelEdit))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Perfil"));

        jLabel3.setText("Seleccionar Perfil:");

        comboProfile2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboProfile2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboProfile2ItemStateChanged(evt);
            }
        });

        btnDeleteProfile.setText("Eliminar");
        btnDeleteProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProfileActionPerformed(evt);
            }
        });

        btnAddProfile.setText("Añadir Perfil");
        btnAddProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProfileActionPerformed(evt);
            }
        });

        jLabel6.setText("Nombre Perfil:");

        txtProfileName.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboProfile2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteProfile)
                    .addComponent(btnAddProfile))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddProfile)
                    .addComponent(jLabel6)
                    .addComponent(txtProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboProfile2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteProfile))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedUP.addTab("Perfiles", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedUP, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedUP)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProfileActionPerformed
        // TODO add your handling code here:
        if (!treeFilled) {
            fillJTree();
        } else {
            clearSelections();
        }
        txtProfileName.setEnabled(true);
        txtProfileName.requestFocus();
        comboProfile2.setEnabled(false);
        btnDeleteProfile.setEnabled(false);
        btnCancelEdit.setEnabled(true);
        btnAddProfile.setEnabled(false);
        comboProfile2.setSelectedIndex(0);
        btnSaveProfile.setEnabled(false);
        treeActions.setEditable(true);
    }//GEN-LAST:event_btnAddProfileActionPerformed

    private void comboProfile2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboProfile2ItemStateChanged
        // TODO add your handling code here:
        if (!treeFilled) {
            fillJTree();
        }
        clearSelections();
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (comboProfile2.getSelectedIndex() != 0) {
                String profileName = comboProfile2.getSelectedItem().toString();
                checkActions(profileName);
                btnEditProfile.setEnabled(true);
            } else {
                btnEditProfile.setEnabled(false);
            }
        }
    }//GEN-LAST:event_comboProfile2ItemStateChanged
    private void checkActions(String profileName) {
        Perfil p = profileApplication.getProfileByName(profileName);
        Set<Accion> actions = p.getAccions();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeActions.getModel().getRoot();
        int numChild = root.getChildCount();

        for (Accion a : actions) {
            for (int i = 0; i < numChild; i++) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
                int numChild2 = child.getChildCount();
                for (int j = 0; j < numChild2; j++) {
                    DefaultMutableTreeNode child2 = (DefaultMutableTreeNode) child.getChildAt(j);
                    if (a.getNombre().equals(child2.toString())) {
                        treeActions.checkNode(new TreePath(child2.getPath()), true);
                    }
                }
            }
        }
    }

    private void clearSelections() {
        DefaultTreeModel model = (DefaultTreeModel) treeActions.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        treeActions.checkNode(new TreePath(root.getPath()), false);
    }
    private void btnEditProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProfileActionPerformed
        // TODO add your handling code here:
        btnSaveProfile.setEnabled(true);
        btnCancelEdit.setEnabled(true);
        treeActions.setEditable(true);
        btnEditProfile.setEnabled(false);
    }//GEN-LAST:event_btnEditProfileActionPerformed

    private void btnCancelEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelEditActionPerformed
        // TODO add your handling code here:
        btnSaveProfile.setEnabled(false);
        btnCancelEdit.setEnabled(false);
        treeActions.setEditable(false);
        txtProfileName.setText("");
        txtProfileName.setEnabled(false);
        btnDeleteProfile.setEnabled(true);
        comboProfile2.setEnabled(true);
        if (btnAddProfile.isEnabled()) {
            clearSelections();
            checkActions(comboProfile2.getSelectedItem().toString());
            btnEditProfile.setEnabled(true);
        } else {
            clearSelections();
            comboProfile2.setSelectedIndex(0);
        }
        txtProfileName.setBorder(regularBorder);
        btnAddProfile.setEnabled(true);

    }//GEN-LAST:event_btnCancelEditActionPerformed

    private void btnSaveProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveProfileActionPerformed
        // TODO add your handling code here:
        btnSaveProfile.setEnabled(false);
        btnCancelEdit.setEnabled(false);
        treeActions.setEditable(false);
        if (!txtProfileName.getText().equals("")) {
            if (isValidProfile()) {
                Perfil profile = new Perfil();
                profile.setNombrePerfil(txtProfileName.getText().trim());
                profile.setAccions(getActionsFromTree());
                profileApplication.insertProfile(profile);
                JOptionPane.showMessageDialog(this, Strings.MESSAGE_NEW_PROFILE_CREATED);
                fillCombos();
                comboProfile2.setSelectedIndex(comboProfile2.getItemCount() - 1);
                txtProfileName.setText("");
                txtProfileName.setEnabled(false);
                btnAddProfile.setEnabled(true);
                comboProfile2.setEnabled(true);
                btnDeleteProfile.setEnabled(true);
                txtProfileName.setBorder(regularBorder);
                btnEditProfile.setEnabled(true);
            } else {
                btnSaveProfile.setEnabled(true);
                btnCancelEdit.setEnabled(true);
                treeActions.setEditable(true);
            }
        } else {
            Perfil profile = profileApplication.getProfileByName(comboProfile2.getSelectedItem().toString());
            profile.setAccions(getActionsFromTree());
            profileApplication.updateProfile(profile);
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_PROFILE_EDITED);
            btnEditProfile.setEnabled(true);
        }

    }//GEN-LAST:event_btnSaveProfileActionPerformed
    private boolean isValidProfile() {
        String profileName = txtProfileName.getText().trim();
        String message = "";
        if (profileName.length() > 0 && profileName.length() <= 40) {
            if (!profileName.matches(Regex.NUMBER_AND_LETTERS)) {
                message += Strings.ERROR_PROFILE_NUMBERS_AND_LETTERS + "\n";
                txtProfileName.setBorder(errorBorder);
            } else {
                txtProfileName.setBorder(regularBorder);
            }
        } else {
            if (profileName.length() > 40) {
                message += Strings.ERROR_PROFILE_LENGTH + "\n";
            }
            txtProfileName.setBorder(errorBorder);
        }
        if (!message.equals("")) {
            JOptionPane.showMessageDialog(this, message, "Mensaje", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private Set getActionsFromTree() {
        Set actions = new HashSet(0);
        Set paths = treeActions.checkedPaths;
        for (TreePath tp : (Set<TreePath>) paths) {
            Accion a = actionApplication.getActionByName(tp.getLastPathComponent().toString());
            if (a != null) {
                actions.add(a);
            }
        }
        return actions;
    }
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, Strings.MESSAGE_RESTABLISH_PASSWORD);
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUserActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) usersGrid.getModel();
        int selectedRow = -1;
        selectedRow = usersGrid.getSelectedRow();
        Usuario user = null;
        if (selectedRow != -1) {
            String userId = model.getValueAt(selectedRow, 0).toString();
            user = userApplication.getUserById(userId);
            if (user != null) {
                EditUserAdmin editUserAdmin = new EditUserAdmin((JFrame) SwingUtilities.getWindowAncestor(this), true, user);
                editUserAdmin.setVisible(true);
            }
        }


    }//GEN-LAST:event_btnEditUserActionPerformed

    private void btnNewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewUserActionPerformed
        // TODO add your handling code here:
        NewUserView newUserView = new NewUserView((JFrame) SwingUtilities.getWindowAncestor(this), true);
        newUserView.setVisible(true);
    }//GEN-LAST:event_btnNewUserActionPerformed

    private void btnDeleteProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProfileActionPerformed
        // TODO add your handling code here:
        if (comboProfile2.getSelectedIndex() != 0) {
            Perfil p = profileApplication.getProfileByName(comboProfile2.getSelectedItem().toString());
            if (p != null && p.getUsuarios().size() == 0) {
                profileApplication.deleteProfile(p);
                JOptionPane.showMessageDialog(this, Strings.MESSAGE_PROFILE_DELETED);
                clearSelections();
                fillCombos();
                comboProfile2.setSelectedIndex(0);
                btnEditProfile.setEnabled(false);
            } else {

                JOptionPane.showMessageDialog(this, Strings.ERROR_PROFILE_HAS_USERS, "Mensaje", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeleteProfileActionPerformed

    private void btnSearchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchUserActionPerformed
        // TODO add your handling code here:
        Usuario user = new Usuario();
        user.setCorreo(emailTxt.getText());
        user.setNombre(txtName.getText());
        if (profileCombo1.getSelectedIndex() != 0) {
            user.setPerfil(profileApplication.getProfileByName(profileCombo1.getSelectedItem().toString()));
        }
        if (comboState.getSelectedIndex() != 0) {
            user.setEstado(comboState.getSelectedIndex() - 1);
        }
        fillTableWithUsers(userApplication.searchUser(user));
    }//GEN-LAST:event_btnSearchUserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProfile;
    private javax.swing.JButton btnCancelEdit;
    private javax.swing.JButton btnDeleteProfile;
    private javax.swing.JButton btnEditProfile;
    private javax.swing.JButton btnEditUser;
    private javax.swing.JButton btnNewUser;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSaveProfile;
    private javax.swing.JButton btnSearchUser;
    private javax.swing.JComboBox comboProfile2;
    private javax.swing.JComboBox comboState;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox profileCombo1;
    private javax.swing.JTabbedPane tabbedUP;
    private client.user.JCheckBoxTree treeActions;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtProfileName;
    private javax.swing.JTable usersGrid;
    // End of variables declaration//GEN-END:variables
}
