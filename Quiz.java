
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import AddressFiles.Address;
import AddressFiles.AddressManager;


//Try to make manager.sortQuizList sort list in list order
//Try to remove background of check and x mark images
//Create a conformation Box.
public class Quiz {
    private Stage window;
    private Stage resultStage = new Stage();
    private Scene quizScene;
    private Scene resultScene;
    private AddressManager manager;
    private MainMenu mainMenu;
    private long questCount = 0;

    private long questAttempted;
    private long questCorrect;
    private long questIncorrect;
    private int quizFormat;
    private int newQuizFormat;
    
    
    public Quiz(long questCount){
        mainMenu = MainMenu.getInstance();
        window = mainMenu.getWindow();
        manager = mainMenu.getManager();
        this.questCount = questCount;
        quizFormat = mainMenu.getQuizFormat();
    }
    
    public Scene createQuizScene(){
        /**Panes */
        BorderPane quizBP = new BorderPane();
        HBox addressHB = new HBox(14);

        /**Page components */
        ListView<Label> questionsLV = new ListView<>(populateListView());
        TextField answerTF = new TextField();
        Button checkAnswerBtn = new Button("Check Answer");
        Button submitQuizBtn = new Button("Submit Quiz");

        questAttempted = 0;
        questCorrect = 0;
        questIncorrect = 0;

        int index;
        Label address;
        Text checkBtnResult;

        
        /**Modifications to conponents */
        questionsLV.setPrefSize(120, 300);
        questionsLV.getSelectionModel().selectFirst();
        answerTF.setPrefColumnCount(3);

        index = questionsLV.getSelectionModel().getSelectedIndex();
        address = new Label(manager.getAddressQuizListItem(index).printPrefix());
        checkBtnResult = new Text(manager.getAddressQuizListItem(index).getAnswerResults());

        address.setFont(Font.font("Times", FontWeight.BOLD, 15));
        checkBtnResult.setFont(Font.font("Times", FontWeight.BOLD, 15));
        address.setGraphic(checkBtnResult);
        address.setContentDisplay(ContentDisplay.BOTTOM);
        
         /**Set actions */
        questionsLV.getSelectionModel().selectedItemProperty().addListener(ov ->{
            int indexTemp = questionsLV.getSelectionModel().getSelectedIndex();
            Address addressSelected = manager.getAddressQuizListItem(indexTemp);
            Text answerResultTemp = new Text(addressSelected.getAnswerResults());
            Label addressTemp = new Label(addressSelected.printPrefix());
            
            answerTF.setStyle(addressSelected.getTextFieldStyle());
            answerTF.setEditable(addressSelected.getEditableTextField());
            answerTF.setText(addressSelected.getUserRoutingNumber());

            addressTemp.setFont(Font.font("Times", FontWeight.BOLD, 15));
            answerResultTemp.setFont(Font.font("Times", FontWeight.BOLD, 15));
            addressTemp.setGraphic(answerResultTemp);
            addressTemp.setContentDisplay(ContentDisplay.BOTTOM);

            addressHB.getChildren().clear();
            addressHB.getChildren().addAll(addressTemp,answerTF);
            addressHB.setAlignment(Pos.CENTER);
        });


        checkAnswerBtn.setOnMouseEntered(e ->{
            quizScene.setCursor(Cursor.HAND);
        });

        checkAnswerBtn.setOnMouseExited(e ->{
            quizScene.setCursor(Cursor.DEFAULT);
        });

        checkAnswerBtn.setOnAction(e ->{
            checkUserInput(questionsLV,answerTF,address,addressHB);
        });

        submitQuizBtn.setOnMouseEntered(e ->{
            quizScene.setCursor(Cursor.HAND);
        });

        submitQuizBtn.setOnMouseExited(e ->{
            quizScene.setCursor(Cursor.DEFAULT);
        });

        submitQuizBtn.setOnAction(e ->{
            createResultScene();
        });

        window.setOnCloseRequest(e->{
            if(resultStage.showingProperty().get()){
              e.consume();
            }
      
            else{
              //Create a conformation Box.
            }
          });

        
        /**Add to Panes */
        addressHB.getChildren().addAll(address,answerTF);
        
        quizBP.setRight(checkAnswerBtn);
        quizBP.setLeft(questionsLV);
        
        quizBP.setCenter(addressHB);
        quizBP.setBottom(submitQuizBtn);

        /**Modifications to Panes */

         /**Set margins and alignment */
        quizBP.disableProperty().bind(resultStage.showingProperty());
        HBox.setMargin(address, new Insets(10, 15, 0, 15));

        BorderPane.setMargin(checkAnswerBtn, new Insets(0, 10, 0, 20));
        BorderPane.setMargin(submitQuizBtn, new Insets(0, 0, 20, 0));
        BorderPane.setMargin(questionsLV, new Insets(0, 20, 0, 0));

        BorderPane.setAlignment(checkAnswerBtn, Pos.CENTER);
        BorderPane.setAlignment(submitQuizBtn, Pos.CENTER);
        BorderPane.setAlignment(addressHB, Pos.CENTER);
        addressHB.setAlignment(Pos.CENTER);
        

        /**Insert and return Scene */
        quizScene = new Scene(quizBP);
        window.setTitle("Quiz");
        return quizScene;
    }//end of createQuizScene

    /**NOTE: try to make image background transparent */
    private void checkUserInput(ListView<Label> questionsLV, TextField answerTF, 
                                Label addressLabel, HBox addressHB) {

        int index = questionsLV.getSelectionModel().getSelectedIndex();
        Address address = manager.getAddressQuizListItem(index);
        String answear = address.getRoutingNumber();
        Text result = (Text)addressLabel.getGraphic();
        ImageView imgView;
       
        //prevents code below from executing if text field edit property is false
        if(!address.getEditableTextField()){
            return;
        }

        address.setTextFieldStyle("-fx-background-color:GREY;");
        address.setEditableTextField(false);
        address.setUserRoutingNumber(answerTF.getText());

        answerTF.setStyle(address.getTextFieldStyle());
        answerTF.setEditable(address.getEditableTextField());
        
        if(answear.compareToIgnoreCase(answerTF.getText()) == 0){
            address.setAnswerResults("Correct!!!");
            imgView = new ImageView(new Image("images/check-mark.png")); 
            questCorrect++;
        }

        else{
            address.setAnswerResults("Incorrect. The answer is " + address.getRoutingNumber());
            imgView = new ImageView(new Image("images/x-mark.png"));
            questIncorrect++;
        }

        questAttempted++;

        imgView.setFitHeight(15);
        imgView.setFitWidth(16);
        //imgView.setStyle("-fx-background-color: transparent;");
        
        ObservableList<Label> questLabel = questionsLV.getSelectionModel().getSelectedItems();
        questLabel.get(0).setGraphic(imgView);
        questLabel.get(0).setContentDisplay(ContentDisplay.RIGHT);
        //questLabel.get(0).setStyle("-fx-background-color:transparent;");

        result.setText(address.getAnswerResults());
        addressLabel.setGraphic(result);
        addressLabel.setContentDisplay(ContentDisplay.BOTTOM);

        addressHB.getChildren().clear();
        addressHB.getChildren().addAll(addressLabel,answerTF);
        addressHB.setAlignment(Pos.CENTER);

    }// end of checkUserInput()

    private void createResultScene(){
        BorderPane resultBP = new BorderPane();
        GridPane textGridPane = new GridPane();
        HBox buttonBHBox = new HBox(10);
        
        Text numOfAttemp = new Text("Number of Attempted questions: " + questAttempted + "\n");
        Text numOfCorrect = new Text("Number of Correct Answer(s):       " + questCorrect + "\n");
        Text numOfInCorrect = new Text("Number of Incorrect Answer(s):    " + questIncorrect + "\n");

        Button tryAgainBtn = new Button("Try Quiz Again");
        Button changeOptionsBtn = new Button("Change Quiz Options");
        Button returnToMainMenuBtn = new Button("Return to Main Menu");

        numOfAttemp.setFont(Font.font("Times", FontWeight.BOLD, 13));
        numOfCorrect.setFont(Font.font("Times", FontWeight.BOLD, 13));
        numOfInCorrect.setFont(Font.font("Times", FontWeight.BOLD, 13));

        tryAgainBtn.setFont(Font.font("Times", FontWeight.MEDIUM, 13));
        changeOptionsBtn.setFont(Font.font("Times", FontWeight.MEDIUM, 13));
        returnToMainMenuBtn.setFont(Font.font("Times", FontWeight.MEDIUM, 13));

        tryAgainBtn.setOnMouseEntered(e->{
            resultScene.setCursor(Cursor.HAND);
        });

        tryAgainBtn.setOnMouseExited(e->{
            resultScene.setCursor(Cursor.DEFAULT);
        });

        tryAgainBtn.setOnAction(e->{
            resultScene.setCursor(Cursor.WAIT);
            manager.reset();
            window.setScene(createQuizScene());
            resultStage.close();
        });


        changeOptionsBtn.setOnMouseEntered(e->{
            resultScene.setCursor(Cursor.HAND);
        });

        changeOptionsBtn.setOnMouseExited(e->{
            resultScene.setCursor(Cursor.DEFAULT);
        });

        changeOptionsBtn.setOnAction(e->{
            changeOptionsPopUpWindow(resultBP);
        });


        returnToMainMenuBtn.setOnMouseEntered(e->{
            resultScene.setCursor(Cursor.HAND);
        });

        returnToMainMenuBtn.setOnMouseExited(e->{
            resultScene.setCursor(Cursor.DEFAULT);
        });

        returnToMainMenuBtn.setOnAction(e->{
            resultScene.setCursor(Cursor.WAIT);
            manager.getAddressQuizList().clear();

            //MainMenu mainMenu = new MainMenu();
            
            window.setScene(mainMenu.createMainMenu());
            resultStage.close();
        });

        
        textGridPane.add(numOfAttemp, 0, 0);
        textGridPane.add(numOfCorrect, 0, 1);
        textGridPane.add(numOfInCorrect, 0, 2);
        

        buttonBHBox.getChildren().addAll(tryAgainBtn,changeOptionsBtn,returnToMainMenuBtn);

        resultBP.setCenter(textGridPane);
        resultBP.setBottom(buttonBHBox);

        resultScene = new Scene(resultBP);
        resultStage.setScene(resultScene);
        resultStage.setTitle("Your Results");
        resultStage.show();
    }//end of createResultScene()


    private ObservableList<Label> populateListView() {
        List<Label> qustList = new ArrayList<Label>();
        NumberFormat formatQuestNum = NumberFormat.getInstance();
        formatQuestNum.setGroupingUsed(true);
        
        for(long qCount = 1; qCount <= questCount; qCount++)
            qustList.add(new Label("Question " + formatQuestNum.format(qCount)));
        
        return FXCollections.observableArrayList(qustList);
    }//end of populateListView()


    private void changeOptionsPopUpWindow(BorderPane resultBP){
        Stage popUpWindow = new Stage();
        BorderPane optionsBorderPane = new BorderPane();
        GridPane numOfQuestGridPane = new GridPane();
        
        Text title = new Text("Options");
        TextField  numOfQuestTextFeild = new TextField();
        Text errorText = new Text("");
        Label numOfQuestLabel = new Label("Enter number between 1 and " + manager.getLengthOfQuizList());
        
        Button setOptionBtn = new Button("Set Options");
        ToggleGroup radioOptionsGroup = new ToggleGroup();
        RadioButton sequentialBtn = new RadioButton("Sequential");
        RadioButton randomBtn = new RadioButton("Random");
        Label radioButtonLabel = new Label("Select quiz format");
        
      
        /**Modifications to conponents */
        title.setFont(Font.font("Times", FontWeight.BOLD, 20));
        errorText.setFont(Font.font("Times", FontWeight.BOLD, 14));
        errorText.setFill(Color.RED);

        numOfQuestLabel.setFont(Font.font("Times", FontWeight.BOLD, 15));
        radioButtonLabel.setFont(Font.font("Times", FontWeight.BOLD, 15));

        sequentialBtn.setFont(Font.font("Times", FontWeight.BOLD, 13));
        randomBtn.setFont(Font.font("Times", FontWeight.BOLD, 13));
        setOptionBtn.setFont(Font.font("Times", FontWeight.MEDIUM, 12));

        sequentialBtn.setToggleGroup(radioOptionsGroup);
        randomBtn.setToggleGroup(radioOptionsGroup);

        if(quizFormat == 0){
            sequentialBtn.setSelected(true);
        }

        else{
            randomBtn.setSelected(true);
        }

        /**Add to Panes */
        numOfQuestGridPane.add(numOfQuestLabel, 0, 0);
        numOfQuestGridPane.add(numOfQuestTextFeild, 1, 0);
        numOfQuestGridPane.add(errorText, 1, 2);
        numOfQuestGridPane.add(radioButtonLabel, 0, 4);
        numOfQuestGridPane.add(sequentialBtn, 1, 4);
        numOfQuestGridPane.add(randomBtn, 2, 4);

        optionsBorderPane.setTop(title);
        optionsBorderPane.setCenter(numOfQuestGridPane);
        optionsBorderPane.setBottom(setOptionBtn);

        /**Modifiy Panes*/
        resultBP.disableProperty().bind(popUpWindow.showingProperty());

        GridPane.setMargin(numOfQuestLabel, new Insets(10, 15, 10, 0));
        GridPane.setMargin(numOfQuestTextFeild, new Insets(10, 15, 10, 0));
        GridPane.setMargin(errorText, new Insets(10, 15, 10, 15));
        GridPane.setMargin(setOptionBtn, new Insets(20, 0, 10, 0));

        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(numOfQuestGridPane, Pos.CENTER);
        BorderPane.setAlignment(setOptionBtn, Pos.CENTER);

        /**Modifiy Stage and add Scene */
        Scene optionScene = new Scene(optionsBorderPane,550,200);
        popUpWindow.setScene(optionScene);
        popUpWindow.setTitle("Options");
        popUpWindow.setResizable(false);
        popUpWindow.show();

        sequentialBtn.setOnAction(e -> {
            newQuizFormat = 0;
          });
          randomBtn.setOnAction(e -> {
            newQuizFormat = 1;
          });

        setOptionBtn.setOnMouseEntered(e->{
            optionScene.setCursor(Cursor.HAND);
        });

        setOptionBtn.setOnMouseExited(e->{
            optionScene.setCursor(Cursor.DEFAULT);
        });

        setOptionBtn.setOnAction(e->{
            optionScene.setCursor(Cursor.WAIT);

            if(verifyInput(numOfQuestTextFeild,errorText))
                setNewQuizFormat(popUpWindow);

            else 
                optionScene.setCursor(Cursor.DEFAULT); 
        });

        resultStage.setOnCloseRequest(e->{
            if(popUpWindow.showingProperty().get()){
              e.consume();
            }
      
            else{
              //Create a conformation Box.
            }
        });

    }//end of numOfQuestionsPopUpWindow()

    private boolean verifyInput(TextField tf,Text errorText) {
        long input = 0;

        if(tf.getText().length() == 0){
            return true;
        }
       
        else{
            try {
                input = Long.parseLong(tf.getText());
            } catch (Exception e) {
                errorText.setText("Invalid input");
                return false;
            }

            if(input > 0 && input <= manager.getLengthOfQuizList()){
                questCount = input;
            }

            else{
                errorText.setText("Input not in range");
                return false;
            }
            
        }
        return true;
    }//end of verifyInput

    private void setNewQuizFormat(Stage popUpWindow) {
        if(newQuizFormat != quizFormat){
            if(newQuizFormat == 0){
                manager.sortQuizList();
                quizFormat = 0;
            }

            else{
                manager.shuffleQuizList();
                quizFormat = 1;
            }
        }

        popUpWindow.close();
    }//end of setNewQuizFormat()

}//end of class