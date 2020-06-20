//package Program_Components;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConformationWindow {
    private Stage popUpStage = new Stage();
    
    public void createPopUpWindow(Stage window){
        GridPane conferGridPane = new GridPane();
        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("no");
        Text conferText = new Text("Are you sure you want to terminate this program?");

        conferText.setFont(Font.font("Times", FontWeight.BOLD, 15));
        yesBtn.setFont(Font.font("Times", FontWeight.MEDIUM, 14));
        noBtn.setFont(Font.font("Times", FontWeight.MEDIUM, 14));

        conferGridPane.add(conferText, 0, 0);
        conferGridPane.add(yesBtn, 0, 1);
        conferGridPane.add(noBtn, 0, 2);

        GridPane.setMargin(yesBtn, new Insets(15, 0, 0, 0));
        GridPane.setMargin(noBtn, new Insets(15, 0, 0, 0));
        GridPane.setHalignment(yesBtn, HPos.CENTER);
        GridPane.setHalignment(noBtn, HPos.CENTER);


        Scene popUpScene = new Scene(conferGridPane,357,110);

        conferGridPane.prefWidthProperty().bind(popUpScene.widthProperty());
        conferGridPane.prefHeightProperty().bind(popUpScene.heightProperty());
        conferGridPane.setAlignment(Pos.CENTER);
        
        popUpStage.setScene(popUpScene);
        popUpStage.show();

        yesBtn.setOnMouseEntered(e -> {
            popUpScene.setCursor(Cursor.HAND);
       });

       yesBtn.setOnMouseExited(e -> {
            popUpScene.setCursor(Cursor.DEFAULT);
       });

       yesBtn.setOnAction(e -> {
            popUpStage.close();
            window.close();
       });


       noBtn.setOnMouseEntered(e -> {
            popUpScene.setCursor(Cursor.HAND);
       });

       noBtn.setOnMouseExited(e -> {
            popUpScene.setCursor(Cursor.DEFAULT);
       });

       noBtn.setOnAction(e -> {
            popUpStage.close();
       });


       popUpStage.setOnCloseRequest(e->{
            e.consume();
      });

    }//end of createPopUpWindow()

    public Stage getWindow(){
        return popUpStage;
    }
}//end of class