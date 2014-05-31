package example.hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private Registry _registry;
    private Hello _helloStub;
    private ILeader _leader;

    public  Client()
    {
        try {
            //_registry = LocateRegistry.getRegistry(null);
            //_helloStub = (Hello) _registry.lookup("NodeHello");

        } catch (Exception e)
        {
            System.err.println("Client exception: " + e.toString());
            //e.printStackTrace();
        }
    }

    public void GetHello(String nodeID)
    {
        try {
            _registry = LocateRegistry.getRegistry(GlobalHost.HostName);
            _helloStub = (Hello) _registry.lookup("NodeHello");

            String response = _helloStub.sayHello();
            System.out.println("Response on Node: " + nodeID + " Got response: " + response);
        } catch (Exception e)
        {
            System.err.println("Client exception: " + e.toString());
            //e.printStackTrace();
        }
    }

    public String CallGloriousLeader(String nodeID)
    {
        String response = "NoResponse";

        try {
            _registry = LocateRegistry.getRegistry(GlobalHost.HostName);
            _leader = (ILeader) _registry.lookup("GloriousLeader");
            response = _leader.GloriousLeaderFunction("Somedata");
            System.out.println("Node: " + nodeID + " Called glorious leader: " + response);

        } catch (Exception e)
        {
            System.err.println("Client exception: " + e.toString());
            //e.printStackTrace();

        }
        return response;
    }

    public int GetNodeNum()
    {
        try {
            int nodeNum = _helloStub.getNodeNum();
            System.out.println("NodeNum: " + nodeNum);
            return nodeNum;
        } catch (Exception e)
        {
            System.err.println("Client exception: " + e.toString());
            //e.printStackTrace();
            return -1;
        }
    }
}

/*
public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Hello stub = (Hello) registry.lookup("Hello");
            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
 */