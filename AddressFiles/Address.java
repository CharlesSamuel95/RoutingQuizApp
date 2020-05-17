package AddressFiles;

public class Address {
    private String address;
    private String zipCode;
    private String routingNumber;
    private long questionNumber;

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

    public long getQuestionNumber(){
        return questionNumber;
    }

    public void setQuestionNumber(long number){
        questionNumber = number;
    }
}//end of class