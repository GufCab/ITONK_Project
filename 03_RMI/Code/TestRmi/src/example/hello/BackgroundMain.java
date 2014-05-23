package example.hello;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by guf on 5/23/14.
 */
public class BackgroundMain {
    public static void main(String args[]) {
        System.setProperty("java.rmi.server.hostname", GlobalHost.HostName);
        ArrayList<Node> nodes = new ArrayList<Node>();

        for(String arg: args) {
            nodes.add(new Node(arg, Integer.parseInt(arg), false));
        }



        while(true){}
    }

}
