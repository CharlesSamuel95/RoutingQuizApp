import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import AddressFiles.AddressManager;
import AddressFiles.Address;

public class NumberOfQuestions extends Application {
    Stage window;
    AddressManager manager;

    public NumberOfQuestions(Stage window, AddressManager manager) {
        this.window = window;
        this.manager = manager;
        startFX();
    }

    private void startFX(){
        try {
            start(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button b = new Button("" + manager.getLengthOfQuizList());
        Scene s = new Scene(b,200,200);
        //s.setCursor(Cursor.DEFAULT);
        window.setScene(s);
        window.show();

    }
    
}