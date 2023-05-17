package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.VO.Pessoa;

import java.io.IOException;

public class App extends Application {

    private Stage stage; // Palco principal
    private Integer width = 490;
    private Integer height = 800;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        //showSceneLogin(); // Exibe a tela de login

        showSceneAdminFuncionarios();
    }

    public void showSceneLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas

        LoginController controller = loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);

        // Passa o objeto 'App' para o controlador da tela de cadastro
        controller.setApp(this);

        // Exibe a tela de cadastro
        stage.show();
    }

    public void showSceneCadFunc() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CadastroFuncionario.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas

        CadastroFuncionarioController controller = loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);

        // Passa o objeto 'App' para o controlador da tela de cadastro
        controller.setApp(this);

        // Exibe a tela de cadastro
        stage.show();
    }

    public void showSceneCadEnd(Pessoa pessoa) throws IOException {

        System.out.println("Realizando o cadastro do endereço do individuo: "+ pessoa.getNome());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CadastroEndereco.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas

        CadastroEnderecoController controller = loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);

        // Passa o objeto 'App' para o controlador da tela de cadastro
        controller.setApp(this);

        // Passa a pessoa que vai ser cadastrada com o determinado endereço
        controller.setPessoa(pessoa);

        // Exibe a tela de cadastro
        stage.show();
    }

    public void showSceneAdminFuncionarios() throws IOException {

        this.setWidth(810);
        this.setHeight(566);

        System.out.println("Redirecionando para a tela de adminfuncionarios");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminFuncionarios.fxml"));
        Parent root = loader.load();

        // Obtém o controlador das telas

        AdminFuncionariosController controller = loader.getController();

        // Define a cena da tela de cadastro no palco
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);

        // Passa o objeto 'App' para o controlador da tela de cadastro
        controller.setApp(this);

        // Passa a pessoa que vai ser cadastrada com o determinado endereço
        //controller.setPessoa(pessoa);

        // Exibe a tela de cadastro
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }


}
