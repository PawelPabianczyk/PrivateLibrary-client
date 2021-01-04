package GUI.controllers;

import GUI.UserHolder;
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
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public PasswordField password;
    public TextField username;

    //TODO sprawdzanie poprawnosci wprowadzonych danych
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void login(MouseEvent mouseEvent) throws IOException {
        User user = new User();
        user.setUsername(username.getText());
        user.setPassword(password.getText());

        Boolean isLoginDataValid = false;

        if(!user.getUsername().isEmpty() && !user.getPassword().isEmpty()){
            try {
                Socket socket = new Socket("localhost", 4444);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject("PUT login");
                outputStream.writeObject(user);

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                isLoginDataValid = (Boolean) inputStream.readObject();

            } catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }

        if(isLoginDataValid){

            UserHolder.getInstance().setUser(user);

            Parent home = FXMLLoader.load(getClass().getResource("../views/home.fxml"));
            Scene homeScene = new Scene(home);
            Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
            window.setTitle("Private Library");
            window.setScene(homeScene);
            window.show();
        }
        else{
            System.out.println("Login and password are incorrect.");
            username.clear();
            password.clear();
        }
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
