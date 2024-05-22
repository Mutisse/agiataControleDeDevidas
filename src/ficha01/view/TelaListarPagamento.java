/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package ficha01.view;

import ficha01.model.Dao.Conectar;
import ficha01.model.Dao.DividasDao;
import ficha01.model.Divida;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Mutisse
 */
public final class TelaListarPagamento extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement stmt = null; //pst quer dizer  PreparedStatement
    ResultSet rst = null;
    Date data = new Date();
    DateFormat dtHora = DateFormat.getDateTimeInstance();
    DividasDao dividasDao = new DividasDao();

    DividasDao divida = new DividasDao();
    List<Divida> lista = divida.listarClientesComDividas();
    Boolean Staus;

    /**
     * Creates new form TelaPagmmentos
     */
    public TelaListarPagamento() {
        initComponents();
        linhas = new Object[][]{};
        sdf = new SimpleDateFormat("dd MMMM yyyy");
        //preencherTabela();
        PrencherTabela();
        lbl_Atual.setText(dtHora.format(data));
        jTabbedPane1.setSelectedIndex(0);
        showPieChart();
        showBarChart();

    }

    void preencherTabela(String filtro) {
        try {

            DefaultTableModel newModelTable = (DefaultTableModel) jTablePagamentos.getModel();
            newModelTable.setRowCount(0);
            for (Divida d : lista) {

                // Verifica se o estado da dívida é "pendente" antes de adicionar à tabela
                //String[] colunas = new String[]{"ID ","Nome Completo","Emprestado", "A Pagar", "Remanescente", "Estado"};
                if (d.getEstadoDivida().getStatusFormatado().equalsIgnoreCase("Liquidada") && d.getIsdeleted() != true) {
                    if (filtro.isEmpty()
                            || d.getCliente().getNomeProprio().toLowerCase().contains(filtro.toLowerCase())
                            || d.getCliente().getApelido().toLowerCase().contains(filtro.toLowerCase())
                            || d.getEstadoDivida().getStatusFormatado().toLowerCase().contains(filtro.toLowerCase())) {

                        newModelTable.addRow(new Object[]{
                            d.getId_divida(),
                            d.getCliente().getNomeProprio() + " " + d.getCliente().getApelido(),
                            d.getValorDivida(),
                            d.getValorAPagar(),
                            d.getRemanescente(),
                            d.getEstadoDivida().getStatusFormatado() // Aqui está a correção
                        });
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e.getMessage() + " Erro ao preencher a tabela", "Notificação", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void PrencherTabela() {
        try {

            DefaultTableModel newModelTable = (DefaultTableModel) jTclientes.getModel();
            newModelTable.setRowCount(0);
            for (Divida d : lista) {
                // Verifica se o estado da dívida é "pendente" antes de adicionar à tabela
                //String[] colunas = new String[]{"ID ","Nome Completo","Emprestado", "A Pagar", "Remanescente", "Estado"};
                if (d.getIsdeleted() != true) {
                    if (d.getEstadoDivida().getStatusFormatado().equalsIgnoreCase("Liquidada")) {
                        Staus = true;
                    } else {
                        Staus = false;
                    }
                    newModelTable.addRow(new Object[]{
                        d.getId_divida(),
                        d.getCliente().getNomeProprio() + " " + d.getCliente().getApelido(),
                        d.getCliente().getGenero(),
                        d.getValorDivida(),
                        d.getValorAPagar(),
                        Staus});
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage() + " Erro ao preencher atabela ", "Notificação", JOptionPane.ERROR);
        }
    }

//    public void showBarChart() {
//        // Suponha que você tenha um array de valores de dívidas para cada mês
//        double[] monthlyDebts = {1000, 500, 2500, 200, 1800, 2200};
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        String[] months = {"Maior Devedor", "Menor Devedor", "Divida Pagas", "Dividas não Pagas", "ToTal Divdas", "Media"};
//
//        // Adicionando as quantidades de dívidas para cada mês
//        for (int i = 0; i < months.length; i++) {
//            dataset.addValue(monthlyDebts[i], "", months[i]);
//        }
//
//        JFreeChart chartBar = ChartFactory.createBarChart(
//                "Gráfico de Barras",
//                "Mês",
//                "Quantidade em Dívidas",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true, // Exibir legenda
//                true, // Exibir s
//                false // Exibir urls
//        );
//
//        ChartPanel barChartPanel = new ChartPanel(chartBar);
//        barChartPanel.setBounds(20, 20, 550, 230);
//        showBarChart.add(barChartPanel, BorderLayout.CENTER);
//        showPieChart.validate();
//
//    }
    public void showBarChart() {
        conexao = Conectar.conector();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String query = "SELECT "
                + "MAX(valorDivida) AS maiorDevedor, "
                + "MIN(valorDivida) AS menorDevedor, "
                + "SUM(CASE WHEN theStatus = 'Liquidada' THEN valorDivida ELSE 0 END) AS dividasLiquidadas, "
                + "SUM(CASE WHEN theStatus = 'Parcial' THEN valorDivida ELSE 0 END) AS dividasParciais, "
                + "SUM(CASE WHEN theStatus = 'Pendente' THEN valorDivida ELSE 0 END) AS dividasPendentes, "
                + "SUM(valorDivida) AS totalDividas, "
                + "AVG(valorDivida) AS media "
                + "FROM tbdividas";
        try {

            stmt = conexao.prepareStatement(query);
            rst = stmt.executeQuery();
            if (rst.next()) {
                double maiorDevedor = rst.getDouble("maiorDevedor");
                double menorDevedor = rst.getDouble("menorDevedor");
                double dividasLiquidadas = rst.getDouble("dividasLiquidadas");
                double dividasParciais = rst.getDouble("dividasParciais");
                double dividasPendentes = rst.getDouble("dividasPendentes");
                double totalDividas = rst.getDouble("totalDividas");
                double media = rst.getDouble("media");

                dataset.addValue(maiorDevedor, "Valor", "Maior Devedor");
                dataset.addValue(menorDevedor, "Valor", "Menor Devedor");
                dataset.addValue(dividasLiquidadas, "Valor", "Dividas Liquidadas");
                dataset.addValue(dividasParciais, "Valor", "Dividas Parciais");
                dataset.addValue(dividasPendentes, "Valor", "Dividas Pendentes");
                dataset.addValue(totalDividas, "Valor", "Total Dividas");
                dataset.addValue(media, "Valor", "Média");
            }

        } catch (SQLException e) {
        }

        // Criando o gráfico de barras
        JFreeChart chartBar = ChartFactory.createBarChart(
                "Gráfico de Barras",
                "Atributo",
                "Valor",
                dataset,
                PlotOrientation.VERTICAL,
                true, // Exibir legenda
                true, // Exibir tooltips
                false // Exibir urls
        );

        ChartPanel barChartPanel = new ChartPanel(chartBar);
        barChartPanel.setBounds(05, 20, 570, 230);
        showBarChart.add(barChartPanel, BorderLayout.CENTER);
        showPieChart.validate();
    }

    public void showPieChart() {
        // Dados fictícios para simular as variáveis necessárias
        double DevMaiorDivida = 5000;
        double tMenorDivida = 2000;
        double totalNPagas = 3000;
        double totalPagas = 7000;
        double mediaDividas = 4000;
        double totalDividas = 10000;
        // Calculando as porcentagens das dívidas
        double percentDividasFeitas = (totalDividas / totalDividas) * 100;
        double percentDividasPagas = (totalPagas / totalDividas) * 100;
        double percentDividasRestantes = ((totalDividas - totalPagas) / totalDividas) * 100;
        double percentMenorDivida = (tMenorDivida / totalDividas) * 100;
        //double percentMaiorDivida = (DevMaiorDivida / totalDividas) * 100;

        // Criando o conjunto de dados do gráfico de pizza
        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue("Homens", percentDividasFeitas);
        dataset.setValue("Mulheres", percentDividasPagas);
        dataset.setValue("feitas por Homenes", percentDividasRestantes);
        dataset.setValue("feitas por Mulheres", percentMenorDivida);
        //  dataset.setValue("Total", percentMaiorDivida);

        // Criando o gráfico de pizza
        JFreeChart pieChart = ChartFactory.createPieChart("Gráfico de Pizza", dataset, true, true, false);

        pieChart.setBackgroundPaint(Color.white);
        //create chartPanel to display chart(graph)
        ChartPanel pieChartPanel = new ChartPanel(pieChart);

        pieChartPanel.setBounds(05, 05, 300, 190);
        showPieChart.add(pieChartPanel, BorderLayout.CENTER);
        showPieChart.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        txtPesquisar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        showPieChart = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTclientes = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePagamentos = new javax.swing.JTable();
        txtPesquisar1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        lbl_Atual = new javax.swing.JLabel();
        showBarChart = new javax.swing.JPanel();

        getContentPane().setLayout(null);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(null);

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
        jPanel6.add(txtPesquisar);
        txtPesquisar.setBounds(10, 180, 200, 30);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/Pesq.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1);
        jButton1.setBounds(220, 180, 40, 30);

        showPieChart.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        showPieChart.setLayout(null);
        jPanel6.add(showPieChart);
        showPieChart.setBounds(300, 10, 310, 200);

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(51, 51, 51));
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("Relatorio da divida ");
        jLabel66.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel66.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel6.add(jLabel66);
        jLabel66.setBounds(10, 30, 270, 26);

        jTclientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIdex){
                return false;
            }
        };
        jTclientes.setBackground(new java.awt.Color(204, 204, 204));
        jTclientes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTclientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome Completo", "Género", "divida", "emprestado", "Status"
            }
        ));
        jTclientes.setToolTipText("Seelecione o Funcionário que pretendes eliminar ou  Atualizar os dados ");
        jTclientes.setFocusable(false);
        jTclientes.setPreferredSize(new java.awt.Dimension(897, 600));
        jTclientes.setSelectionBackground(new java.awt.Color(232, 242, 252));
        jTclientes.setSelectionForeground(new java.awt.Color(240, 240, 240));
        jTclientes.getTableHeader().setReorderingAllowed(false);
        jTclientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTclientesMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTclientesMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTclientes);

        jPanel6.add(jScrollPane2);
        jScrollPane2.setBounds(10, 230, 600, 150);

        jTabbedPane1.addTab("Realizados", jPanel6);

        jPanel1.setLayout(null);

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(51, 51, 51));
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("Pagamentos Realizados");
        jLabel67.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel67.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel67);
        jLabel67.setBounds(110, 40, 360, 26);

        jTablePagamentos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIdex){
                return false;
            }
        };
        String[] colunas = new String[]{"ID ","Nome ","Emprestado", "A Pagar", "Remanescente", "Status"};
        model = new DefaultTableModel(linhas, colunas);
        jTablePagamentos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTablePagamentos.setModel(model);
        jTablePagamentos.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTablePagamentos.getColumnModel().getColumn(1).setPreferredWidth(115);
        jTablePagamentos.getColumnModel().getColumn(2).setPreferredWidth(60);
        jTablePagamentos.getColumnModel().getColumn(3).setPreferredWidth(60);
        jTablePagamentos.getColumnModel().getColumn(4).setPreferredWidth(60);
        jTablePagamentos.getColumnModel().getColumn(5).setPreferredWidth(60);
        jTablePagamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePagamentosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePagamentos);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 180, 600, 200);

        txtPesquisar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisar1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPesquisar1KeyTyped(evt);
            }
        });
        txtPesquisar1.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txtPesquisar1VetoableChange(evt);
            }
        });
        jPanel1.add(txtPesquisar1);
        txtPesquisar1.setBounds(10, 140, 280, 30);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/Pesq.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(300, 140, 40, 30);

        jTabbedPane1.addTab("Pagamentos Realizados", jPanel1);

        jPanel7.setLayout(null);

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(51, 51, 51));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("Pagamentos");
        jLabel64.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel64.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel7.add(jLabel64);
        jLabel64.setBounds(130, 30, 320, 48);

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/loan.png"))); // NOI18N
        jPanel7.add(jLabel59);
        jLabel59.setBounds(490, 10, 120, 100);

        lbl_Atual.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jPanel7.add(lbl_Atual);
        lbl_Atual.setBounds(10, 10, 180, 20);

        showBarChart.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        showBarChart.setLayout(null);
        jPanel7.add(showBarChart);
        showBarChart.setBounds(10, 120, 600, 260);

        jTabbedPane1.addTab("Resultados", jPanel7);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 0, 620, 420);

        setBounds(0, 0, 639, 458);
    }// </editor-fold>//GEN-END:initComponents

    private void jTablePagamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePagamentosMouseClicked

    }//GEN-LAST:event_jTablePagamentosMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        preencherTabela();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPesquisarVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtPesquisarVetoableChange

    }//GEN-LAST:event_txtPesquisarVetoableChange

    private void txtPesquisarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyTyped

    }//GEN-LAST:event_txtPesquisarKeyTyped

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        dividasDao.findData(txtPesquisar.getText(), jTablePagamentos);
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked

    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        if (jTabbedPane1.equals("Pagamentos Realizados")) {
            PrencherTabela();
        }
    }//GEN-LAST:event_jPanel6MouseClicked

    private void txtPesquisar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisar1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisar1KeyReleased

    private void txtPesquisar1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisar1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisar1KeyTyped

    private void txtPesquisar1VetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtPesquisar1VetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisar1VetoableChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTclientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTclientesMouseClicked

    }//GEN-LAST:event_jTclientesMouseClicked

    private void jTclientesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTclientesMouseReleased
        preencherTabela(txtPesquisar.getText());
    }//GEN-LAST:event_jTclientesMouseReleased

    private DefaultTableModel model;
    private final Object[][] linhas;
    private String dataf;
    private final SimpleDateFormat sdf;
    private boolean found = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTablePagamentos;
    private javax.swing.JTable jTclientes;
    public javax.swing.JLabel lbl_Atual;
    private javax.swing.JPanel showBarChart;
    private javax.swing.JPanel showPieChart;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtPesquisar1;
    // End of variables declaration//GEN-END:variables
}
