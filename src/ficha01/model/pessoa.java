/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha01.model;

/**
 *
 * @author i'mutisse
 */
public abstract class pessoa {

    private String id, nomeProprio, apelido, genero;

    public pessoa(String id, String nomeProprio, String apelido, String genero) {
        this.id = id;
        this.nomeProprio = nomeProprio;
        this.apelido = apelido;
        this.genero = genero;
    }

    public pessoa() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeProprio() {
        return nomeProprio;
    }

    public void setNomeProprio(String nomeProprio) {
        this.nomeProprio = nomeProprio;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "pessoa{" + "id=" + id + ", nomeProprio=" + nomeProprio + ", apelido=" + apelido + ", genero=" + genero + '}';
    }

}
