package GUI.controllers;

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

public class ExploreController implements Initializable {

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String> author;

    @FXML
    private TableColumn<Book, String> genre;

    @FXML
    private TableColumn<Book, String> owner;

    @FXML
    private TableColumn<Book, LocalDate> dateAdded;

    @FXML
    private ChoiceBox<String> choiceCategory;

    @FXML
    private TextField tfPhrase;

    private ObservableList<String> categories = FXCollections.observableArrayList(
            "All",
            "Title",
            "Author",
            "Genre",
            "Owner"
    );

    private ObservableList<Book> data;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceCategory.setValue("All");
        choiceCategory.setItems(categories);
        data = FXCollections.observableArrayList(getBooks("All", null));
        showData(data);
    }

    private ArrayList<Book> getBooks(String category, String phrase) {
        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            switch (category){
                case "All":
                    outputStream.writeObject("GET all books");
                    break;
                default:
                    outputStream.writeObject("GET all books with phrase");
                    outputStream.writeObject(category);
                    outputStream.writeObject(phrase);
                    break;
            }

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
        data = FXCollections.observableArrayList(getBooks(category, phrase));
        showData(data);
        choiceCategory.setValue("All");
        tfPhrase.setText("Phrase");
    }

    private void showData(ObservableList<Book> data){
        title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        genre.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        dateAdded.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("dateAdded"));
        owner.setCellValueFactory(new PropertyValueFactory<Book, String>("owner"));
        booksTable.setItems(data);
    }
}
