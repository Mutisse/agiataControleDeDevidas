/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package ficha01.view;

import ficha01.model.Acesso;
import ficha01.model.Dao.AcessoDao;
import ficha01.model.Dao.UsuarioDao;
import ficha01.model.Status;
import ficha01.model.Usuario;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mutisse
 */
public final class TelaUsuario extends javax.swing.JInternalFrame {

    private Usuario usuarioLogado;

    public Usuario getUsuarioLogado() {
        return usuarioLogado;

    }

    AcessoDao acessoDAo = new AcessoDao();
    List<Acesso> niveldeAcrsso = acessoDAo.ListarAcessos();
    String idUsuarioLogado = null;
    /**
     * Creates new form NewJInternalFrame
     */
    private DefaultTableModel model;
    private final Object[][] linhas = null;
    // Criando objeto TelaRegistrarUser com a lista
    UsuarioDao uDao = new UsuarioDao();

    public TelaUsuario(Usuario usuario) {
        //System.out.println("TelaUsuario " + usuario);

        initComponents();
        preencherTabela();
        preencherCb();

        lbl_id.setText(uDao.gerarCodigo());
        this.usuarioLogado = usuario;
    }

    // Método para preencher o JComboBox
    public void preencherCb() {
        if (niveldeAcrsso != null && cb_perfil != null) {
            cb_perfil.removeAllItems();
            for (Acesso acesso : niveldeAcrsso) {
                if (!acesso.getTipo_Acesso().equalsIgnoreCase("Administrador")) {
                    cb_perfil.addItem(acesso.getTipo_Acesso());
                }
            }
        } else {
            System.out.println("A lista niveldeAcesso ou cb_perfil é nula.");
        }
    }

// Método para preencher a tabela
    public void preencherTabela() {
       // System.out.println(" get Usuario " + getUsuarioLogado());
        try {
            UsuarioDao user = new UsuarioDao();
            List<Usuario> lista = user.ListarUsuarios();
            DefaultTableModel newModelTable = (DefaultTableModel) jTableRegistro.getModel();
            newModelTable.setRowCount(0);

            // Obtém o ID do usuário logado
            for (Usuario u : lista) {
                // Verifica se o usuário está autorizado e não é o usuário logado
                String genero;
                if ("Masculino".equals(u.getGenero())) {
                    genero = "Masculino";
                } else if ("Feminino".equals(u.getGenero())) {
                    genero = "Feminino";
                } else {
                    genero = "Indefinido";
                }
                if (u.getThisTstus().equals(Status.AUTORIZADO) && u.getAcesso().getId_Acesso() != 1) {
                   
                    if (!u.getId().equals(getUsuarioLogado().getId())) {
                       newModelTable.addRow(new Object[]{
                        u.getId(),
                        u.getNomeProprio() + " " + u.getApelido(),
                        genero,
                        u.getEmail(),
                        u.getAcesso(),
                        u.getThisTstus().getStatusFormatado()});  
                    }
                   
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e.getMessage() + " Erro ao preencher a tabela", "Notificação", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setCampos() {
        try {
            ////String[] colunas = new String[]{"ID", "Nome", "Gênero", "Email", "Perfil", "Senha", "Estado"};
            int setar = jTableRegistro.getSelectedRow();
            lbl_id.setText(jTableRegistro.getModel().getValueAt(setar, 0).toString());
            // Obtém o nome completo (nome + apelido)
            String nomeCompleto = jTableRegistro.getModel().getValueAt(setar, 1).toString();
            // Divide o nome completo em nome e apelido
            String[] partesNome = nomeCompleto.split(" ");
            if (partesNome.length >= 2) {
                txtNome.setText(partesNome[0]); // Define o nome
                txtApelido.setText(partesNome[1]); // Define o apelido
            } else {
                // Caso não haja espaço para dividir, defina o nome completo
                txtNome.setText(nomeCompleto);
                txtApelido.setText(""); // Deixe o campo de apelido vazio
            }
            // Verifica o gênero e seleciona o RadioButton apropriado
            if (jTableRegistro.getModel().getValueAt(setar, 2).equals("Masculino")) {
                rb_Masculino.setSelected(true);
                jButtonFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/face-head-man-icon-9.png")));

            } else if (jTableRegistro.getModel().getValueAt(setar, 2).equals("Feminino")) {
                rb_Femenino.setSelected(true);
                jButtonFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/female-user-icon-19.png")));
            }
            // Preenche outros campos conforme necessário
            txtemail.setText(jTableRegistro.getModel().getValueAt(setar, 3).toString());
            // Supondo que cb_perfil seja um JComboBox<Double>
            String valor = String.valueOf(jTableRegistro.getModel().getValueAt(setar, 4).toString());
            cb_perfil.setSelectedItem(valor);
            // pfSenha.setText(controller.decryptPassword(jTableRegistro.getModel().getValueAt(setar, 5).toString()));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e + " Erro!! Campo Selecionado está vazio", "Notificação", JOptionPane.ERROR);
        }
    }

//    // Classe Usuario
//    public void buscarUsuario(String nomePesquisa, JTable tabela) {
//        UsuarioDao usuarioDAO = new UsuarioDao();
//        usuarioDAO.FindUser(nomePesquisa, tabela);
//    }
    public void buscarUsuarioSeNaoVazio(JTextField txtPesquisar, JTable tabela) {
        String nomePesquisa = txtPesquisar.getText();
        if (!nomePesquisa.isEmpty()) {
            UsuarioDao usuarioDAO = new UsuarioDao();
            usuarioDAO.FindUser(nomePesquisa, tabela);
        } else {
            preencherTabela();
        }
    }

    void Atualizar() {

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        rb_Masculino = new javax.swing.JRadioButton();
        rb_Femenino = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        cb_perfil = new javax.swing.JComboBox<>();
        btnLimpar1 = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btnRegistar1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        lbl_id = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        txtApelido = new javax.swing.JTextField();
        jButtonFoto = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtPesquisar2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRegistro = new javax.swing.JTable();

        getContentPane().setLayout(null);

        jTabbedPane.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel2.setLayout(null);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Apelido");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 80, 90, 22);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registrar Usuário ");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel1);
        jLabel1.setBounds(160, 10, 260, 40);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Nome");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(250, 80, 90, 22);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("email");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(250, 150, 140, 30);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Genero:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 150, 150, 30);

        buttonGroup1.add(rb_Masculino);
        rb_Masculino.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rb_Masculino.setText("Masculino");
        rb_Masculino.setEnabled(false);
        rb_Masculino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_MasculinoActionPerformed(evt);
            }
        });
        jPanel2.add(rb_Masculino);
        rb_Masculino.setBounds(40, 210, 100, 21);

        buttonGroup1.add(rb_Femenino);
        rb_Femenino.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rb_Femenino.setText("Femenino");
        rb_Femenino.setEnabled(false);
        jPanel2.add(rb_Femenino);
        rb_Femenino.setBounds(40, 250, 100, 21);

        jLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jLabel9);
        jLabel9.setBounds(20, 180, 190, 110);

        cb_perfil.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cb_perfil.setToolTipText("");
        jPanel2.add(cb_perfil);
        cb_perfil.setBounds(250, 260, 190, 30);

        btnLimpar1.setBackground(new java.awt.Color(204, 255, 255));
        btnLimpar1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnLimpar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/clean.png"))); // NOI18N
        btnLimpar1.setText("Limpar");
        jPanel2.add(btnLimpar1);
        btnLimpar1.setBounds(20, 350, 170, 29);

        btneditar.setBackground(new java.awt.Color(204, 255, 255));
        btneditar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/atualiz.png"))); // NOI18N
        btneditar.setText("Editar");
        jPanel2.add(btneditar);
        btneditar.setBounds(250, 350, 140, 29);

        btnRegistar1.setBackground(new java.awt.Color(204, 255, 255));
        btnRegistar1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnRegistar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/add.png"))); // NOI18N
        btnRegistar1.setText("Registar");
        btnRegistar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnRegistar1);
        btnRegistar1.setBounds(430, 350, 140, 29);

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Atribuir  nivel de Acesso:");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(250, 230, 170, 20);

        lbl_id.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_id.setForeground(new java.awt.Color(255, 51, 51));
        lbl_id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_id.setToolTipText("");
        lbl_id.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(lbl_id);
        lbl_id.setBounds(10, 10, 110, 25);

        txtNome.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
        });
        jPanel2.add(txtNome);
        txtNome.setBounds(20, 110, 190, 30);

        txtemail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtemailActionPerformed(evt);
            }
        });
        jPanel2.add(txtemail);
        txtemail.setBounds(250, 190, 210, 30);

        txtApelido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel2.add(txtApelido);
        txtApelido.setBounds(250, 110, 210, 30);

        jButtonFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/male-icon-11.png"))); // NOI18N
        jPanel2.add(jButtonFoto);
        jButtonFoto.setBounds(480, 90, 120, 160);

        jTabbedPane.addTab("Registrar", jPanel2);

        jPanel4.setLayout(null);

        txtPesquisar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisar2ActionPerformed(evt);
            }
        });
        txtPesquisar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisar2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPesquisar2KeyTyped(evt);
            }
        });
        txtPesquisar2.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txtPesquisar2VetoableChange(evt);
            }
        });
        jPanel4.add(txtPesquisar2);
        txtPesquisar2.setBounds(20, 120, 190, 30);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/Pesq.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);
        jButton1.setBounds(220, 120, 40, 30);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/multest.png"))); // NOI18N
        jPanel4.add(jLabel68);
        jLabel68.setBounds(470, 20, 120, 140);

        jTableRegistro = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIdex){
                return false;
            }
        };

        String[] colunas = new String[]{"ID", "Nome", "Gênero", "Email", "Perfil", "Satus"};
        model = new DefaultTableModel(linhas, colunas);
        jTableRegistro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTableRegistro.setModel(model);
        jTableRegistro.setToolTipText("Clica na Imagem de pesquisa para Atualizar a tabela ");
        jTableRegistro.getColumnModel().getColumn(0).setPreferredWidth(45);
        jTableRegistro.getColumnModel().getColumn(1).setPreferredWidth(115);
        jTableRegistro.getColumnModel().getColumn(2).setPreferredWidth(70);
        jTableRegistro.getColumnModel().getColumn(3).setPreferredWidth(83);
        jTableRegistro.getColumnModel().getColumn(4).setPreferredWidth(60);
        jTableRegistro.getColumnModel().getColumn(5).setPreferredWidth(60);
        jTableRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableRegistroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableRegistro);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(20, 190, 580, 170);

        jTabbedPane.addTab("Lista", jPanel4);

        getContentPane().add(jTabbedPane);
        jTabbedPane.setBounds(10, 0, 610, 420);
        jTabbedPane.getAccessibleContext().setAccessibleName("Registro");

        setBounds(0, 0, 639, 459);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPesquisar2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisar2KeyTyped
        //        if (txtEstado.getText().equals("Não Pago")) {
        //            btnRegistar.setEnabled(true);
        //        } else if (txtEstado.getText().equals("Pago")) {
        //            btnRegistar.setEnabled(false);
        //        }
    }//GEN-LAST:event_txtPesquisar2KeyTyped

    private void txtPesquisar2VetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtPesquisar2VetoableChange

    }//GEN-LAST:event_txtPesquisar2VetoableChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        preencherTabela();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTableRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRegistroMouseClicked
//        jTmauseClick();
        setCampos();
        jTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_jTableRegistroMouseClicked

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed

    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtemailActionPerformed

    private void btnRegistar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistar1ActionPerformed
        jTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_btnRegistar1ActionPerformed

    private void txtPesquisar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisar2KeyReleased
        buscarUsuarioSeNaoVazio(txtPesquisar2, jTableRegistro);
    }//GEN-LAST:event_txtPesquisar2KeyReleased

    private void txtPesquisar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisar2ActionPerformed

    private void rb_MasculinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_MasculinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_MasculinoActionPerformed
    public void LimparCampos() {
        txtNome.setText(null);
        txtApelido.setText(null);
        buttonGroup1.clearSelection();
        lbl_id.setText(null);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpar1;
    private javax.swing.JButton btnRegistar1;
    private javax.swing.JButton btneditar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cb_perfil;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonFoto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableRegistro;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JRadioButton rb_Femenino;
    private javax.swing.JRadioButton rb_Masculino;
    private javax.swing.JTextField txtApelido;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisar2;
    private javax.swing.JTextField txtemail;
    // End of variables declaration//GEN-END:variables
}
