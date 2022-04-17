package Entities;
import java.util.List;

public class Flat {

    private String id, address;
    private List<String> members;
    private int size;

    // address = a specific name the user wants to enter (could be address, could be some nickname)
    // id = placeholder, will be overwritten inside AddFlatPresenter
    public Flat(String address, String id, List<String> members, int size){
        this.id = id;
        this.size = size;
        this.members = members;
        this.address = address;
    }

    public Flat(){}

    public String getId(){
        return id;
    }

    public int getSize(){
        return size;
    }

    public List<String> getMembers(){
        return members;
    }

    public String getAddress(){
        return address;
    }

}