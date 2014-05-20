package example.hello;

import java.rmi.registry.LocateRegistry;

/**
 * Created by guf on 5/15/14.
 */
public class MainProgram {
    public static void main(String args[]) {

        try {
            System.setProperty("java.rmi.server.hostname", "192.168.204.130");
            LocateRegistry.createRegistry(1099);
        } catch (Exception e) {

        }


        //Node leader = new Node("Leader", 10, true);
        //leader.SetLeader();
        Node slave0 = new Node("Slave0", 0, false);
        Node slave1 = new Node("Slave1", 1, false);
        Node slave2 = new Node("Slave2", 2, false);
        Node slave3 = new Node("Slave3", 3, false);
        Node slave4 = new Node("Slave4", 4, false);
        Node slave5 = new Node("Slave5", 5, false);
        Node slave6 = new Node("Slave6", 6, false);
        Node slave7 = new Node("Slave7", 7, false);
        Node slave8 = new Node("Slave8", 8, false);
        Node slave9 = new Node("Slave9", 9, false);


        slave1.CallGloriousLeader();
        slave2.CallGloriousLeader();
        slave4.CallGloriousLeader();


        //leader = null;

        slave5.SetLeader();

        slave1.CallGloriousLeader();
        slave2.CallGloriousLeader();
        slave4.CallGloriousLeader();

        //leader = new Node("Leader", 10, true);

        //slave5.StartQuesting();
        //slave5.StartBullyElection();

        //leader = null;
        System.gc();

        slave4.CallGloriousLeader();
        //slave1.CallGloriousLeader();
        //slave2.CallGloriousLeader();



        while(true)
        {}


       /*
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

        slave1.BeginServer();

        slave3.GetHello();
        slave4.GetHello();
        slave2.GetHello();

        slave1.StopServer();

        slave2.BeginServer();
        slave3.GetHello();
        slave4.GetHello();
        slave2.GetHello();

        while (true)
        {
        }
*/
    }
}
