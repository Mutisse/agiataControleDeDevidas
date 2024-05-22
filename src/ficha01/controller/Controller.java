package ficha01.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Controller {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "mySecretKey12345";
    // Número máximo de tentativas
    // Chave secreta para a criptografia

    public boolean camposNaoPreenchidos(String nome, String apelido, String email, String senha, String confirmarSenha) {
        return nome.isEmpty() || apelido.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty();
    }

    public boolean senhaIgualConfirmarSenha(String senha, String confirmarSenha) {
        return senha.equals(confirmarSenha);
    }

    public  boolean validarEmail(String email) {
        // Expressão regular para validar endereços de e-mail
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validarNome(String nome) {
        // Expressão regular para validar se o nome contém apenas letras
        String regex = "^[a-zA-Z]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nome);
        return matcher.matches();
    }

    public static boolean validarSenha(String senha) {
        // Verificar o comprimento da senha
        if (senha.length() < 6 || senha.length() > 16) {
            return false;
        }
        // Expressão regular para verificar se a senha contém apenas caracteres alfanuméricos
        String regex = "^[a-zA-Z0-9]+$";

        return senha.matches(regex);
    }

    // Método para criptografar uma senha
    public static String encryptPassword(String password) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            return null;
        }
    }

    // Método para descriptografar uma senha
    public static String decryptPassword(String encryptedPassword) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            return null;
        }
    }

  /*  public Controller() {
        this.tentativaAtual = 0;
        // Criar uma instância de UsuarioDao
        UsuarioDao dao = new UsuarioDao();
        // Em seguida, usar a instância de UsuarioDao para criar UsuarioDAO
        this.usuarioDao = dao.new UsuarioDAO();
    }

    public boolean autenticar(String email, String senha) {
        if (tentativaAtual < maximoTentativas) {
            // Implementar a autenticação do usuário aqui
            Usuario usuario = usuarioDao.autenticarUsuario(email, senha); // Chama o método para autenticar o usuário
            if (usuario != null) {
                abrirTela(usuario); // Chama o método para abrir a tela principal
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Combinação de credenciais incorreta.", "Notificação", JOptionPane.INFORMATION_MESSAGE);
                tentativaAtual++;
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tentativas Esgotadas!!\nUsuário Bloqueado", "Notificação", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }*/

}
