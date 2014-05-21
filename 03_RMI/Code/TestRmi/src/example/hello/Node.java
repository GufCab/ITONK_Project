package example.hello;

/**
 * Created by guf on 5/15/14.
 */
public class Node {
    private String _id;
    private Client _client;
    private Server _server;
    private int _nodeNum;
    private int _currentLeaderId;
    private boolean _isLeader;

    public Node(String id, int nodeNum, boolean isLeader)
    {
        _id = id;
        _nodeNum = nodeNum;

        _client = new Client();
        _server = new Server(id, _nodeNum, 10);
        _isLeader = isLeader;

        if(isLeader)
        {
            _server.SetLeader();
        }
    }

    public void BeginServer()
    {
        _server = new Server(_id, _nodeNum, _currentLeaderId);
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

    public void CallGloriousLeader()
    {
        String response = _client.CallGloriousLeader(_id);
        if (response.equals("NoResponse"))
        {
            //StartQuesting();
            //StartBullyElection();
            StartRingElection();
        }
    }

    private void StartRingElection() {
        _server.StartRingElection();
    }

    public void SetLeader()
    {
        _server.SetLeader();
    }

    public int GetNodeNum()
    {
        return _client.GetNodeNum();
    }

    public int StartQuesting() {
        return _server.QuestFunction();
    }
    public int StartBullyElection() {
        return _server.BullyElection();
    }

}
