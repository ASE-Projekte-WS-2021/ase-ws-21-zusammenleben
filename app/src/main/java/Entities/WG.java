package Entities;

import java.util.List;

public class WG {

    private String id, address;
    private List<String> members;
    private int size;

    public WG(String id, int size, List<String> members, String address){
        this.id = id;
        this.size = size;
        this.members = members;
        this.address = address;
    }

    public WG(){
    }

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
