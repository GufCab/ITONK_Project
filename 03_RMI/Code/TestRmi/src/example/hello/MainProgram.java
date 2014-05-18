package example.hello;

import java.io.Console;

/**
 * Created by guf on 5/15/14.
 */
public class MainProgram {
    public static void main(String args[]) {
        Node leader = new Node("Leader", 8);
        leader.BeginServer();

        Node slave1 = new Node("slave1", 1);
        Node slave2 = new Node("slave2", 2);
        Node slave3 = new Node("slave3", 3);
        Node slave4 = new Node("slave4", 4);

        slave1.GetHello();
        slave3.GetHello();
        slave4.GetHello();
        slave2.GetHello();

        leader.StopServer();

        slave3.BeginServer();

        slave3.GetHello();
        slave4.GetHello();
        slave2.GetHello();


        while (true)
        {
        }

    }
}
