/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package ficha01.view;

import ficha01.controller.controllerDivida;
import ficha01.model.Dao.DividasDao;
import ficha01.model.Divida;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mutisse
 */
public final class TelaRegistar extends javax.swing.JInternalFrame {

    /**
     * Creates new form NewJInternalFrame
     */
    private DefaultTableModel model;
    private final Object[][] linhas;
    DividasDao dividas = new DividasDao();
    controllerDivida controlador = new controllerDivida();
    Divida dividasModel = new Divida();
    //Status s = new Status();

    public TelaRegistar() {
        linhas = new Object[][]{};
        initComponents();
        preencherTabela();
        jBEditar.setEnabled(false);
        jBEliminar.setEnabled(false);
        lbl_id.setText(dividas.gerarCodigo());

    }

    public void setCampos() {
        try {
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
            } else if (jTableRegistro.getModel().getValueAt(setar, 2).equals("Feminino")) {
                rb_Femenino.setSelected(true);
            }

            // Preenche outros campos conforme necessário
            txf_contacto.setText(jTableRegistro.getModel().getValueAt(setar, 3).toString());
            spnValorDivida.setValue(Double.valueOf(jTableRegistro.getModel().getValueAt(setar, 4).toString()));
            lblValorApagar.setText(jTableRegistro.getModel().getValueAt(setar, 5).toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e + " Erro!! Campo Selecionado está vazio", "Notificação", JOptionPane.ERROR);
        }
    }

    void preencherTabela() {
        try {

            DividasDao divida = new DividasDao();
            List<Divida> lista = divida.listarClientesComDividas();
            DefaultTableModel newModelTable = (DefaultTableModel) jTableRegistro.getModel();
            newModelTable.setRowCount(0);
            for (Divida d : lista) {
                String genero;

                if ("Masculino".equals(d.getCliente().getGenero())) {
                    genero = "Masculino";
                } else if ("Feminino".equals(d.getCliente().getGenero())) {
                    genero = "Feminino";
                } else {
                    genero = "Indefinido";
                }

                // Verifica se o estado da dívida é "pendente" antes de adicionar à tabela
                if (!d.getEstadoDivida().getStatus().equalsIgnoreCase("Liquidada")) {
                    newModelTable.addRow(new Object[]{
                        d.getId_divida(),
                        d.getCliente().getNomeProprio() + " " + d.getCliente().getApelido(),
                        genero,
                        d.getCliente().getContacto(),
                        d.getValorDivida(),
                        d.getValorAPagar(),
                        d.getEstadoDivida().getStatusFormatado() // Aqui está a correção
                    });
                }

            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e.getMessage() + " Erro ao preencher a tabela", "Notificação", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void LimpaCampos() {
        txtNome.setText(null);
        txtApelido.setText(null);
        lblValorApagar.setText(null);
        lbl_id.setText(null);
        txf_contacto.setText(null);
        buttonGroup.clearSelection();
        spnValorDivida.setValue(0);

    }

    void clickEdit() {
        if (jBEditar.getText().equals("Editar")) {
            jBEditar.setText("Atualizar");
            btn_regitrar.setEnabled(false);
            jBEliminar.setEnabled(false);
            txtNome.setEnabled(true);
            txtApelido.setEnabled(true);
            txf_contacto.setEnabled(true);
            spnValorDivida.setEnabled(true);
            rb_Femenino.setEnabled(true);
            rb_Masculino.setEnabled(true);
        } else {
            jBEditar.setText("Editar");
            btn_regitrar.setEnabled(true);
            jBEditar.setEnabled(false);
            jBEliminar.setEnabled(false);

            String generoSelecionado;
            if (rb_Masculino.isSelected()) {
                generoSelecionado = "M"; // Supondo que 'M' seja o código para o gênero masculino
            } else if (rb_Femenino.isSelected()) {
                generoSelecionado = "F"; // Supondo que 'F' seja o código para o gênero feminino
            } else {
                generoSelecionado = "Indefinido"; // Defina um valor padrão caso nenhum seja selecionado
            }

            try {

                BigDecimal valorDivida = new BigDecimal(Double.parseDouble(spnValorDivida.getValue().toString()));
                BigDecimal valorApagar = new BigDecimal(lblValorApagar.getText());
                controlador.Update(lbl_id.getText(), txtNome.getText(), txtApelido.getText(), txf_contacto.getText(), generoSelecionado, valorDivida, valorApagar);
                LimpaCampos();
                preencherTabela();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + "Erro ao converter o texto para número: ", "Notificação", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    void jTmauseClick() {
        jBEditar.setEnabled(true);
        jBEliminar.setEnabled(true);
        btn_regitrar.setEnabled(false);
        txtNome.setEnabled(false);
        txtApelido.setEnabled(false);
        txf_contacto.setEnabled(false);
        spnValorDivida.setEnabled(false);
        rb_Femenino.setEnabled(false);
        rb_Masculino.setEnabled(false);
        jTabbedPane.setSelectedIndex(0);
    }

    void JbActionEliminar() {
        String idDivida = lbl_id.getText();
        controlador.isDeleted(idDivida, txtNome.getText(), txtApelido.getText());
        LimpaCampos();
        preencherTabela();
        jBEditar.setEnabled(false);
        jBEliminar.setEnabled(false);
        btn_regitrar.setEnabled(true);
        //
        txtNome.setEnabled(true);
        txtApelido.setEnabled(true);
        txf_contacto.setEnabled(true);
        spnValorDivida.setEnabled(true);
        rb_Femenino.setEnabled(true);
        rb_Masculino.setEnabled(true);
    }

    public void buscarDividaSeNaoVazio(JTextField txtPesquisar, JTable tabela) {
        String nomePesquisa = txtPesquisar.getText();
        if (!nomePesquisa.isEmpty()) {
            DividasDao dividaDao = new DividasDao();
            dividaDao.findData(nomePesquisa, tabela);
        } else {
            preencherTabela();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btn_regitrar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtApelido = new javax.swing.JTextField();
        spnValorDivida = new javax.swing.JSpinner();
        lblValorApagar = new javax.swing.JLabel();
        lbl_id = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txf_contacto = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        rb_Masculino = new javax.swing.JRadioButton();
        rb_Femenino = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jBEliminar = new javax.swing.JButton();
        jBEditar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRegistro = new javax.swing.JTable();
        txtPesquisar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();

        jTabbedPane.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.setLayout(null);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/FundoRegistarDividas.png"))); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(490, 10, 120, 100);

        btn_regitrar.setBackground(new java.awt.Color(204, 255, 255));
        btn_regitrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_regitrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/salary2.png"))); // NOI18N
        btn_regitrar.setText("Registar ");
        btn_regitrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regitrarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_regitrar);
        btn_regitrar.setBounds(430, 350, 160, 30);

        jButton2.setBackground(new java.awt.Color(204, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/clean.png"))); // NOI18N
        jButton2.setText("Limpar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(10, 350, 120, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Nome:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 130, 80, 22);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Contacto:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(420, 130, 90, 22);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Valor a Pagar:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(310, 240, 130, 22);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Valor da  Divida:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(150, 240, 140, 22);

        txtNome.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
        });
        jPanel1.add(txtNome);
        txtNome.setBounds(10, 160, 190, 30);

        txtApelido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel1.add(txtApelido);
        txtApelido.setBounds(230, 160, 160, 30);

        spnValorDivida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        spnValorDivida.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 5000.0d, 50.0d));
        spnValorDivida.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnValorDividaStateChanged(evt);
            }
        });
        spnValorDivida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                none(evt);
            }
        });
        spnValorDivida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                spnValorDividaKeyReleased(evt);
            }
        });
        jPanel1.add(spnValorDivida);
        spnValorDivida.setBounds(150, 270, 140, 30);

        lblValorApagar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblValorApagar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValorApagar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(lblValorApagar);
        lblValorApagar.setBounds(320, 270, 120, 30);

        lbl_id.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_id.setForeground(new java.awt.Color(255, 51, 51));
        lbl_id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_id.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(lbl_id);
        lbl_id.setBounds(10, 90, 80, 25);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText(" Registro de Dívida");
        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel11);
        jLabel11.setBounds(110, 20, 360, 50);

        jLabel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel12);
        jLabel12.setBounds(10, 240, 110, 70);

        try {
            txf_contacto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(+258)  ## ### ####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txf_contacto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txf_contacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txf_contactoActionPerformed(evt);
            }
        });
        jPanel1.add(txf_contacto);
        txf_contacto.setBounds(420, 160, 170, 30);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Apelido:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(230, 130, 70, 22);

        buttonGroup.add(rb_Masculino);
        rb_Masculino.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rb_Masculino.setText("Masculino");
        jPanel1.add(rb_Masculino);
        rb_Masculino.setBounds(20, 250, 100, 20);

        buttonGroup.add(rb_Femenino);
        rb_Femenino.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rb_Femenino.setText("Femenino");
        jPanel1.add(rb_Femenino);
        rb_Femenino.setBounds(20, 280, 100, 21);

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Genero:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 210, 70, 20);

        jBEliminar.setBackground(new java.awt.Color(204, 255, 255));
        jBEliminar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/remov.png"))); // NOI18N
        jBEliminar.setText("Eliminar");
        jBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(jBEliminar);
        jBEliminar.setBounds(150, 350, 120, 30);

        jBEditar.setBackground(new java.awt.Color(204, 255, 255));
        jBEditar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/loan (1).png"))); // NOI18N
        jBEditar.setText("Editar");
        jBEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEditarActionPerformed(evt);
            }
        });
        jPanel1.add(jBEditar);
        jBEditar.setBounds(290, 350, 120, 30);

        jTabbedPane.addTab(" Registar Dívidas", jPanel1);

        jPanel2.setLayout(null);

        jTableRegistro = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIdex){
                return false;
            }
        };
        String[] colunas = new String[]{"ID", "Nome_Completo","Genero","Telefone", "Credit", "Quitar", "Status_Divida"};
        model = new DefaultTableModel(linhas, colunas);
        jTableRegistro.setModel(model);
        jTableRegistro.setToolTipText("Clica na Imagem de pesquisa para Atualizar a tabela ");
        jTableRegistro.getColumnModel().getColumn(0).setPreferredWidth(45);
        jTableRegistro.getColumnModel().getColumn(1).setPreferredWidth(115);
        jTableRegistro.getColumnModel().getColumn(2).setPreferredWidth(70);
        jTableRegistro.getColumnModel().getColumn(3).setPreferredWidth(83);
        jTableRegistro.getColumnModel().getColumn(4).setPreferredWidth(60);
        jTableRegistro.getColumnModel().getColumn(5).setPreferredWidth(60);
        jTableRegistro.getColumnModel().getColumn(6).setPreferredWidth(60);
        jTableRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableRegistroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableRegistro);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(20, 130, 580, 230);

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyTyped(evt);
            }
        });
        txtPesquisar.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txtPesquisarVetoableChange(evt);
            }
        });
        jPanel2.add(txtPesquisar);
        txtPesquisar.setBounds(20, 70, 190, 30);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/Pesq.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(220, 70, 40, 30);

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/FundoRegistarDividas.png"))); // NOI18N
        jPanel2.add(jLabel68);
        jLabel68.setBounds(490, 20, 120, 100);

        jTabbedPane.addTab("Dados Dos Devedores", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_regitrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regitrarActionPerformed

    }//GEN-LAST:event_btn_regitrarActionPerformed


    private void spnValorDividaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spnValorDividaKeyReleased
//        dividasModel.calcularValor(Float.parseFloat(spnValorDivida.getValue().toString()));
//lblValorApagar.setText(String.valueOf(dividasModel.calcularValor(Float.parseFloat(spnValorDivida.getValue().toString()))));
   
    }//GEN-LAST:event_spnValorDividaKeyReleased

    private void none(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_none
      
    }//GEN-LAST:event_none

    private void spnValorDividaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnValorDividaStateChanged
      
        lblValorApagar.setText(String.valueOf(dividasModel.calcularValor(Float.parseFloat(spnValorDivida.getValue().toString()))));
   
    }//GEN-LAST:event_spnValorDividaStateChanged

    private void jTableRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRegistroMouseClicked
        jTmauseClick();
        setCampos();

    }//GEN-LAST:event_jTableRegistroMouseClicked

    private void jBEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEditarActionPerformed
        clickEdit();

    }//GEN-LAST:event_jBEditarActionPerformed

    private void jBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEliminarActionPerformed
        JbActionEliminar();


    }//GEN-LAST:event_jBEliminarActionPerformed

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed

    }//GEN-LAST:event_txtNomeKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LimpaCampos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txf_contactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txf_contactoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txf_contactoActionPerformed

    private void txtPesquisarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyTyped

    }//GEN-LAST:event_txtPesquisarKeyTyped

    private void txtPesquisarVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtPesquisarVetoableChange

    }//GEN-LAST:event_txtPesquisarVetoableChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        preencherTabela();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        buscarDividaSeNaoVazio(txtPesquisar, jTableRegistro);
    }//GEN-LAST:event_txtPesquisarKeyReleased
// cridas 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_regitrar;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JButton jBEditar;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableRegistro;
    private javax.swing.JLabel lblValorApagar;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JRadioButton rb_Femenino;
    private javax.swing.JRadioButton rb_Masculino;
    private javax.swing.JSpinner spnValorDivida;
    private javax.swing.JFormattedTextField txf_contacto;
    private javax.swing.JTextField txtApelido;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables

}
