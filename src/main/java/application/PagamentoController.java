package application;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modelo.DAO.CaixaDAO;
import modelo.DAO.MesaDAO;
import modelo.VO.Caixa;
import util.Compose;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PagamentoController implements Initializable {

    App app;

    private String idMesa;

    @FXML
    private Label mesaSelecionada;

    @FXML
    private GridPane itensConsumidos;

    @FXML
    private Label valTotal;

    @FXML
    protected void btnVoltar() throws IOException {
        app.showSceneCaixa();
    }

    @FXML
    protected void pagar(){
        
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(CaixaController.getIdMesa() != null) {
            mesaSelecionada.setText("Mesa " + CaixaController.getIdMesa());
            idMesa = CaixaController.getIdMesa();
            CaixaController.setIdMesa(null);
        } else {
            mesaSelecionada.setText("Mesa " + Compose.getSelectedMesaId());
            idMesa = Compose.getSelectedMesaId();
            Compose.setSelectedMesaId(null);
        }

        CaixaDAO caixaDAO = new CaixaDAO();
        JsonObject mesa = (JsonObject) caixaDAO.exibirDetalhes(idMesa).get(0);

        int j=1;
        double sumVal = 0;
        for(JsonElement json: mesa.get("pedidos").getAsJsonArray()){

            if (json == null)
                return;

            JsonObject pedido = json.getAsJsonObject();

            Label pastelLabel = new Label(pedido.get("produto").getAsString());
            Label quantidadePastelLabel = new Label(pedido.get("quantidade").getAsString());

            Label precoPastelLabel = new Label("R$"+pedido.get("valor").getAsString());
            precoPastelLabel.setId(pedido.get("valor").getAsString());

            TextField textField = new TextField();

            CheckBox checkBox = new CheckBox();
            checkBox.setId(pedido.get("idItenPedido").getAsString());

            itensConsumidos.add(pastelLabel, 0, j);
            itensConsumidos.add(quantidadePastelLabel, 1, j);
            itensConsumidos.add(precoPastelLabel, 2, j);
            itensConsumidos.add(textField, 3, j);
            itensConsumidos.add(checkBox, 4, j);

            itensConsumidos.setPrefHeight(itensConsumidos.getPrefHeight()+30);

            sumVal += pedido.get("quantidade").getAsDouble()*pedido.get("valor").getAsDouble();

            j++;
        }

        valTotal.setText("R$"+sumVal);

    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }
}
