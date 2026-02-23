import java.time.LocalDate;

public class ProdutoPerecivel extends Produto{
    private static final double DESCONTO = 0.25;
    private static final int PRAZO_DESCONTO = 7;
    private LocalDate dataDeValidade;

    public ProdutoPerecivel (String desc, double precoCusto, double margemLucro, LocalDate validade) {

        super(desc, precoCusto, margemLucro);

        if (validade.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Data de validade não pode ser anterior a hoje.");
        }
        this.dataDeValidade = validade;
    }

    @Override
    public double valorDeVenda(){
        LocalDate hoje = LocalDate.now();

        // Verifica se está vencido
        if (dataDeValidade.isBefore(hoje)){
            throw new IllegalStateException("Produto vencido! Venda não permitida");
        }

        // Calcular valor base
        double valorBase = super.valorDeVenda();

        // Verifica se está no prazo de desconto (7 dias ou menos)
        if (!dataDeValidade.isAfter(hoje.plusDays(PRAZO_DESCONTO))){
            return valorBase * (1- DESCONTO);
        }
        return valorBase;
    }

    @Override
    public String toString() {
        String infoBase = super.toString();

        // Data formatada (dd/mm/aaaa)
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // retorna o texto da mãe (Produto) com a validade
        return String.format("%s (Validade: %s)", infoBase, dataDeValidade.format(fmt));
    }
}
