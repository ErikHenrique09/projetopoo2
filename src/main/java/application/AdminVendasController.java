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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.DAO.ItenPedidoDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class AdminVendasController implements Initializable {

    private final ItenPedidoDAO itenDAO = new ItenPedidoDAO();
    private App app;
    private JsonObject selectedVenda;
    @FXML
    private TableView<JsonObject> tableViewVendas;
    @FXML
    private TableColumn<JsonObject, String> funcionario;
    @FXML
    private TableColumn<JsonObject, String> produto;
    @FXML
    private TableColumn<JsonObject, Integer> quantidade;
    @FXML
    private TableColumn<JsonObject, Integer> valorFinal;
    @FXML
    private Label outputFunc;
    @FXML
    private Label outputProd;
    @FXML
    private Label outputQtd;
    @FXML
    private Label outputValFim;
    @FXML
    private Label outputIniPed;
    @FXML
    private Label outputFimPed;
    @FXML
    private Label outputDataPed;
    @FXML
    private Label outputStatus;
    private ObservableList<JsonObject> observableListVendas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        carregarTableViewVendas();

        tableViewVendas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedVenda = newValue;
                    selecionarItemTableViewVendas(newValue);
                });
    }

    public void carregarTableViewVendas() {

        funcionario.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("func").getAsString()));
        produto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("produto").getAsString()));
        quantidade.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().get("quantidade").getAsInt()).asObject());
        valorFinal.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().get("valFinal").getAsInt()).asObject());

        JsonArray jsonArray = itenDAO.exibir(0);
        System.out.println(jsonArray);
        //exit(130);
        observableListVendas = FXCollections.observableArrayList();

        for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
                JsonObject venda = element.getAsJsonObject();
                observableListVendas.add(venda);
            }
        }

        tableViewVendas.setItems(observableListVendas);
    }


    public void selecionarItemTableViewVendas(JsonObject obj) {

        if (obj != null) {
            outputFunc.setText(String.valueOf(obj.get("func").getAsString()));
            outputProd.setText(String.valueOf(obj.get("produto").getAsString()));
            outputQtd.setText(String.valueOf(obj.get("quantidade").getAsString()));
            outputValFim.setText(String.valueOf(obj.get("valFinal").getAsString()));
            outputIniPed.setText(String.valueOf(obj.get("iniPed").getAsString()));
            outputFimPed.setText(String.valueOf(obj.get("fimPed").getAsString()));
            outputDataPed.setText(String.valueOf(obj.get("dataPed").getAsString()));
            outputStatus.setText(String.valueOf(obj.get("status").getAsString()));
            try {
                outputFimPed.setText(String.valueOf(obj.get("fimPed").getAsString()));
            } catch (UnsupportedOperationException nulo) {
                outputFimPed.setText("");
            }
        } else {
            outputFunc.setText("");
            outputProd.setText("");
            outputQtd.setText("");
            outputValFim.setText("");
            outputIniPed.setText("");
            outputFimPed.setText("");
            outputDataPed.setText("");
            outputStatus.setText("");
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
    public void escolhaCozinha() {
        System.out.println("cozinha");
    }


    /*
    @FXML
    protected void btnInserir() throws IOException {

        //app.showSceneCadFunc(null);

    }

    @FXML
    protected void btnAlterar() throws IOException {
        if(selectedFuncionario != null) {
            System.out.println(selectedFuncionario);
            app.showSceneCadFunc(selectedFuncionario.get("idPessoa").getAsString());
        }
    }

    @FXML
    protected void btnRemover() throws IOException {

        if(selectedFuncionario != null){
            PessoaDAO pesDAO = new PessoaDAO();
            pesDAO.deletById(selectedFuncionario.get("idPessoa").getAsInt());
            carregarTableViewFuncionario();
        }
    }*/

    public void setApp(App app) {
        this.app = app;
    }
}
