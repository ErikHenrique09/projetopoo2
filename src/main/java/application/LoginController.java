package application;

import jakarta.persistence.NoResultException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.DAO.PessoaDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private App app; // Referência para a classe App
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

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    protected void btnEntrar() throws Exception {
        PessoaDAO pesDAO = new PessoaDAO();

        String email = inputEmail.getText();
        String senha = inputSenha.getText();

        try {

            msglogin.setText("");

            app.setIdUser(pesDAO.findByEmailSenha(email, senha).getIdPessoa());

            System.out.println(email + " : " + senha);

            msglogin.setText("Login Efetuado");

            app.showSceneAdminFuncionarios();

        } catch (NoResultException nre) {
            msglogin.setText("Usuario não encontrado");
        }
    }

    @FXML
    protected void btnCadastrarFuncionario() throws Exception {
        System.out.println("Deu errado");
        app.showSceneCadFunc(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

}