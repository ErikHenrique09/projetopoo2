package application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.DAO.PessoaDAO;
import modelo.RN.PessoaRN;
import modelo.VO.Pessoa;
import util.ConexaoHibernate;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private App app; // Referência para a classe App

    public void setApp(App app) {
        this.app = app;
    }

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
    protected void btnEntrar() throws Exception {
        PessoaDAO pesDAO = new PessoaDAO();

        String email = inputEmail.getText();
        String senha = inputSenha.getText();

        try {
            msglogin.setText("");

            pesDAO.findByEmailSenha(email, senha);
            System.out.println(email + " : " + senha);

            msglogin.setText("Login Efetuado");


            app.showSceneAdminFuncionarios();

        }catch (NoResultException nre){
            msglogin.setText("Usuario não encontrado");
        }
    }

    @FXML
    protected void btnCadastrarFuncionario() throws Exception {
        System.out.println("Deu errado");
        app.showSceneCadFunc();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //TODO
    }

}