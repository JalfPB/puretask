# PureTask

**PureTask** is a desktop application developed using Java and JavaFX, designed to help users manage their tasks efficiently. It features a user-friendly interface where users can add, view, and manage their tasks. Each task includes a title, description, and a completion status.

## Features

- **Add New Tasks**: Users can input a title and description for new tasks, and save them to the database.
- **View Tasks**: The application displays a list of all tasks, showing their titles.
- **Task Management**: Users can mark tasks as completed and update task details.
- **Persistent Storage**: The application uses SQLite for persistent storage, ensuring that tasks are saved between sessions.

## Installation

To run this application on your local machine, follow these steps:

### Prerequisites

- Ensure you have Java Development Kit (JDK) installed. You can download it from [Oracle's official site](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
- Ensure you have a development environment set up, such as [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) or [Eclipse](https://www.eclipse.org/downloads/).

### Clone the Repository

```bash
git clone https://github.com/jalfpb/puretask.git
cd puretask
```

### Set Up the Database

1. Create a directory named `db` in the root of the project.
2. The database file `tasks.db` will be created automatically in this directory when you run the application.

### Build and Run the Application

1. Open the project in your preferred development environment.
2. Build the project to resolve dependencies.
3. Run the `App` class.

## Usage

- **Adding a Task**: Enter the title and description in the respective fields and click "Add Task".
- **Viewing Tasks**: The tasks will be listed in the ListView. Click on a task to view its details.
- **Marking a Task as Completed**: This feature can be implemented as needed.

## Project Structure

```
src/
|-- main/
|   |-- java/
|   |   |-- com/
|   |   |   |-- example/
|   |   |   |   |-- TaskManagerApp.java
|   |   |   |   |-- TaskManagerController.java
|   |   |   |   |-- Task.java
|   |   |   |   |-- TaskDAO.java
|   |   |   |   |-- Database.java
|   |-- resources/
|       |-- com/
|           |-- example/
|               |-- primary.fxml
db/
```

## Contributing

If you would like to contribute to this project, please fork the repository and create a pull request. We welcome all contributions that improve the application.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any questions or suggestions, please contact:

- **Alfonso** - [apalominobaile@gmail.com](mailto:apalominobaile@gmail.com)
- **Project Link** - https://github.com/jalfpb/puretask
