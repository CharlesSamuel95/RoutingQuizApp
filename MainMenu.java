import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import AddressFiles.AddressManager;
import AddressFiles.Address;

/**
 * This class displays the main menu of this app. This is the first page the user will encounter.
 * It allows users to customized their quiz format before they take it. The page includes two
 * question mark icons for the two options which each creates a new window when clicked. This new
 * window will contain an explanation on the function of each option. Once the user is satisfied
 * they can click the next buttun to procced to the next page for another option.
 */
public class MainMenu extends Application {
  
  Scene mainMenu; //
  ArrayList<Character> listOfLetters = new ArrayList<>();
  AddressManager manager = new AddressManager();

  /**
   * Here for debuging purposes only. Launches the fx app
   * @param args
   */
 public static void main(String[] args) {
    Application.launch(args);
  }

  /**
   * Calls methods to read from csv file and create the main menu.
   */
  @Override
  public void start(Stage window) throws Exception {
    readAddressFile();
    createMainMenu(window);
  }// end of start

  /**
   * Reads from a csv file, adds the first charater from the address 
   * into listOfLetters, and creates Address objects from the read data.
   * 
   * Before the letter is added the Address prefex (addressPrefix) must be checked.
   * If the next character isn't a white space then get the first character,
   * else get the next character after the white space. (this has to be checked
   * because of the format of some Addresses). 
   * 
   * Next we check if the letter is in listOfLetters by calling contains() 
   * (listOfLetterswill be used in createMainMenu). Last we create the Address 
   * object and insert into mangers list with manager.insert().
   */
  private void readAddressFile() {
    Scanner scan;
    try {
      scan = new Scanner(new File("AddressFiles/Address.csv"));
      
      while(scan.hasNextLine()){
        String row = scan.nextLine();
        String data[] = row.split(",");
        
        /**Remove this after you finish typing the address file */
        if(data.length < 4)
          break;
      
        String addressPrefix = data[0];
        String addressSuffix = data[1];
        String zipCode = data[2];
        String routingNumber = data[3];

        char addressLetter = ' ';

        if(!Character.isWhitespace(addressPrefix.charAt(1))){
          addressLetter = addressPrefix.charAt(0);
        }

        else{
          addressLetter = addressPrefix.charAt(2);
        }

        if(!contains(addressLetter)){
          listOfLetters.add(new Character(addressLetter));
        }

        manager.insert(new Address(addressPrefix,addressSuffix,zipCode,routingNumber));
      }//end of while
      scan.close();
    }//ens of try

    catch (FileNotFoundException e) {
      e.printStackTrace();
    }

   }//end of readAddressFile

   /**
    * Determines if addressLetter is in listOfLetters
    * @param addressLetter first chararcter of the Address
    * @return true if its in the list. false otherwise.
    */
   private boolean contains(char addressLetter){
      
      for(int iter = 0; iter < listOfLetters.size(); iter++){
        if(listOfLetters.get(iter).charValue() == addressLetter)
          return true;
      }
      return false;
   }//end of contains


   /**
    * Creates the Main Menu.
    * @param window The stage object.
    */
    private void createMainMenu(Stage window){
      /**Main menu Panes */
        HBox hboxRadioOptions = new HBox(10);
        HBox hboxlistView = new HBox(10);
        GridPane menu = new GridPane();

      /**Main menu components */
        Text introText = new Text("Welcome, Please chose your quiz options and hit the next page button\n");
        RadioButton sequentialBtn = new RadioButton("Sequential");
        RadioButton randomBtn = new RadioButton("Random");

        Button nextPageBtn = new Button("Next Page");
        Button quizPageBtn = new Button("Quiz Page");

        ImageView questionImg = new ImageView(new Image("images/question-mark-image2.jpg"));
        ImageView questionImg2 = new ImageView(new Image("images/question-mark-image2.jpg"));
        
        Tooltip messageTip = new Tooltip("Explanation");
        
        Label listViewLabel = new Label("Select Addresses to include");
        Label radioButtonLabel = new Label("Select quiz format");
        
          /**allows user to chose which address to include in quiz*/
        ListView<Character> includedAddress = new ListView<>(FXCollections.observableArrayList(listOfLetters));
        

      /**Modifications to conponents */
        introText.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, 20));
        introText.setTextAlignment(TextAlignment.CENTER);

        includedAddress.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        includedAddress.setPrefSize(50, 100);

        messageTip.setStyle("-fx-font-size:13");

         /**Adjuncts dimimsions of question mark image */
        questionImg.setFitHeight(15);
        questionImg.setFitWidth(15);
        questionImg2.setFitHeight(15);
        questionImg2.setFitWidth(15);

         /**Adds tooltip to image */
        Tooltip.install(questionImg, messageTip);
        Tooltip.install(questionImg2, messageTip);

          /**radio button group */
        ToggleGroup radioOptionsGroup = new ToggleGroup();
        sequentialBtn.setToggleGroup(radioOptionsGroup);
        randomBtn.setToggleGroup(radioOptionsGroup);
        sequentialBtn.setSelected(true);

        listViewLabel.setFont(Font.font("Times", FontWeight.SEMI_BOLD,16));
        questionImg.setOnMouseEntered(e->{mainMenu.setCursor(Cursor.HAND);});
        questionImg.setOnMouseExited(e->{mainMenu.setCursor(Cursor.DEFAULT);});
        questionImg.setOnMouseClicked(e->{mainMenuExplainListView();});

        radioButtonLabel.setFont(Font.font("Times", FontWeight.SEMI_BOLD, 16));
        questionImg2.setOnMouseEntered(e->{mainMenu.setCursor(Cursor.HAND);});
        questionImg2.setOnMouseExited(e->{mainMenu.setCursor(Cursor.DEFAULT);});
        questionImg2.setOnMouseClicked(e->{mainMenuExplainRadioButton();});

        nextPageBtn.setOnMouseEntered(e ->{mainMenu.setCursor(Cursor.HAND);});
        nextPageBtn.setOnMouseExited(e->{mainMenu.setCursor(Cursor.DEFAULT);});
        nextPageBtn.setOnAction(e ->{createOptionPage(window);});

        quizPageBtn.setOnAction(e->{createQuizPage(window);});


      /**Add nodes to panes */
        hboxRadioOptions.getChildren().addAll(questionImg2,radioButtonLabel,sequentialBtn,randomBtn);
        hboxlistView.getChildren().addAll(questionImg,listViewLabel,includedAddress);

        menu.add(introText, 1, 1,4,1);

        menu.add(hboxlistView, 1, 2);
        menu.add(hboxRadioOptions, 3, 2);
        
        menu.add(nextPageBtn, 3, 5);
        menu.add(quizPageBtn, 4, 5);
        //menu.add(new Text("" + manager.getLength()),1,5);

      /**Modifiy panes */
        GridPane.setHalignment(nextPageBtn, HPos.CENTER);
        GridPane.setHalignment(quizPageBtn, HPos.CENTER);
        menu.setHgap(25);
        menu.setVgap(7);
        
      /**Sets the Stage and Scene */
        mainMenu = new Scene(menu,800,250);
        window.setTitle("Main Menu");
        window.setScene(mainMenu);
        window.show();
    }//end of createMainMenu

   /**
    * Creates a window that explains the listview option.
    */
    private void mainMenuExplainListView(){
      FlowPane pane = new FlowPane(new Text("new window"));
      Scene scene = new Scene(pane,100,100);
      Stage window = new Stage();
      window.setTitle("new window");
      window.setScene(scene);
      window.show();
    }

    /**
     * Creates a window that explains the radio button options.
     */
    private void mainMenuExplainRadioButton(){

    }

    
    private void createOptionPage(Stage window){
        Button btn = new Button("back to main menu page");
        btn.setOnAction(e->{createMainMenu(window);});
        Scene questionCount = new Scene(btn);
        window.setScene(questionCount);
        window.setTitle("Options");
        window.show();
    }

    private void createQuizPage(Stage window){
        
    }

    private void createResultsPage(Stage window){
        
    }

}//end of class