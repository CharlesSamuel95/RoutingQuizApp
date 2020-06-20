//package Program_Components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.HPos;
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

import AddressFiles.Address;
import AddressFiles.AddressManager;

/**
 * This class displays the main menu of this app. This is the first page the
 * user will encounter. It allows users to customized their quiz format before
 * they take it. The page includes two question mark icons for the two options
 * which each creates a new window when clicked. This new window will contain an
 * explanation on the function of each option. Once the user is satisfied they
 * can click the next buttun to procced to the next page for another option.
 */


public class MainMenu extends Application {
  private static Stage window = new Stage();
  private Stage popUpWindow = new Stage();
  private Scene mainMenu; 
  private static ListView<Character> includedAddress; 
  private static ArrayList<Character> listOfLetters = new ArrayList<>();
  private static AddressManager manager = new AddressManager();
  private static MainMenu mainMenuPage = null;
  private static int quizFormat = 0;

  /**
   * Here for debuging purposes only. Launches the fx app
   * 
   * @param args
   */
  public static void main(String[] args) {
    Application.launch(args);
  }

  /**
   * Calls methods to read from csv file and create the main menu.
   */
  @Override
  public void start(Stage primarywindow) throws Exception {
    readAddressFile();
    //window.setMaxWidth(800);
    //window.setMaxHeight(250);

    window.setTitle("Main Menu");
    window.setScene(createMainMenu());
    window.show();
  }// end of start


  public static MainMenu getInstance(){
    if(mainMenuPage == null){
      mainMenuPage = new MainMenu();
    }
    return mainMenuPage;
  }

  public Stage getWindow(){
    return window;
  }

  public AddressManager getManager(){
    return manager;
  }

  public int getQuizFormat(){
    return quizFormat;
  }

  public ObservableList<Character> getSelectedItemsListView(){
    return includedAddress.getSelectionModel().getSelectedItems();
  }

  /**
   * Reads from a csv file, adds the first charater from the address into
   * listOfLetters, and creates Address objects from the read data.
   * 
   * Before the letter is added the Address prefex (addressPrefix) must be
   * checked. If the next character isn't a white space then get the first
   * character, else get the next character after the white space. (this has to be
   * checked because of the format of some Addresses).
   * 
   * Next we check if the letter is in listOfLetters by calling contains()
   * (listOfLetterswill be used in createMainMenu). Last we create the Address
   * object and insert into mangers list with manager.insert().
   */
  private void readAddressFile() {
    Scanner scan;
    try {
      scan = new Scanner(new File("AddressFiles/Address.csv"));
    
      while (scan.hasNext()) {
        String row = scan.nextLine();
        String data[] = row.split(",");

        String addressRange = "";
        String addressPrefix = "";
        String addressSuffix = "";
        String zipCode = "";
        String routingNumber = "";
        char addressLetter = ' ';

        if(data.length == 5){
          addressRange = data[0];
          addressPrefix = data[1];
          addressSuffix = data[2];
          zipCode = data[3];
          routingNumber = data[4];
        }

        else{
          addressRange = "";
          addressPrefix = data[0];
          addressSuffix = data[1];
          zipCode = data[2];
          routingNumber = data[3];
        }
        

        if (!Character.isWhitespace(addressPrefix.charAt(1))) {
          addressLetter = addressPrefix.charAt(0);
        }

        else {
          addressLetter = addressPrefix.charAt(2);
        }

        if (!contains(addressLetter)) {
          listOfLetters.add(new Character(addressLetter));
        }
        
        manager.insert(new Address(addressRange,addressPrefix, addressSuffix, zipCode, routingNumber));
      } // end of while
      scan.close();
    } // end of try

    catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }// end of readAddressFile

  /**
   * Determines if addressLetter is in listOfLetters
   * 
   * @param addressLetter first chararcter of the Address
   * @return true if its in the list. false otherwise.
   */
  private boolean contains(char addressLetter) {

    for (int iter = 0; iter < listOfLetters.size(); iter++) {
      if (listOfLetters.get(iter).charValue() == addressLetter)
        return true;
    }
    return false;
  }// end of contains

  /**
   * Creates the Main Menu.
   */
  public Scene createMainMenu() {
    /** Main menu Panes */
    HBox hboxRadioOptions = new HBox(10);
    HBox hboxlistView = new HBox(10);
    GridPane menu = new GridPane();

    /** Main menu components */
    ConformationWindow win = new ConformationWindow();
    Text introText = new Text("Welcome, Please choose your quiz options and hit the next page button\n");
    RadioButton sequentialBtn = new RadioButton("Sequential");
    RadioButton randomBtn = new RadioButton("Random");

    Button nextPageBtn = new Button("Next Page");
    Button resetListViewBtn = new Button("Deselect Items");

    ImageView questionImg = new ImageView(new Image("images/question-mark-image2.jpg"));
    ImageView questionImg2 = new ImageView(new Image("images/question-mark-image2.jpg"));

    Tooltip messageTip = new Tooltip("Explanation");

    Label listViewLabel = new Label("Select Addresses to include");
    Label radioButtonLabel = new Label("Select quiz format");

    /** allows user to chose which address to include in quiz */
    includedAddress = new ListView<>(FXCollections.observableArrayList(listOfLetters));

    /** Modifications to conponents */
    introText.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, 20));
    introText.setTextAlignment(TextAlignment.CENTER);

    includedAddress.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    includedAddress.setPrefSize(50, 100);

    sequentialBtn.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, 13));
    randomBtn.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, 13));

    messageTip.setStyle("-fx-font-size:13");

    /** Adjuncts dimimsions of question mark image */
    questionImg.setFitHeight(15);
    questionImg.setFitWidth(15);
    questionImg2.setFitHeight(15);
    questionImg2.setFitWidth(15);

    /** Adds tooltip to image */
    Tooltip.install(questionImg, messageTip);
    Tooltip.install(questionImg2, messageTip);

    /** radio button group */
    ToggleGroup radioOptionsGroup = new ToggleGroup();
    sequentialBtn.setToggleGroup(radioOptionsGroup);
    randomBtn.setToggleGroup(radioOptionsGroup);

    sequentialBtn.setOnAction(e -> {
      quizFormat = 0;
    });
    randomBtn.setOnAction(e -> {
      quizFormat = 1;
    });
    sequentialBtn.setSelected(true);

    listViewLabel.setFont(Font.font("Times", FontWeight.SEMI_BOLD, 16));
    questionImg.setOnMouseEntered(e -> {
      mainMenu.setCursor(Cursor.HAND);
    });
    questionImg.setOnMouseExited(e -> {
      mainMenu.setCursor(Cursor.DEFAULT);
    });
    questionImg.setOnMouseClicked(e -> {
      explainListView();
    });

    radioButtonLabel.setFont(Font.font("Times", FontWeight.SEMI_BOLD, 16));
    questionImg2.setOnMouseEntered(e -> {
      mainMenu.setCursor(Cursor.HAND);
    });
    questionImg2.setOnMouseExited(e -> {
      mainMenu.setCursor(Cursor.DEFAULT);
    });
    questionImg2.setOnMouseClicked(e -> {
      explainRadioButton();
    });

    /**Set actions on Buttons and Stage */
    nextPageBtn.setOnMouseEntered(e -> {
      mainMenu.setCursor(Cursor.HAND);
    });
    nextPageBtn.setOnMouseExited(e -> {
      mainMenu.setCursor(Cursor.DEFAULT);
    });
    nextPageBtn.setOnAction(e -> {
      mainMenu.setCursor(Cursor.WAIT);
      createNextPage(includedAddress.getSelectionModel().getSelectedItems());
    });


    resetListViewBtn.setOnMouseEntered(e -> {
      mainMenu.setCursor(Cursor.HAND);
    });
    resetListViewBtn.setOnMouseExited(e -> {
      mainMenu.setCursor(Cursor.DEFAULT);
    });
    resetListViewBtn.setOnAction(e -> {
      includedAddress.getSelectionModel().clearSelection();
    });

    window.setOnCloseRequest(e->{
      if(popUpWindow.showingProperty().get() || win.getWindow().showingProperty().get()){
        e.consume();
      }

      else{
        //Create a conformation Box.
        e.consume();
        win.createPopUpWindow(getWindow());
      }
    });

    /** Add nodes to panes */
    hboxRadioOptions.getChildren().addAll(questionImg2, radioButtonLabel, sequentialBtn, randomBtn);
    hboxlistView.getChildren().addAll(questionImg, listViewLabel, includedAddress);

    menu.add(introText, 1, 1, 4, 1);

    menu.add(hboxlistView, 1, 2);
    menu.add(resetListViewBtn,1,2);
    menu.add(hboxRadioOptions, 3, 2);
    menu.add(nextPageBtn, 3, 5);
   
    /** Modifiy panes */
    GridPane.setHalignment(nextPageBtn, HPos.CENTER);
    menu.setHgap(25);
    menu.setVgap(7);

    
    /** Sets the Stage and Scene */
    mainMenu = new Scene(menu, 800,250);

  /**Bind GridPane */
    menu.disableProperty().bind(popUpWindow.showingProperty());
    menu.disableProperty().bind(win.getWindow().showingProperty());
    menu.prefWidthProperty().bind(mainMenu.widthProperty());
    menu.prefHeightProperty().bind(mainMenu.heightProperty());
    menu.setAlignment(Pos.CENTER);

    return mainMenu;
  }// end of createMainMenu

  // end to know: which item(s) in listview was selected.
  // which radio button was selected. use quizFormat value
  // :0 = sequentail, 1 = random
  private void createNextPage(ObservableList<Character> selectedItemsList) {
    if (selectedItemsList.size() != 0)
      includeSubsetOfAddress(selectedItemsList);

    else
      manager.copyIntoQuizList();

    if (quizFormat == 1)
      // random
      manager.shuffleQuizList(); 
      
    else {
      // sequentail
    }
    //NumberOfQuestions quest = new NumberOfQuestions(window, mainMenu, manager,quizFormat);
    NumberOfQuestions quest = new NumberOfQuestions(mainMenu);
    window.setScene(quest.createScene());
  }// end of createNextPage


  // have threads read file
  private void includeSubsetOfAddress(ObservableList<Character> selectedItems) {
    for (Address address : manager.getAddressList()) {
      for (Character character : selectedItems) {
          Character c = ' ';

          if (Character.isWhitespace(address.getAddress().charAt(1))) {
              c = address.getAddress().charAt(2);
          }
  
          else{
              c = address.getAddress().charAt(0);
          }

          if(c.charValue() == character.charValue()){
              manager.insertIntoQuizList(address);
          }
      }//end of inner
    }//end of outer
   

  }//end of includeSubsetOfAddress

   /**
    * Creates a window that explains the listview option.
    * The user is able to select which address will appear in the quiz by
    * selecting an element from this listview. For example if the user selects
    * A then only addresses that start with A will be included; if A and B are 
    * selected then only addresses that start with A abd B will be included, etc.
    * By default all addresses will be included. Hold Ctrl to select multiple items
    * in listview. If you want to inclued all address then either don't select an item
    * in the listview or click the reset button to deselect all selected itmes.
    * 
    * NOTE: This does not determine the number of questions a user will receive.
    */
    private void explainListView(){
      String text = "The items in this listview contains letters that can range from A to Z. " + 
                    "The letters represents addresses that start with that letter.\n For example, " +
                    "'A' represents all addresses that start with 'A', 'B' represents all addresses\n " +
                    "that start with 'B', etc. Selecting an item will determine which addresses " +
                    "will be included in the quiz.\n\n If 'A' is selected then all addresses that start " + 
                    "with 'A' will be included, etc. You can select multiple items in the listview " + 
                    "by holding Ctrl.\n If you only, for exapmle, choose 'A' and 'B' then the quiz will " + 
                    "only include addresses that start with A and B.\n" + "If you want to inclued all " +
                    "address then either don't select an item " + "or click the Deselect Items button to " + 
                    "deselect all selected itmes.\n\n" + 
                    "NOTE: This does not determine the number of questions you will receive.";
      
      Text textExplain = new Text(text);
      textExplain.setFont(Font.font("Times", FontWeight.BOLD, 15));

      FlowPane pane = new FlowPane(textExplain);
      
      Scene scene = new Scene(pane);

      pane.prefWidthProperty().bind(scene.widthProperty());
      pane.prefHeightProperty().bind(scene.heightProperty());
      pane.setAlignment(Pos.CENTER);

      popUpWindow.setTitle("Listview");
      popUpWindow.setScene(scene);
      popUpWindow.show();
      
    }//end of explainListView()

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
      "to include only 'A' address and select sequential,\n then the first few address " +
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

      
      text1.setFont(Font.font("Times", FontWeight.BOLD, 19));
      text2.setFont(Font.font("Times", FontWeight.BOLD, 19));
      text3.setFont(Font.font("Times", FontWeight.BOLD, 19));

      GridPane pane = new GridPane();
      pane.add(text1, 0, 0);
      pane.add(text2, 0, 2);
      pane.add(text3, 0, 4);
      /*pane.setTop(text1);
      pane.setCenter(text2);
      pane.setBottom(text3);*/

      
      //BorderPane.setAlignment(text3, Pos.BOTTOM_CENTER);

      Scene scene = new Scene(pane);

      pane.prefWidthProperty().bind(scene.widthProperty());
      pane.prefHeightProperty().bind(scene.heightProperty());
      pane.setAlignment(Pos.CENTER);
      //BorderPane.setAlignment(pane, Pos.CENTER);

      popUpWindow.setTitle("Radio Buttons");
      popUpWindow.setScene(scene);
      popUpWindow.show();
    }//end of explainRadioButton()

}//end of MainMenu class