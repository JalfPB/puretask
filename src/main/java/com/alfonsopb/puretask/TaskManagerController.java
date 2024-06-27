/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alfonsopb.puretask;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TaskManagerController {

    @FXML
    private ListView<Task> allTasksListView;
    @FXML
    private ListView<Task> completedTasksListView;
    @FXML
    private ListView<Task> incompletedTasksListView;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;

    private TaskDAO taskDAO = new TaskDAO();
    private ObservableList<Task> allTasksList = FXCollections.observableArrayList();
    private ObservableList<Task> completedTasksList = FXCollections.observableArrayList();
    private ObservableList<Task> incompletedTasksList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        allTasksListView.setItems(allTasksList);
        completedTasksListView.setItems(completedTasksList);
        incompletedTasksListView.setItems(incompletedTasksList);

        allTasksListView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null || task.getTitle() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Text text = new Text(task.getTitle());
                    if (task.isCompleted()) {
                        text.setFill(Color.GRAY);
                        text.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 12));
                    } else {
                        text.setFill(Color.BLACK);
                        text.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 12));
                    }
                    setGraphic(text);
                }
            }
        });

        completedTasksListView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null || task.getTitle() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Text text = new Text(task.getTitle());
                    text.setFill(Color.GRAY);
                    text.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 12));
                    setGraphic(text);
                }
            }
        });
        
        incompletedTasksListView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null || task.getTitle() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Text text = new Text(task.getTitle());
                    text.setFill(Color.BLACK);
                    text.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 12));
                    setGraphic(text);
                }
            }
        });
        
        allTasksListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Task selectedTask = allTasksListView.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    showTaskDetails(selectedTask);
                }
            }
        });

        loadTasks();
    }

    private void loadTasks() {
        allTasksList.setAll(taskDAO.getAllTasks());
        completedTasksList.setAll(taskDAO.getCompletedTasks());
        incompletedTasksList.setAll(taskDAO.getIncompletedTasks());
    }

    @FXML
    private void handleAddTask() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        Task task = new Task(0, title, description, false);
        taskDAO.addTask(task);
        loadTasks();
        titleField.clear();
        descriptionField.clear();
    }

    @FXML
    private void handleCompleteTask() {
        Task selectedTask = allTasksListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            selectedTask.setCompleted(true);
            taskDAO.updateTask(selectedTask);
            loadTasks();
        }
    }

    @FXML
    private void handleEditTask() {
        Task selectedTask = allTasksListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            String title = titleField.getText();
            String description = descriptionField.getText();
            selectedTask.setTitle(title);
            selectedTask.setDescription(description);
            taskDAO.updateTask(selectedTask);
            loadTasks();
            titleField.clear();
            descriptionField.clear();
        }
    }
    
    @FXML
    private void handleDeleteTask() {
        Task selectedTask = allTasksListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskDAO.deleteTask(selectedTask.getId());
            loadTasks();
        }
    }
    
    private void showTaskDetails(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("taskDetails.fxml"));
            VBox taskDetailsRoot = loader.load();

            TaskDetailsController controller = loader.getController();
            controller.setTask(task);
            controller.setTaskManagerController(this);

            Stage stage = new Stage();
            stage.setTitle("Task Details");
            stage.setScene(new Scene(taskDetailsRoot));
            stage.setOnCloseRequest(event -> updateTaskList());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void showNoTaskSelectedDialog() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("No Task Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select a task to view its details.");
        alert.showAndWait();
    }
    
    @FXML
    private void handleShowTaskDetails() {
        Task selectedTask = allTasksListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            showTaskDetails(selectedTask);
        } else {
            showNoTaskSelectedDialog();
        }
    }
    
    public void updateTaskList() {
        loadTasks();
        /*allTasksList.setAll(taskDAO.getAllTasks());
        completedTasksList.setAll(taskDAO.getCompletedTasks());
        incompletedTasksList.setAll(taskDAO.getIncompletedTasks());*/
    }
    
}