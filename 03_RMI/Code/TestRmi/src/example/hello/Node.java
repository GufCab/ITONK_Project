package example.hello;

/**
 * Created by guf on 5/15/14.
 */
public class Node {
    private String _id;
    private Client _client;
    private Server _server;
    private int _nodeNum;

    public Node(String id, int nodeNum)
    {
        _id = id;
        _nodeNum = nodeNum;

        _client = new Client();
    }

    public void BeginServer()
    {
        _server = new Server(_id, _nodeNum);
    }

    public void StopServer()
    {
        _server.StopServer();
        _server = null;
    }

    public void GetHello()
    {
        _client.GetHello(_id);
    }

    public int GetNodeNum()
    {
        return _client.GetNodeNum();
    }

}
