/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alfonsopb.puretask;

/**
 *
 * @author Alfon
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TaskManagerController {

    @FXML
    private ListView<Task> listView;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;

    private TaskDAO taskDAO = new TaskDAO();
    private ObservableList<Task> taskList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        listView.setItems(taskList);
        listView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null || task.getTitle() == null) {
                    setText(null);
                } else {
                    setText(task.getTitle());
                }
            }
        });
        taskList.setAll(taskDAO.getAllTasks());
    }

    @FXML
    private void handleAddTask() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        Task task = new Task(0, title, description, false);
        taskDAO.addTask(task);
        taskList.setAll(taskDAO.getAllTasks());
        titleField.clear();
        descriptionField.clear();
    }
}
