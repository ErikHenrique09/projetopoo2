package modelo.RN;

import modelo.DAO.FuncionarioDAO;
import modelo.DAO.PessoaDAO;
import modelo.VO.Funcionario;
import modelo.VO.Funcoes;
import modelo.VO.Pessoa;
import org.jetbrains.annotations.NotNull;


public class PessoaRN {


    public Pessoa validarCadastro(@NotNull Pessoa pessoa) throws Exception {
        PessoaDAO pesDAO = new PessoaDAO();

        // Verifica se ja tem alguem com o mesmo email
        if (!pesDAO.validaEmail(pessoa.getEmail()))
            throw new Exception("emailExistente");

        pesDAO.save(pessoa);

        Funcionario func = new Funcionario();
        FuncionarioDAO funcDAO = new FuncionarioDAO();

        func.setPessoa(pesDAO.find(Math.toIntExact(pessoa.getIdPessoa())));
        func.setFunc(Funcoes.ATENDIMENTO);
        funcDAO.save(func);

        return pesDAO.find(Math.toIntExact(pessoa.getIdPessoa()));

    }

    public Pessoa validarAlteracao(@NotNull Pessoa pessoa) throws Exception {
        PessoaDAO pesDAO = new PessoaDAO();

        // Verifica se ja tem alguem com o mesmo email
        if (!pesDAO.validaUpdateEmail(pessoa)) {
            throw new Exception("emailExistente");
        } else {
            System.out.println("Cadastro realizado com sucesso!");
        }

        pesDAO.update(pessoa);

        return pesDAO.findByEmailSenha(pessoa.getEmail(), pessoa.getSenha());

    }

}
