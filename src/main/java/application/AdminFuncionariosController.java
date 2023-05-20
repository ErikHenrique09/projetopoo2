package application;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.DAO.FuncionarioDAO;
import modelo.DAO.PessoaDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminFuncionariosController implements Initializable {

    private final FuncionarioDAO funcDAO = new FuncionarioDAO();
    private App app;
    private JsonObject selectedFuncionario;
    @FXML
    private MenuItem menuVendas;
    @FXML
    private MenuItem menuFuncionarios;
    @FXML
    private TableView<JsonObject> tableViewFuncionarios;
    @FXML
    private TableColumn<JsonObject, String> nome;
    @FXML
    private TableColumn<JsonObject, String> email;
    @FXML
    private TableColumn<JsonObject, Integer> func;
    @FXML
    private Label outputNome;
    @FXML
    private Label outputEmail;
    @FXML
    private Label outputIdade;
    @FXML
    private Label outputFuncao;
    @FXML
    private Label outputTelefone1;
    @FXML
    private Label outputTelefone2;
    private ObservableList<JsonObject> observableListFuncionarios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        carregarTableViewFuncionario();

        tableViewFuncionarios.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedFuncionario = newValue;
                    selecionarItemTableViewFuncionarios(newValue);
                });
    }

    public void carregarTableViewFuncionario() {
        nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("nome").getAsString()));
        email.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("email").getAsString()));
        func.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().get("func").getAsInt()).asObject());

        JsonArray jsonArray = funcDAO.exibir();

        observableListFuncionarios = FXCollections.observableArrayList();

        for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
                JsonObject funcionario = element.getAsJsonObject();
                observableListFuncionarios.add(funcionario);
            }
        }

        tableViewFuncionarios.setItems(observableListFuncionarios);
    }

    public void selecionarItemTableViewFuncionarios(JsonObject obj) {

        if (obj != null) {
            outputNome.setText(String.valueOf(obj.get("nome").getAsString()));
            outputEmail.setText(String.valueOf(obj.get("email").getAsString()));
            outputIdade.setText(String.valueOf(obj.get("idade").getAsString()));
            outputFuncao.setText(String.valueOf(obj.get("func").getAsString()));
            outputTelefone1.setText(String.valueOf(obj.get("tel1").getAsString()));
            try {
                outputTelefone2.setText(String.valueOf(obj.get("tel2").getAsString()));
            } catch (UnsupportedOperationException nulo) {
                outputTelefone2.setText("");
            }
        } else {
            outputNome.setText("");
            outputEmail.setText("");
            outputIdade.setText("");
            outputFuncao.setText("");
            outputTelefone1.setText("");
            outputTelefone2.setText("");
        }
    }

    @FXML
    public void menuFuncionarios() throws IOException {
        app.showSceneAdminFuncionarios();
    }

    @FXML
    public void menuVendas() throws IOException {
        app.showSceneAdminVendas();
    }

    @FXML
    protected void btnInserir() throws IOException {

        app.showSceneCadFunc(null);

    }

    @FXML
    protected void btnAlterar() throws IOException {
        if (selectedFuncionario != null) {
            System.out.println(selectedFuncionario);
            app.showSceneCadFunc(selectedFuncionario.get("idPessoa").getAsString());
        }
    }

    @FXML
    protected void btnRemover() throws IOException {

        if (selectedFuncionario != null) {
            PessoaDAO pesDAO = new PessoaDAO();
            pesDAO.deletById(selectedFuncionario.get("idPessoa").getAsInt());
            carregarTableViewFuncionario();
        }
    }

    public void setApp(App app) {
        this.app = app;
    }
}
