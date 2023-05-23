package application;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modelo.DAO.CaixaDAO;
import util.Compose;
import util.errors;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CaixaController implements Initializable {

    public static App app;

    private static String idMesa;

    @FXML
    private TextField inputMesa;

    @FXML
    private GridPane gridMesas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Integer i=0, j=0;

        CaixaDAO caixaDAO = new CaixaDAO();

        JsonArray mesas = caixaDAO.exibirDetalhes(null);

        for(JsonElement mesa: mesas.getAsJsonArray()){
            System.out.println(mesa);

            gridMesas.addColumn(1);
            gridMesas.setPrefWidth(gridMesas.getPrefWidth()+gridMesas.getPrefWidth()+30);
            gridMesas.add(Compose.createVBoxMesa(mesa.getAsJsonObject()),i,0);

            i++;
        }

        gridMesas.setPrefWidth(gridMesas.getPrefWidth()+gridMesas.getPrefWidth());

        gridMesas.setPadding(new Insets(10,10,10,10));

    }

    @FXML
    public void buscarMesa(){
        CaixaDAO caixaDAO = new CaixaDAO();
        try {
            JsonObject j = (JsonObject) caixaDAO.exibirDetalhes(inputMesa.getText()).get(0);
            idMesa = j.get("idMesa").getAsString();
            app.showScenePagamento();
        }catch (Exception e){
            errors.mesaNEncontrada();
        }

    }

    public void goCozinha() throws IOException {
        app.showSceneCozinha();
    }

    public void goPedido() throws IOException {
        app.showScenePedidos();
    }

    public void goPagamento() throws IOException {
        System.out.println();
        if (inputMesa.getText() != null)
            app.showScenePagamento();
        else
            app.showScenePagamento();

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


    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        CaixaController.app = app;
    }

    public static String getIdMesa() {
        return idMesa;
    }

    public static void setIdMesa(String idMesa) {
        CaixaController.idMesa = idMesa;
    }
}
