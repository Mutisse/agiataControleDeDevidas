/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ficha01.view;

import ficha01.controller.Controller;
import ficha01.controller.ControllerUsuario;
import ficha01.model.Dao.Conectar;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Mutisse
 */
public final class TelaLogin extends javax.swing.JFrame {
    // variaves de conexao

    Connection conexao = null;
    Controller validcao = new Controller();
    ControllerUsuario controllerUser = new ControllerUsuario();
//

    /**
     * Creates new form Login
     */
    public TelaLogin() {
        initComponents();
        URL url = this.getClass().getResource("/ficha01/view/icons/Senha.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);
        pfSenha.setEchoChar('•');
        txt_Username.requestFocus();
        btn_ocultar.setVisible(false);
        lbl_recuperarSenha.setVisible(false);
        conexao = Conectar.conector();
        if (conexao != null) {

            lbl_Conexao.setToolTipText(" Conectado Com a Banco de Dados");
            lbl_Conexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/conectado.png")));

        } else {
            lbl_auteticaca.setVisible(false);
            lbl_Conexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/deconectado.png")));
            lbl_Conexao.setToolTipText(" Erro na conecção Com o Banco de Dados, o servidor esta desligado ");

        }
        horaData();
    }

    void ValidarUsername() {
        if (txt_Username.getText().isBlank()) {
            btn_entrar.setEnabled(false);
            txt_Username.requestFocus();
            JOptionPane.showMessageDialog(null, "Introduza o seu e-mail", "", JOptionPane.INFORMATION_MESSAGE);
        } else if (validcao.validarEmail(txt_Username.getText()) == true) {
            pfSenha.requestFocus();
        } else {
            txt_Username.requestFocus();
            btn_entrar.setEnabled(false);
            txt_Username.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red));
            lbl_iconUser.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red));
            JOptionPane.showMessageDialog(null, "  e-mail Introduza e invalido", "", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        }
    }

    void ValidarSenha() {
        if (pfSenha.getText().isBlank()) {
            pfSenha.requestFocus();
            pfSenha.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red));
            lbl_iconSenha.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red));
            JOptionPane.showMessageDialog(null, "Introduza a sua senha!", "", JOptionPane.INFORMATION_MESSAGE);
            btn_entrar.setEnabled(false);
            limparCampos();
        } else {
            btn_entrar.setEnabled(true);
            controllerUser.abrirTela(txt_Username.getText(), pfSenha.getText());
            new TelaLogin().setVisible(false);
            this.dispose();
        }
    }

    void horaData() {
        Date data = new Date();
        sdf = new SimpleDateFormat("dd MMMM yyyy");
        Calendar now = Calendar.getInstance();
        class hora implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar now = Calendar.getInstance();
                lbl_horaData.setText(String.format("Data: " + sdf.format(data) + ",  Horas:  " + "%1$tH : %1$tM : %1$tS", now));

            }

        }

        Timer timer = new Timer(1000, new hora());
        timer.start();
        Calendar c1 = Calendar.getInstance();
        int hora = c1.get(Calendar.HOUR_OF_DAY);

        if (hora <= 12 && hora <= 12) {
            lbl_boasVindas.setText("Bom Dia");
        } else if (hora > 12 && hora < 18) {
            lbl_boasVindas.setText("Boa Tarde");
        } else {
            lbl_boasVindas.setText("Boa Noite");
        }

    }

    void limparCampos() {
        pfSenha.setText(null);
        txt_Username.setText(null);
        txt_Username.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(204, 255, 255)));
        lbl_iconUser.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(204, 255, 255)));
        lbl_iconSenha.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(204, 255, 255)));
        pfSenha.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(204, 255, 255)));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbl_imagem = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jlbl_Icone = new javax.swing.JLabel();
        pfSenha = new javax.swing.JPasswordField();
        txt_Username = new javax.swing.JTextField();
        lbl_iconSenha = new javax.swing.JLabel();
        lbl_iconUser = new javax.swing.JLabel();
        btn_entrar = new javax.swing.JButton();
        lbl_auteticaca = new javax.swing.JLabel();
        lbl_Conexao = new javax.swing.JLabel();
        lbl_recuperarSenha = new javax.swing.JLabel();
        btn_ocultar = new javax.swing.JButton();
        btn_desecultar = new javax.swing.JButton();
        lbl_boasVindas = new javax.swing.JLabel();
        lbl_horaData = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbl_border = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(" Login");
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(214, 217, 223));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jPanel1.setLayout(null);

        lbl_imagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_imagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/Senha.png"))); // NOI18N
        jPanel1.add(lbl_imagem);
        lbl_imagem.setBounds(20, 111, 190, 210);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setLayout(null);

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/Cancel.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(502, 5, 32, 22);

        jlbl_Icone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/Senha3.png"))); // NOI18N
        jPanel2.add(jlbl_Icone);
        jlbl_Icone.setBounds(6, 2, 27, 22);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 540, 30);

        pfSenha.setBackground(new java.awt.Color(214, 217, 223));
        pfSenha.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        pfSenha.setText("senha789");
        pfSenha.setToolTipText("A sua senha ");
        pfSenha.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 255, 255)));
        pfSenha.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pfSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfSenhaActionPerformed(evt);
            }
        });
        pfSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pfSenhaKeyPressed(evt);
            }
        });
        jPanel1.add(pfSenha);
        pfSenha.setBounds(300, 261, 180, 30);

        txt_Username.setBackground(new java.awt.Color(214, 217, 223));
        txt_Username.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_Username.setText("carlos@email.com");
        txt_Username.setToolTipText("O email  deve conter @ dominio.com");
        txt_Username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 255, 255)));
        txt_Username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_UsernameActionPerformed(evt);
            }
        });
        txt_Username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_UsernameKeyPressed(evt);
            }
        });
        jPanel1.add(txt_Username);
        txt_Username.setBounds(300, 211, 180, 30);

        lbl_iconSenha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_iconSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/senhaLogin.png"))); // NOI18N
        lbl_iconSenha.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 255, 255)));
        jPanel1.add(lbl_iconSenha);
        lbl_iconSenha.setBounds(270, 261, 30, 30);

        lbl_iconUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_iconUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/userLogin.png"))); // NOI18N
        lbl_iconUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 255, 255)));
        jPanel1.add(lbl_iconUser);
        lbl_iconUser.setBounds(270, 211, 30, 30);

        btn_entrar.setBackground(new java.awt.Color(204, 255, 255));
        btn_entrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_entrar.setForeground(new java.awt.Color(51, 51, 51));
        btn_entrar.setText("iniciar");
        btn_entrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_entrarMouseEntered(evt);
            }
        });
        btn_entrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_entrarActionPerformed(evt);
            }
        });
        btn_entrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_entrarKeyPressed(evt);
            }
        });
        jPanel1.add(btn_entrar);
        btn_entrar.setBounds(330, 330, 120, 27);

        lbl_auteticaca.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_auteticaca.setForeground(new java.awt.Color(153, 153, 153));
        lbl_auteticaca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_auteticaca.setText("Autenticação");
        lbl_auteticaca.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(lbl_auteticaca);
        lbl_auteticaca.setBounds(290, 101, 190, 40);
        jPanel1.add(lbl_Conexao);
        lbl_Conexao.setBounds(10, 40, 40, 50);

        lbl_recuperarSenha.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        lbl_recuperarSenha.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_recuperarSenha.setText("Recuperar a senha ");
        lbl_recuperarSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_recuperarSenhaMouseClicked(evt);
            }
        });
        lbl_recuperarSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lbl_recuperarSenhaKeyPressed(evt);
            }
        });
        jPanel1.add(lbl_recuperarSenha);
        lbl_recuperarSenha.setBounds(270, 300, 150, 13);

        btn_ocultar.setBackground(new java.awt.Color(214, 217, 223));
        btn_ocultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/ocultar.png"))); // NOI18N
        btn_ocultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ocultarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ocultar);
        btn_ocultar.setBounds(480, 260, 32, 25);

        btn_desecultar.setBackground(new java.awt.Color(214, 217, 223));
        btn_desecultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/visualizar.png"))); // NOI18N
        btn_desecultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_desecultarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_desecultar);
        btn_desecultar.setBounds(480, 260, 32, 25);

        lbl_boasVindas.setFont(new java.awt.Font("Yu Gothic UI Semilight", 3, 18)); // NOI18N
        lbl_boasVindas.setForeground(new java.awt.Color(153, 153, 153));
        jPanel1.add(lbl_boasVindas);
        lbl_boasVindas.setBounds(10, 330, 100, 20);

        lbl_horaData.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        lbl_horaData.setForeground(new java.awt.Color(153, 153, 153));
        jPanel1.add(lbl_horaData);
        lbl_horaData.setBounds(10, 360, 230, 18);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 1, 0, 0);

        lbl_border.setBackground(new java.awt.Color(204, 255, 255));
        lbl_border.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray), "Login", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 3, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.add(lbl_border);
        lbl_border.setBounds(240, 31, 290, 350);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(538, 389));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
Timer tm;

    /**
     * ************************ Metodos ***************************
     */

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void pfSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pfSenhaActionPerformed

    }//GEN-LAST:event_pfSenhaActionPerformed

    private void btn_entrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_entrarActionPerformed
        ValidarSenha();
    }//GEN-LAST:event_btn_entrarActionPerformed

    private void btn_entrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_entrarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ValidarUsername();
        }
    }//GEN-LAST:event_btn_entrarKeyPressed

    private void txt_UsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_UsernameKeyPressed

    }//GEN-LAST:event_txt_UsernameKeyPressed

    private void pfSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pfSenhaKeyPressed
        ValidarUsername();

    }//GEN-LAST:event_pfSenhaKeyPressed

    private void lbl_recuperarSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_recuperarSenhaMouseClicked

    }//GEN-LAST:event_lbl_recuperarSenhaMouseClicked

    private void btn_entrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_entrarMouseEntered
//        camposVazios();
    }//GEN-LAST:event_btn_entrarMouseEntered

    private void lbl_recuperarSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbl_recuperarSenhaKeyPressed


    }//GEN-LAST:event_lbl_recuperarSenhaKeyPressed

    private void txt_UsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_UsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_UsernameActionPerformed

    private void btn_desecultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_desecultarActionPerformed
        pfSenha.setEchoChar('•');
        btn_ocultar.setVisible(true);
        btn_desecultar.setVisible(false);
    }//GEN-LAST:event_btn_desecultarActionPerformed

    private void btn_ocultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ocultarActionPerformed
        btn_ocultar.setVisible(false);
        btn_desecultar.setVisible(true);
        pfSenha.setEchoChar((char) 0);
    }//GEN-LAST:event_btn_ocultarActionPerformed
    /**
     * @param args the command line arguments
     */

    private String dataf;
    private SimpleDateFormat sdf;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_desecultar;
    private javax.swing.JButton btn_entrar;
    private javax.swing.JButton btn_ocultar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel jlbl_Icone;
    public javax.swing.JLabel lbl_Conexao;
    public javax.swing.JLabel lbl_auteticaca;
    public javax.swing.JLabel lbl_boasVindas;
    public javax.swing.JLabel lbl_border;
    public javax.swing.JLabel lbl_horaData;
    public javax.swing.JLabel lbl_iconSenha;
    public javax.swing.JLabel lbl_iconUser;
    public javax.swing.JLabel lbl_imagem;
    public javax.swing.JLabel lbl_recuperarSenha;
    public javax.swing.JPasswordField pfSenha;
    protected javax.swing.JTextField txt_Username;
    // End of variables declaration//GEN-END:variables
  public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</ed
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }

//    private void setIconImage() {
//        URL url = this.getClass().getResource("/Jail_ts2022/icons/Senha.png");
//        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
//        this.setIconImage(iconeTitulo);
//    }
}
