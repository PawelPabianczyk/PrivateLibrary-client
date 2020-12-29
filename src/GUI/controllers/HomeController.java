package GUI.controllers;

import GUI.UserHolder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public Button btnEdit;
    @FXML
    private BorderPane homeLayout;
    @FXML
    private BorderPane profileLayout;

    @FXML
    private Label lFirstName;

    @FXML
    private Label lLastName;

    @FXML
    private Label lGender;

    @FXML
    private Label lCountry;

    @FXML
    private Label lFavouriteGenre;

    @FXML
    private Label lFavouriteAuthor;
    //TODO pobierac z biblioteki najczesciej wystepujace gatunki

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User u = getUserData();
        lFirstName.setText(u.firstName);
        lLastName.setText(u.lastName);
        lGender.setText(u.gender);
        lCountry.setText(u.country);
        lFavouriteGenre.setText(getFavouriteGenre());
        lFavouriteAuthor.setText(null); //TODO zrobic funkcje do pobierania ulubionego autora
        UserHolder.getInstance().setUser(u);
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

    private User getUserData(){
        Socket socket = null;
        try {
            socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("GET user data");
            outputStream.writeObject(UserHolder.getInstance().getUser().username);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return (User) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFavouriteGenre(){
        Socket socket = null;
        try {
            socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("GET favourite genre");
            outputStream.writeObject(UserHolder.getInstance().getUser().username);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return (String) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
