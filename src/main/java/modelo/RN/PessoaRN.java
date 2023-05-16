package modelo.RN;

import jakarta.persistence.NoResultException;
import modelo.DAO.PessoaDAO;
import modelo.VO.Pessoa;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class PessoaRN {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@gmail\\.com$";

    private boolean validaEmail(@NotNull String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void validarCadastro(@NotNull Pessoa pessoa) throws Exception {
        PessoaDAO pesDAO = new PessoaDAO();

        if (pessoa.getEmail().isEmpty()){
            throw new Exception("Precisa criar um email");
        }

        if (!validaEmail(pessoa.getEmail())){
            throw new Exception("Email invalido!");
        }

        if (pessoa.getEmail().isEmpty()){
            throw new Exception("Bandido cadastrando sem senha");
        }

        // Verifica se ja tem alguem com o mesmo email
        if (pesDAO.validaEmail(pessoa.getEmail())){
            throw new Exception("Este email ja esta sendo utilizado");
        }else{
            System.out.println("Cadastro realizado com sucesso!");
        }
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
