/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.user;

import application.action.ActionApplication;
import application.profile.ProfileApplication;
import application.user.UserApplication;
import entity.Accion;
import entity.Perfil;
import entity.Usuario;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import util.EntityState;
import util.EntityType;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author dabarca
 */
public class UserView extends javax.swing.JInternalFrame {

    UserApplication userApplication = InstanceFactory.Instance.getInstance("userApplicaiton", UserApplication.class);
    ProfileApplication profileApplication = InstanceFactory.Instance.getInstance("profileApplication", ProfileApplication.class);
    ActionApplication actionApplication = InstanceFactory.Instance.getInstance("actionApplication", ActionApplication.class);
    public static UserView userView;
    String newProfileName = "";
    boolean treeFilled=false;
    /**
     * Creates new form UserForm
     */
    public UserView(int tab) {
        initComponents();
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

    public void clearTree() {
        DefaultTreeModel model = (DefaultTreeModel) treeActions.getModel();
        model.setRoot(null);
    }

    public void fillJTree() {

        ArrayList<Accion> pActions = actionApplication.getParents();

        

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Acciones");
        //Set<Accion> profileActions= p.getAccions();
        for (Accion a : pActions) {
            DefaultMutableTreeNode parent=new DefaultMutableTreeNode(a.getNombre());
            
            root.add(parent);
            ArrayList<Accion> children= actionApplication.getChildsByParent(a.getId());
            for(Accion h: children){
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(h.getNombre());
                parent.add(child);               
            }
        }
        DefaultTreeModel model = (DefaultTreeModel)new DefaultTreeModel(root);        
        treeActions.setModel(model);
        model.reload();
        
       
        

    }

    public void addListenerToProfileName() {
        txtProfileName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                if (!txtProfileName.getText().equals("")) {
                    saveProfileBtn.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                if (txtProfileName.getText().equals("")) {
                    saveProfileBtn.setEnabled(false);
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
            String state = user.getEstado() != null ? EntityState.getUsersState()[user.getEstado()] : "";
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
            String state = user.getEstado() != null ? EntityState.getUsersState()[user.getEstado()] : "";
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
        newBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        profileCombo1 = new javax.swing.JComboBox();
        searchBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        usersGrid = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        cancelEditBtn = new javax.swing.JButton();
        saveProfileBtn = new javax.swing.JButton();
        editProfileBtn = new javax.swing.JButton();
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

        newBtn.setText("Nuevo");
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        editBtn.setText("Editar");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        jButton7.setText("Restablecer Contraseña");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Búsqueda", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("Correo:");

        profileCombo1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        searchBtn.setText("Buscar");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        jLabel2.setText("Perfil:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(profileCombo1, 0, 111, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(searchBtn)
                .addGap(103, 103, 103))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn)
                    .addComponent(jLabel2)
                    .addComponent(profileCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
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
        jScrollPane1.setViewportView(usersGrid);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(newBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editBtn)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newBtn)
                            .addComponent(editBtn)
                            .addComponent(jButton7))))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(122, Short.MAX_VALUE))
        );

        tabbedUP.addTab("Usuarios", jPanel1);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones"));

        cancelEditBtn.setText("Cancelar");
        cancelEditBtn.setEnabled(false);
        cancelEditBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEditBtnActionPerformed(evt);
            }
        });

        saveProfileBtn.setText("Guardar");
        saveProfileBtn.setEnabled(false);
        saveProfileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveProfileBtnActionPerformed(evt);
            }
        });

        editProfileBtn.setText("Modificar");
        editProfileBtn.setEnabled(false);
        editProfileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProfileBtnActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(treeActions);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(editProfileBtn)
                        .addGap(18, 18, 18)
                        .addComponent(saveProfileBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelEditBtn)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveProfileBtn)
                    .addComponent(editProfileBtn)
                    .addComponent(cancelEditBtn))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddProfile))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(comboProfile2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(btnDeleteProfile)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddProfile)
                    .addComponent(jLabel6)
                    .addComponent(txtProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
                .addContainerGap(18, Short.MAX_VALUE))
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
                .addComponent(tabbedUP, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
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
        if(!treeFilled)
            fillJTree();
        else
            clearSelections();
        txtProfileName.setEnabled(true);
        txtProfileName.requestFocus();
        comboProfile2.setEnabled(false);
        btnDeleteProfile.setEnabled(false);
        cancelEditBtn.setEnabled(true);
        btnAddProfile.setEnabled(false);
        comboProfile2.setSelectedIndex(0);
        saveProfileBtn.setEnabled(false);
        treeActions.setEditable(true);        
    }//GEN-LAST:event_btnAddProfileActionPerformed

    private void comboProfile2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboProfile2ItemStateChanged
        // TODO add your handling code here:
        if(!treeFilled)
            fillJTree();
        clearSelections();
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (comboProfile2.getSelectedIndex() != 0) {
                String profileName = comboProfile2.getSelectedItem().toString();
                checkActions(profileName);
                editProfileBtn.setEnabled(true);
            } else {
                editProfileBtn.setEnabled(false);
            }
        }
    }//GEN-LAST:event_comboProfile2ItemStateChanged
    private void checkActions(String profileName){
        Perfil p= profileApplication.getProfileByName(profileName);
        Set<Accion> actions=p.getAccions();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeActions.getModel().getRoot();
        int numChild=root.getChildCount();
        
        for(Accion a:actions){
            for(int i=0;i<numChild;i++){
                DefaultMutableTreeNode child=(DefaultMutableTreeNode)root.getChildAt(i);
                int numChild2=child.getChildCount();
                for(int j=0;j<numChild2;j++){
                    DefaultMutableTreeNode child2=(DefaultMutableTreeNode)child.getChildAt(j);
                    if(a.getNombre().equals(child2.toString()))
                        treeActions.checkNode(new TreePath(child2.getPath()), true);
                }
            }
        }
    }
    private void clearSelections(){
        DefaultTreeModel model=(DefaultTreeModel)treeActions.getModel();
        DefaultMutableTreeNode root=(DefaultMutableTreeNode)model.getRoot();
        treeActions.checkNode(new TreePath(root.getPath()), false);
    }
    private void editProfileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProfileBtnActionPerformed
        // TODO add your handling code here:
        saveProfileBtn.setEnabled(true);
        cancelEditBtn.setEnabled(true);
        treeActions.setEditable(true);
        editProfileBtn.setEnabled(false);        
    }//GEN-LAST:event_editProfileBtnActionPerformed

    private void cancelEditBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelEditBtnActionPerformed
        // TODO add your handling code here:
        saveProfileBtn.setEnabled(false);
        cancelEditBtn.setEnabled(false);
        treeActions.setEditable(false);
        txtProfileName.setText("");
        txtProfileName.setEnabled(false);
        btnDeleteProfile.setEnabled(true);
        comboProfile2.setEnabled(true);
        if (btnAddProfile.isEnabled()) {
            clearSelections();
            checkActions(comboProfile2.getSelectedItem().toString());
            editProfileBtn.setEnabled(true);
        } else {
            clearSelections();
            comboProfile2.setSelectedIndex(0);
        }
        btnAddProfile.setEnabled(true);

    }//GEN-LAST:event_cancelEditBtnActionPerformed

    private void saveProfileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveProfileBtnActionPerformed
        // TODO add your handling code here:
        saveProfileBtn.setEnabled(false);
        cancelEditBtn.setEnabled(false);
        treeActions.setEditable(false);
        if (!txtProfileName.getText().equals("")) {
            Perfil profile = new Perfil();
            profile.setNombrePerfil(txtProfileName.getText());
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
        } else {
            Perfil profile = profileApplication.getProfileByName(comboProfile2.getSelectedItem().toString());
            profile.setAccions(getActionsFromTree());
            profileApplication.updateProfile(profile);
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_PROFILE_EDITED);
        }
        editProfileBtn.setEnabled(true);
    }//GEN-LAST:event_saveProfileBtnActionPerformed
    private Set getActionsFromTree(){
        Set actions=new HashSet(0);
        Set paths=treeActions.checkedPaths;
        for(TreePath tp:(Set<TreePath>)paths){
            Accion a=actionApplication.getActionByName(tp.getLastPathComponent().toString());
            if(a!=null)
                actions.add(a);
        }
        return actions;
    }
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, Strings.MESSAGE_RESTABLISH_PASSWORD);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
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


    }//GEN-LAST:event_editBtnActionPerformed

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        // TODO add your handling code here:
        NewUserView newUserView = new NewUserView((JFrame) SwingUtilities.getWindowAncestor(this), true);
        newUserView.setVisible(true);
    }//GEN-LAST:event_newBtnActionPerformed

    private void btnDeleteProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProfileActionPerformed
        // TODO add your handling code here:
        if (comboProfile2.getSelectedIndex() != 0) {
            Perfil p = profileApplication.getProfileByName(comboProfile2.getSelectedItem().toString());
            if (p != null) {
                profileApplication.deleteProfile(p);
                JOptionPane.showMessageDialog(this, Strings.MESSAGE_PROFILE_DELETED);
                clearSelections();
                fillCombos();
                comboProfile2.setSelectedIndex(0);
                editProfileBtn.setEnabled(false);
            }
        }
    }//GEN-LAST:event_btnDeleteProfileActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        // TODO add your handling code here:
        Usuario user = new Usuario();
        user.setCorreo(emailTxt.getText());
        if (profileCombo1.getSelectedIndex() != 0) {
            user.setPerfil(profileApplication.getProfileByName(profileCombo1.getSelectedItem().toString()));
        }
        fillTableWithUsers(userApplication.searchUser(user));
    }//GEN-LAST:event_searchBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProfile;
    private javax.swing.JButton btnDeleteProfile;
    private javax.swing.JButton cancelEditBtn;
    private javax.swing.JComboBox comboProfile2;
    private javax.swing.JButton editBtn;
    private javax.swing.JButton editProfileBtn;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton newBtn;
    private javax.swing.JComboBox profileCombo1;
    private javax.swing.JButton saveProfileBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTabbedPane tabbedUP;
    private client.user.JCheckBoxTree treeActions;
    private javax.swing.JTextField txtProfileName;
    private javax.swing.JTable usersGrid;
    // End of variables declaration//GEN-END:variables
}
