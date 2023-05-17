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
        System.out.println("!!!Entramo aq porra finalmente!!!!");
        msgCadastro.setText("");
        EnderecoDAO DAO = new EnderecoDAO();
        Endereco endereco = new Endereco();
        endereco.setTest();
        endereco.setPessoa(pessoa);
        try {

            /*
            endereco.setBairro(inputBairro.getText());
            endereco.setNumero(Integer.parseInt(inputNumero.getText()));
            endereco.setRua(inputRua.getText());
            endereco.setComplemento(inputComplemento.getText());
            endereco.setPessoa(this.pessoa);*/
            DAO.save(endereco);
            app.showSceneLogin();

        } catch (NoResultException nre){
            msgCadastro.setText("Preencha todos os campos");
        } catch (NumberFormatException nfe){
            msgCadastro.setText("Preencha todos os campos");
        }
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

    public TextField getInputBairro() {
        return inputBairro;
    }

    public void setInputBairro(TextField inputBairro) {
        this.inputBairro = inputBairro;
    }

    public TextField getInputNumero() {
        return inputNumero;
    }

    public void setInputNumero(TextField inputNumero) {
        this.inputNumero = inputNumero;
    }

    public TextField getInputRua() {
        return inputRua;
    }

    public void setInputRua(TextField inputRua) {
        this.inputRua = inputRua;
    }

    public TextField getInputComplemento() {
        return inputComplemento;
    }

    public void setInputComplemento(TextField inputComplemento) {
        this.inputComplemento = inputComplemento;
    }

    public Label getMsgCadastro() {
        return msgCadastro;
    }

    public void setMsgCadastro(Label msgCadastro) {
        this.msgCadastro = msgCadastro;
    }
}
