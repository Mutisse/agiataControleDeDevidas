/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package ficha01.view;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import ficha01.model.Dao.Conectar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
public final class TelaRelatorio extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement stmt = null; //pst quer dizer  PreparedStatement
    ResultSet rst = null;

    /**
     * Creates new form Relatorio
     */
    public TelaRelatorio() {
        initComponents();
//        RelatorioPDF();
        showPieChart();
        showBarChart();
        //  ChartGenerator.showBarChart();
        showLineChart();
        Date data = new Date();
        DateFormat formato = DateFormat.getDateInstance(DateFormat.LONG);
        dat = formato.format(data);
    }



    /*
    Explicação:
O método showPieChart() agora consulta o banco de dados para obter o valor total da dívida agrupado por status e cria um gráfico de pizza com base nesses dados.
Cada fatia do gráfico representa um status de dívida, e a área de cada fatia é proporcional ao valor total da dívida para esse status.
Os dados são recuperados do banco de dados e adicionados ao conjunto de dados (dataset), que é então utilizado para criar o gráfico de pizza.
     */
    public void showPieChart() {
        conexao = Conectar.conector();
        DefaultPieDataset dataset = new DefaultPieDataset();
        String query = "SELECT theStatus, SUM(valorDivida) AS total FROM tbdividas GROUP BY theStatus";

        try {
            stmt = conexao.prepareStatement(query);
            rst = stmt.executeQuery();

            while (rst.next()) {
                String status = rst.getString("theStatus");
                double total = rst.getDouble("total");

                dataset.setValue(status, total);
            }

        } catch (SQLException e) {
        }

        // Criando o gráfico de pizza
        JFreeChart pieChart = ChartFactory.createPieChart("Gráfico de Pizza - Distribuição do Valor da Dívida por Status",
                dataset, true, true, false);

        pieChart.setBackgroundPaint(Color.white);

        // Criação do painel para exibir o gráfico
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setBounds(0, 0, 448, 302);
        PiepanelChart2.add(pieChartPanel, BorderLayout.CENTER);
        PiepanelChart2.validate();
    }


    /*Explicação:
O método showBarChart() agora consulta o banco de dados para obter o valor total da dívida por mês e cria um gráfico de barras com esses dados.
Os meses são representados no eixo x do gráfico, e o valor total da dívida é representado no eixo y.
O método getMonthName() é usado para obter o nome do mês com base no número do mês retornado pela consulta SQL.
     */
    public void showBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        conexao = Conectar.conector();
        String query = "SELECT MONTH(data) AS month, SUM(valorDivida) AS totalDebt FROM tbdividas GROUP BY month";

        try {
            stmt = conexao.prepareStatement(query);
            rst = stmt.executeQuery();
            while (rst.next()) {
                int month = rst.getInt("month");
                double totalDebt = rst.getDouble("totalDebt");

                dataset.addValue(totalDebt, "Dívidas", getMonthName(month));
            }

        } catch (SQLException e) {
        }

        JFreeChart chartBar = ChartFactory.createBarChart(
                "Gráfico de Barras - Valor Total da Dívida por Mês",
                "Mês",
                "Valor Total da Dívida",
                dataset,
                PlotOrientation.VERTICAL,
                true, // Exibir legenda
                true, // Exibir tooltips
                false // Exibir urls
        );

        ChartPanel barChartPanel = new ChartPanel(chartBar);
        barChartPanel.setBounds(5, 5, 600, 370);
        showBarChart.removeAll();
        showBarChart.add(barChartPanel, BorderLayout.CENTER);

    }

    private String getMonthName(int month) {
        Map<Integer, String> monthsMap = new HashMap<>();
        monthsMap.put(1, "Jan");
        monthsMap.put(2, "Fev");
        monthsMap.put(3, "Mar");
        monthsMap.put(4, "Abr");
        monthsMap.put(5, "Mai");
        monthsMap.put(6, "Jun");
        monthsMap.put(7, "Jul");
        monthsMap.put(8, "Ago");
        monthsMap.put(9, "Set");
        monthsMap.put(10, "Out");
        monthsMap.put(11, "Nov");
        monthsMap.put(12, "Dez");

        return monthsMap.getOrDefault(month, "");
    }

    /*
Explicação:
O método showLineChart() agora consulta o banco de dados para obter o valor total da dívida agrupado por mês e cria um gráfico de linha com base nesses dados.
Cada ponto no gráfico de linha representa o valor total da dívida para um mês específico.
O método getMonthName() é usado para obter o nome do mês com base no número do mês retornado pela consulta SQL.    
     */
    public void showLineChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        conexao = Conectar.conector();
        String query = "SELECT MONTH(data) AS month, SUM(valorDivida) AS totalDebt FROM tbdividas GROUP BY month";

        try {
            stmt = conexao.prepareStatement(query);
            rst = stmt.executeQuery();
            while (rst.next()) {
                int month = rst.getInt("month");
                double totalDebt = rst.getDouble("totalDebt");

                dataset.addValue(totalDebt, "Valor Total da Dívida", getMonthNameLine(month));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Criando o gráfico de linha
        JFreeChart chartLine = ChartFactory.createLineChart(
                "Gráfico de Linha - Valor Total da Dívida ao Longo do Tempo",
                "Mês",
                "Valor Total da Dívida",
                dataset,
                PlotOrientation.VERTICAL,
                true, // Exibir legenda
                true, // Exibir tooltips
                false // Exibir urls
        );

        // Cria o painel do gráfico
        ChartPanel lineChartPanel = new ChartPanel(chartLine);
        ChartPanel barChartPanel = new ChartPanel(chartLine);
        barChartPanel.setBounds(5, 5, 580, 340);
        PiepanelChart.removeAll();
        PiepanelChart.add(barChartPanel, BorderLayout.CENTER);
    }

    private String getMonthNameLine(int month) {
        String[] months = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        return months[month - 1];
    }

    /*Nesta versão, o método gerarRelatorio() obtém os dados diretamente
    da base de dados usando uma consulta SQL. Ele calcula as estatísticas 
    relevantes e as adiciona ao relatório PDF, conforme sugerido anteriormente.
    Os métodos getDevedorComMaiorDivida() e getDevedorComMenorDivida() 
    foram adicionados para obter o nome dos devedores com a maior e menor dívida, respectivamente.*/
    public class RelatorioPDF {

        private Connection connection;

        public RelatorioPDF(Connection connection) {
            this.connection = connection;
        }

        public void gerarRelatorio() {
            try {
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream("Relatorio.pdf"));
                documento.open();

                // Adiciona o título do relatório
                Paragraph titulo = new Paragraph("RELATÓRIO");
                titulo.setAlignment(Element.ALIGN_CENTER);
                documento.add(titulo);
                documento.add(Chunk.NEWLINE);

                // Obtém os dados da base de dados
                String query = "SELECT MONTH(data) AS mes, SUM(valorDivida) AS total FROM tbdividas GROUP BY mes";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                // Calcula os meses com as maiores e menores dívidas
                int monthMaxDebt = 0;
                int monthMinDebt = 0;
                double totalDividas = 0;
                double menorDivida = Double.MAX_VALUE;
                double maiorDivida = Double.MIN_VALUE;

                while (resultSet.next()) {
                    int mes = resultSet.getInt("mes");
                    double total = resultSet.getDouble("total");

                    if (total > maiorDivida) {
                        maiorDivida = total;
                        monthMaxDebt = mes;
                    }

                    if (total < menorDivida) {
                        menorDivida = total;
                        monthMinDebt = mes;
                    }

                    totalDividas += total;
                }

                // Calcula os dados restantes
                double mediaDividas = totalDividas / 12;
                double totalPagas = 7000; // Supondo um valor fixo para fins de exemplo
                double totalNPagas = totalDividas - totalPagas;
                double percentDividasFeitas = (totalDividas > 0) ? (totalDividas / totalDividas) * 100 : 0;
                double percentDividasPagas = (totalDividas > 0) ? (totalPagas / totalDividas) * 100 : 0;
                double percentDividasRestantes = (totalDividas > 0) ? (totalNPagas / totalDividas) * 100 : 0;
                double percentMenorDivida = (totalDividas > 0) ? (menorDivida / totalDividas) * 100 : 0;
                double percentMaiorDivida = (totalDividas > 0) ? (maiorDivida / totalDividas) * 100 : 0;

                // Data
                Date data = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                // Adiciona informações ao relatório
                documento.add(new Paragraph("Devedor com maior dívida:   " + getDevedorComMaiorDivida()));
                documento.add(new Paragraph("Devedor com menor dívida:   " + getDevedorComMenorDivida()));
                documento.add(new Paragraph("Maior dívida (no mês de " + getMonthName(monthMaxDebt) + "):   " + maiorDivida));
                documento.add(new Paragraph("Menor dívida (no mês de " + getMonthName(monthMinDebt) + "):   " + menorDivida));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Total em dívidas pagas:   " + totalPagas));
                documento.add(new Paragraph("Total em dívidas não pagas:   " + totalNPagas));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Percentagem das dívidas feitas:  " + new DecimalFormat("#.##").format(percentDividasFeitas) + "%"));
                documento.add(new Paragraph("Percentagem das dívidas pagas:   " + new DecimalFormat("#.##").format(percentDividasPagas) + "%"));
                documento.add(new Paragraph("Percentagem das dívidas restantes:   " + new DecimalFormat("#.##").format(percentDividasRestantes) + "%"));
                documento.add(new Paragraph("Percentagem da dívida do menor devedor:   " + new DecimalFormat("#.##").format(percentMenorDivida) + "%"));
                documento.add(new Paragraph("Percentagem da dívida do maior devedor:   " + new DecimalFormat("#.##").format(percentMaiorDivida) + "%"));
                documento.add(Chunk.NEWLINE);
                documento.add(new Paragraph("Média das dívidas:   " + mediaDividas));
                documento.add(new Paragraph("Total em dívidas:   " + totalDividas));
                documento.add(Chunk.NEWLINE);
                documento.add(new Paragraph("Data: " + sdf.format(data) + "  Local: * Maputo-Moçambique "));

                documento.close();
                System.out.println("Relatório gerado com sucesso!");
            } catch (DocumentException | FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        // Método para obter o nome do mês com base no índice
        public String getMonthName(int month) {
            String[] months = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
            return months[month - 1];
        }

        // Método para obter o devedor com a maior dívida da base de dados
        public String getDevedorComMaiorDivida() throws SQLException {
            String query = "SELECT CONCAT(c.nomeProprio, ' ', c.apelido) AS `nomeCompleto`"
                    + "FROM tbdividas  d inner join tbcliente c on d.id_cliente = c.id\n"
                    + "WHERE valorDivida = (SELECT MAX(valorDivida) FROM tbdividas);";
            stmt = conexao.prepareStatement(query);
            rst = stmt.executeQuery();
            rst.next();
            return rst.getString("nomeCompleto");
        }

        // Método para obter o devedor com a menor dívida da base de dados
        public String getDevedorComMenorDivida() throws SQLException {
            String query = "SELECT CONCAT(c.nomeProprio, ' ', c.apelido) AS `nomeCompleto`\n"
                    + "FROM tbdividas  d inner join tbcliente c on d.id_cliente = c.id \n"
                    + "WHERE valorDivida = (SELECT MIN(valorDivida) FROM tbdividas);";
            stmt = conexao.prepareStatement(query);
            rst = stmt.executeQuery();
            rst.next();
            return rst.getString("nomeCompleto");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        PiepanelChart = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        PiepanelChart2 = new javax.swing.JPanel();
        showBarChart = new javax.swing.JPanel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        PiepanelChart1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        ttt1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtMaiorDivida1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDevMaiorDivida1 = new javax.swing.JLabel();
        txtMenorDivida1 = new javax.swing.JLabel();
        txtTotalPagas1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblTotalNPagas1 = new javax.swing.JLabel();
        txtDevMenorDivida1 = new javax.swing.JLabel();
        txtMediaDividas1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtTotalDividas1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        showBarChart1 = new javax.swing.JPanel();

        jPanel1.setLayout(null);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ficha01/view/icons/FundoRegistarDividas.png"))); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(450, 20, 100, 120);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jPanel4.setLayout(null);

        PiepanelChart.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PiepanelChart.setLayout(null);
        jPanel4.add(PiepanelChart);
        PiepanelChart.setBounds(10, 10, 600, 360);

        jTabbedPane1.addTab("showPieChart", jPanel4);

        jPanel2.setLayout(null);

        PiepanelChart2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PiepanelChart2.setLayout(null);
        jPanel2.add(PiepanelChart2);
        PiepanelChart2.setBounds(85, 39, 448, 302);

        jTabbedPane1.addTab("showPieChart", jPanel2);

        showBarChart.setLayout(null);
        jTabbedPane1.addTab("showBarChart", showBarChart);

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jPanel5.setLayout(null);

        PiepanelChart1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PiepanelChart1.setLayout(null);
        jPanel5.add(PiepanelChart1);
        PiepanelChart1.setBounds(10, 50, 390, 290);

        jLabel5.setText(" Cliente com maior dívida");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(430, 120, 160, 20);

        ttt1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        ttt1.setForeground(new java.awt.Color(255, 51, 51));
        ttt1.setText("     Raltorio");
        ttt1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(ttt1);
        ttt1.setBounds(460, 50, 120, 40);

        jLabel11.setText("Valor da divida");
        jPanel5.add(jLabel11);
        jLabel11.setBounds(430, 190, 100, 20);

        txtMaiorDivida1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMaiorDivida1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(txtMaiorDivida1);
        txtMaiorDivida1.setBounds(430, 220, 80, 20);

        jLabel7.setText("Valorda divida");
        jPanel5.add(jLabel7);
        jLabel7.setBounds(430, 300, 100, 20);

        txtDevMaiorDivida1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDevMaiorDivida1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(txtDevMaiorDivida1);
        txtDevMaiorDivida1.setBounds(430, 150, 180, 40);

        txtMenorDivida1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMenorDivida1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(txtMenorDivida1);
        txtMenorDivida1.setBounds(430, 320, 80, 20);

        txtTotalPagas1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalPagas1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(txtTotalPagas1);
        txtTotalPagas1.setBounds(80, 360, 60, 20);

        jLabel12.setText(" Total Pago");
        jPanel5.add(jLabel12);
        jLabel12.setBounds(10, 360, 70, 20);

        jLabel13.setText("Total nao Pago");
        jPanel5.add(jLabel13);
        jLabel13.setBounds(150, 360, 90, 20);

        lblTotalNPagas1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotalNPagas1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(lblTotalNPagas1);
        lblTotalNPagas1.setBounds(240, 360, 70, 20);

        txtDevMenorDivida1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDevMenorDivida1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(txtDevMenorDivida1);
        txtDevMenorDivida1.setBounds(430, 250, 180, 40);

        txtMediaDividas1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMediaDividas1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(txtMediaDividas1);
        txtMediaDividas1.setBounds(430, 360, 80, 20);

        jLabel14.setText("Media da Dadividas");
        jPanel5.add(jLabel14);
        jLabel14.setBounds(320, 360, 110, 20);

        txtTotalDividas1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalDividas1.setForeground(new java.awt.Color(255, 153, 0));
        txtTotalDividas1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(txtTotalDividas1);
        txtTotalDividas1.setBounds(520, 348, 90, 30);

        jLabel15.setText("        Total ");
        jPanel5.add(jLabel15);
        jLabel15.setBounds(520, 320, 80, 20);

        jTabbedPane2.addTab("showPieChart", jPanel5);

        showBarChart1.setLayout(null);
        jTabbedPane2.addTab("showBarChart", showBarChart1);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setBounds(0, 0, 639, 459);
    }// </editor-fold>//GEN-END:initComponents
    String dat;
    float totalDividas = 0, mediaDividas = 0, totalPagas = 0, totalNPagas = 0, DevMaiorDivida = 0, tMenorDivida = 0;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PiepanelChart;
    private javax.swing.JPanel PiepanelChart1;
    private javax.swing.JPanel PiepanelChart2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblTotalNPagas1;
    private javax.swing.JPanel showBarChart;
    private javax.swing.JPanel showBarChart1;
    private javax.swing.JLabel ttt1;
    private javax.swing.JLabel txtDevMaiorDivida1;
    private javax.swing.JLabel txtDevMenorDivida1;
    private javax.swing.JLabel txtMaiorDivida1;
    private javax.swing.JLabel txtMediaDividas1;
    private javax.swing.JLabel txtMenorDivida1;
    private javax.swing.JLabel txtTotalDividas1;
    private javax.swing.JLabel txtTotalPagas1;
    // End of variables declaration//GEN-END:variables

}
