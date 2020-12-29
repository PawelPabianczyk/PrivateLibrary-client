package GUI.controllers;

import GUI.UserHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Book;
import models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class AddBookController implements Initializable {
    public TextField tfTitle;
    public TextField tfLanguage;
    public Button btnAdd;
    public TextArea taDescription;
    public DatePicker dpPublishDate;
    public DatePicker dpDateOfReturn;
    public Label lbNumberOfBooks;

    @FXML
    public ChoiceBox<String> choiceGenre;
    @FXML
    public ChoiceBox<String> choicePublisher;
    @FXML
    public BorderPane bp;

    @FXML
    public ChoiceBox<String> choiceAuthor;

    private ExecutorService ex;
    private ArrayList<Task> taskList;
    private int numberOfBooks;
    private final Semaphore available = new Semaphore(1);

    private ObservableList<String> genresList;
    private ObservableList<String> publishersList;
    private ObservableList<String> authorsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskList = new ArrayList<>();
        numberOfBooks = 0;

        genresList = getGenresList();
        publishersList = getPublishersList();
        authorsList = getAuthorsList();

        choiceGenre.setValue("Classic");
        choiceGenre.setItems(genresList);

        choicePublisher.setValue("Supernowa");
        choicePublisher.setItems(publishersList);

        choiceAuthor.setValue("George Orwell");
        choiceAuthor.setItems(authorsList);
    }

    public void sendBookToServer(MouseEvent mouseEvent) {

        if (taskList.isEmpty())
            return;

        if (ex == null) {
            ex = Executors.newFixedThreadPool(taskList.size());
        }

        for (Task task :
                taskList) {
            ex.execute(task);
        }

        ex.shutdown();

        while(!ex.isTerminated()){

        }
        showNumberOfBooks();

        taskList.clear();
        ex = null;
    }


    public void addNewBook(MouseEvent mouseEvent) {
        User u = UserHolder.getInstance().getUser();

        Book book = getBook();

        String taskName = "Task ";
        Task task = new Task(u.getUsername(), taskName, book);

        taskList.add(task);
        incrementNumberOfBooks();

        System.out.println(taskName + " added");
        clearForm();
    }

    public synchronized void incrementNumberOfBooks() {
        numberOfBooks++;
        lbNumberOfBooks.setText("Number of added books : " + numberOfBooks);
    }

    public synchronized void showNumberOfBooks() {
        lbNumberOfBooks.setText("Number of added books : " + numberOfBooks);
    }

    private void clearForm() {
        this.tfTitle.clear();
        this.choicePublisher.setValue("Supernowa");
        this.choiceAuthor.setValue("George Orwell");
        this.dpPublishDate.getEditor().clear();
        this.dpPublishDate.setValue(null);
        this.dpDateOfReturn.getEditor().clear();
        this.dpDateOfReturn.setValue(null);
        this.tfLanguage.clear();
        this.choiceGenre.setValue("Classic");
        this.taDescription.clear();
    }

    private Book getBook(){
        Book book = new Book();
        book.setTitle(tfTitle.getText());
        book.setDescription(taDescription.getText());
        book.setLanguage(tfLanguage.getText());
        book.setGenre(choiceGenre.getValue());
        book.setAuthor(choiceAuthor.getValue());
        book.setPublishDate(dpPublishDate.getValue());
        book.setReturnDate(dpDateOfReturn.getValue());
        book.setPublisher(choicePublisher.getValue());
        return book;
    }

    private ObservableList<String> getGenresList(){
        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("GET genres list");

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ArrayList<String> genres = (ArrayList<String>) inputStream.readObject();

            return FXCollections.observableArrayList(genres);

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private ObservableList<String> getPublishersList(){
        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("GET publishers list");

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ArrayList<String> publishers = (ArrayList<String>) inputStream.readObject();

            return FXCollections.observableArrayList(publishers);

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private ObservableList<String> getAuthorsList(){
        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("GET authors list");

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ArrayList<String> authors = (ArrayList<String>) inputStream.readObject();

            return FXCollections.observableArrayList(authors);

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    class Task implements Runnable {
        private Book book;
        private String name;
        private String username;

        public Task(String username, String name, Book book) {
            this.name = name;
            this.book = book;
            this.username = username;
        }

        public void run() {
            try {
                Socket socket = new Socket("localhost", 4444);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject("POST books");

                outputStream.writeObject(book);
                outputStream.writeObject(username);
                available.acquire();
                numberOfBooks--;

            } catch (IOException | InterruptedException exception) {
                exception.printStackTrace();
            }
            finally {
                available.release();
            }
            System.out.printf("%s ended\n", name);
        }
    }
}