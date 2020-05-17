package AddressFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
//use Collections.shuffle() to randomly sort the list

public class AddressManager {
    private List<Address> addressList = new ArrayList<>();
    
    public boolean matchCharacters(char c, int index){
        //return addressList.get(index).getAddress().charAt
        return true;
    }

    public void insert(Address address){
        addressList.add(address);
    }

    public int getLength(){
        return addressList.size();
    }

}