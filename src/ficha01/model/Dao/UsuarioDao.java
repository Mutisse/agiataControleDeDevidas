/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha01.model.Dao;

import ficha01.model.Acesso;
import ficha01.model.Status;
import ficha01.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author edils
 */
public class UsuarioDao {

    Connection conexao = null;
    PreparedStatement pst = null; //pst quer dizer  PreparedStatement
    ResultSet rst = null;

    /**
     * **************** metodo para adciomar usuario
     *
     *******************
     * @param add
     */
    public void Add(Usuario add) {

        conexao = Conectar.conector();
        String sql = "INSERT INTO `dmi_inf2ano_tech`.`tbUsuarios` (`Nome`, `Genero`, `Username`, `email`, `biografia`, `estado`, `Senha`) VALUES (?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            //   pst.setString(1, add.getNome());
//            pst.setString(2, add.getGenero());
//            pst.setString(3, add.getNomeUsuario());
//            pst.setString(4, add.getEmail());
//            pst.setString(5, add.getBiografia());
//            pst.setString(6, add.getEstado());
            pst.setString(7, add.getSenha());
            pst.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Usuario << " + add.getNome() + " >> Cadastrado com sucesso!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " Erro no cadastro", "Notificação", WARNING_MESSAGE);
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
    }

    /**
     * **************** metodo para alterar usuario
     *
     *******************
     * @param edit
     */
    public void update(Usuario edit) {
        conexao = Conectar.conector();
        String sql = "update tbUsuarios set Nome =?, Genero =?, Username =?, email =?,biografia=?,estado=?,Senha=? where Id_User =?";
        try {
            pst = conexao.prepareStatement(sql);
//            pst.setString(1, edit.getNome());
            // pst.setString(2, edit.getGenero());
//            pst.setString(3, edit.getNomeUsuario());
//            pst.setString(4, edit.getEmail());
//            pst.setString(5, edit.getBiografia());
//            pst.setString(6, edit.getEstado());
//            pst.setString(7, edit.getSenha());
//            pst.setString(8, edit.getId());
            pst.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Os dados o Usuario << " + edit.getNome() + " de ID << " + edit.getId() + " >> foram atualizados com sucesso!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " Erro ao atualizar", "Notificação", WARNING_MESSAGE);
        } finally {
            fecharConexao();
        }
    }

    /**
     * ********* metodo responsavel pela remocao de usuarios
     *
     ******************
     * @param idUser
     */
    public void delete(String idUser) {
        conexao = Conectar.conector();
        String sql = "UPDATE `tbusuario` SET `theStatus`= 'Excluído' WHERE id=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, idUser);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "O Usuario foi excluído com sucesso!!");
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e + " Erro ao remover", "Notificação", JOptionPane.ERROR_MESSAGE);
        } finally {
            fecharConexao();
        }
    }

    public void reademitir(String idUser) {
        conexao = Conectar.conector();
        String sql = "UPDATE `tbusuario` SET`theStatus`='Autorizado' WHERE id=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, idUser);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "O Usuario foi readimitido com sucesso!!");
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e + " Erro ao remover", "Notificação", JOptionPane.ERROR_MESSAGE);
        } finally {
            fecharConexao();
        }
    }

    public void demitir(String idUser) {
        conexao = Conectar.conector();
        String sql = "UPDATE `tbusuario` SET`theStatus`='Não autorizado' WHERE id=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, idUser);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "O Usuario foi Demitido com sucesso!!");
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e + " Erro ao remover", "Notificação", JOptionPane.ERROR_MESSAGE);
        } finally {
            fecharConexao();
        }
    }

    public void Promover(String idUser, String novoPerfil) {
        conexao = Conectar.conector();
        String sql = "UPDATE tbusuario "
                + "SET id_acesso = (SELECT id_acesso FROM tbacesso WHERE perfil = ?) "
                + "WHERE id = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, novoPerfil);
            pst.setString(2, idUser);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "O perfil do usuário foi atualizado com sucesso!!");
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado ou perfil não existente",
                        "Notificação", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o perfil do usuário: " + e.getMessage(),
                    "Notificação", JOptionPane.ERROR_MESSAGE);
        } finally {
            fecharConexao();
        }
    }

    // ''''''''''''''''''''''''''' metodos nao usados '''''''''''''''''''''''
    public String gerarCodigo() {
        try {
            conexao = Conectar.conector();
            String querySql = "SELECT id FROM tbusuario WHERE id LIKE 'U%'";
            String ultimoId = "";

            pst = conexao.prepareStatement(querySql);
            rst = pst.executeQuery();

            // Extrai o último ID
            while (rst.next()) {
                ultimoId = rst.getString("id");
            }

            Random rand = new Random();
            // Gera três dígitos aleatórios
            int novoId = rand.nextInt(1000);

            // Formata o novo ID
            String codigo = String.format("U%03d", novoId);

            // Verifica se o código já existe na base de dados
            pst = conexao.prepareStatement("SELECT id FROM tbusuario WHERE id = ?");
            pst.setString(1, codigo);
            rst = pst.executeQuery();

            // Se o código já existir, chama o método recursivamente para gerar um novo código
            if (rst.next()) {
                return gerarCodigo();
            }

            return codigo;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " Erro ao gerar o código", "Notificação", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            fecharConexao();
        }
    }

    public List<Usuario> ListarUsuarios() {
        conexao = Conectar.conector();
        List<Usuario> Lista = new ArrayList<>();
        String query = "SELECT\n"
                + "    u.`id`, \n"
                + "    u.`nomeProprio`, \n"
                + "    u.`apelido`, \n"
                + "    u.`email`, \n"
                + "    u.`senha`, \n"
                + "    u.`theStatus`, \n"
                + "    CASE\n"
                + "        WHEN g.`thisSexo` = 'M' THEN 'Masculino'\n"
                + "        WHEN g.`thisSexo` = 'F' THEN 'Feminino'\n"
                + "        ELSE 'Indefinido'\n"
                + "    END AS `Sexo`, \n"
                + "    u.`id_sexo`, \n"
                + "    u.`id_acesso`, \n"
                + "    CASE\n"
                + "        WHEN a.`perfil` = 'Administrador' THEN 'Administrador'\n"
                + "        WHEN a.`perfil` = 'Gestor' THEN 'Gestor'\n"
                + "        WHEN a.`perfil` = 'Supervisor' THEN 'Supervisor'\n"
                + "        ELSE 'Outro'\n"
                + "    END AS `Perfil`\n"
                + "FROM \n"
                + "    `tbusuario` u\n"
                + "JOIN \n"
                + "    `tbgenero` g ON u.`id_sexo` = g.`id_sexo`\n"
                + "JOIN \n"
                + "    `tbacesso` a ON u.`id_acesso` = a.`id_acesso`\n"
                + "ORDER BY \n"
                + "    u.`nomeProprio`, \n"
                + "    u.`apelido` Asc";

        try {
            //SELECT `id`, `nomeProprio`, `apelido`, `email`, `senha`, `theStatus`, `thisSexo`, `perfil` FROM `tbusuario` WHERE 1
            pst = conexao.prepareStatement(query);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultado.getString("id"));
                usuario.setNomeProprio(resultado.getString("nomeProprio"));
                usuario.setApelido(resultado.getString("apelido"));
                usuario.setEmail(resultado.getString("email"));
                usuario.setSenha(resultado.getString("senha"));
                String statusString = resultado.getString("theStatus"); // ou qualquer outro valor válido
                Status status = Status.setStatus(statusString);
                usuario.setThisTstus(status);
                usuario.setGenero(resultado.getString("Sexo"));
                usuario.setSenha(resultado.getString("senha"));
                String tipoAcesso = resultado.getString("perfil"); // Obtido do resultado do banco de dados
                int id_acsso = resultado.getInt("id_acesso");
                Acesso acesso = new Acesso(id_acsso, tipoAcesso);
                usuario.setAcesso(acesso);
                Lista.add(usuario);

            }
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e + " Erro ao buscar o registro", "Notificação", JOptionPane.ERROR);
        } finally {
            fecharConexao();
        }
        return Lista;
    }

// FuncionarioDAO.java
    public void FindUser(String nomePesquisa, JTable tabela) {
        conexao = Conectar.conector();
        String query = "SELECT\n"
                + "    u.`id` AS `ID`,\n"
                + "    CONCAT(u.`nomeProprio`, ' ', u.`apelido`) AS `Nome`,\n"
                + "    CASE\n"
                + "        WHEN g.`thisSexo` = 'M' THEN 'Masculino'\n"
                + "        WHEN g.`thisSexo` = 'F' THEN 'Feminino'\n"
                + "        ELSE 'Indefinido'\n"
                + "    END AS `Sexo`,\n"
                + "    u.`email`,\n"
                + "    CASE\n"
                + "        WHEN a.`perfil` = 'Administrador' THEN 'Administrador'\n"
                + "        WHEN a.`perfil` = 'Gestor' THEN 'Gestor'\n"
                + "        WHEN a.`perfil` = 'Supervisor' THEN 'Supervisor'\n"
                + "        ELSE a.`perfil`\n"
                + "    END AS `Perfil`,\n"
                + "    u.`theStatus` AS `Status`\n"
                + "FROM\n"
                + "    `tbusuario` u\n"
                + "JOIN\n"
                + "    `tbgenero` g ON u.`id_sexo` = g.`id_sexo`\n"
                + "JOIN\n"
                + "    `tbacesso` a ON u.`id_acesso` = a.`id_acesso`\n"
                + "WHERE\n"
                + "    (u.`nomeProprio` LIKE ? OR u.`apelido` LIKE ? or a.`perfil` LIKE ?)\n"
                + "    AND u.`theStatus` != 'Excluído'\n"
                + "    AND a.`perfil` != 'Administrador'\n"
                + "ORDER BY\n"
                + "    u.`nomeProprio`,\n"
                + "    u.`apelido` Asc;";

        try {
            pst = conexao.prepareStatement(query);
            pst.setString(1, "%" + nomePesquisa + "%");
            pst.setString(2, "%" + nomePesquisa + "%");
            pst.setString(3, "%" + nomePesquisa + "%");
            rst = pst.executeQuery();
            tabela.setModel(DbUtils.resultSetToTableModel(rst));
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e.getMessage() + "  Registro nao Encontrado", "Notificação", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            fecharConexao();
        }
    }
    // Método para autenticar o usuário

    public Usuario autenticarUsuario(String email, String senha) {
        conexao = Conectar.conector();
        // Consulta para obter os dados do usuário
        String query = "SELECT u.id,CONCAT(u.nomeProprio, ' ', u.apelido) AS `nomeCompleto`, "
                + "u.email, u.senha, u.theStatus, "
                + "CASE "
                + "    WHEN g.thisSexo = 'M' THEN 'Masculino' "
                + "    WHEN g.thisSexo = 'F' THEN 'Feminino' "
                + "    ELSE 'Indefinido' "
                + "END AS `sexo`, "
                + "a.perfil, "
                + "a.id_acesso "
                + "FROM tbusuario u "
                + "JOIN tbacesso a ON u.id_acesso = a.id_acesso "
                + "JOIN tbgenero g ON u.id_sexo = g.id_sexo "
                + "WHERE u.email = ? AND u.senha = ?";
        try {
            pst = conexao.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, senha);
            rst = pst.executeQuery();
            if (rst.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rst.getString("id"));
                usuario.setNomeProprio(rst.getString("nomeCompleto"));
                usuario.setEmail(rst.getString("email"));
                usuario.setSenha(rst.getString("senha"));
                String statusString = rst.getString("theStatus");
                Status status = Status.setStatus(statusString);
                usuario.setThisTstus(status);
                // Verifique se "Sexo" está retornando corretamente
                String genero = rst.getString("Sexo");
                usuario.setGenero(genero);
                // Verifique se "perfil" está retornando corretamente
                // Verificando valores das coluna
                String tipoAcesso = rst.getString("perfil");
                int id_acsso = rst.getInt("id_acesso");
                Acesso acesso = new Acesso(id_acsso, tipoAcesso);
                usuario.setAcesso(acesso);
                return usuario;
            }

        } catch (SQLException ex) {
        } finally {
            fecharConexao();
        }

        return null;
    }

    private void fecharConexao() {
        try {
            if (pst != null) {
                pst.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
        }
    }

}
