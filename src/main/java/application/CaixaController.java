package application;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modelo.DAO.CaixaDAO;
import modelo.DAO.PessoaDAO;
import util.Compose;
import util.errors;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static util.errors.erroAdmin;

public class CaixaController implements Initializable {

    public static App app;

    private static String idMesa;

    @FXML
    private TextField inputMesa;

    @FXML
    private GridPane gridMesas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int i = 0;

        CaixaDAO caixaDAO = new CaixaDAO();

        List<JsonObject> mesas = caixaDAO.exibirDetalhes(null, null);

        if (mesas.size() == 0)
            return;

        for(Object m: mesas.toArray()){
            JsonObject mesa = new Gson().fromJson(m.toString(), JsonObject.class);

            gridMesas.addColumn(1);
            gridMesas.setPrefWidth(gridMesas.getPrefWidth()+gridMesas.getPrefWidth()+30);

            gridMesas.add(Compose.createVBoxMesa(mesa), i, 0);

            i++;
        }

        gridMesas.setPrefWidth(gridMesas.getPrefWidth()+gridMesas.getPrefWidth());

        gridMesas.setPadding(new Insets(10,10,10,10));

    }

    @FXML
    public void buscarMesa() {
        CaixaDAO caixaDAO = new CaixaDAO();
            try{

                List<JsonObject> j = caixaDAO.exibirDetalhes(null, inputMesa.getText());
                JsonElement jsonElement = JsonParser.parseString(String.valueOf(j.get(0)));
                JsonObject json = jsonElement.getAsJsonObject();

                idMesa = String.valueOf(json.get("idMesa"));
                app.showScenePagamento(idMesa);

            }catch (Exception e){
                System.out.println(e.getMessage());
                errors.mesaNEncontrada();
            }


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
