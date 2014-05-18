package example.hello;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public class Server implements Hello {
    private String _id;
    private int _nodeNum;
    //private Server _server;
    private Hello _helloStub;
    private Registry _registry;
    private final String NODE_NAME = "NodeHello";

    public Server(String id, int nodeNum)
    {
        _id = id;
        _nodeNum = nodeNum;

        try {
            _helloStub = (Hello)UnicastRemoteObject.exportObject(this, 0);
            _registry = LocateRegistry.getRegistry();
            _registry.bind(NODE_NAME, _helloStub);

        } catch (Exception e)
        {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

    public String sayHello()
    {
        return "Hello from: " + _id;
    }

    public int getNodeNum()
    {
        return _nodeNum;
    }

    public void StopServer()
    {
        try{
            _registry.unbind(NODE_NAME);
        } catch (Exception e)
        {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

}

/*
public static void main(String args[]) {
        try {
            Server obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
// Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
 */