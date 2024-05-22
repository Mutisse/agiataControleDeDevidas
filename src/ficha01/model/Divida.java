package ficha01.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Divida implements Serializable, Comparable<Divida> {

    private String id_divida;
    private BigDecimal valorDivida;
    private BigDecimal valorAPagar;
    private BigDecimal remanescente;
    private Timestamp data;
    private Cliente cliente;
    private Status estadoDivida;
    private boolean isdeleted;

    // Construtor e métodos getter e setter
    public Divida(String id_divida, BigDecimal valorDivida, BigDecimal valorAPagar, BigDecimal remanescente, Timestamp data, Cliente cliente, Status estadoDivida, boolean isdeleted) {
        this.id_divida = id_divida;
        this.valorDivida = valorDivida;
        this.valorAPagar = valorAPagar;
        this.remanescente = remanescente;
        this.data = data;
        this.cliente = cliente;
        this.estadoDivida = estadoDivida;
        this.isdeleted = isdeleted;
    }

    public Divida() {
    }

    // Método para calcular o remanescente 
    // tornar esse metod dinaminco fazer trocar valor quanestiver a 
//    public BigDecimal calcularRemanescente(BigDecimal valorAPagar) {
//        BigDecimal valorPago = BigDecimal.ZERO; // Inicialize com zero
//
//        if (remanescente.compareTo(BigDecimal.ZERO) >= 0) {
//            valorPago = valorDivida.subtract(valorAPagar);
//        }
//        return valorPago;
//    }


    // Método para atualizar o estado da dívida
    public void atualizarEstadoDivida() {
        if (valorAPagar.equals(BigDecimal.ZERO) && remanescente.equals(BigDecimal.ZERO)) {
            estadoDivida = Status.LIQUIDADA;
        } else if (!valorAPagar.equals(valorDivida)) {
            estadoDivida = Status.PARCIAL;
        } else if (valorDivida.equals(remanescente) && remanescente.equals(BigDecimal.ZERO)) {
            estadoDivida = Status.LIQUIDADA;
        }
    }

    // Método para fazer um pagamento
    public void fazerPagamento(BigDecimal valorPagamento) {
        valorAPagar = valorAPagar.subtract(valorPagamento);
//        remanescente = calcularRemanescente();
        atualizarEstadoDivida();
    }

    public float calcularValor(Float valorDivida) {
        float valor2;
        if (valorDivida <= 100.00) {
            valor2 = (float) (valorDivida + valorDivida * 0.4);

        } else if (valorDivida <= 500.00) {
            valor2 = (float) (valorDivida + valorDivida * 0.3);

        } else if (valorDivida <= 1000.00) {
            valor2 = (float) (valorDivida + valorDivida * 0.2);

        } else {
            valor2 = (float) (valorDivida + valorDivida * 0.15);

        }
        return valor2;
    }

    // Outros métodos getter e setter
    public String getId_divida() {
        return id_divida;
    }

    public void setId_divida(String id_divida) {
        this.id_divida = id_divida;
    }

    public BigDecimal getValorDivida() {
        return valorDivida;
    }

    public void setValorDivida(BigDecimal valorDivida) {
        this.valorDivida = valorDivida;
    }

    public BigDecimal getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(BigDecimal valorAPagar) {
        this.valorAPagar = valorAPagar;
    }

    public BigDecimal getRemanescente() {
        return remanescente;
    }

    public void setRemanescente(BigDecimal remanescente) {
        this.remanescente = remanescente;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Status getEstadoDivida() {
        return estadoDivida;
    }

    public void setEstadoDivida(Status estadoDivida) {
        this.estadoDivida = estadoDivida;
    }

    public boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    @Override
    public int compareTo(Divida o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
