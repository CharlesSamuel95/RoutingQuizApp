//package Program_Components;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import AddressFiles.*;

import java.text.NumberFormat;


public class NumberOfQuestions {
    private Stage window;
    private Scene mainMenuScene;
    private Scene numOfQuestScene;
    private AddressManager manager;
    private MainMenu mainMenuPage;
   
    
    public NumberOfQuestions(Scene mainMenu) {
        mainMenuPage = MainMenu.getInstance();
        this.window = mainMenuPage.getWindow();
        mainMenuScene = mainMenu;
        this.manager = mainMenuPage.getManager();
    }
    
    public Scene createScene()  {
        /**Panes */
        BorderPane numOfQuestPageBorderPane = new BorderPane();
        GridPane textFeildGridPane = new GridPane();
        HBox buttonBox = new HBox(40);
       
        /**Page components */
        ConformationWindow win = new ConformationWindow();
        NumberFormat formatQuestSize = NumberFormat.getInstance();
        formatQuestSize.setGroupingUsed(true);

        String introTextDescription = "Based on your settings this quiz session will contain " +
                                        formatQuestSize.format(manager.getLengthOfQuizList()) 
                                        + " questions in total.\n";

        Text introText = new Text(introTextDescription);
        Text errorText = new Text("");
        

        Button startQuizBtn = new Button("Start Quiz");
        Button goBackToMainMenuBtn = new Button(" <- Go Back");

        TextField  numOfQuestTextFeild = new TextField();
        Label numOfQuestLabel = new Label("Please enter how many questions you would like to receive " + 
        "(must be between 1 and " + manager.getLengthOfQuizList() +  ")");


       /** Modifications to conponents */
       introText.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, 20));
        
       errorText.setFont(Font.font("Times", FontWeight.BOLD, 15));
       errorText.setFill(Color.RED);
      

       numOfQuestLabel.setFont(Font.font("Times", FontWeight.BOLD, 18));
       numOfQuestTextFeild.setFont(Font.font("Times", FontWeight.BOLD, 17));
       numOfQuestTextFeild.setAlignment(Pos.BASELINE_LEFT);
       numOfQuestTextFeild.setPrefColumnCount(7);
       
       
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
            verifyInput(numOfQuestTextFeild,errorText);
            numOfQuestScene.setCursor(Cursor.DEFAULT);
       });

       window.setOnCloseRequest(e->{
        if(win.getWindow().showingProperty().get()){
          e.consume();
        }
  
        else{
          //Create a conformation Box.
          e.consume();
          win.createPopUpWindow(window);
        }
      });

       
       /**Add to Panes */
       textFeildGridPane.add(numOfQuestLabel, 0, 0);
       textFeildGridPane.add(numOfQuestTextFeild, 1, 0);
       textFeildGridPane.add(errorText, 1, 1);
       textFeildGridPane.add(buttonBox, 0, 4);

       buttonBox.getChildren().addAll(goBackToMainMenuBtn,startQuizBtn);
       
       numOfQuestPageBorderPane.setTop(introText);
       numOfQuestPageBorderPane.setCenter(textFeildGridPane);
       //numOfQuestPageBorderPane.setBottom(buttonBox);

       /**Modifications to Panes */
       numOfQuestPageBorderPane.disableProperty().bind(win.getWindow().showingProperty());

        /**Set margins and alignment */
       GridPane.setMargin(numOfQuestLabel, new Insets(2, 5, 10, 5));
       GridPane.setMargin(numOfQuestTextFeild, new Insets(2, 5, 10, 5));
       
       HBox.setMargin(buttonBox, new Insets(15, 5, 20, 5));
       buttonBox.setAlignment(Pos.CENTER);
      
       /**Insert and return Scene */
       numOfQuestScene = new Scene(numOfQuestPageBorderPane,950,180);

      /**Bind Panes and set alignment */
       textFeildGridPane.prefWidthProperty().bind(numOfQuestScene.widthProperty());
       textFeildGridPane.prefHeightProperty().bind(numOfQuestScene.heightProperty());
       textFeildGridPane.setAlignment(Pos.CENTER);

       numOfQuestPageBorderPane.prefWidthProperty().bind(numOfQuestScene.widthProperty());
       numOfQuestPageBorderPane.prefHeightProperty().bind(numOfQuestScene.heightProperty());
       BorderPane.setAlignment(numOfQuestPageBorderPane, Pos.CENTER);
       BorderPane.setAlignment(introText, Pos.CENTER);

       return numOfQuestScene;
    }

    private void setMainMenuScene(){
        manager.getAddressQuizList().clear();
        window.setScene(mainMenuScene);
    }

    private void verifyInput(TextField tf,Text errorText) {
        long input = 0;
        NumberFormat formatInput = NumberFormat.getInstance();
        formatInput.setGroupingUsed(true);
       
        try {
            input = Long.parseLong(tf.getText());
        } catch (Exception e) {
            errorText.setText("Invalid input");
            return;
        }

        if(input > 0 && input <= manager.getLengthOfQuizList()){
            createQuiz(input);
        }

        else{
            errorText.setText("Input not in range");
        }
    }//end of verifyInput

    private void createQuiz(long quizCount){
        //Quiz q = new Quiz(window, manager,quizCount,quizFormat);
        Quiz q = new Quiz(quizCount);
        window.setScene(q.createQuizScene());
    }
    
}//end of class