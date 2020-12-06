package GUI.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public PasswordField password;
    public TextField username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void login(MouseEvent mouseEvent) throws IOException {
        User user = new User();
        user.username = username.getText();
        user.password = password.getText();

        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(user);

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Parent home = FXMLLoader.load(getClass().getResource("../views/home.fxml"));
        Scene homeScene = new Scene(home);
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setTitle("Private Library");
        window.setScene(homeScene);
        window.show();
    }

    public void register(MouseEvent mouseEvent) throws IOException {
        Parent register = FXMLLoader.load(getClass().getResource("../views/register.fxml"));
        Scene registerScene = new Scene(register);
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setTitle("Register");
        window.setScene(registerScene);
        window.show();
    }
}
