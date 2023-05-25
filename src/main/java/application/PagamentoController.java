package application;

import com.google.gson.Gson;
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
import util.Compose;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PagamentoController implements Initializable {

    App app;

    private final CaixaDAO caixa = new CaixaDAO();

    MesaDAO mesa = new MesaDAO();


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
    protected void pagar() throws IOException {

        System.out.println("Pagando...");

        int i = 2;
        boolean select = false;
        while(true){
            try {
                if(itensConsumidos.getChildren().get(i) instanceof CheckBox) {
                    if(((CheckBox) itensConsumidos.getChildren().get(i)).isSelected()) {

                        CheckBox id = (CheckBox) itensConsumidos.getChildren().get(i);
                        String qtd = ((TextField)itensConsumidos.getChildren().get(i - 1)).getText();

                        if(qtd.equals(""))
                            caixa.pagarUnico(idMesa, id.getId(), "1");
                        else
                            caixa.pagarUnico(idMesa, id.getId(), qtd);

                        select = true;

                    }
                }
            } catch (IndexOutOfBoundsException normal) {
                break;
            }
            i+=1;
        }

        if(!select) {
            System.out.println("Nao era p vim aq n em!!!");
            caixa.pagarTodos(this.idMesa);
            app.showSceneCaixa();
        }else {
            app.showScenePagamento(this.idMesa);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(CaixaController.getIdMesa() != null) {
            this.idMesa = CaixaController.getIdMesa();
            CaixaController.setIdMesa(null);
        }else {
            this.idMesa = Compose.getSelectedMesaId();
            Compose.setSelectedMesaId(null);
        }
        carregaItens(this.idMesa);
        if(this.idMesa != null)
            mesaSelecionada.setText("Mesa " + mesa.find(Integer.parseInt(this.idMesa)).getNumero());
    }

    private void carregaItens(String id){
        itensConsumidos.getChildren().clear();
        CaixaDAO caixaDAO = new CaixaDAO();

        List<JsonObject> itens = caixaDAO.exibirDetalhes(id, null);

        if(itens.size() == 0)
            return;

        String jsonString = String.valueOf(caixaDAO.exibirDetalhes(id, null).get(0));
        JsonObject mesa = new Gson().fromJson(jsonString, JsonObject.class);

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
            sumVal += Double.parseDouble(precoPastelLabel.getId())*Double.parseDouble(quantidadePastelLabel.getText());
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

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
        mesaSelecionada.setText("Mesa "+mesa.find(Integer.parseInt(this.idMesa)).getNumero().toString());

        carregaItens(this.idMesa);
    }
}
