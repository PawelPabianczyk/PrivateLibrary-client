package GUI.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import models.Book;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class AddBookController implements Initializable {
    public TextField tfTitle;
    public TextField tfAuthor;
    public TextField tfPublisher;
    public TextField tfLanguage;
    public TextField tfGenre;
    public Button btnAdd;
    public TextArea taDescription;
    public DatePicker dpPublishDate;
    public DatePicker dpDateOfReturn;
    public Label lbNumberOfBooks;
    private ExecutorService ex;
    private ArrayList<Task> taskList;
    private int numberOfBooks;
    private final Semaphore available = new Semaphore(1);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskList = new ArrayList<>();
        numberOfBooks = 0;
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
        Book book = new Book();
        book.setTitle(tfTitle.getText());
        book.setAuthor(tfAuthor.getText());
        book.setDescription(taDescription.getText());
        book.setLanguage(tfLanguage.getText());
        book.setGenre(tfGenre.getText());
        book.setPublishDate(dpPublishDate.getValue());
        book.setReturnDate(dpDateOfReturn.getValue());
        book.setPublisher(tfPublisher.getText());

        String taskName = "Task " + book.id;
        Task task = new Task(taskName, book);

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
        this.tfAuthor.clear();
        this.tfPublisher.clear();
        this.dpPublishDate.getEditor().clear();
        this.dpPublishDate.setValue(null);
        this.dpDateOfReturn.getEditor().clear();
        this.dpDateOfReturn.setValue(null);
        this.tfLanguage.clear();
        this.tfGenre.clear();
        this.taDescription.clear();
    }


    class Task implements Runnable {
        private Book book;
        private String name;

        public Task(String name, Book book) {
            this.name = name;
            this.book = book;
        }

        public void run() {
            try {
                Socket socket = new Socket("localhost", 4444);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println(book.toString());
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