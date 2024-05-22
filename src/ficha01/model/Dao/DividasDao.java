/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha01.model.Dao;

import ficha01.model.Cliente;
import ficha01.model.Divida;
import ficha01.model.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author i'mutisse
 */
public class DividasDao {

    Connection conexao = null;
    PreparedStatement pst = null; //pst quer dizer  PreparedStatement
    ResultSet rst = null;

    public List<Divida> listarClientesComDividas() {
        List<Divida> listaClientes = new ArrayList<>();
        try {
            conexao = Conectar.conector();
            String sql = "SELECT\n"
                    + "    c.`id` AS `ID_Cliente`,\n"
                    + "    c.`nomeProprio` AS `Nome`,\n"
                    + "    c.`apelido` AS `Apelido`,\n"
                    + "    c.`contacto` AS `Contacto`,\n"
                    + "    CASE\n"
                    + "        WHEN g.`thisSexo` = 'M' THEN 'Masculino'\n"
                    + "        WHEN g.`thisSexo` = 'F' THEN 'Feminino'\n"
                    + "        ELSE 'Indefinido'\n"
                    + "    END AS `Sexo`,\n"
                    + "    d.`id` AS `ID_Divida`,\n"
                    + "    d.`valorDivida` AS `Valor_Divida` ,\n"
                    + "    d.`valorAPagar` AS `Valor_A_Pagar`,\n"
                    + "    d.`remanescente` AS `Remanescente`,\n"
                    + "    d.`data` AS `Data`,\n"
                    + "    d.`theStatus` AS `Status_Divida`,\n"
                    + "    d.`isdeleted`\n"
                    + "FROM\n"
                    + "    `tbcliente` c\n"
                    + "JOIN\n"
                    + "    `tbdividas` d ON c.`id` = d.`id_cliente`\n"
                    + "JOIN\n"
                    + "    `tbgenero` g ON c.`id_sexo` = g.`id_sexo`\n"
                    + "WHERE\n"
                    + "    (d.`isdeleted` != true OR d.`isdeleted` IS NULL)\n"
                    + "  \n"
                    + "ORDER BY\n"
                    + "    c.`nomeProprio`,\n"
                    + "    c.`apelido` Asc;";

            pst = conexao.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {
                Divida clienteDivida = new Divida();
                Cliente cliente = new Cliente();
                cliente.setId(resultado.getString("id_cliente"));
                cliente.setNomeProprio(resultado.getString("Nome"));
                cliente.setApelido(resultado.getString("apelido"));
                cliente.setContacto(resultado.getString("contacto"));
                cliente.setGenero(resultado.getString("Sexo"));
                clienteDivida.setCliente(cliente);
                // problrma comeca quui
                clienteDivida.setId_divida(resultado.getString("id_divida"));
                clienteDivida.setValorDivida(resultado.getBigDecimal("Valor_Divida"));
                clienteDivida.setValorAPagar(resultado.getBigDecimal("Valor_A_Pagar"));
                clienteDivida.setRemanescente(resultado.getBigDecimal("remanescente"));
                clienteDivida.setIsdeleted(resultado.getBoolean("isdeleted"));

                String statusString = resultado.getString("Status_Divida");
                Status status = Status.setStatus(statusString);
                clienteDivida.setEstadoDivida(status);

                listaClientes.add(clienteDivida);
            }
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Erro ao buscar registros: " + e.getMessage(), "Notificação", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro na conexão: " + e.getMessage(), "Notificação", JOptionPane.ERROR_MESSAGE);
            }
        }
        return listaClientes;
    }

    /*````````````````````````````````````````  Crair novo codigo da divida ``````````````````````````````````````````````````````*/
    public String gerarCodigo() {
        try {
            conexao = Conectar.conector();
            String querySql = "SELECT MAX(id) FROM tbDividas";
            String ultimoId = "";

            pst = conexao.prepareStatement(querySql);
            rst = pst.executeQuery();

            if (rst != null && rst.next()) {
                ultimoId = rst.getString(1);
            }

            // Extrai o número do ID
            int novoId = 0;
            if (ultimoId != null && !ultimoId.isEmpty()) {
                novoId = Integer.parseInt(ultimoId.substring(1)) + 1;
            }

            // Formata o novo ID
            Calendar cal = Calendar.getInstance();
            int mes = cal.get(Calendar.MONTH) + 1; // Adiciona 1 pois o mês começa em 0
            String codigo = String.format("D%02d%02d", mes, novoId);

            // Verifica se o código gerado já existe na base de dados
            while (verificarExistenciaCodigo(codigo)) {
                // Se existir, gere um novo ID
                novoId++;
                codigo = String.format("D%02d%02d", mes, novoId);
            }

            return codigo;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " Erro ao gerar o código", "Notificação", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + " Erro na conexão", "Notificação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean verificarExistenciaCodigo(String codigo) {
        try {
            conexao = Conectar.conector();
            String querySql = "SELECT id FROM tbDividas WHERE id = ?";
            pst = conexao.prepareStatement(querySql);
            pst.setString(1, codigo);
            rst = pst.executeQuery();

            return rst.next(); // Se retornar verdadeiro, o código já existe
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " Erro ao verificar o código", "Notificação", JOptionPane.ERROR_MESSAGE);
            return true; // Considerando um erro como código existente
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + " Erro na conexão", "Notificação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /*`````````````````````````````````````````````` Metodo para fazer apagar de forma logica nos dados ``````````````````````````````````````````````````````*/
    public void delete(String idDivida) {
        conexao = Conectar.conector();
        String sql = "UPDATE  tbdividas d SET isdeleted =1 WHERE d.id = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, idDivida);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "O Devedor foi excluído com sucesso!!");
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e + " Erro ao remover", "Notificação", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e + "Erro na conexão");
            }
        }
    }

    public void UpdateDao(Divida divida) {
        conexao = Conectar.conector();
        String sql = "UPDATE tbcliente c\n"
                + "JOIN tbdividas d ON c.id = d.id_cliente\n"
                + "SET \n"
                + "    c.nomeProprio = ?,\n"
                + "    c.apelido = ?,\n"
                + "    c.contacto = ?,\n"
                + "    c.thisSexo = ?,\n"
                + "    d.valorDivida = ?,\n"
                + "    d.valorAPagar = ?,\n"
                + "    d.remanescente = ?,\n"
                + "    d.theStatus = ?\n"
                + "WHERE \n"
                + "    d.id = ?;";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, divida.getCliente().getNomeProprio());
            pst.setString(2, divida.getCliente().getApelido());
            pst.setString(3, divida.getCliente().getContacto());
            pst.setString(4, String.valueOf(divida.getCliente().getGenero()));
            pst.setBigDecimal(5, divida.getValorDivida());
            pst.setBigDecimal(6, divida.getValorAPagar());
            pst.setBigDecimal(7, divida.getRemanescente());
            pst.setString(8, divida.getEstadoDivida().toString());
            pst.setString(9, divida.getId_divida());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Dívida atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar dívida: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void InsertDao(Divida divida) {
        
        try {
            conexao = Conectar.conector();
            String sql = "INSERT INTO tbDividas (valorDivida, valorAPagar, remanescente, id_cliente, data) "
                    + "VALUES (?, ?, ?, ?, ?)";

            pst = conexao.prepareStatement(sql);
            pst.setBigDecimal(1, divida.getValorDivida());
            pst.setBigDecimal(2, divida.getValorAPagar());
            pst.setBigDecimal(3, divida.getRemanescente());
            // pst.setInt(4, divida.getCliente().getId()); // Assumindo que getId() retorna o ID do cliente
            pst.setTimestamp(5, divida.getData());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Nova dívida inserida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir nova dívida: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void findData(String nomePesquisa, JTable tabela) {
        conexao = Conectar.conector();
        String sql = "SELECT\n"
                + "    d.`id` AS `ID_Divida`,\n"
                + "    CONCAT(c.`nomeProprio`, ' ', c.`apelido`) AS `Nome_Completo`,\n"
                + "    CASE\n"
                + "        WHEN g.`thisSexo` = 'M' THEN 'Masculino'\n"
                + "        WHEN g.`thisSexo` = 'F' THEN 'Feminino'\n"
                + "        ELSE 'Indefinido'\n"
                + "    END AS `Sexo`,\n"
                + "    c.`contacto` AS `Telefone`,\n"
                + "    d.`valorDivida` AS `Credito`,\n"
                + "    d.`valorAPagar` AS `Quitar`,\n"
                + "    d.`theStatus` AS `Status_Divida`\n"
                + "FROM\n"
                + "    `tbcliente` c\n"
                + "JOIN\n"
                + "    `tbdividas` d ON c.`id` = d.`id_cliente`\n"
                + "LEFT JOIN\n"
                + "    `tbgenero` g ON c.`id_sexo` = g.`id_sexo`\n"
                + "WHERE\n"
                + "    (d.`isdeleted` != true OR d.`isdeleted` IS NULL)\n"
                + "    AND (c.`nomeProprio` LIKE ? OR c.`apelido` LIKE ? OR d.`theStatus` LIKE ?)\n"
                + "    AND d.`theStatus` != 'Liquidada'\n"
                + "ORDER BY\n"
                + "    c.`nomeProprio`,\n"
                + "    c.`apelido`;";

        try {
            if (nomePesquisa.isEmpty()) {
                ((DefaultTableModel) tabela.getModel()).setRowCount(0);
            } else {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, "%" + nomePesquisa + "%");
                pst.setString(2, "%" + nomePesquisa + "%");
                pst.setString(3, "%" + nomePesquisa + "%");
                rst = pst.executeQuery();
                tabela.setModel(DbUtils.resultSetToTableModel(rst));
            }
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e.getMessage() + " Registro não Encontrado", "Notificação", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e + " Erro na conexão");
            }
        }
    }

}
