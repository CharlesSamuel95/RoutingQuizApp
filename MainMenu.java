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
    //explainListView();
    //explainRadioButton();
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
        Text introText = new Text("Welcome, Please choose your quiz options and hit the next page button\n");
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
        questionImg.setOnMouseClicked(e->{explainListView();});

        radioButtonLabel.setFont(Font.font("Times", FontWeight.SEMI_BOLD, 16));
        questionImg2.setOnMouseEntered(e->{mainMenu.setCursor(Cursor.HAND);});
        questionImg2.setOnMouseExited(e->{mainMenu.setCursor(Cursor.DEFAULT);});
        questionImg2.setOnMouseClicked(e->{explainRadioButton();});

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
    * The user is able to select which address will appear in the quiz by
    * selecting an element from this listview. For example if the user selects
    * A then only addresses that start with A will be included; if A and B are 
    * selected then only addresses that start with A abd B will be included, etc.
    * By default all addresses will be included. Hold Ctrl to select multiple items
    * in listview.
    * 
    * NOTE: This does not determine the number of questions a user will receive.
    */
    private void explainListView(){
      String text = "The items in this listview contains letters that can range from A to Z. " + 
                    "The letters represents addresses that start with that letter.\n For example, " +
                    "'A' represents all addresses that start with 'A', 'B' represents all addresses\n " +
                    "that start with 'B', etc. Selecting an item will determine which addresses " +
                    "will be included in the quiz.\n If 'A' is selected then all addresses that start " + 
                    "with 'A' will be included, etc. You can select multiple items in the listview " + 
                    "by holding Ctrl.\n If you only, for exapmle, choose 'A' and 'B' then the quiz will " + 
                    "only include addresses that start with A and B.\n\n" + 
                    "NOTE: This does not determine the number of questions you will receive.";
      
      Text textExplain = new Text(text);
      textExplain.setFont(Font.font("Times", FontWeight.BOLD, 15));

      FlowPane pane = new FlowPane(textExplain);
      //pane.setAlignment(Pos.CENTER);

      Scene scene = new Scene(pane);
      Stage window = new Stage();
      window.setTitle("Listview");
      window.setScene(scene);
      window.show();
    }

    /**
     * Creates a window that explains the radio button options.
     * These buttons determine if the user will receive their questions
     * sequentially (list ordering) or randomly. For example if the user choses
     * to include only A address and selects sequential, then the first few address
     * (i.e questions) will be 
     * 
     * ABBOTSBURY,LN,31088,R020
     * ABERCORN,ST,31088,RO21
     * ABNEY,CT,31028,C031
     * ACORN RIDGE,CT,31088,C028
     * ATERBURNER,WAY,31098,C050
     * AFTON,CT,31028,C043
     * AINSLEY,CT,31093,R004
     * AIR PARK,DR,31088,R001.
     * 
     * If random is selected then address ordering(i.e question ordering) will be random.
     * 
     * NOTE: If more then one value in the listview is selected (say A and B) and sequential 
     * is chosen, then the user will have to answear all A address questions before they will 
     * encounter a B address question.
     */
    private void explainRadioButton(){
      String t1 = " These buttons determine if questions will be received " +
      "sequentially (list ordering) or randomly.\n For example if you chose " + 
      "to include only 'A' address and selects sequential,\n then the first few address " +
      "(i.e questions) will be in this order\n";

      String t2 = 
         "ABBOTSBURY,LN,31088,R020\n" +
         "ABERCORN,ST,31088,RO21\n" +
         "ABNEY,CT,31028,C031\n" +
         "ACORN RIDGE,CT,31088,C028\n" +
         "ATERBURNER,WAY,31098,C050\n" +
         "AFTON,CT,31028,C043\n" +
         "AINSLEY,CT,31093,R004\n" +
         "AIR PARK,DR,31088,R001.\n";

      String t3 = "If random is selected then address ordering(i.e question ordering) will be random.\n" +
      "NOTE: If more then one value in the listview is selected (say 'A' and 'B') and sequential " + 
      "is chosen, \nthen you will have to answear all 'A' address questions before you will " + 
      "encounter any 'B' address questions.\n\n" + 
      "RECOMMENDATION: Only select sequential if you chose only one item in the listview.";

      
      Text text1 = new Text(t1);
      Text text2 = new Text(t2);
      Text text3 = new Text(t3);

      
      text1.setFont(Font.font("Times", FontWeight.BOLD, 15));
      text2.setFont(Font.font("Times", FontWeight.BOLD, 15));
      text3.setFont(Font.font("Times", FontWeight.BOLD, 15));

      BorderPane pane = new BorderPane();
      pane.setTop(text1);
      pane.setCenter(text2);
      pane.setBottom(text3);

      BorderPane.setAlignment(text1, Pos.CENTER);
      BorderPane.setAlignment(text3, Pos.BOTTOM_CENTER);

      Scene scene = new Scene(pane);
      Stage window = new Stage();
      window.setTitle("Radio Buttons");
      window.setScene(scene);
      window.show();
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