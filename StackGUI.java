import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Alert;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.paint.Color;

public class StackGUI extends Application {

    private FixedSizeStack<String> stack; // Adjust the capacity as needed

    private Label stackLabel;
    private String sizeInput;

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            this.primaryStage = primaryStage;
            primaryStage.setTitle("Stack Implementation");

            // Create buttons
            Button setSizeButton = createButton("Set Size", "tomato");
            Button pushButton = createButton("Push", "tomato");
            Button popButton = createButton("Pop", "tomato");
            Button peekButton = createButton("Peek", "tomato");

            // Set actions for the buttons
            setSizeButton.setOnAction(e -> {
                sizeInput = showInputDialog("Enter the size of the stack");
                try {
                    int newSize = Integer.parseInt(sizeInput);
                    if (newSize >= 0) {
                        if (stack == null || newSize != stack.size()) {
                            stack = new FixedSizeStack<>(newSize);

                            while (!stack.isEmpty()) {
                                stack.pop();
                            }
                            showLoadingPopup("Creating the Stack");
                            stack.resize(newSize);
                            updateStackView();
                        }
                        showAlert("Stack size set to " + newSize, Duration.seconds(1.5));
                    } else {
                        showAlert("Please enter a non-negative integer for the stack size.", Duration.seconds(1.5));
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Invalid input. Please enter a valid integer for the stack size.", Duration.seconds(1.5));
                }
            });

          
            pushButton.setOnAction(e -> {
                String Input = showInputDialog("Enter the element to push");
              
                try{
                if (! stack.isFull()) {
                    if (Input == "") {
                        showLoadingPopup("Pushing the element onto stack");
                        showAlert("Please enter a valid input ", Duration.seconds(1.5));
                    }

                    else {

                        stack.push(Input);
                        updateStackView();
                        showLoadingPopup("Pushing the element onto stack");
                        showAlert("Operation completed !", Duration.seconds(1.5));

                   }
                
                   
                }
               
 	      
 	       else{
                        showLoadingPopup("Pushing the element onto stack");
                        showAlert("Stack is full. Cannot push", Duration.seconds(1.5));
                   	} 
                 }
                 catch(Exception ex){
                   	}
                   	
                   	   
            });

            popButton.setOnAction(e -> {
                showLoadingPopup("Popping element from the stack");
                if (!stack.isEmpty()) {
                    stack.pop();
                    updateStackView();
                    showAlert("Operation completed!", Duration.seconds(1.5));
                }
                
                 else {
                    showAlert("Stack is empty. Cannot pop.", Duration.seconds(1.5));
                }
            });

            peekButton.setOnAction(e -> {
                showLoadingPopup("Fetching the top element. ");

                if (!stack.isEmpty()) {
                    showAlert("Operation completed! Top element: " + stack.peek(), Duration.seconds(1.5));
                } else {
                    showAlert("Operation completed! Stack is empty. Cannot peek.", Duration.seconds(1.5));
                }
            });

            // Layout
            VBox mainLayout = new VBox(10, createCreditLablel(), createTitleLabel(),
                    createButtonLayout(setSizeButton, pushButton, popButton, peekButton), Space());

            mainLayout.setAlignment(Pos.CENTER);

            StackPane layout = new StackPane(mainLayout);
            // colour
            layout.setStyle("-fx-background-color: lightgreen;");

            // Set the scene
            Scene scene = new Scene(layout, 1600, 800);

            primaryStage.setScene(scene);

            // Show the stage
            primaryStage.show();
            // Initialize stackLabel after primaryStage.show()
            stackLabel = new Label("CURRENT STACK: ");
            stackLabel.setStyle("-fx-font-size: 36; -fx-font-weight: bold;"); // Adjust the font size as needed

            mainLayout.getChildren().add(stackLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setMinWidth(200);
        button.setMinHeight(100);

        button.setStyle("-fx-font-size: 24;-fx-background-color: " + color + "; -fx-text-fill: black;");

        return button;
    }

    // Layout
    private Label Space() {
        Label space = new Label("  ");
        return space;
    }

    private Label createTitleLabel() {
        Label titleLabel1 = new Label("CHOOSE AN ACTION TO PERFORM :\n  ");
        titleLabel1.setStyle("-fx-font-size: 36; -fx-font-weight: bold;");
        return titleLabel1;
    }

    private Label createCreditLablel() {
        Label creditLabel = new Label("22BCE9727\n22BCE9165\n22BCE8665\n ");
        creditLabel.setStyle("-fx-font-size: 36; -fx-font-weight: bold;");
        return creditLabel;
    }

    private HBox createButtonLayout(Button... buttons) {
        HBox buttonLayout = new HBox(10, buttons);
        buttonLayout.setAlignment(Pos.CENTER);
        return buttonLayout;
    }

    private String showInputDialog(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("User Input");
        dialog.setHeaderText(null);

        dialog.setContentText(prompt);
        dialog.getDialogPane().lookup(".text-input").setStyle("-fx-control-inner-background: tomato");
        dialog.getDialogPane().setStyle("-fx-background-color: lightgreen;");

        return dialog.showAndWait().orElse(null);
    }

    private void showAlert(String message, Duration delay) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.getDialogPane().setStyle("-fx-background-color: lightgreen;");

        PauseTransition pause = new PauseTransition(delay);
        pause.setOnFinished(event -> Platform.runLater(() -> alert.showAndWait()));
        pause.play();
    }

    private void updateStackView() {
        // Update the stack view
        StringBuilder stackString = new StringBuilder("Current Stack: \n");
        for (String element : stack) {
            stackString.append("           ").append(element + "\n");
        }

        Platform.runLater(() -> {
            stackLabel.setText(stackString.toString());
        });
    }

    private void showLoadingPopup(String loadingMessage) {
        Stage loadingStage = new Stage();
        loadingStage.initOwner(primaryStage);
        loadingStage.setTitle("Loading");

        // Create a ProgressBar
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setStyle("-fx-accent: tomato;"); // Blue color

        // Create a Label to display the loading message
        Label loadingLabel = new Label(loadingMessage);

        VBox loadingLayout = new VBox(10, loadingLabel, progressBar);

        loadingLayout.setAlignment(Pos.CENTER);
        loadingLayout.setStyle("-fx-background-color: lightgreen; -fx-padding: 20px;");

        Scene loadingScene = new Scene(loadingLayout, 1200, 600);
        loadingStage.setScene(loadingScene);

        // Prevent manual closing of the pop-up window
        loadingStage.setOnCloseRequest(WindowEvent::consume);

        // Simulate loading to 100%
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                final double progress = i / 100.0;
                try {
                    Thread.sleep(10); // Simulate some work being done
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Update progress bar on the JavaFX Application Thread
                Platform.runLater(() -> progressBar.setProgress(progress));
            }

            // Close the loading pop-up window once loading is complete
            Platform.runLater(() -> {
                loadingStage.close();
            });
        }).start();

        // Show the loading pop-up window
        loadingStage.show();
    }
}

