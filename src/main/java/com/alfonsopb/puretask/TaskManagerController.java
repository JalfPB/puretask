/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alfonsopb.puretask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TaskManagerController {

    @FXML
    private ListView<Task> allTasksListView;
    @FXML
    private ListView<Task> completedTasksListView;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;

    private TaskDAO taskDAO = new TaskDAO();
    private ObservableList<Task> allTasksList = FXCollections.observableArrayList();
    private ObservableList<Task> completedTasksList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        allTasksListView.setItems(allTasksList);
        completedTasksListView.setItems(completedTasksList);

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

        loadTasks();
    }

    private void loadTasks() {
        allTasksList.setAll(taskDAO.getAllTasks());
        completedTasksList.setAll(taskDAO.getCompletedTasks());
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
}