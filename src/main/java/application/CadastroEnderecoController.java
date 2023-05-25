package application;

import jakarta.persistence.NoResultException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.DAO.EnderecoDAO;
import modelo.DAO.PessoaDAO;
import modelo.VO.Endereco;
import modelo.VO.Pessoa;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static util.errors.erroCancelamentoCadastro;

public class CadastroEnderecoController implements Initializable {

    private App app;

    private Pessoa pessoa;


    @FXML
    private TextField inputBairro;

    @FXML
    private TextField inputNumero;

    @FXML
    private TextField inputRua;

    @FXML
    private TextField inputComplemento;

    @FXML
    private Label msgCadastro;

    @FXML
    protected void btnCadastrarEndereco() throws Exception {
        msgCadastro.setText("");
        EnderecoDAO DAO = new EnderecoDAO();
        Endereco endereco = new Endereco();

        try {

            endereco.setBairro(inputBairro.getText());
            endereco.setNumero(Integer.parseInt(inputNumero.getText()));
            endereco.setRua(inputRua.getText());
            endereco.setComplemento(inputComplemento.getText());
            endereco.setPessoa(this.pessoa);
            DAO.save(endereco);
            app.showSceneLogin();

        } catch (NoResultException | NumberFormatException nre) {
            msgCadastro.setText("Preencha todos os campos");
        }
    }

    @FXML
    protected void btnHome() throws IOException {
        PessoaDAO pesDAO = new PessoaDAO();

        pesDAO.deletById(pessoa.getIdPessoa().intValue());

        erroCancelamentoCadastro();

        app.showSceneLogin();
    }

    @FXML
    protected void btnVoltar() throws IOException{


        PessoaDAO pesDAO = new PessoaDAO();

        pesDAO.deletById(pessoa.getIdPessoa().intValue());

        erroCancelamentoCadastro();

        app.voltar();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}
