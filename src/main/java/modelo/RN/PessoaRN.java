package modelo.RN;

import jakarta.persistence.NoResultException;
import modelo.DAO.PessoaDAO;
import modelo.VO.Pessoa;
import modelo.DAO.FuncionarioDAO;
import modelo.VO.Funcionario;
import org.jetbrains.annotations.NotNull;


public class PessoaRN {

    //private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@gmail\\.com$";

    /*private boolean validaEmail(@NotNull String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }*/



    public Pessoa validarCadastro(@NotNull Pessoa pessoa) throws Exception {
        PessoaDAO pesDAO = new PessoaDAO();
        //pessoa.setTest();
        if (pessoa.getEmail().isEmpty()){
            throw new Exception("Precisa criar um email");
        }

        //validar a estreutura de email
        /*if (!validaEmail(pessoa.getEmail())){
            throw new Exception("Email invalido!");
        }*/

        if (pessoa.getSenha().isEmpty()){
            throw new Exception("Bandido cadastrando sem senha");
        }

        // Verifica se ja tem alguem com o mesmo email
        /*if (!pesDAO.validaEmail(pessoa.getEmail())){
            throw new Exception("emailExistente");
        }else{
            System.out.println("Cadastro realizado com sucesso!");
        }*/

        pesDAO.save(pessoa);

        Funcionario func = new Funcionario();
        FuncionarioDAO funcDAO = new FuncionarioDAO();

        func.setPessoa(pessoa);
        funcDAO.save(func);

        return pesDAO.findByEmailSenha(pessoa.getEmail(), pessoa.getSenha());

    }

    public void login(@NotNull String email, @NotNull String senha) throws Exception {
        PessoaDAO pesDAO = new PessoaDAO();
        try{
           pesDAO.findByEmailSenha(email,senha);
        }catch (NoResultException nre){
           throw new Exception("Usuario ou senha incorretos");
        }
    }

}
