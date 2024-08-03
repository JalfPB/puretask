package com.alfonsopb.puretask;

import javafx.scene.control.ListCell;
import javafx.scene.input.*;
import javafx.scene.text.Text;

public class TaskCell extends ListCell<Task> {

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (empty || task == null || task.getTitle() == null) {
            setText(null);
            setGraphic(null);
        } else {
            Text text = new Text(task.getTitle());
            setGraphic(text);

            // Configura el arrastre
            setOnDragDetected(event -> {
                if (task != null) {
                    Dragboard db = startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(String.valueOf(task.getId())); // Asumiendo que el task tiene un ID
                    db.setContent(content);
                    event.consume();
                }
            });
        }
    }
}
