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
import modelo.DAO.AdminDAO;
import modelo.DAO.PessoaDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static util.errors.erroAdmin;

public class AdminVendasController implements Initializable {

    private final AdminDAO admin = new AdminDAO();
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

        funcionario.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("funcionario").getAsString()));
        produto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("produto").getAsString()));
        quantidade.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().get("qtd").getAsInt()).asObject());
        valorFinal.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().get("val").getAsInt()).asObject());

        JsonArray jsonArray = admin.listVendas();

        if(jsonArray == null)
            return;

        ObservableList<JsonObject> observableListVendas = FXCollections.observableArrayList();

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
            outputFunc.setText(String.valueOf(obj.get("funcionario").getAsString()));
            outputProd.setText(String.valueOf(obj.get("produto").getAsString()));
            outputQtd.setText(String.valueOf(obj.get("qtd").getAsString()));
            outputValFim.setText("R$"+(obj.get("val").getAsString()));
            outputIniPed.setText(String.valueOf(obj.get("iniPed").getAsString()));
            outputDataPed.setText(String.valueOf(obj.get("dataPed").getAsString()));

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

    public void setApp(App app) {
        this.app = app;
    }

    public void goCozinha() throws IOException {
        app.showSceneCozinha();
    }

    public void goPedido() throws IOException {
        app.showScenePedidos();
    }

    public void goAdmin() throws IOException {
        PessoaDAO pesDAO = new PessoaDAO();
        if (pesDAO.validaAdmin(app.getIdUser().toString()))
            app.showSceneAdminFuncionarios();
        else
            erroAdmin();
    }

    public void goCaixa() throws IOException {
        app.showSceneCaixa();
    }


}
