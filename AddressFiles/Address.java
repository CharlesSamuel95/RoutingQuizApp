package AddressFiles;

public class Address implements Comparable<Address> {
    private String address;
    private String zipCode;
    private String routingNumber;
    private String userRoutingNumber = "";

    private String quizTextFieldStyle = "-fx-background-color:WHITE;";
    private String quizAnswerResults = "Enter the correct routing number.";
    private boolean editableTextField = true;
    
    private int removeEmptySpaceNumber = 0;
    
    public Address(String addressPrefix, String addressSuffix,String 
                    zipCode, String routingNumber){
        
        removeEmptySpaceNumber = addressSuffix.charAt(0) + addressSuffix.charAt(1);
        
        if(removeEmptySpaceNumber == 68){
            address = addressPrefix;
        }

        else{
            address = addressPrefix + " " + addressSuffix;
        }
          
        this.zipCode = zipCode;
        this.routingNumber = routingNumber;
    }

    public String getAddress(){
        return address;
    }

    public String getZipCode(){
        return zipCode;
    }

    public String getRoutingNumber(){
        return routingNumber;
    }

    public String getUserRoutingNumber(){
        return userRoutingNumber;
    }

    public String getTextFieldStyle(){
        return quizTextFieldStyle;
    }

    public String getAnswerResults(){
        return quizAnswerResults;
    }

    public boolean getEditableTextField(){
        return editableTextField;
    }

    public void setUserRoutingNumber(String userInput){
        userRoutingNumber = userInput;
    } 

    public void setTextFieldStyle(String style){
        quizTextFieldStyle = style;
    }

    public void setAnswerResults(String resultString){
        quizAnswerResults = resultString;
    }

    public void setEditableTextField(boolean edit){
        editableTextField = edit;
    }

    
    public void resetAnswerResultAndTextField(){
        setAnswerResults("Enter the correct routing number.");
        setTextFieldStyle("-fx-background-color:WHITE;");
        setEditableTextField(true);
        setUserRoutingNumber("");
    }

    public String printPrefix(){
        return getAddress() + ", " + getZipCode();
    }
   
    @Override
    public int compareTo(Address address) {
        String address1Prefix1 = "";
        String address1Prefix2 = "";

        if (Character.isWhitespace(this.getAddress().charAt(1))) {
            address1Prefix1 = this.getAddress().substring(2);
        }

        else{
            address1Prefix1 = this.getAddress();
        }

        if (Character.isWhitespace(address.getAddress().charAt(1))) {
            address1Prefix2 = address.getAddress().substring(2);
          }

        else{
            address1Prefix2 = address.getAddress();
        }
  
        return address1Prefix1.compareTo(address1Prefix2);
    }
}//end of class