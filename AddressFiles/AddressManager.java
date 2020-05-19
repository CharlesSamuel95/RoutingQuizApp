package AddressFiles;
import java.util.ArrayList;
import java.util.List;
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

    public Address getAddress(int index){
        return addressList.get(index);
    }

    public Address getAddressQuizList(int index){
        return addressQuizList.get(index);
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

    public void copyIntoQuizList(){
        for (Address address : addressList) {
            addressQuizList.add(address);
        }
    }

}//end of AddressManager class

