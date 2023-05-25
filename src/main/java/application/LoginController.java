package application;

import jakarta.persistence.NoResultException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.DAO.FuncionarioDAO;
import modelo.DAO.PessoaDAO;
import modelo.VO.Pessoa;

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
        FuncionarioDAO funcDAO = new FuncionarioDAO();
        String email = inputEmail.getText();
        String senha = inputSenha.getText();

        try {
            Pessoa pessoa = pesDAO.findByEmailSenha(email, senha);
            app.setIdUser(pessoa.getIdPessoa());
            msglogin.setText("Login Efetuado");

            switch (pesDAO.getFuncaoAtual(pessoa.getIdPessoa())){
                case "0":
                    app.showSceneAdminFuncionarios();
                    break;
                case "1":
                    app.showScenePedidos();
                    break;
                default:
                    app.showSceneCozinha();
                    break;
            }

        } catch (NoResultException nre) {
            msglogin.setText("Acesso bloqueado!");
        }
    }

    @FXML
    protected void btnCadastrarFuncionario() throws Exception {
        app.showSceneCadFunc(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

}