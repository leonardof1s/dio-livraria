package one.digitalinovation.laboojava.console;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.*;
import one.digitalinovation.laboojava.negocio.ClienteNegocio;
import one.digitalinovation.laboojava.negocio.PedidoNegocio;
import one.digitalinovation.laboojava.negocio.ProdutoNegocio;
import one.digitalinovation.laboojava.utilidade.LeitoraDados;

import java.util.Optional;

/**
 * Classe responsável por controlar a execução da aplicação.
 * 
 * @author leonardo ferreira
 * 
 */
public class Start {

    private static Optional<Cliente> clienteLogado = null;

    public static Optional<Cliente> getClienteLogado() {
        return clienteLogado;
    }

    private static String consultaLivroPorNome;
    private static String consultaCadernoPorNome;

    private static Banco banco = new Banco();

    private static ClienteNegocio clienteNegocio = new ClienteNegocio(banco);
    private static PedidoNegocio pedidoNegocio = new PedidoNegocio(banco);
    private static ProdutoNegocio produtoNegocio = new ProdutoNegocio(banco);
    private static Cliente cliente = new Cliente();
    private static String cpf = "";
    private static String inicioSistema = "";

    /**
     * Método utilitário para inicializar a aplicação.
     * 
     * @param args Parâmetros que podem ser passados para auxiliar na execução.
     */
    public static void main(String[] args) {

        System.out.println("Bem vindo ao e-Compras");

        String opcao = "";

        while (true) {

            if (clienteLogado == null) {

                System.out.println("Digite o cpf:");

                String cpf = "";
                cpf = LeitoraDados.lerDado();

                identificarUsuario(cpf);
            }

            System.out.println("Selecione uma opção:");

            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Excluir cliente");
            System.out.println("3 - Listar cliente");

            System.out.println("4 - Cadastrar Livro");
            System.out.println("5 - Excluir Livro");
            // TODO Desafio: Consultar Livro(nome)

            System.out.println("6 - Cadastrar Caderno");
            System.out.println("7 - Excluir Caderno");
            System.out.println("8 - Buscar livro pelo nome ");
            System.out.println("9 - Consultar caderno por materia :");
            // TODO Desafio: Consultar Caderno(matéria)
            System.out.println("10 - Fazer pedido");
            System.out.println("11 - Excluir pedido");
            // TODO Desafio: Consultar Pedido(código)
            System.out.println("12 - Listar produtos");
            System.out.println("13 - Listar pedidos");
            System.out.println("14 - Deslogar");
            System.out.println("15 - Sair");

            opcao = LeitoraDados.lerDado();

            switch (opcao) {
                case "1":
                    System.out.println("1 - Cadastrar Cliente");
                    cliente = LeitoraDados.lerCliente();
                    verificaAntesdeAdicionarCliente(cliente);
                    break;
                case "2":
                    System.out.println("2 - Excluir Cliente");
                    String cpf = LeitoraDados.lerDado();
                    clienteNegocio.excluirCliente(cpf);
                    break;
                case "3":
                    System.out.println("Clientes Cadastrados");
                    clienteNegocio.listarClientes();
                    break;
                case "4":
                    System.out.println("Cadastrar livro");
                    Livro livro = LeitoraDados.lerLivro();
                    produtoNegocio.salvar(livro);
                    break;
                case "5":
                    System.out.println("Digite o código do livro");
                    String codigoLivro = LeitoraDados.lerDado();
                    produtoNegocio.excluir(codigoLivro);
                    break;
                case "6":
                    Caderno caderno = LeitoraDados.lerCaderno();
                    produtoNegocio.salvar(caderno);
                    break;
                case "7":
                    System.out.println("Digite o codigo do caderno");
                    String codigoCaderno = LeitoraDados.lerDado();
                    produtoNegocio.excluir(codigoCaderno);
                    break;
                case "8":

                    System.out.println("10 -Consultar Livro por Nome");
                    consultaLivroPorNome = LeitoraDados.lerDado();
                    exibeConsultaLivroPorNome();
                    break;
                case "9":
                    System.out.println("Consultar caderno por materia ");
                    consultaCadernoPorNome = LeitoraDados.lerDado();
                    exibeConsultaCadernoPorMateria();
                    break;
                case "10":
                    Pedido pedido = LeitoraDados.lerPedido(banco);
                    Optional<Cupom> cupom = LeitoraDados.lerCupom(banco);

                    if (cupom.isPresent()) {
                        pedidoNegocio.salvar(pedido, cupom.get(), clienteLogado);
                    } else {
                        pedidoNegocio.salvar(pedido, clienteLogado);
                    }
                    break;
                case "11":
                    System.out.println("Digite o código do pedido");
                    String codigoPedido = LeitoraDados.lerDado();
                    pedidoNegocio.excluir(codigoPedido);
                    break;
                case "12":
                    produtoNegocio.listarTodos();
                    break;
                case "13":
                    pedidoNegocio.listarTodos();
                    break;
                case "14":
                    System.out.println("LOGOF");
                    System.out.println(String.format("Volte sempre %s!", clienteLogado.get().getNome()));
                    boasVindas();
                    break;
                case "15":
                    System.out.println("Aplicação encerrada.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    /**
     * Procura o usuário na base de dados.
     * 
     * @param cpf CPF do usuário que deseja logar na aplicação
     */

    private static void identificarUsuario(String cpf) {

        Optional<Cliente> cliente = clienteNegocio.consultarCliente(cpf);

        cliente.ifPresentOrElse((c) -> {
            System.out.println(String.format("Olá %s! Você está logado.", cliente.get().getNome()));
            clienteLogado = cliente;

        }, () -> {
            System.out.println("Cliente não cadastrado.Criar cadastro");
            System.out.println("1 - Cadastrar Cliente");
            Cliente clienteCad = LeitoraDados.lerCliente();
            verificaAntesdeAdicionarCliente(clienteCad);
        });

    }

    private static void exibeConsultaLivroPorNome() {

        Optional<Produto> livro = produtoNegocio.consultarLivroPorNome(consultaLivroPorNome);
        livro.ifPresentOrElse((l) -> {
            System.out.println(livro);
        }, () -> {
            System.out.println("Livro não Localizado");
        });

    }

    private static void exibeConsultaCadernoPorMateria() {

        Optional<Produto> caderno = produtoNegocio.consultarCadernoPorMateria(consultaCadernoPorNome);
        caderno.ifPresentOrElse((l) -> {
            System.out.println(caderno);
        }, () -> {
            System.out.println("Livro não Localizado");
        });

    }

    private static void verificaAntesdeAdicionarCliente(Cliente cliente) {

        Optional<Cliente> clienteResultado = clienteNegocio.consultarCliente(cliente.getCpf());
        clienteResultado.ifPresentOrElse((c) -> {
            System.out.println("Cliente já Cadastrado!");
        }, () -> {
            clienteNegocio.adicionarCliente(cliente);
            clienteLogado = Optional.ofNullable(cliente);
        });
    }

    private static void boasVindas() {
        System.out.println("Bem vindo ao e-Compras");
        System.out.println("Já e Cliente S/N ?");
        inicioSistema = LeitoraDados.lerDado();

        switch (inicioSistema) {

            case "S": {
                System.out.println("Digite o cpf:");
                cpf = LeitoraDados.lerDado();
                identificarUsuario(cpf);
                System.out.println(String.format("Olá %s! Você está logado.", clienteLogado.get().getNome()));
                break;
            }
            case "N": {
                System.out.println("Castro de Cliente");
                Cliente cliente = LeitoraDados.lerCliente();
                verificaAntesdeAdicionarCliente(cliente);
                identificarUsuario(cliente.getCpf());
                break;
            }
        }
    }

}
