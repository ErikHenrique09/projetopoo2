package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.VO.Pessoa;
import util.errors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private Stage stage; // Palco principal
    private List<String> url;
    private Integer width = 810;
    private Integer height = 600;
    private Long idUser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;
        this.url = new ArrayList<String>();

        this.url.add("login");
        showSceneLogin(); // Exibe a tela de login, começaremos por ela

    }

    public void showSceneLogin() throws IOException {
        this.url.add("login");

        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas

        LoginController controller = loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);

        // Passa o objeto 'App' para o controlador da tela de cadastro
        controller.setApp(this);

        // Exibe a tela de cadastro
        stage.setTitle("Login");
        stage.show();
    }

    public void showSceneCadFunc(String idPessoa) throws IOException {

        this.url.add("cadFunc");
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CadastroFuncionario.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas

        CadastroFuncionarioController controller = loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);

        // Passa o objeto 'App' para o controlador da tela de cadastro
        controller.setApp(this);

        if (idPessoa != null) {
            System.out.println("idPessoa sendo passado para cadFunc: " + idPessoa);
            // Passa a pessoa que vai ter seus dados alterados
            controller.setIdPessoa(idPessoa);
        } else {
            System.out.println("idPessoa null");
        }

        // Exibe a tela de cadastro
        stage.setTitle("Cadastro de Funcionario");
        stage.show();
    }

    public void showSceneCadEnd(Pessoa pessoa) throws IOException {

        this.url.add("cadEnd");
        stage.close();

        System.out.println("Realizando o cadastro do endereço do individuo: " + pessoa.getNome());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CadastroEndereco.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas

        CadastroEnderecoController controller = loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);

        // Passa o objeto 'App' para o controlador da tela de cadastro
        controller.setApp(this);

        // Passa a pessoa que vai ser cadastrada com o determinado endereço
        controller.setPessoa(pessoa);

        // Exibe a tela de cadastro
        stage.setTitle("Cadastro de Endereço");
        stage.show();
    }

    public void showSceneAdminFuncionarios() throws IOException {

        if(this.getIdUser() == null)
            errors.erroFazLogin();

        this.url.add("adminFunc");
        stage.close();

        System.out.println("Redirecionando para a tela de adminfuncionarios");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminFuncionarios.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas

        AdminFuncionariosController controller = loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);

        // Passa o objeto 'App' para o controlador da tela de cadastro
        controller.setApp(this);

        // Passa a pessoa que vai ser cadastrada com o determinado endereço
        //controller.setPessoa(pessoa);

        // Exibe a tela de cadastro
        stage.setTitle("Administração Funcionarios");
        stage.show();
    }

    public void showSceneAdminVendas() throws IOException {

        if(this.getIdUser() == null)
            errors.erroFazLogin();

        this.url.add("adminVendas");
        stage.close();

        System.out.println("Redirecionando para a tela de adminvendas");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminVendas.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas

        AdminVendasController controller = loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);

        // Passa o objeto 'App' para o controlador da tela de cadastro
        controller.setApp(this);

        // Passa a pessoa que vai ser cadastrada com o determinado endereço
        //controller.setPessoa(pessoa);

        // Exibe a tela de cadastro
        stage.setTitle("Administração Vendas");
        stage.show();
    }

    public void showSceneCozinha() throws IOException {

        if(this.getIdUser() == null)
            errors.erroFazLogin();

        this.url.add("adminVendas");
        stage.close();

        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Cozinha.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas
        CozinhaController controller =  loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);

        // Passa o objeto 'App' para o controlador da tela cozinha
        controller.setApp(this);

        stage.setTitle("Cozinha");
        stage.show();

    }

    public void showScenePedidos() throws IOException {

        if(this.getIdUser() == null)
            errors.erroFazLogin();

        this.url.add("adminVendas");
        stage.close();

        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Pedido.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas
        PedidoController controller =  loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);

        // Passa o objeto 'App' para o controlador da tela cozinha
        controller.setApp(this);

        stage.setTitle("Pedido");
        stage.show();

    }

    public void showSceneCaixa() throws IOException {

        if(this.getIdUser() == null)
            errors.erroFazLogin();

        this.url.add("adminVendas");
        stage.close();

        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Caixa.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas
        CaixaController controller =  loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);

        // Passa o objeto 'App' para o controlador da tela cozinha
        controller.setApp(this);

        stage.setTitle("Caixa");
        stage.show();

    }

    public void showScenePagamento(String idMesa) throws IOException {

        if(this.getIdUser() == null)
            errors.erroFazLogin();

        this.url.add("pagamento");

        stage.close();

        this.width = 510;
        this.height = 430;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Pagamento.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas
        PagamentoController controller =  loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);

        // Passa o objeto 'App' para o controlador da tela cozinha
        controller.setApp(this);

        if(idMesa != null) {
            System.out.println("PASSANDO ID "+idMesa+" PARA PAGAMENTO CONTROLLER");

            controller.setIdMesa(idMesa);
        }

        stage.setTitle("Pagamento");
        stage.show();
        this.width = 810;
        this.height = 600;
    }

    public void voltar() throws IOException {

        String pag;

        if (this.url.size() != 1)
            pag = this.url.get(this.url.size() - 2);
        else
            pag = "login";

        this.url.remove(this.url.size() - 1);

        switch (pag) {
            case "login":
                this.showSceneLogin();
                this.url.clear();
                break;
            case "adminFunc":
                this.showSceneAdminFuncionarios();
                break;
            case "adminVendas":
                this.showSceneAdminVendas();
                break;
            case "cadFunc":
                this.showSceneCadFunc(null);
                break;
            case "cadEnd":
                this.showSceneCadEnd(null);
                break;
            default:
                this.showSceneLogin();
        }
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
