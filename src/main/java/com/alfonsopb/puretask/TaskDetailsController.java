/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.alfonsopb.puretask;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskDetailsController {

    @FXML
    private TextField titleTextField;
    
    @FXML
    private TextArea descriptionTextArea;
    
    private Task task;
    private TaskDAO taskDAO = new TaskDAO();
    private TaskManagerController taskManagerController;

    public void setTask(Task task) {
        this.task = task;
        titleTextField.setText(task.getTitle());
        descriptionTextArea.setText(task.getDescription());
    }
    
    @FXML
    private void handleCompleteTask() {
        if (task != null) {
            task.setCompleted(true);
            taskDAO.updateTask(task);
            taskManagerController.updateTaskList();
            closeWindow();
        }
    }
    
    private void closeWindow() {
        Stage stage = (Stage) titleTextField.getScene().getWindow();
        stage.close();
    }
    
    public void setTaskManagerController(TaskManagerController taskManagerController) {
        this.taskManagerController = taskManagerController;
    }
    
}
