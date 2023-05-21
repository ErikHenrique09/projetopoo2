package util;

import application.CozinhaController;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.animation.ScaleTransition;
import javafx.geometry.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Compose {

    private static boolean isSelected = false;
    private static String idVbox = null;

    public static VBox createVBox(String idVbox, CozinhaController cozinhaController) {
        VBox vBox = new VBox();

        vBox.setMaxHeight(Double.NEGATIVE_INFINITY);
        vBox.setMaxWidth(Double.NEGATIVE_INFINITY);
        vBox.setMinHeight(Double.NEGATIVE_INFINITY);
        vBox.setMinWidth(Double.NEGATIVE_INFINITY);
        vBox.setAlignment(Pos.CENTER);

        vBox.setOnMouseClicked(event -> {
            vBox.setStyle("-fx-border-color: #989898; -fx-border-width: 2px; -fx-border-radius: 15;");
            if (isSelected) {
                setIdVbox(idVbox);
                vBox.setStyle("-fx-border-color: #6b2020; -fx-border-width: 3px; -fx-border-radius: 15;");
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), vBox);
                scaleTransition.setFromX(1.0);
                scaleTransition.setFromY(1.0);
                scaleTransition.setToX(1.1);
                scaleTransition.setToY(1.1);
                scaleTransition.setCycleCount(2);
                scaleTransition.setAutoReverse(true);
                scaleTransition.play();

                System.out.println("Usuário clicou na VBox: " + idVbox);
                cozinhaController.setLabelSelectedItenPedido(idVbox);

                Duration.millis(1000);
                isSelected = false;
            } else {
                isSelected = true;
                vBox.setStyle("-fx-border-color: #989898; -fx-border-width: 2px; -fx-border-radius: 15;");
            }
        });

        vBox.setPrefHeight(160);
        vBox.setPrefWidth(250);
        vBox.setStyle("-fx-border-color: #989898; -fx-border-width: 2px; -fx-border-radius: 15;");

        GridPane gridPane = createGridPane();
        vBox.getChildren().add(gridPane);

        return vBox;
    }

    private static GridPane createGridPane() {
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMaxHeight(Double.NEGATIVE_INFINITY);
        gridPane.setMaxWidth(Double.NEGATIVE_INFINITY);
        gridPane.setMinHeight(Double.NEGATIVE_INFINITY);
        gridPane.setMinWidth(Double.NEGATIVE_INFINITY);
        gridPane.setPrefHeight(154);
        gridPane.setPrefWidth(220);
        gridPane.getStyleClass().add("bordered-element");

        ColumnConstraints col1 = createCol();
        ColumnConstraints col2 = createCol();

        RowConstraints row1 = createRow();
        RowConstraints row2 = createRow();
        RowConstraints row3 = createRow();
        RowConstraints row4 = createRow();

        gridPane.getColumnConstraints().addAll(col1, col2);
        gridPane.getRowConstraints().addAll(row1, row2, row3, row4);

        Label labelTempoEspera = createInputLabel(45, 130, "Tempo em espera: ", 0);
        Label labelPedido = createInputLabel(45, 66, "Pedido: ", 1);
        Label labelQuantidade = createInputLabel(45, 89, "Quantidade: ", 2);
        Label labelMesa = createInputLabel(45, 57, "Mesa: ", 3);

        Label outputTempoEspera = createOutputLabel(1, 0, "outputTempoEspera");
        Label outputPedido = createOutputLabel(1, 1, "outputPedido");
        Label outputQuantidade = createOutputLabel(1, 2, "ouputQuantidade");
        Label outputMesa = createOutputLabel(1, 3, "outputMesa");

        gridPane.getChildren().addAll(
                labelTempoEspera, labelPedido, labelQuantidade, labelMesa,
                outputTempoEspera, outputPedido, outputQuantidade, outputMesa
        );

        return gridPane;
    }

    public static VBox createInputVBox(JsonObject json) {
        VBox vbox = new VBox();
        vbox.setId(json.get("categoria").getAsString());
        vbox.setPrefHeight(200);
        vbox.setPrefWidth(100);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPrefHeight(160);
        gridPane.setPrefWidth(760);

        ColumnConstraints col1 = createInputCols(400.0,10.0,372.0);
        ColumnConstraints col2 = createInputCols(115.0,10.0,259.0);
        ColumnConstraints col3 = createInputCols(115.0,10.0,67.0);

        RowConstraints rowTitle = createRow();
        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        gridPane.getRowConstraints().addAll(rowTitle);

        System.out.println(json.get("categoria"));

        Label labelTitulo = createTitulo(json.get("categoria").getAsString());
        GridPane.setHalignment(labelTitulo, HPos.LEFT);
        GridPane.setValignment(labelTitulo, VPos.CENTER);
       // GridPane.setMargin(labelTitulo, new Insets(20, 0, 0, 20));
        gridPane.getChildren().addAll(labelTitulo);

        Integer j = 1;

        for(JsonElement produtos: json.get("produtos").getAsJsonArray()) {

            JsonObject js = produtos.getAsJsonObject();

            Label labelProduto = new Label(js.get("titulo").getAsString());
            labelProduto.setPadding(new Insets(10,10,10,10));
            labelProduto.setFont(new Font(15));
            GridPane.setRowIndex(labelProduto, j);
            GridPane.setMargin(labelProduto, new Insets(0, 0, 0, 100));

            CheckBox checkBox = createCheckBox(js.get("idItenCardapio").getAsString());
            GridPane.setHgrow(checkBox, Priority.SOMETIMES);
            GridPane.setColumnIndex(checkBox, 2);
            GridPane.setRowIndex(checkBox, j);

            TextField fieldQtd = createTextField();
            fieldQtd.setId("qtd");
            GridPane.setColumnIndex(fieldQtd, 1);
            GridPane.setRowIndex(fieldQtd, j);
            GridPane.setHgrow(fieldQtd, Priority.ALWAYS);

            vbox.setPrefHeight(vbox.getPrefHeight() + fieldQtd.getPrefHeight());

            gridPane.addRow(j,labelProduto, fieldQtd, checkBox);

            j++;
        }


        //vbox.setPrefHeight(vbox.getPrefHeight() + 43.0*(j+1));
        vbox.getChildren().add(gridPane);

        return vbox;
    }

    private static CheckBox createCheckBox(String id){
        CheckBox check = new CheckBox();
        check.setId(id);
        check.setPadding(new Insets(0,0,0,10));
        check.setAlignment(Pos.CENTER);
        check.setContentDisplay(ContentDisplay.RIGHT);

        return check;
    }

    private static TextField createTextField(){
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        textField.setMaxWidth(Double.MAX_VALUE);
        textField.setPrefHeight(40.0);

        return textField;

    }

    private static Label createTitulo(String txt){
        Label labelTitulo = new Label(txt);
        labelTitulo.setFont(Font.font("System Bold", 20));
        labelTitulo.setStyle("-fx-border-insets: 0,90,0,0;");
        labelTitulo.setMaxWidth(Double.MAX_VALUE);
        labelTitulo.setPrefHeight(54);
        labelTitulo.setPrefWidth(440);

        return labelTitulo;
    }

    public static ColumnConstraints createInputCols(Double maxWidth, Double minWidth, Double prefWidth){
        ColumnConstraints col = new ColumnConstraints();
        col.setHalignment(HPos.LEFT);
        col.setHgrow(Priority.SOMETIMES);
        col.setMaxWidth(maxWidth);
        col.setMinWidth(minWidth);
        col.setPrefWidth(prefWidth);

        return col;
    }

    private static RowConstraints createInputRows(){
        RowConstraints row = new RowConstraints();
        row.setMinHeight(10);
        row.setPrefHeight(30);
        row.setVgrow(Priority.SOMETIMES);

        return row;
    }

    private static ColumnConstraints createCol() {
        ColumnConstraints col = new ColumnConstraints();
        col.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col.setMinWidth(10);
        col.setPrefWidth(100);

        return col;
    }

    private static RowConstraints createRow() {
        RowConstraints row = new RowConstraints();
        row.setMinHeight(10);
        row.setPrefHeight(30);
        row.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        return row;
    }

    private static Label createInputLabel(double prefHeight, double prefWidth, String text, int rowIndex) {
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        label.setPrefHeight(prefHeight);
        label.setPrefWidth(prefWidth);
        label.setText(text);
        GridPane.setRowIndex(label, rowIndex);
        return label;
    }

    private static Label createOutputLabel(int columnIndex, int rowIndex, String id) {
        Label label = new Label();
        label.setId(id);
        label.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        label.setMaxHeight(Double.NEGATIVE_INFINITY);
        label.setMaxWidth(Double.NEGATIVE_INFINITY);
        label.setMinHeight(Double.NEGATIVE_INFINITY);
        label.setMinWidth(Double.NEGATIVE_INFINITY);
        label.setPrefHeight(35);
        label.setPrefWidth(100);
        GridPane.setColumnIndex(label, columnIndex);
        GridPane.setRowIndex(label, rowIndex);
        return label;
    }

    public static String getIdVbox() {
        return idVbox;
    }

    public static void setIdVbox(String idVbox) {
        Compose.idVbox = idVbox;
    }
}
