package example.hello;

import java.util.ArrayList;

/**
 * Created by chr on 5/20/14.
 */
public class Main {
    public static void main(String args[]) {

        ArrayList<Node> nodes = new ArrayList<Node>();

        for(String arg: args) {
            nodes.add(new Node(arg, Integer.parseInt(arg), false));
        }
        
        nodes.get(0).CallGloriousLeader();

        while(true){}
    }
}
