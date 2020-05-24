package AddressFiles;

public class Address {
    private String address;
    private String zipCode;
    private String routingNumber;
    private String userRoutingNumber = "";

    private String quizTextFieldStyle = "-fx-background-color:WHITE;";
    private String quizAnswerResults = "Enter the correct routing number.";
    private boolean editableTextField = true;
    
    
    public Address(String address, String 
                    zipCode, String routingNumber){
        this.address = address;
        this.zipCode = zipCode;
        this.routingNumber = routingNumber;

    }

    public Address(String address, String address2,String 
                    zipCode, String routingNumber){
        this.address = address + " " + address2;
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

    public String printPrefix(){
        
        return getAddress() + ", " + getZipCode();
    }
}//end of class