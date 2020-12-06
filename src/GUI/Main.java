package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent login = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        Scene loginScene = new Scene(login);
        window = primaryStage;
        window.setTitle("Private Library");
        window.setScene(loginScene);
        window.show();
    }


    public static void main(String[] args){
        launch(args);
    }
}