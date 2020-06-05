package AddressFiles;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

import java.util.Collections;

public class AddressManager{
    private static List<Address> addressList = new ArrayList<>();
    private static List<Address> addressQuizList = new ArrayList<>();
    

    public void insert(Address address){
        addressList.add(address);
    }

    public void insertIntoQuizList(Address address){
        addressQuizList.add(address);
    }

    public Address getAddressListItem(int index){
        return addressList.get(index);
    }

    public Address getAddressQuizListItem(int index){
        return addressQuizList.get(index);
    }

    public List<Address> getAddressList(){
        return addressList;
    }

    public List<Address> getAddressQuizList(){
        return addressQuizList;
    }

    public int getLength(){
        return addressList.size();
    }

    public int getLengthOfQuizList(){
        return addressQuizList.size();
    }

    public void shuffleQuizList(){
        Collections.shuffle(addressQuizList);
    }

    public void sequentialSortQuizList(ObservableList<Character> selectedItems){
        addressQuizList.clear();

        if(selectedItems.size() == 0){
            copyIntoQuizList();
        }

        else{
            for (Address address : addressList) {
                for (Character character : selectedItems) {
                    Character c = ' ';

                    if (Character.isWhitespace(address.getAddress().charAt(1))) {
                        c = address.getAddress().charAt(2);
                    }
            
                    else{
                        c = address.getAddress().charAt(0);
                    }

                    if(c.charValue() == character.charValue()){
                        insertIntoQuizList(address);
                    }
                }//end of inner
            }//end of outer
        }//end of else
    }//end of sequentialSortQuizList()


    public void copyIntoQuizList(){
        for (Address address : addressList) 
            addressQuizList.add(address);
    }

    public void reset(){
        for (Address address : addressQuizList) 
            address.resetAnswerResultAndTextField();
    }

}//end of AddressManager class

