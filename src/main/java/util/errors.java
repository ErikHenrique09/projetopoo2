package util;

import javafx.scene.control.Alert;

public class errors {

    public static void erroPreenchimento() {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.ERROR);
        a.setContentText("Preencha todos os campos");
        a.show();
    }

    public static void erroEmail() {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.WARNING);
        a.setContentText("Email ja cadastrado");
        a.show();
    }

    public static void erroCancelamentoCadastro() {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.WARNING);
        a.setContentText("Cancelamento de registro efetuado, o usuario não foi registrado");
        a.show();
    }

    public static void erroSelecionaMesa(){
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.WARNING);
        a.setContentText("Seleciona uma mesa");
        a.show();
    }

    public static void erroFazLogin(){

        /*Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.WARNING);
        a.setContentText("É necessario estar logado para acessar o sistema!!!");
        a.show();*/
    }

    public static void erroAdmin(){
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.WARNING);
        a.setContentText("Permissão Negada !!!");
        a.show();
    }

}
