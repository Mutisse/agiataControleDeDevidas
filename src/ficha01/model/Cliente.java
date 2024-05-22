/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha01.model;

/**
 *
 * @author i'mutisse
 */
public class Cliente extends pessoa {

    private String contacto;
    private Usuario usuario;

    public Cliente() {
    }

    @Override
    public String toString() {
        return "Cliente{" + "contacto=" + contacto + ", usuario=" + usuario + '}';
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setGenero(char genero) {
    }

}
