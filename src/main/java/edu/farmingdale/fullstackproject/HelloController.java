package edu.farmingdale.fullstackproject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HelloController {
    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, Integer> idColumn; // Column for ID
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> departmentColumn;
    @FXML
    private TableColumn<Person, String> majorColumn;
    @FXML
    private TableColumn<Person, String> emailColumn;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField imageURLField; // Field for image URL
    @FXML
    private ImageView imageView;

    private final ObservableList<Person> personList = FXCollections.observableArrayList();
    private Image defaultLogoImage; // Variable to hold the default logo image

    @FXML
    public void initialize() {
        // Set the default logo image
        defaultLogoImage = new Image("file:///C:/Users/antho/Downloads/account_circle_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24.png");
        imageView.setImage(defaultLogoImage); // Set default image in ImageView

        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        departmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartment()));
        majorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMajor()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        tableView.setItems(personList);
        // Add a listener to update the ImageView when a row is selected
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                imageView.setImage(new Image(newSelection.getImageURL())); // Set the image from the selected Person
            } else {
                imageView.setImage(defaultLogoImage); // Revert to default logo when no selection
            }
        });
    }

    @FXML
    private void handleAddAction() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String department = departmentField.getText();
        String major = majorField.getText();
        String email = emailField.getText();
        String imageURL = imageURLField.getText(); // Get the image URL from the text field

        Person person = new Person(firstName, lastName, department, major, email, imageURL);
        personList.add(person);

        // Update ImageView with the newly added person's image
        imageView.setImage(new Image(imageURL)); // Display the image in the ImageView

        // Optionally clear fields after adding
        clearFields();
    }

    @FXML
    private void handleClearAction() {
        clearFields();
    }

    @FXML
    private void handleDeleteAction() {
        // Get the selected person from the TableView
        Person selectedPerson = tableView.getSelectionModel().getSelectedItem();

        // Check if a person is selected
        if (selectedPerson != null) {
            // Remove the selected person from the list
            personList.remove(selectedPerson);
        } else {
            // Show an alert if no person is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table to delete.");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        departmentField.clear();
        majorField.clear();
        emailField.clear();
        imageURLField.clear(); //
        imageView.setImage(defaultLogoImage); // Reset ImageView to the default logo
    }

    @FXML
    private void handleQuitAction() {
        // Close the application
        System.exit(0);
    }

    @FXML
    private void handleSaveAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Records");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Person person : personList) {
                    writer.write(person.getId() + "," + person.getFirstName() + "," + person.getLastName() + ","
                            + person.getDepartment() + "," + person.getMajor() + "," + person.getEmail() + ","
                            + person.getImageURL()); // Include image URL in the saved file
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception
            }
        }
    }

    @FXML
    private void handleOpenAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Records");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                personList.clear(); // Clear existing records before loading new ones
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 7) { // Expecting 7 fields now
                        int id = Integer.parseInt(data[0]); // Parse the ID
                        String firstName = data[1];
                        String lastName = data[2];
                        String department = data[3];
                        String major = data[4];
                        String email = data[5];
                        String imageURL = data[6]; // Get the image URL from the file

                        // Create the Person object and add it to the list
                        Person person = new Person(firstName, lastName, department, major, email, imageURL);
                        personList.add(person);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception
            }
        }
    }
}
