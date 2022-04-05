package Entities;

import java.util.List;

public class Flat {

    private String id, address;
    private List<String> members;
    private int size;

    public Flat(String address, String id, List<String> members, int size){
        this.id = id;
        this.size = size;
        this.members = members;
        this.address = address;
    }

    public Flat(){
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

// TODO : Modellieren - größe der wg vs größe der liste

// TODO : ist die size nicht komplett obsolete ?