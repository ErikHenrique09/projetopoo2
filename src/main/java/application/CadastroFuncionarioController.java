package application;

import jakarta.persistence.NoResultException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.DAO.PessoaDAO;
import modelo.RN.PessoaRN;
import modelo.VO.Pessoa;
import util.errors;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CadastroFuncionarioController implements Initializable {

    private String idPessoa = null;

    private Pessoa pessoa;
    private App app; // Referência para a classe App
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

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    protected void btnCadastrarFuncionario() {
        msgCadastro.setText("");
        PessoaDAO pesDAO;
        PessoaRN RN = new PessoaRN();

        String nome = null;
        String sobrenome = null;
        String email = null;
        String senha = null;
        Integer idade = null;
        String telefone = null;

        try {

            if (this.idPessoa != null) {
                pesDAO = new PessoaDAO();
                pessoa = pesDAO.find(Integer.parseInt(idPessoa));
            }

            if (!inputNome.getText().equals("") && this.idPessoa != null) {
                pessoa.setNome(inputNome.getText());
            } else if (!inputNome.getText().equals("") && this.idPessoa == null) {
                nome = inputNome.getText();
            }

            if (!inputSobrenome.getText().equals("") && this.idPessoa != null) {
                pessoa.setSobrenome(inputSobrenome.getText());
            } else if (!inputSobrenome.getText().equals("") && this.idPessoa == null) {
                sobrenome = inputSobrenome.getText();
            }

            if (!inputEmail.getText().equals("") && this.idPessoa != null) {
                pessoa.setEmail(inputEmail.getText());
            } else if (!inputEmail.getText().equals("") && this.idPessoa == null) {
                email = inputEmail.getText();
            }

            if (!inputSenha.getText().equals("") && this.idPessoa != null) {
                pessoa.setSenha(inputSenha.getText());
            } else if (!inputSenha.getText().equals("") && this.idPessoa == null) {
                senha = inputSenha.getText();
            }

            if (!inputIdade.getText().equals("") && this.idPessoa != null) {
                pessoa.setIdade(Integer.parseInt(inputIdade.getText()));
            } else if (!inputIdade.getText().equals("") && this.idPessoa == null) {
                idade = Integer.parseInt(inputIdade.getText());
            }

            if (!inputTelefone.getText().equals("") && this.idPessoa != null) {
                pessoa.setTel1(inputTelefone.getText());
                pessoa.setTel2(inputTelefone.getText());
            } else if (!inputTelefone.getText().equals("") && this.idPessoa == null) {
                telefone = inputTelefone.getText();
            }

            if (this.idPessoa != null) {
                RN.validarAlteracao(this.pessoa);
                Thread.sleep(3000);
                app.showSceneAdminFuncionarios();
                return;
            }

            pessoa = new Pessoa();

            pessoa.setNome(nome);
            pessoa.setSobrenome(sobrenome);
            pessoa.setEmail(email);
            pessoa.setSenha(senha);
            pessoa.setIdade(idade);
            pessoa.setTel1(telefone);
            pessoa.setTel2(telefone);
            pessoa.setAccess(false);

            app.showSceneCadEnd(RN.validarCadastro(pessoa));

        } catch (NoResultException nre) {
            System.out.println("NoResultException: " + nre.getMessage());
            errors.erroPreenchimento();
        } catch (NullPointerException | NumberFormatException npe) {
            System.out.println("Exception: " + npe.getMessage());
            errors.erroPreenchimento();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            if (e.getMessage().equals("emailExistente"))
                errors.erroEmail();
            else
                errors.erroPreenchimento();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    protected void btnHome() throws IOException {
        app.showSceneLogin();
    }

    @FXML
    protected void btnVoltar() throws IOException {
        app.voltar();
    }

    public void setIdPessoa(String idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
