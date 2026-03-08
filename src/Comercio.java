import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Comercio {
    /** Para inclusão de novos produtos no vetor */
    static final int MAX_NOVOS_PRODUTOS = 10;

    /** Nome do arquivo de dados. O arquivo deve estar localizado na raiz do projeto */
    static String nomeArquivoDados;

    /** Scanner para leitura do teclado */
    static Scanner teclado;

    /** Vetor de produtos cadastrados. Sempre terá espaço para 10 novos produtos a cada execução */
    static Produto[] produtosCadastrados;

    /** Quantidade produtos cadastrados atualmente no vetor */
    static int quantosProdutos;

    /** Gera um efeito de pausa na CLI. Espera por um enter para continuar */
    static void pausa() {
        System.out.println("Digite enter para continuar...");
        teclado.nextLine();
    }

    /** Cabeçalho principal da CLI do sistema */
    static void cabecalho() {
        System.out.println("AEDII COMÉRCIO DE COISINHAS");
        System.out.println("===========================");
    }

    /**
     * Imprime o menu principal, lê a opção do usuário e a retorna (int).
     * @return Um inteiro com a opção do usuário.
     */
    static int menu() {
        cabecalho();
        System.out.println("1 - Listar todos os produtos");
        System.out.println("2 - Procurar e listar um produto");
        System.out.println("3 - Cadastrar novo produto");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        return Integer.parseInt(teclado.nextLine());
    }

    /**
     * Lê os dados de um arquivo texto e retorna um vetor de produtos. Arquivo no formato
     * N  (quantidade de produtos)
     * tipo; descrição;preçoDeCusto;margemDeLucro;[dataDeValidade]
     * Deve haver uma linha para cada um dos produtos. Retorna um vetor vazio em caso de problemas com o arquivo.
     * @param nomeArquivoDados Nome do arquivo de dados a ser aberto.
     * @return Um vetor com os produtos carregados, ou vazio em caso de problemas de leitura.
     */
    static Produto[] lerProdutos(String nomeArquivoDados) {
        Produto[] vetorProdutos = new Produto[0];
        try (Scanner arquivo = new Scanner(new File(nomeArquivoDados), Charset.forName("ISO-8859-2"))) {
            int n = Integer.parseInt(arquivo.nextLine().trim());
            vetorProdutos = new Produto[n + MAX_NOVOS_PRODUTOS];
            quantosProdutos = 0;
            for (int i = 0; i < n && arquivo.hasNextLine(); i++) {
                String linha = arquivo.nextLine();
                Produto p = Produto.criarDoTexto(linha);
                if (p != null) {
                    vetorProdutos[quantosProdutos++] = p;
                }
            }
        } catch (FileNotFoundException e) {
            quantosProdutos = 0;
            vetorProdutos = new Produto[MAX_NOVOS_PRODUTOS];
        } catch (Exception e) {
            quantosProdutos = 0;
            vetorProdutos = new Produto[MAX_NOVOS_PRODUTOS];
        }
        return vetorProdutos;
    }

    /** Lista todos os produtos cadastrados, numerados, um por linha */
    static void listarTodosOsProdutos() {
        cabecalho();
        System.out.println("\nPRODUTOS CADASTRADOS:");
        for (int i = 0; i < quantosProdutos; i++) {
            if (produtosCadastrados[i] != null) {
                System.out.println(String.format("%02d - %s", (i + 1), produtosCadastrados[i].toString()));
            }
        }
    }

    /**
     * Localiza um produto no vetor de cadastrados, a partir do nome, e imprime seus dados.
     * A busca não é sensível ao caso. Em caso de não encontrar o produto, imprime mensagem padrão.
     */
    static void localizarProdutos() {
        cabecalho();
        System.out.print("Digite a descrição do produto a procurar: ");
        String descricao = teclado.nextLine();
        Produto encontrado = null;
        for (int i = 0; i < quantosProdutos; i++) {
            if (produtosCadastrados[i] != null && produtosCadastrados[i].getDescricao().equalsIgnoreCase(descricao.trim())) {
                encontrado = produtosCadastrados[i];
                break;
            }
        }
        if (encontrado != null) {
            System.out.println("\nProduto encontrado: " + encontrado.toString());
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    /**
     * Rotina de cadastro de um novo produto: pergunta o tipo, lê os dados, cria o objeto e inclui no vetor.
     */
    static void cadastrarProduto() {
        cabecalho();
        System.out.println("\nCadastrar novo produto");
        System.out.println("1 - Produto não perecível");
        System.out.println("2 - Produto perecível");
        System.out.print("Tipo: ");
        int tipo = Integer.parseInt(teclado.nextLine());
        System.out.print("Descrição: ");
        String descricao = teclado.nextLine();
        System.out.print("Preço de custo: ");
        double precoCusto = Double.parseDouble(teclado.nextLine().replace(",", "."));
        System.out.print("Margem de lucro (ex: 0,30): ");
        double margemLucro = Double.parseDouble(teclado.nextLine().replace(",", "."));
        Produto novo = null;
        if (tipo == 1) {
            novo = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
        } else if (tipo == 2) {
            System.out.print("Data de validade (dd/mm/aaaa): ");
            String dataStr = teclado.nextLine().trim();
            LocalDate validade = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            novo = new ProdutoPerecivel(descricao, precoCusto, margemLucro, validade);
        }
        if (novo != null && quantosProdutos < produtosCadastrados.length) {
            produtosCadastrados[quantosProdutos++] = novo;
            System.out.println("Produto cadastrado com sucesso.");
        } else if (novo != null) {
            System.out.println("Vetor cheio. Não foi possível cadastrar.");
        }
    }

    /**
     * Salva os dados dos produtos cadastrados no arquivo csv informado. Sobrescreve todo o conteúdo do arquivo.
     * @param nomeArquivo Nome do arquivo a ser gravado.
     */
    public static void salvarProdutos(String nomeArquivo) {
        try (FileWriter fw = new FileWriter(nomeArquivo, Charset.forName("ISO-8859-2"))) {
            fw.write(quantosProdutos + "\n");
            for (int i = 0; i < quantosProdutos; i++) {
                if (produtosCadastrados[i] != null) {
                    fw.write(produtosCadastrados[i].gerarDadosTexto() + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        teclado = new Scanner(System.in, Charset.forName("ISO-8859-2"));
        nomeArquivoDados = "dadosProdutos.csv";
        produtosCadastrados = lerProdutos(nomeArquivoDados);
        int opcao = -1;
        do {
            opcao = menu();
            switch (opcao) {
                case 1 -> listarTodosOsProdutos();
                case 2 -> localizarProdutos();
                case 3 -> cadastrarProduto();
            }
            pausa();
        } while (opcao != 0);

        salvarProdutos(nomeArquivoDados);
        teclado.close();
    }
}
