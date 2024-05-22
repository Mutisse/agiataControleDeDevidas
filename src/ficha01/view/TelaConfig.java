/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package ficha01.view;

import ficha01.controller.ControllerUsuario;
import ficha01.model.Acesso;
import ficha01.model.Dao.AcessoDao;
import ficha01.model.Dao.UsuarioDao;
import ficha01.model.Status;
import ficha01.model.Usuario;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mutisse
 */
public final class TelaConfig extends javax.swing.JInternalFrame {

    AcessoDao acessoDAo = new AcessoDao();
    List<Acesso> niveldeAcesso = acessoDAo.ListarAcessos();
    ControllerUsuario controller = new ControllerUsuario();

    /**
     * Creates new form NewJInternalFrame
     */
    private DefaultTableModel model;
    private final Object[][] linhas = null;
    DefaultListModel newModel = new DefaultListModel();

    /**
     * Creates new form NewJInternalFrame
     */
    public TelaConfig() {

        initComponents();
        txt_update.setEnabled(false);
        preencherTabela();
        preencherCb();
        jList.setModel(newModel);
    }
    // Método para preencher o JComboBox

    public void preencherCb() {
        if (niveldeAcesso != null && cb_perfil != null) {
            cb_perfil.removeAllItems();
            for (Acesso acesso : niveldeAcesso) {
                cb_perfil.addItem(acesso.getTipo_Acesso());
            }
        } else {
            System.out.println("A lista niveldeAcesso ou Combox perfil é nula.");
        }
    }

    void preencherLista() {
        if (niveldeAcesso != null && newModel != null) {
            if (newModel.isEmpty()) {
                for (Acesso acesso : niveldeAcesso) {
                    newModel.addElement(acesso.getTipo_Acesso());
                }
            } else {
                JOptionPane.showMessageDialog(null, "A lista já está preenchida.", "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            System.out.println("A lista niveldeAcesso ou newModel é nula.");
        }
    }

    void preencherTabela() {
        try {
            UsuarioDao User = new UsuarioDao();
            List<Usuario> lista = User.ListarUsuarios();
            DefaultTableModel newModelTable = (DefaultTableModel) jTableRegistro.getModel();
            newModelTable.setRowCount(0);

            for (Usuario u : lista) {
                String genero;

                if ("Masculino".equals(u.getGenero())) {
                    genero = "Masculino";
                } else if ("Feminino".equals(u.getGenero())) {
                    genero = "Feminino";
                } else {
                    genero = "Indefinido";
                }
//                 Verifica se o estado da dívida é "pendente" antes de adicionar à tabela
                //String[] colunas = new String[]{"ID", "Nome", "Gênero", "Email", "Perfil", "Senha", "Estado"};
                if (u.getAcesso().getId_Acesso() != 1) {
                    newModelTable.addRow(new Object[]{
                        u.getId(),
                        u.getNomeProprio() + " " + u.getApelido(),
                        genero, u.getAcesso(),
                        u.getThisTstus().getStatusFormatado()});
                }
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage() + " Erro ao preencher a tabela", "Notificação", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void verificarStatus() {
        // Obtém o número de colunas na tabela
        int numColunas = jTableRegistro.getColumnCount();

        // Variável para armazenar o índice da coluna de status
        int indiceStatus = -1;

        // Itera pelas colunas para encontrar a coluna 'status'
        for (int i = 0; i < numColunas; i++) {
            // Obtém o nome da coluna na posição atual
            String nomeColuna = jTableRegistro.getColumnName(i);

            // Verifica se o nome da coluna é 'status'
            if (nomeColuna.equalsIgnoreCase("Status")) {
                // Se encontrarmos a coluna 'status', armazenamos seu índice e saímos do loop
                indiceStatus = i;
                break;
            }
        }

        // Verifica se o índice da coluna de status foi encontrado
        if (indiceStatus != -1) {
            // Obtém o índice da linha selecionada
            int rowIndex = jTableRegistro.getSelectedRow();

            // Verifica se a linha está selecionada
            if (rowIndex != -1) {
                // Obtém o status da linha selecionada
                Object status = jTableRegistro.getValueAt(rowIndex, indiceStatus);
                if (status != null) {

                    // Define a visibilidade e o texto dos botões
                    if (status.equals("Nao_autorizado")) {
                        btn_permicoes.setText("Desbloquer");
                        btn_permicoes.setBackground(new java.awt.Color(51, 255, 51));
                        btn_permicoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/people (1).png")));
                        ///

                    } else if (status.equals("Autorizado")) {
                        btn_permicoes.setText("Bloquear");
                        btn_permicoes.setBackground(new java.awt.Color(255, 102, 102));
                        btn_permicoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/people.png")));

                    }

                    if (status.equals("Excluido")) {

                        btn_eliminar.setText("Restaurar");
                        btn_eliminar.setBackground(new java.awt.Color(242, 242, 242));
                        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/people (4).png")));

                    } else if (!status.equals("Excluido")) {
                        btn_eliminar.setText("Eliminar");
                        btn_eliminar.setBackground(new java.awt.Color(204, 255, 255));
                        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/remov.png")));

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada.", "Notificação", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "A coluna 'status' não foi encontrada.", "Notificação", JOptionPane.ERROR_MESSAGE);
            }
        }
        preencherTabela();
    }

    public void setCampos() {
        try {
            ////String[] colunas = new String[]{"ID", "Nome", "Gênero", "Email", "Perfil", "Senha", "Estado"};
            int setar = jTableRegistro.getSelectedRow();
            lbl_Id.setText(jTableRegistro.getModel().getValueAt(setar, 0).toString());
            // Obtém o nome completo (nome + apelido)
            String nomeCompleto = jTableRegistro.getModel().getValueAt(setar, 1).toString();
            // Divide o nome completo em nome e apelido
            String[] partesNome = nomeCompleto.split(" ");
            if (partesNome.length >= 2) {
                jLabelNome.setText(partesNome[0]); // Define o nome
                jLabelApelido.setText(partesNome[1]); // Define o apelido
            } else {
                // Caso não haja espaço para dividir, defina o nome completo
                jLabelNome.setText(nomeCompleto);
                jLabelApelido.setText(""); // Deixe o campo de apelido vazio
            }

            if (jTableRegistro.getModel().getValueAt(setar, 2).toString().equalsIgnoreCase("Masculino")) {
                jLabelSexo.setText("Masculino");
                jButtonFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/face-head-man-icon-9.png"))); // NOI18N
            } else if (jTableRegistro.getModel().getValueAt(setar, 2).toString().equalsIgnoreCase("Feminino")) {
                jLabelSexo.setText("Feminino");
                jButtonFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/female-user-icon-19.png"))); // NOI18N
            } else {
                jButtonFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/male-icon-11.png"))); // NOI18N
                jLabelSexo.setText("Não especificado");
            }

            // Preenche outros campos conforme necessário
            // Supondo que cb_perfil seja um JComboBox<Double>
            String valor = String.valueOf(jTableRegistro.getModel().getValueAt(setar, 3).toString());
            cb_perfil.setSelectedItem(valor);
            // pfSenha.setText(controller.decryptPassword(jTableRegistro.getModel().getValueAt(setar, 5).toString()));
            // Verifica o gênero e seleciona o RadioButton apropriado
            if (jTableRegistro.getModel().getValueAt(setar, 4).equals("Autorizado")) {
                jCheckBoxAutorizado.setSelected(true);
                //  perfilFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/male.png")));
            } else if (jTableRegistro.getModel().getValueAt(setar, 4).equals("Não Autorizado")) {
                jCheckBoxNAutorizado.setSelected(true);
                //perfilFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/female.png")));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e + " Erro!! Campo Selecionado está vazio", "Notificação", JOptionPane.ERROR);
        }
        verificarStatus();
    }

    void editarEAtualizar() {
        try {
            if (btn_atualizar.getText().equalsIgnoreCase("Editar")) {
                btn_atualizar.setText("Atualizar");
                txt_addElemento.setText(String.valueOf(jList.getSelectedValue()));
                jList.setEnabled(false);
                btn_remover.setEnabled(false);
                btn_cancelar.setEnabled(false);

            } else {
                btn_atualizar.setText("Editar");
                int index = jList.getSelectedIndex();
                newModel.setElementAt(txt_addElemento.getText(), index);
                jList.setEnabled(true);
                btn_add.setEnabled(true);
                btn_atualizar.setEnabled(false);
                btn_remover.setEnabled(false);
                txt_addElemento.setText(null);

                JOptionPane.showMessageDialog(null, "Elemento Atualizado", "", JOptionPane.INFORMATION_MESSAGE);
                limparAlista();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void add() {
        if (txt_addElemento.getText().equalsIgnoreCase(null)) {
            JOptionPane.showMessageDialog(null, "Sem Elemento para adicionar!", "", JOptionPane.ERROR_MESSAGE);
        } else {
            newModel.addElement(txt_addElemento.getText().trim());
            txt_addElemento.setText(txt_addElemento.getText());
            JOptionPane.showMessageDialog(null, "Elemento adicionar", "", JOptionPane.INFORMATION_MESSAGE);
            txt_addElemento.setText(null);
            limparAlista();
        }
    }

    void remove() {
        int index = jList.getSelectedIndex();
        int confirmar = JOptionPane.showConfirmDialog(null, "Deseja remover esse elemento da lista? ", "", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            newModel.removeElementAt(index);
            JOptionPane.showMessageDialog(null, "Elemento removido", "", JOptionPane.INFORMATION_MESSAGE);
            txt_addElemento.setText(null);

        } else {
            btn_add.setEnabled(true);
            btn_atualizar.setEnabled(false);
            btn_remover.setEnabled(false);
            limparAlista();
        }
    }

    void cancelar() {
        int confirmar = JOptionPane.showConfirmDialog(null, "Deseja remover Todos elementos da lista? ", "", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            newModel.removeAllElements();
            JOptionPane.showMessageDialog(null, "Elementos removidos", "", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    void limparAlista() {
        if (jList.getModel().getSize() > 1) {
            btn_add.setEnabled(true);
            jList.removeAll();
            jList.setEnabled(true);
        } else {
            btn_cancelar.setEnabled(false);
        }

    }

    void JbActionEliminar() {
        String iduser = lbl_Id.getText();
        controller.isDeleted(iduser, jLabelNome.getText(), jLabelApelido.getText());
        limpaCampos();
        preencherTabela();

    }

    void JbActionPromovrt() {
        String iduser = lbl_Id.getText();
        controller.ispromote(iduser, jLabelNome.getText(), jLabelApelido.getText(), (String) cb_perfil.getSelectedItem());
        limpaCampos();
        preencherTabela();
    }

    void JbActionDemiti() {
        String iduser = lbl_Id.getText();
        controller.isDismiss(iduser, jLabelNome.getText(), jLabelApelido.getText());
        limpaCampos();
        preencherTabela();

    }

    void JbActionReadmitir() {
        String iduser = lbl_Id.getText();
        controller.isAdmit(iduser, jLabelNome.getText(), jLabelApelido.getText());
        limpaCampos();
        preencherTabela();
    }

    void limpaCampos() {
        jLabelApelido.setText(null);
        jLabelNome.setText(null);
        jLabelSexo.setText(null);
        buttonGroup1.clearSelection();

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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        btn_eliminar = new javax.swing.JButton();
        btn_promover = new javax.swing.JButton();
        btn_permicoes = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabelApelido = new javax.swing.JLabel();
        jLabelSexo = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jCheckBoxNAutorizado = new javax.swing.JCheckBox();
        jCheckBoxAutorizado = new javax.swing.JCheckBox();
        jButtonFoto = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        cb_perfil = new javax.swing.JComboBox();
        lbl_Id = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableRegistro = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txt_addElemento = new javax.swing.JTextField();
        btn_add = new javax.swing.JButton();
        btn_atualizar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        btn_remover = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jC_Advogados = new javax.swing.JCheckBox();
        txt_update = new javax.swing.JTextField();
        btneditar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        getContentPane().setLayout(null);

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel2.setLayout(null);

        btn_eliminar.setBackground(new java.awt.Color(204, 255, 255));
        btn_eliminar.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/remov.png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.setToolTipText("");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btn_eliminar);
        btn_eliminar.setBounds(120, 230, 140, 32);

        btn_promover.setBackground(new java.awt.Color(255, 255, 102));
        btn_promover.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btn_promover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/devedor.png"))); // NOI18N
        btn_promover.setText("Promover");
        btn_promover.setToolTipText("seleciona o funcinario ba tabela");
        btn_promover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_promoverActionPerformed(evt);
            }
        });
        jPanel2.add(btn_promover);
        btn_promover.setBounds(290, 230, 120, 32);

        btn_permicoes.setBackground(new java.awt.Color(51, 255, 51));
        btn_permicoes.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btn_permicoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/people (1).png"))); // NOI18N
        btn_permicoes.setText("Desbloquer");
        btn_permicoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_permicoesActionPerformed(evt);
            }
        });
        jPanel2.add(btn_permicoes);
        btn_permicoes.setBounds(440, 230, 160, 32);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Nome");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(200, 30, 90, 15);

        jLabelApelido.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelApelido.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jLabelApelido);
        jLabelApelido.setBounds(40, 60, 120, 30);

        jLabelSexo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelSexo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jLabelSexo);
        jLabelSexo.setBounds(40, 120, 120, 30);

        jLabelNome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelNome.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jLabelNome);
        jLabelNome.setBounds(200, 60, 120, 30);

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Apelido");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(40, 30, 80, 15);

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Genero");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(40, 100, 80, 15);

        buttonGroup1.add(jCheckBoxNAutorizado);
        jCheckBoxNAutorizado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBoxNAutorizado.setText("Não Autorizado");
        jCheckBoxNAutorizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxNAutorizadoActionPerformed(evt);
            }
        });
        jPanel2.add(jCheckBoxNAutorizado);
        jCheckBoxNAutorizado.setBounds(200, 130, 120, 20);

        buttonGroup1.add(jCheckBoxAutorizado);
        jCheckBoxAutorizado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBoxAutorizado.setText("Autorizado");
        jPanel2.add(jCheckBoxAutorizado);
        jCheckBoxAutorizado.setBounds(200, 100, 110, 20);

        jButtonFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/male-icon-11.png"))); // NOI18N
        jPanel2.add(jButtonFoto);
        jButtonFoto.setBounds(350, 60, 110, 130);

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Atribuir novo nivel de Acesso:");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(40, 160, 190, 20);

        cb_perfil.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cb_perfil.setToolTipText("");
        cb_perfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cb_perfilMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cb_perfilMouseEntered(evt);
            }
        });
        cb_perfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_perfilActionPerformed(evt);
            }
        });
        cb_perfil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cb_perfilKeyPressed(evt);
            }
        });
        jPanel2.add(cb_perfil);
        cb_perfil.setBounds(40, 180, 170, 30);

        lbl_Id.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_Id.setForeground(new java.awt.Color(255, 51, 51));
        lbl_Id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Id.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.lightGray));
        jPanel2.add(lbl_Id);
        lbl_Id.setBounds(350, 30, 110, 25);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jLabel3);
        jLabel3.setBounds(20, 20, 460, 200);

        jTableRegistro = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIdex){
                return false;
            }
        };

        String[] colunas = new String[]{"ID", "Nome Completo", "Gênero", "Perfil", "Status"};
        model = new DefaultTableModel(linhas, colunas);
        jTableRegistro.setModel(model);
        jTableRegistro.getColumnModel().getColumn(0).setPreferredWidth(45);
        jTableRegistro.getColumnModel().getColumn(1).setPreferredWidth(115);
        jTableRegistro.getColumnModel().getColumn(2).setPreferredWidth(70);
        jTableRegistro.getColumnModel().getColumn(3).setPreferredWidth(83);
        jTableRegistro.getColumnModel().getColumn(4).setPreferredWidth(60);
        jTableRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableRegistroMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableRegistro);

        jPanel2.add(jScrollPane3);
        jScrollPane3.setBounds(20, 270, 590, 110);

        jTabbedPane1.addTab("Definições ", jPanel2);

        jPanel1.setLayout(null);

        txt_addElemento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_addElemento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_addElementoFocusGained(evt);
            }
        });
        jPanel1.add(txt_addElemento);
        txt_addElemento.setBounds(20, 130, 230, 34);

        btn_add.setBackground(new java.awt.Color(0, 255, 0));
        btn_add.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btn_add.setText("Adcionar");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        jPanel1.add(btn_add);
        btn_add.setBounds(20, 180, 100, 32);

        btn_atualizar.setBackground(new java.awt.Color(204, 204, 0));
        btn_atualizar.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btn_atualizar.setText("Editar");
        btn_atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_atualizar);
        btn_atualizar.setBounds(200, 180, 100, 32);

        btn_cancelar.setBackground(new java.awt.Color(102, 102, 102));
        btn_cancelar.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_cancelar);
        btn_cancelar.setBounds(200, 230, 100, 32);

        btn_remover.setBackground(new java.awt.Color(255, 102, 102));
        btn_remover.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btn_remover.setText("Remover");
        btn_remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removerActionPerformed(evt);
            }
        });
        jPanel1.add(btn_remover);
        btn_remover.setBounds(20, 230, 100, 32);

        jList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(380, 130, 190, 140);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Configurar Acesso ");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(160, 10, 290, 60);

        jC_Advogados.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jC_Advogados.setText("Redefinir a Taxa ");
        jC_Advogados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jC_AdvogadosActionPerformed(evt);
            }
        });
        jPanel1.add(jC_Advogados);
        jC_Advogados.setBounds(30, 300, 140, 19);
        jPanel1.add(txt_update);
        txt_update.setBounds(40, 330, 120, 30);

        btneditar.setBackground(new java.awt.Color(204, 255, 255));
        btneditar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btneditar.setText("Atualizar");
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });
        jPanel1.add(btneditar);
        btneditar.setBounds(450, 340, 110, 29);

        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 280, 560, 100);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/loan (1).png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(260, 130, 40, 30);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Lista de acesso ao sistema ");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(390, 100, 160, 20);

        jTabbedPane1.addTab("Configuração ", jPanel1);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 0, 620, 420);

        setBounds(0, 0, 639, 459);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_addElementoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_addElementoFocusGained
        //   Atualizar();
    }//GEN-LAST:event_txt_addElementoFocusGained

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        //  add();
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizarActionPerformed
        //   editarEAtualizar();
    }//GEN-LAST:event_btn_atualizarActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed

        txt_addElemento.setText(null);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_removerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removerActionPerformed
        //  remove();
    }//GEN-LAST:event_btn_removerActionPerformed

    private void jListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListMouseClicked

        txt_addElemento.setText((String) jList.getSelectedValue());
    }//GEN-LAST:event_jListMouseClicked

    private void cb_perfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cb_perfilMouseClicked

    }//GEN-LAST:event_cb_perfilMouseClicked

    private void cb_perfilMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cb_perfilMouseEntered

    }//GEN-LAST:event_cb_perfilMouseEntered

    private void cb_perfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_perfilActionPerformed

    }//GEN-LAST:event_cb_perfilActionPerformed

    private void cb_perfilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cb_perfilKeyPressed

    }//GEN-LAST:event_cb_perfilKeyPressed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed

        if (btn_eliminar.getText().equalsIgnoreCase("Eliminar")) {
            JbActionEliminar();
        } else if (btn_eliminar.getText().equalsIgnoreCase("Restaurar")) {
            JbActionDemiti();
        }
        preencherTabela();
        limpaCampos();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_promoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_promoverActionPerformed
        JbActionPromovrt();
        preencherTabela();
    }//GEN-LAST:event_btn_promoverActionPerformed

    private void btn_permicoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_permicoesActionPerformed

        if (btn_permicoes.getText().equalsIgnoreCase("Desbloquer")) {
            JbActionReadmitir();
        } else if (btn_permicoes.getText().equalsIgnoreCase("Bloquear")) {
            JbActionDemiti();
        }

        preencherTabela();
        limpaCampos();
    }//GEN-LAST:event_btn_permicoesActionPerformed

    private void jC_AdvogadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jC_AdvogadosActionPerformed
        if (jC_Advogados.isSelected()) {
            txt_update.setEnabled(true);
        } else {
            txt_update.setEnabled(false);
        }
    }//GEN-LAST:event_jC_AdvogadosActionPerformed

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed


    }//GEN-LAST:event_btneditarActionPerformed

    private void jCheckBoxNAutorizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxNAutorizadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxNAutorizadoActionPerformed

    private void jTableRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRegistroMouseClicked
        //        jTmauseClick();
        setCampos();

    }//GEN-LAST:event_jTableRegistroMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        preencherLista();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_atualizar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_permicoes;
    private javax.swing.JButton btn_promover;
    private javax.swing.JButton btn_remover;
    private javax.swing.JButton btneditar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cb_perfil;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonFoto;
    private javax.swing.JCheckBox jC_Advogados;
    private javax.swing.JCheckBox jCheckBoxAutorizado;
    private javax.swing.JCheckBox jCheckBoxNAutorizado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelApelido;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelSexo;
    private javax.swing.JList jList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableRegistro;
    private javax.swing.JLabel lbl_Id;
    private javax.swing.JTextField txt_addElemento;
    private javax.swing.JTextField txt_update;
    // End of variables declaration//GEN-END:variables
}
