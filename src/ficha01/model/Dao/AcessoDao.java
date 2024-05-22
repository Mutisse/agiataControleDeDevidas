/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha01.model.Dao;

import ficha01.model.Acesso;
import ficha01.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author i'mutisse
 */
public class AcessoDao {

    Connection conexao = null;
    PreparedStatement pst = null; //pst quer dizer  PreparedStatement
    ResultSet rst = null;

    public List<Acesso> ListarAcessos() {
        conexao = Conectar.conector();
        List<Acesso> Lista = new ArrayList<>();
        String sql = "SELECT `perfil` FROM `tbacesso` order by perfil";

        try {
            pst = conexao.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                Acesso perfil = new Acesso();
                perfil.setTipo_Acesso(resultado.getString("perfil"));
               
                Lista.add(perfil);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " Erro ao buscar o registro", "Notificação", JOptionPane.ERROR);
        } finally {
            try {
                pst.close();
                conexao.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e + "Erro na conexao");

            }
        }
        return Lista;
    }

}
