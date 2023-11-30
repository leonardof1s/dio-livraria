package one.digitalinovation.laboojava.negocio;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.Cliente;

import java.util.Optional;

/**
 * Classe para manipular a entidade {@link Cliente}.
 * 
 * @author thiago leite
 */
public class ClienteNegocio {

    /**
     * {@inheritDoc}.
     */
    private static Banco bancoDados;

    /**
     * Construtor.
     * 
     * @param banco Banco de dados para ter acesso aos clientes cadastrados
     */
    public ClienteNegocio(Banco banco) {
        ClienteNegocio.bancoDados = banco;
    }

    /**
     * Consulta o cliente pelo seu CPF.
     * 
     * @param cpf CPF de um cliente
     * @return O cliente que possuir o CPF passado.
     */
    public Optional<Cliente> consultarCliente(String cpf) {
        for (Cliente cliente : bancoDados.getCliente()) {
            if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                return Optional.ofNullable(cliente);
            }
        }
        return Optional.empty();
    }

    /**
     * Cadastra um novo cliente.
     * 
     * @param cliente Novo cliente que terá acesso a aplicação
     */

    public void adicionarCliente(Cliente cliente) {
        bancoDados.adicionarCliente(cliente);
    }

    /**
     * Exclui um cliente específico.
     * 
     * @param cpf CPF do cliente
     */
    public void excluirCliente(String cpf) {
        Optional<Cliente> excluirCliente = consultarCliente(cpf);
        excluirCliente.ifPresentOrElse((c) -> {
            bancoDados.removerCliente(excluirCliente.get());
            System.out.println("Cliente excluido com sucesso");
        }, () -> {
            System.out.println("Cliente não encontrado");
        });
    }

    public void listarClientes() {

        if (bancoDados.getCliente().length == 0) {
            System.out.println("Não existem clientes Cadastrados");
        } else {

            for (Cliente cliente : bancoDados.getCliente()) {
                System.out.println(cliente.toString());
            }
        }
    }
}
