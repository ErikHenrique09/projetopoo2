package application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.DAO.PessoaDAO;
import modelo.RN.PessoaRN;
import modelo.VO.Pessoa;
import util.ConexaoHibernate;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    //PessoaRN pessoaRN = new PessoaRN();

    EntityManager entityManager;
    @FXML
    private PasswordField inputSenha;
    @FXML
    private TextField inputEmail;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelSenha;

    @FXML
    private Label msglogin;
    @FXML
    protected void onHelloButtonClick() throws Exception {
        PessoaDAO pesDAO = new PessoaDAO();

        String email = inputEmail.getText();
        String senha = inputSenha.getText();

        try{
            pesDAO.findByEmailSenha(email,senha);
            System.out.println(email +" : "+ senha);
        }catch (NoResultException nre){
            msglogin.setText("Usuario não encontrado");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //TODO
    }

}