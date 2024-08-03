package com.alfonsopb.puretask;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;

public class KanbanBoardController {
    @FXML
    private ListView<Task> toDoListView;
    @FXML
    private ListView<Task> inProgressListView;
    @FXML
    private ListView<Task> doneListView;
    @FXML
    private VBox toDoColumn;
    @FXML
    private VBox inProgressColumn;
    @FXML
    private VBox doneColumn;

    private TaskDAO taskDAO = new TaskDAO();

    @FXML
    public void initialize() {
        toDoListView.setItems(FXCollections.observableArrayList(taskDAO.getTasksByStatus("To Do")));
        inProgressListView.setItems(FXCollections.observableArrayList(taskDAO.getTasksByStatus("In Progress")));
        doneListView.setItems(FXCollections.observableArrayList(taskDAO.getTasksByStatus("Done")));

        configureDragAndDrop(toDoListView, "To Do");
        configureDragAndDrop(inProgressListView, "In Progress");
        configureDragAndDrop(doneListView, "Done");
    }

    private void configureDragAndDrop(ListView<Task> listView, String status) {
        listView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
            @Override
            public ListCell<Task> call(ListView<Task> param) {
                ListCell<Task> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(Task item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                };

                cell.setOnDragDetected(event -> {
                    if (cell.getItem() == null) {
                        return;
                    }

                    Dragboard dragboard = cell.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(String.valueOf(cell.getItem().getId()));
                    dragboard.setContent(content);
                    event.consume();
                });

                cell.setOnDragOver(event -> {
                    if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                cell.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {
                        int taskId = Integer.parseInt(db.getString());
                        Task task = taskDAO.getTaskById(taskId);
                        if (task != null) {
                            task.setStatus(status);
                            taskDAO.updateTask(task);
                            refreshLists();
                            success = true;
                        }
                    }
                    event.setDropCompleted(success);
                    event.consume();
                });

                cell.setOnDragEntered(event -> {
                    if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
                        cell.setStyle("-fx-background-color: lightblue;");
                    }
                    event.consume();
                });

                cell.setOnDragExited(event -> {
                    cell.setStyle("");
                    event.consume();
                });

                return cell;
            }
        });

        listView.setOnDragOver(event -> {
            if (event.getGestureSource() != listView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        listView.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                int taskId = Integer.parseInt(db.getString());
                Task task = taskDAO.getTaskById(taskId);
                if (task != null) {
                    task.setStatus(status);
                    taskDAO.updateTask(task);
                    refreshLists();
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        listView.setOnDragEntered(event -> {
            if (event.getGestureSource() != listView && event.getDragboard().hasString()) {
                listView.setStyle("-fx-background-color: lightgreen;");
            }
            event.consume();
        });

        listView.setOnDragExited(event -> {
            listView.setStyle("");
            event.consume();
        });
    }
    
    public void handleShowTaskDetails(ActionEvent event) {
    Task selectedTask = getSelectedTask();
    if (selectedTask != null) {
        showTaskDetails(selectedTask);
    } else {
        showNoTaskSelectedDialog();
    }
}
    
    private Task getSelectedTask() {
    if (toDoListView.getSelectionModel().getSelectedItem() != null) {
        return toDoListView.getSelectionModel().getSelectedItem();
    } else if (inProgressListView.getSelectionModel().getSelectedItem() != null) {
        return inProgressListView.getSelectionModel().getSelectedItem();
    } else if (doneListView.getSelectionModel().getSelectedItem() != null) {
        return doneListView.getSelectionModel().getSelectedItem();
    }
    return null;
}

    private void showTaskDetails(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("taskDetails.fxml"));
            Parent root = loader.load();

            TaskDetailsController controller = loader.getController();
            controller.setTask(task);

            Stage stage = new Stage();
            stage.setTitle("Task Details");
            stage.setScene(new Scene(root));
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

    private void refreshLists() {
        toDoListView.setItems(FXCollections.observableArrayList(taskDAO.getTasksByStatus("To Do")));
        inProgressListView.setItems(FXCollections.observableArrayList(taskDAO.getTasksByStatus("In Progress")));
        doneListView.setItems(FXCollections.observableArrayList(taskDAO.getTasksByStatus("Done")));
    }
}