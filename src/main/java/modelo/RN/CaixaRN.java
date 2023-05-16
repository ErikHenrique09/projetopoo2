package modelo.RN;

import modelo.DAO.CaixaDAO;
import modelo.VO.Caixa;


public class CaixaRN {
    public void validarCaixa(Caixa caixa) throws Exception {
        CaixaDAO caixaDAO = new CaixaDAO();

        if(!caixaDAO.validaCaixa(caixa)){
            throw new Exception("O caixa de hoje ja esta criado");
        }

    }
}
