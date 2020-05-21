import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import AddressFiles.AddressManager;
import AddressFiles.Address;

public class NumberOfQuestions {
    Stage window;
    Scene mainMenuScene;
    Scene numOfQuestScene;
    AddressManager manager;
    
    public NumberOfQuestions(Stage window, Scene mainMenu, AddressManager manager) {
        this.window = window;
        mainMenuScene = mainMenu;
        this.manager = manager;
    }

    
    public Scene createScene()  {
        /**Panes */
        BorderPane numOfQuestPageBorderPane = new BorderPane();
        HBox textFeildBox = new HBox(12);
        HBox buttonBox = new HBox(40);
       
        /**Page components */
        String introTextDescription = "Based on your settings this quiz session will contain " +
                                        manager.getLengthOfQuizList() + " questions in total.\n";

        Text introText = new Text(introTextDescription);
        Text errorText = new Text("");

        Button startQuizBtn = new Button("Start Quiz");
        Button goBackToMainMenuBtn = new Button(" <- Go Back");

        TextField  numOfQuestTextFeild = new TextField();
        Label numOfQuestLabel = new Label("Please enter how many questions you would like to receive " + 
        "(must be between 1 and " + manager.getLengthOfQuizList() +  ")");


       /** Modifications to conponents */
       introText.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, 20));
        
       //errorText.
       errorText.setFont(Font.font("Times", FontWeight.BOLD, 15));
       errorText.setFill(Color.RED);

       numOfQuestLabel.setFont(Font.font("Times", FontWeight.BOLD, 15));
       numOfQuestTextFeild.setFont(Font.font("Times", FontWeight.BOLD, 14));
       numOfQuestTextFeild.setAlignment(Pos.BASELINE_LEFT);
       numOfQuestTextFeild.setPrefColumnCount(5);
       
        /**Set actions to buttons */
      goBackToMainMenuBtn.setOnMouseEntered(e -> {
            numOfQuestScene.setCursor(Cursor.HAND);
       });
      goBackToMainMenuBtn.setOnMouseExited(e -> {
            numOfQuestScene.setCursor(Cursor.DEFAULT);
       });

       goBackToMainMenuBtn.setOnAction(e -> {
            numOfQuestScene.setCursor(Cursor.WAIT);
            setMainMenuScene();
       });

       startQuizBtn.setOnMouseEntered(e -> {
            numOfQuestScene.setCursor(Cursor.HAND);
       });
       startQuizBtn.setOnMouseExited(e -> {
            numOfQuestScene.setCursor(Cursor.DEFAULT);
       });

       startQuizBtn.setOnAction(e -> {
            numOfQuestScene.setCursor(Cursor.WAIT);
            verifyInput(numOfQuestTextFeild,errorText,numOfQuestPageBorderPane);
            numOfQuestScene.setCursor(Cursor.DEFAULT);
       });

       
       /**Add to Panes */
       textFeildBox.getChildren().addAll(numOfQuestLabel,numOfQuestTextFeild);
       buttonBox.getChildren().addAll(errorText,goBackToMainMenuBtn,startQuizBtn);
       
       numOfQuestPageBorderPane.setTop(introText);
       numOfQuestPageBorderPane.setCenter(textFeildBox);
       numOfQuestPageBorderPane.setBottom(buttonBox);

       /**Modifications to Panes */

        /**Set margins and alignment */
       HBox.setMargin(numOfQuestLabel, new Insets(2, 5, 10, 5));
       HBox.setMargin(numOfQuestTextFeild, new Insets(2, 5, 10, 5));
       HBox.setMargin(buttonBox, new Insets(15, 5, 5, 5));
       buttonBox.setAlignment(Pos.CENTER);
      
       /**Insert and return Scene */
       numOfQuestScene = new Scene(numOfQuestPageBorderPane);
       return numOfQuestScene;
    }

    private void setMainMenuScene(){
        manager.getAddressQuizList().clear();
        window.setScene(mainMenuScene);
    }

    private void verifyInput(TextField tf,Text errorText,BorderPane bp) {
        long input = 0;
       
        try {
            input = Integer.parseInt(tf.getText());
        } catch (Exception e) {
            errorText.setText("Invalid input");
            return;
        }

        if(input > 0 && input <= manager.getLengthOfQuizList()){
            errorText.setText("In Range");
        }

        else{
            errorText.setText("Input not in range");
        }
    }//end of verifyInput
    
}//end of class