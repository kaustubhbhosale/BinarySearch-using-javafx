package com.binary_search;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

public class BinarySearch extends Application {
    private int[] c2w_array;
    private HBox c2w_arrayBox;
    private TextField c2w_targetField;
    private Button c2w_searchButton, c2w_inputButton;
    private TextArea c2w_resultArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button c2w_openDialogButton = new Button("Binary Search");
        c2w_openDialogButton.setOnAction(event -> showCode());
        c2w_openDialogButton.setStyle("-fx-color:YELLOW");

        primaryStage.setTitle("Binary Search");
        c2w_arrayBox = new HBox(20);
        c2w_arrayBox.setPadding(new Insets(60));

        // Loading image correctly
        Image c2w_logoImage = new Image(getClass().getResource("/binarysearch.jpeg").toExternalForm());
        ImageView c2w_logoView = new ImageView(c2w_logoImage);
        c2w_logoView.setFitHeight(40);
        c2w_logoView.setPreserveRatio(true);

        Circle c2w_circle = new Circle(65);
        c2w_circle.setFill(Color.LIGHTSKYBLUE);
        Label c2w_dummy = new Label("", c2w_circle);
        c2w_arrayBox.setStyle("-fx-background-color:LIGHTSKYBLUE;");
        c2w_arrayBox.getChildren().add(c2w_dummy);

        Label c2w_targetLabel = new Label("Enter target value:");
        c2w_targetLabel.setStyle("-fx-font-weight: bold;");
        c2w_targetField = new TextField();
        c2w_targetField.setStyle("-fx-background-color: LAVENDER;");
        c2w_targetField.setPadding(new Insets(30));

        c2w_searchButton = new Button("Search");
        c2w_searchButton.setPrefHeight(50);
        c2w_searchButton.setPrefWidth(100);
        c2w_searchButton.setStyle("-fx-background-color:TURQUOISE;-fx-font-weight:bold");
        c2w_searchButton.setOnAction(event -> startBinarySearch());
        c2w_resultArea = new TextArea();
        c2w_resultArea.setPrefHeight(150);
        c2w_resultArea.setPrefWidth(60);
        c2w_resultArea.setPadding(new Insets(100, 70, 80, 50));
        c2w_resultArea.setStyle("-fx-background-color:STEELBLUE;-fx-font-weight:bold;-fx-alignment: center;");
        c2w_resultArea.setEditable(false);
        c2w_inputButton = new Button("Input Array Data");

        c2w_inputButton.setPrefHeight(80);
        c2w_inputButton.setPrefWidth(180);
        c2w_inputButton.setStyle("-fx-background-color:TURQUOISE;-fx-font-weight: bold;-fx-alignment: center;");
        c2w_inputButton.setOnAction(this::do_Action);
        HBox c2w_inputBox = new HBox(50);
        c2w_inputBox.getChildren().addAll(c2w_inputButton, c2w_targetLabel,
                c2w_targetField, c2w_searchButton, c2w_openDialogButton);
        c2w_inputBox.setAlignment(Pos.CENTER);

        VBox c2w_layout = new VBox(10);
        c2w_layout.setPadding(new javafx.geometry.Insets(20));
        c2w_layout.getChildren().addAll(c2w_logoView, c2w_arrayBox,
                c2w_inputBox, c2w_resultArea);
        Scene c2w_scene = new Scene(c2w_layout, 1000, 800);
        primaryStage.setScene(c2w_scene);
        primaryStage.show();
    }

    private void showCode() {
        Dialog<Void> c2w_dialog = new Dialog<>();
        c2w_dialog.setTitle("Binary Search");
        c2w_dialog.setResizable(true);
        TextArea c2w_textArea = new TextArea();
        c2w_textArea.setEditable(false);
        c2w_textArea.setWrapText(true);
        c2w_textArea.setPrefSize(1500, 800);

        c2w_dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        c2w_dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.CLOSE) {
                c2w_dialog.close();
            }
            return null;
        });

        VBox c2w_content = new VBox(10, c2w_textArea);
        c2w_content.resize(1024, 1000);
        c2w_dialog.getDialogPane().setContent(c2w_content);

        try {
            // Use the class loader to load the file from the resources folder
            Path c2w_filePath = Paths.get(getClass().getResource("/BinarySearch.txt").toURI());
            BufferedReader c2w_reader = Files.newBufferedReader(c2w_filePath);
            String c2w_line;
            StringBuilder fileContents = new StringBuilder();
            while ((c2w_line = c2w_reader.readLine()) != null) {
                fileContents.append(c2w_line).append("\n");
            }
            c2w_reader.close();
            c2w_textArea.setText(fileContents.toString());
        } catch (Exception e) {
            e.printStackTrace();
            c2w_textArea.setText("Error reading file.");
        }
        c2w_dialog.show();
    }

    public void do_Action(ActionEvent event) {
        TextInputDialog c2w_dialog = new TextInputDialog();
        c2w_dialog.setTitle("Array Input");
        c2w_dialog.setHeaderText(
                "Enter array elements separated by commas,Input Integer type Data!!!\nKindly enter data count upto 12");
        c2w_dialog.setContentText("Array:");
        Optional<String> c2w_result = c2w_dialog.showAndWait();
        c2w_result.ifPresent(s -> {
            String[] c2w_elements = s.split(",");
            c2w_array = new int[c2w_elements.length];
            for (int i = 0; i < c2w_elements.length; i++) {
                c2w_array[i] = Integer.parseInt(c2w_elements[i].trim());
            }
            c2w_arrayBox.getChildren().clear();
            createArrayBox(c2w_array);
        });
        System.out.println("Array");
    }

    private HBox createArrayBox(int[] arr) {
        Arrays.sort(arr);
        int i = 0;
        for (int value : arr) {
            Circle c2w_circle;
            if (i % 2 == 0) {
                c2w_circle = new Circle(50, Color.YELLOW);
            } else if (i % 4 == 0) {
                c2w_circle = new Circle(50, Color.PAPAYAWHIP);
            } else if (i % 3 == 0) {
                c2w_circle = new Circle(50, Color.ORANGE);
            } else {
                c2w_circle = new Circle(50, Color.PINK);
            }
            Label c2w_label = new Label(Integer.toString(value));
            c2w_label.setGraphic(c2w_circle);
            c2w_label.setContentDisplay(ContentDisplay.CENTER);
            c2w_label.setStyle("-fx-font-size: 18px;-fx-font-weight: bold;");
            VBox c2w_vbox = new VBox(c2w_label);
            c2w_vbox.getStyleClass().add("c2w_array-box");
            c2w_arrayBox.getChildren().add(c2w_vbox);
            i++;
        }
        return c2w_arrayBox;
    }

    private void startBinarySearch() {
        int c2w_target = Integer.parseInt(c2w_targetField.getText());
        int c2w_result = binarySearch(c2w_target, 0, c2w_array.length - 1);
        c2w_resultArea
                .setText(c2w_result != -1 ? "Target value found at index " + c2w_result : "Target value not found.");
    }

    private int binarySearch(int c2w_target, int c2w_left, int c2w_right) {
        while (c2w_left <= c2w_right) {
            int c2w_mid = c2w_left + (c2w_right - c2w_left) / 2;
            if (c2w_array[c2w_mid] == c2w_target) {

                animateSearch(c2w_mid);
                return c2w_mid;
            }
            if (c2w_array[c2w_mid] < c2w_target) {
                // animateSearch(c2w_mid);
                c2w_left = c2w_mid + 1;
            } else {
                // animateSearch(c2w_mid);
                c2w_right = c2w_mid - 1;
            }
        }
        return -1;
    }

    private void animateSearch(int index) {
        VBox c2w_vbox = (VBox) c2w_arrayBox.getChildren().get(index);
        System.out.println(index);
        TranslateTransition c2w_transition = new TranslateTransition(Duration.seconds(2), c2w_vbox);
        c2w_transition.setToY(-60);
        c2w_transition.setCycleCount(4);
        c2w_transition.setAutoReverse(true);
        c2w_transition.play();
        // RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2),
        // c2w_vbox);
        // rotateTransition.setByAngle(360);
        // rotateTransition.play();
        FadeTransition c2w_fadeTransition = new FadeTransition(Duration.seconds(2), c2w_vbox);
        c2w_fadeTransition.setFromValue(0);
        c2w_fadeTransition.setToValue(1);
        c2w_fadeTransition.play();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }
}
