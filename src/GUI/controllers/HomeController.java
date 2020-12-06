package GUI.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public Button btnEdit;
    @FXML
    private BorderPane homeLayout;
    @FXML
    private BorderPane profileLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void home(MouseEvent mouseEvent) {
        homeLayout.setCenter(profileLayout);
    }

    public void books(MouseEvent mouseEvent) {
        loadPage("../views/books");
    }

    public void addBooks(MouseEvent mouseEvent) {
        loadPage("../views/addBook");
    }

    public void explore(MouseEvent mouseEvent) {
        loadPage("../views/explore");

    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        Scene loginScene = new Scene(login);
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setTitle("Welcome");
        window.setScene(loginScene);
        window.show();
    }

    private void loadPage(String page){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        homeLayout.setCenter(root);
    }

    public void editProfile(MouseEvent mouseEvent) {
        loadPage("../views/editProfile");
    }
}
