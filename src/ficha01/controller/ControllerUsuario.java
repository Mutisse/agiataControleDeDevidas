/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha01.controller;

import ficha01.model.Dao.UsuarioDao;
import ficha01.model.Status;
import ficha01.model.Usuario;
import ficha01.view.TelaMenu;
import javax.swing.JOptionPane;

/**
 * u
 *
 * @author i'mutisse
 */
public class ControllerUsuario {

    UsuarioDao usuariodao = new UsuarioDao();
    private int tentativaAtual = 0;
    private final int maximoTentativas = 3;

    public void isDeleted(String idUser, String nome, String apelido) {
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem a certeza  que deseja remover esse Usuário? \n" + nome + " " + apelido, "Notificação", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            try {

                usuariodao.delete(idUser);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada!!");
        }
    }

 
    public void isAdmit(String idUser, String nome, String apelido) {
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem a certeza  que Pretende Readmitir este Usuário? \n" + nome + " " + apelido, "Notificação", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            try {

                usuariodao.reademitir(idUser);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada!!");
        }
    }

    /**
     *
     * @param idUser
     * @param nome
     * @param apelido
     */
    public void isDismiss(String idUser, String nome, String apelido) {
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem a certeza  que deseja Demitir este Usuário? \n" + nome + " " + apelido, "Notificação", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            try {

                usuariodao.demitir(idUser);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada!!");
        }
    }

    public void ispromote(String idUser, String nome, String apelido, String perfil) {
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem a certeza  que deseja Promover? \n" + nome + " " + apelido, "Notificação", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                usuariodao.Promover(idUser, perfil);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada!!");
        }
    }

    public void abrirTela(String username, String password) {
        Usuario usuario = usuariodao.autenticarUsuario(username, password);

        if (usuario != null) {
            // Verifica se as credenciais são válidas

            if (usuario.getEmail().equals(username) && usuario.getSenha().equals(password)) {
                Status status = usuario.getThisTstus();

                // Verifica o status do usuário
                if ("AUTORIZADO".equalsIgnoreCase(status.getStatus())) {
                    int idAcesso = usuario.getAcesso().getId_Acesso();
                    switch (idAcesso) {
                        case 1 -> {
                            // Lógica para usuário com acesso de nível 1
                            new TelaMenu(usuario).setVisible(true);
                            TelaMenu.jMenu1.setVisible(false);
                            TelaMenu.jMenuItem2.setVisible(false);
                        }
                        case 2 -> {// Lógica para usuário com acesso de nível 2
                            new TelaMenu(usuario).setVisible(true);
                            TelaMenu.jMenuItem4.setVisible(false);
                            TelaMenu.jMenuItem5.setVisible(false);
                        }
                        case 3 -> {// Lógica para usuário com acesso de nível 3
                            new TelaMenu(usuario).setVisible(true);
                            TelaMenu.jMenu2.setVisible(false);
                            TelaMenu.jMenu3.setVisible(false);
                        }
                        default ->
                            JOptionPane.showMessageDialog(null, "Tipo de acesso não reconhecido!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if ("NAO_AUTORIZADO".equalsIgnoreCase(status.getStatus())) {
                    // Lógica para usuário não autorizado
                    JOptionPane.showMessageDialog(null, "Usuário Não Autorizado!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                } else if ("EXCLUIDO".equalsIgnoreCase(status.getStatus())) {
                    // Lógica para usuário excluído
                    JOptionPane.showMessageDialog(null, "Usuário não Existente!", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Perfil do usuário não reconhecido!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Combinação de credenciais incorreta.", "Notificação", JOptionPane.INFORMATION_MESSAGE);
                tentativaAtual++;
                if (tentativaAtual >= maximoTentativas) {
                    JOptionPane.showMessageDialog(null, "Tentativas Esgotadas!!\nUsuário Bloqueado", "Notificação", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Combinação de credenciais incorreta.", "Notificação", JOptionPane.INFORMATION_MESSAGE);
            tentativaAtual++;
            if (tentativaAtual >= maximoTentativas) {
                JOptionPane.showMessageDialog(null, "Tentativas Esgotadas!!\nUsuário Bloqueado", "Notificação", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

}
