
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import AddressFiles.Address;
import AddressFiles.AddressManager;

//Ajusted the size of the list view
public class Quiz {
    private Stage window;
    private Stage resultStage = new Stage();
    private Scene quizScene;
    private Scene resultScene;
    private AddressManager manager;
    private long questCount = 0;

    private long questAttempted;
    private long questCorrect;
    private long questIncorrect;
    
    public Quiz(Stage window, AddressManager manager, long questCount) {
        this.window = window;
        this.manager = manager;
        this.questCount = questCount;
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

    /**NOTE: try to make image bacground transparent */
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
        FlowPane textFP = new FlowPane();
        HBox buttonBHBox = new HBox(10);
        
        Text numOfAttemp = new Text("Number of Attempted questions: " + questAttempted + "\n");
        Text numOfCorrect = new Text("Number of Correct Answer(s): " + questCorrect + "\n");
        Text numOfInCorrect = new Text("Number of Incorrect Answer(s): " + questIncorrect + "\n");

        Button tryAgainBtn = new Button("Try Quiz Again");
        Button changeNumOfQuestionsBtn = new Button("Change Number of Questions");
        Button returnToMainMenuBtn = new Button("Return to Main Menu");

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

        textFP.getChildren().addAll(numOfAttemp,numOfCorrect,numOfInCorrect);
        buttonBHBox.getChildren().addAll(tryAgainBtn,changeNumOfQuestionsBtn,returnToMainMenuBtn);

        resultBP.setCenter(textFP);
        resultBP.setBottom(buttonBHBox);

        resultScene = new Scene(resultBP);
        resultStage.setScene(resultScene);
        resultStage.setTitle("Your Results");
        resultStage.show();
    }

    private ObservableList<Label> populateListView() {
        List<Label> qustList = new ArrayList<Label>();
        
        for(long qCount = 1; qCount <= questCount; qCount++)
            qustList.add(new Label("Question " + qCount));
        
        return FXCollections.observableArrayList(qustList);
    }//end of populateListView()

}//end of class