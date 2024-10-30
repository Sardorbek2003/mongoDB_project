package org.example;

import com.mongodb.client.MongoDatabase;
import org.example.config.MongoDBConnection;
import org.example.model.Todo;
import org.example.model.User;
import org.example.repository.TodoRepository;
import org.example.repository.UserRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MongoDBConnection connection = new MongoDBConnection();
        MongoDatabase database = connection.getDatabase();

        UserRepository userRepository = new UserRepository(database);
        TodoRepository todoRepository = new TodoRepository(database);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register\n2. Login\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // buffer to clear

            switch (choice) {
                case 1: // Register
                    System.out.println("Username:");
                    String username = scanner.nextLine();
                    System.out.println("Password:");
                    String password = scanner.nextLine();
                    userRepository.save(new User(username, password));
                    System.out.println("User registered successfully!");
                    break;

                case 2: // Login
                    System.out.println("Username:");
                    username = scanner.nextLine();
                    System.out.println("Password:");
                    password = scanner.nextLine();
                    User user = userRepository.findByUsername(username);
                    if (user != null && user.getPassword().equals(password)) {
                        System.out.println("Login successful!");

                        boolean loggedIn = true;
                        while (loggedIn) {
                            System.out.println("\n1. Add Todo\n2. View Todos\n3. Remove Todo\n4. Logout");
                            int action = scanner.nextInt();
                            scanner.nextLine(); // buffer to clear

                            switch (action) {
                                case 1: // Add Todo
                                    System.out.println("Add Todo:");
                                    String todoTitle = scanner.nextLine();
                                    todoRepository.add(new Todo(user.getId(), todoTitle, false));
                                    System.out.println("Todo added successfully!");
                                    break;

                                case 2: // View Todos
                                    List<Todo> todos = todoRepository.findByUserId(user.getId());
                                    System.out.println("Your Todos:");
                                    for (Todo todo : todos) {
                                        System.out.println(todo.getId() + ": " + todo.getTitle());
                                    }
                                    break;

                                case 3: // Remove Todo
                                    System.out.println("Enter Todo ID to delete:");
                                    String todoIdToDelete = scanner.nextLine();
                                    todoRepository.remove(todoIdToDelete);
                                    System.out.println("Todo deleted successfully!");
                                    break;

                                case 4: // Logout
                                    loggedIn = false;
                                    System.out.println("Logged out successfully!");
                                    break;

                                default:
                                    System.out.println("Invalid choice, please try again.");
                            }
                        }
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case 3: // Exit
                    System.out.println("Exiting the application.");
                    connection.close();
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}

