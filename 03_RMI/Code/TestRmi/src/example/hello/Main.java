package example.hello;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by chr on 5/20/14.
 */
public class Main {
    public static void main(String args[]) {
        System.setProperty("java.rmi.server.hostname", GlobalHost.HostName);
        ArrayList<Node> nodes = new ArrayList<Node>();

        for(String arg: args) {
            nodes.add(new Node(arg, Integer.parseInt(arg), false));
        }

        Console console = System.console();
        String input = "init";

        while(!input.equals("quit"))
        {
            input = console.readLine("Enter command: ");

            if(input.equals("call"))
            {
                nodes.get(0).CallGloriousLeader();
            }
        }

        while(true){}
    }
}
