package modelo.RN;

import modelo.VO.Pedido;

public class PedidoRN {

    public void validarPedido(Pedido pedido) throws Exception {
        // Verifica se o pedido possui pelo menos um item
        if (pedido.getItensPedido().isEmpty()) {
            throw new Exception("O pedido deve conter pelo menos um item.");
        }

        // Verifica se o valor total do pedido é maior que zero
        //if (pedido.getValorTotal() <= 0) {
        //    throw new Exception("O valor total do pedido deve ser maior que zero.");
        //}

        // Verifica se o cliente do pedido é válido
        if (pedido.getFuncionario() == null || pedido.getFuncionario() == null) {
            throw new Exception("O funcionario que fez o pedido não pode ser nulo ou vazio.");
        }

        // Outras validações específicas do negócio poderiam ser adicionadas aqui
    }
}

