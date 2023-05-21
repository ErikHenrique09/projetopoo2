package application;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modelo.DAO.*;
import modelo.VO.ItenPedido;
import modelo.VO.Pedido;
import util.Compose;
import util.errors;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static util.errors.erroAdmin;

public class PedidoController implements Initializable {
    private App app;

    @FXML
    private GridPane produtosCardapio;

    @FXML
    private Label pedidoRegistrado;

    @FXML
    private TextField inputMesa;

    @FXML
    protected void cadastrarPedido() throws IOException, InterruptedException {

        PedidoDAO pedDAO = new PedidoDAO();
        ItenPedidoDAO itemDAO = new ItenPedidoDAO();
        CardapioDAO cardDAO = new CardapioDAO();
        FuncionarioDAO funcDAO = new FuncionarioDAO();
        MesaDAO mesDAO = new MesaDAO();
        Pedido pedido = new Pedido();

        Integer limit = cardDAO.findAll().size();

        if(inputMesa.getText().equals("")) {
            errors.erroSelecionaMesa();
            return;
        }else{
            pedido.setMesa(mesDAO.find(Integer.valueOf(inputMesa.getText())));
        }

        for (Node node : produtosCardapio.getChildren()) {
            GridPane grid = (GridPane) ((VBox) node).getChildren().get(0);// pegando a gridpane das vbox

            Integer i = 0;
            while(i < limit){
                try {

                    Label produto = (Label) grid.getChildren().get(i + 1);      //Produto
                    TextField qtd = (TextField) grid.getChildren().get(i + 2);  // Qtd
                    CheckBox idCard = (CheckBox) grid.getChildren().get(i + 3); //IdCardapio

                    System.out.println("Entrando...");
                    i += 3;
                    if (!idCard.isSelected())
                        continue;

                    ItenPedido item = new ItenPedido();

                    if (qtd.getText().equals(""))
                        item.setQtd(1);
                    else
                        item.setQtd(Integer.valueOf(qtd.getText()));

                    item.setPedido(pedido);
                    item.setCardapio(cardDAO.find(Integer.valueOf(idCard.getId())));
                    item.setStatus(1);

                    itemDAO.save(item);

                }catch (Exception e){
                   break;
                }
            }

        }

        app.setIdUser((long) 1);
        pedido.setFuncionario(funcDAO.find(Math.toIntExact(app.getIdUser())));
        pedido.setStatus(1);

        pedDAO.save(pedido);

        app.showScenePedidos();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        CardapioDAO cardDAO = new CardapioDAO();

        Object[] jsonList = cardDAO.getCardapio().toArray();

        Integer j = 1;
        for (Object json : jsonList) {
            JsonObject jsonObject = new Gson().fromJson(json.toString(), JsonObject.class);

            VBox teste = Compose.createInputVBox(jsonObject);

            produtosCardapio.setPrefHeight(produtosCardapio.getPrefHeight() + 43.0 * jsonObject.getAsJsonArray("produtos").size());
            produtosCardapio.add(teste, 0, j);

            j++;

        }

        // Configurar o layout do produtosCardapio
        produtosCardapio.setAlignment(Pos.CENTER);
        produtosCardapio.setHgap(10);
        produtosCardapio.setVgap(10);

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

    public void setApp(App app) {
        this.app = app;
    }
}
