package ficha01.model;

public enum Status {
    LIQUIDADA,
    PARCIAL,
    PENDENTE,
    AUTORIZADO,
    NAO_AUTORIZADO,
    EXCLUIDO;

    // Getter
    public String getStatus() {
        return this.name(); // Retorna o nome do enum (String)
    }

    // Setter
    public static Status setStatus(String status) {
        // Convertendo a primeira letra para maiúscula e o restante da string para minúsculas
        status = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        switch (status) {
            case "Liquidada":
                return LIQUIDADA;
            case "Parcial":
                return PARCIAL;
            case "Pendente":
                return PENDENTE;
            case "Autorizado":
                return AUTORIZADO;
            case "Não autorizado":
                return NAO_AUTORIZADO;
            case "Excluído":
                return EXCLUIDO;
            default:
                // Tratar caso o status seja inválido
                throw new IllegalArgumentException("Status inválido: " + status);
        }
    }

    // Método para retornar o status formatado
    public String getStatusFormatado() {
        // Converte a primeira letra para maiúscula e o restante para minúsculas
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
}
