package GUI.controllers;

import GUI.UserHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Book;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BooksController implements Initializable {

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String> author;

    @FXML
    private TableColumn<Book, String> genre;

    @FXML
    private TableColumn<Book, LocalDate> publishDate;

    @FXML
    private TableColumn<Book, String> status;

    @FXML
    private ChoiceBox<String> choiceCategory;

    @FXML
    private TextField tfPhrase;

    private ObservableList<String> categories = FXCollections.observableArrayList("All","Title", "Author", "Genre");

    private ObservableList<Book> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceCategory.setValue("All");
        choiceCategory.setItems(categories);
        data = FXCollections.observableArrayList(getUsersBooks("All"));
        showData(data);
    }

    private ArrayList<Book> getUsersBooks(String category){
        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            switch (category){
                case "All":
                    outputStream.writeObject("GET user books");
                    break;
                case "Title":
                    outputStream.writeObject("GET user books with title");
                    break;
                case "Author":
                    outputStream.writeObject("GET user books with author");
                    break;
                case "Genre":
                    outputStream.writeObject("GET user books with genre");
                    break;
            }
            outputStream.writeObject(UserHolder.getInstance().getUser().getUsername());

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ArrayList<Book> books = (ArrayList<Book>) inputStream.readObject();

            return books;

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void search(MouseEvent mouseEvent) {
        String category = choiceCategory.getValue();
        String phrase = tfPhrase.getText();
        data = FXCollections.observableArrayList(getUsersBooks(category));
        showData(data);
        choiceCategory.setValue("All");
        tfPhrase.setText("Phrase");
    }

    private void showData(ObservableList<Book> data){
        title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        genre.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        publishDate.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("publishDate"));
        status.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));
        booksTable.setItems(data);
    }
}
