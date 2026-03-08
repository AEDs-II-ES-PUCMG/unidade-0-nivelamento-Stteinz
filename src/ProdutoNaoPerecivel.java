public class ProdutoNaoPerecivel extends Produto {

    public ProdutoNaoPerecivel(String desc, double precoCusto, double margemLucro) {
        super(desc, precoCusto, margemLucro);
    }

    public ProdutoNaoPerecivel(String desc, double precoCusto) {
        super(desc, precoCusto);
    }

    /**
     * Gera uma linha de texto a partir dos dados do produto. Preço e margem de lucro formatados com 2 casas decimais.
     * @return Uma string no formato "1; descrição;preçoDeCusto;margemDeLucro"
     */
    @Override
    public String gerarDadosTexto() {
        return String.format("1;%s;%.2f;%.2f", getDescricao(), precoCusto, margemLucro);
    }
}