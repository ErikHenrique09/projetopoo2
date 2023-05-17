package application;

import jakarta.persistence.NoResultException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.DAO.FuncionarioDAO;
import modelo.DAO.PessoaDAO;
import modelo.RN.PessoaRN;
import modelo.VO.Funcionario;
import modelo.VO.Pessoa;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CadastroFuncionarioController implements Initializable {

    private App app; // Referência para a classe App

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private TextField inputNome;

    @FXML
    private TextField inputSobrenome;

    @FXML
    private TextField inputIdade;

    @FXML
    private TextField inputTelefone;

    @FXML
    private PasswordField inputSenha;

    @FXML
    private TextField inputEmail;

    @FXML
    private Label msgCadastro;

    @FXML
    protected void btnCadastrarFuncionario() throws Exception {
        msgCadastro.setText("");
        PessoaRN RN = new PessoaRN();
        Pessoa pessoa = new Pessoa();
        pessoa.setTest();

        try {

            /*pessoa.setNome(inputNome.getText());
            pessoa.setSobrenome(inputSobrenome.getText());
            pessoa.setIdade(Integer.parseInt(inputIdade.getText()));

            pessoa.setTel1("981589517");
                    //inputTelefone.getText());

            pessoa.setEmail(inputEmail.getText());
            pessoa.setSenha(inputSenha.getText());*/

            app.showSceneCadEnd(RN.validarCadastro(pessoa));

        }catch (NoResultException nre){

            System.out.println(nre.getMessage());

            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Preencha todos os campos");
            a.show();
        }catch(Exception e){

            System.out.println(e.getMessage());

            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Este email ja esta sendo utilizado");
            a.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }
}
