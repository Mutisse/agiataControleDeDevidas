/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha01.model;

/**
 *
 * @author i'mutisse
 */
public class Acesso {

    private int id_Acesso;
    private String tipo_Acesso;
    // Construtor, getters e setters

    public Acesso() {
    }

    public Acesso(int id_Acesso, String tipo_Acesso) {
        this.id_Acesso = id_Acesso;
        this.tipo_Acesso = tipo_Acesso;
    }

    public void setId_Acesso(int id_Acesso) {
        this.id_Acesso = id_Acesso;
    }

    public int getId_Acesso() {
        return id_Acesso;
    }

    public String getTipo_Acesso() {
        return tipo_Acesso;
    }

    public void setTipo_Acesso(String tipo_Acesso) {
        this.tipo_Acesso = tipo_Acesso;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return tipo_Acesso;
    }

    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
