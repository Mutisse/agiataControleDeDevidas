package ficha01.model;

import java.io.Serializable;

public class Usuario extends pessoa implements Serializable {

    private String email, senha;
    private Acesso acesso;
    private Status thisTstus;

    public Usuario(String email, String isdeleted, String senha, Acesso acesso, Status thisTstus, String id, String nomeProprio, String apelido, String genero) {
        super(id, nomeProprio, apelido, genero);
        this.email = email;
        this.senha = senha;
        this.acesso = acesso;
        this.thisTstus = thisTstus;
    }

    public Usuario() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    public Status getThisTstus() {
        return thisTstus;
    }

    public void setThisTstus(Status thisTstus) {
        this.thisTstus = thisTstus;
    }

    @Override
    public String toString() {
        return "Usuario{" + "email=" + email  + ", senha=" + senha + ", acesso=" + acesso + ", thisTstus=" + thisTstus + '}';
    }

}
