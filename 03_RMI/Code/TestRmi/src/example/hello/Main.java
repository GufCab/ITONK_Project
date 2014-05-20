package example.hello;

import java.util.ArrayList;

/**
 * Created by chr on 5/20/14.
 */
public class Main {
    public static void main(String args[]) {
        System.setProperty("java.rmi.server.hostname", "192.168.204.130");
        ArrayList<Node> nodes = new ArrayList<Node>();

        for(String arg: args) {
            nodes.add(new Node(arg, Integer.parseInt(arg), false));
        }

        nodes.get(0).CallGloriousLeader();

        while(true){}
    }
}
