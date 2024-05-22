/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha01.controller;

import ficha01.model.Cliente;
import ficha01.model.Dao.DividasDao;
import ficha01.model.Divida;
import java.awt.HeadlessException;
import java.math.BigDecimal;
import javax.swing.JOptionPane;

/**
 *
 * @author i'mutisse
 */
public class controllerDivida {

    DividasDao dividasDao = new DividasDao();

    public void isDeleted(String idDivida, String nome, String apelido) {
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem a certeza  que deseja remover esse devedor? \n" + nome + " " + apelido, "Notificação", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            try {

                dividasDao.delete(idDivida);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada!!");
        }
    }
    //txtNome,txtApelido,txf_contacto,spnValorDivida,lblValorApagar,lbl_i

    public void Update(String idDivida, String nome, String apelido, String contacto, String genero, BigDecimal valorDivida, BigDecimal valorAPagar) {
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem a certeza que deseja fazer essa atualização nos dados de: \n" + nome + " " + apelido + "?", "Notificação", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                Divida divida = new Divida();
                divida.setId_divida(idDivida);

                // Define os atributos do cliente
                Cliente cliente = new Cliente();
                cliente.setNomeProprio(nome);
                cliente.setApelido(apelido);
                cliente.setContacto(contacto);
                cliente.setGenero(genero);
                divida.setCliente(cliente);

                // Define os valores da dívida
                divida.setValorDivida(valorDivida);
                divida.setValorAPagar(valorAPagar);
//                divida.calcularRemanescente();

                // Atualiza o estado da dívida
                divida.atualizarEstadoDivida();

                System.out.println(divida);

                // Agora, dividasDao está inicializado corretamente
                dividasDao.UpdateDao(divida);

                JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada!!");
        }
    }
}
