package application;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modelo.DAO.ItenPedidoDAO;
import util.Compose;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static util.Compose.getIdVbox;

public class CozinhaController implements Initializable {

    private App app;

    @FXML
    private Label labelSelectedItenPedido;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPedidos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelSelectedItenPedido.setText("Pedido Selecionado: ");
        setValoresVBox(carregarPedidos());

    }

    @FXML
    protected void pedidoPronto() throws IOException {
        ItenPedidoDAO item = new ItenPedidoDAO();
        JsonObject json = new Gson().fromJson(getIdVbox(), JsonObject.class);

        if (json == null)
            return;

        item.close(item.find(json.get("idItenPedido").getAsInt()));
        app.showSceneCozinha();
    }

    // Definir as informações do GridPane dinamicamente
    public void setValoresVBox(JsonArray json) {

        Integer i = 0, j = 0;

        if (json == null)
            return;

        for (JsonElement js : json.getAsJsonArray()) {

            JsonObject dados = js.getAsJsonObject();
            //System.out.println(dados);
            //System.out.println("{ \"vbox\": \"("+i+","+j+")\", \"idItenPedido\": \""+dados.get("idItenPedido")+"\"}");

            VBox vbox = Compose.createVBox("{ \"vbox\": \"(" + i + "," + j + ")\", \"idItenPedido\": \"" + dados.get("idItenPedido") + "\"}", this);
            GridPane gridPane = (GridPane) vbox.getChildren().get(0);   // Pegando a GridPane da VBox

            // Acessando os labels e alterando os dados
            ((Label) gridPane.lookup("#outputTempoEspera")).setText(dados.get("iniPed").getAsString());
            ((Label) gridPane.lookup("#outputPedido")).setText(dados.get("produto").getAsString());
            ((Label) gridPane.lookup("#ouputQuantidade")).setText(dados.get("quantidade").getAsString());
            ((Label) gridPane.lookup("#outputMesa")).setText(dados.get("mesa").getAsString());
            vbox.setId("vbox(" + i + "," + j + ")");

            if(i%3 == 0 && i != 0) {

                i = 0;

                j++;

                gridPedidos.setPrefHeight(gridPedidos.getPrefHeight()+vbox.getPrefHeight());
                gridPedidos.setMaxHeight(gridPedidos.getPrefHeight());
                gridPedidos.addRow(j);
                gridPedidos.add(vbox, i, j);
                i++;
            }else{
                gridPedidos.add(vbox, i, j);
                i++;
            }
        }
    }

    public JsonArray carregarPedidos() {
        ItenPedidoDAO itenDAO = new ItenPedidoDAO();

        try {
            JsonArray json = itenDAO.exibir(1);
            System.out.println("Numero de pedidos: " + json.size());
            return json;
        } catch (NullPointerException n) {
            labelSelectedItenPedido.setText("Não há pedidos!");
            return new JsonArray();
        }

    }

    public void setLabelSelectedItenPedido(String item) {
        JsonObject json = new Gson().fromJson(item, JsonObject.class);
        labelSelectedItenPedido.setText("Pedido Selecionado: " + json.get("idItenPedido").getAsString());
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
        app.showSceneAdminFuncionarios();
        //Tirar dps
        /*app.setIdUser(0L);
        PessoaDAO pesDAO = new PessoaDAO();
        if (pesDAO.validaAdmin(app.getIdUser().toString()))
            app.showSceneAdminFuncionarios();
        else
            erroAdmin();*/

    }

    public void goCaixa() throws IOException {
        app.showSceneCaixa();
    }


}
