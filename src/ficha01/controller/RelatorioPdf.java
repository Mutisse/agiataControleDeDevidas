package ficha01.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Mutisse
 */
public class RelatorioPdf {

    private String dataf;
    private SimpleDateFormat sdf;

    public void gerarRelatorio(String total, String media, String totalPagas, String totalNPagas, String devMenor, String devMaior, String menor, String maior) {

        Document documento = new Document();

        Date data = new Date();
        sdf = new SimpleDateFormat("dd MMMM yyyy");

        Calendar c1 = Calendar.getInstance();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream("src\\ficha01\\arquivos\\Agiota Business.pdf"));
            documento.open();
            documento.setPageSize(PageSize.A4);
            documento.add(new Paragraph(" "));
             System.out.println("----------------------------------------------------------------------");
            Image imagi = Image.getInstance("src\\ficha01\\view\\icons\\FundoRegistarDividas.png");
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("                                                  ******  RELATÓRIO  ****** "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("   Devedor com maior dívida:  " + devMaior));
            documento.add(new Paragraph("   Devedor com menor dívida:  " + devMenor));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("   Maior dívida            :  " + maior));
            documento.add(new Paragraph("   Menor dívida            :  " + menor));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("   Total em dívidas pagas  :  " + totalPagas));
            documento.add(new Paragraph("   Total em  Nao dívidas pagas  :  " + totalNPagas));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("   Média das dívidas       :  " + media));
            documento.add(new Paragraph("   Total em dívidas        :  " + total));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Data: " + sdf.format(data) + "  Local: * Maputo-Moçambique "));
        } catch (DocumentException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar documento.\nERRO: " + ex);
        } finally {
            documento.close();
        }
    }
}
