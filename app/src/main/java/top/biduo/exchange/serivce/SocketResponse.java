package top.biduo.exchange.serivce;


import top.biduo.exchange.socket.ISocket;


public class SocketResponse {

    private ISocket.CMD cmd; // 传的指令
    private String response; // 返回的参数

    public SocketResponse(ISocket.CMD cmd, String response) {
        this.cmd = cmd;
        this.response = response;
    }

    public ISocket.CMD getCmd() {
        return cmd;
    }

    public void setCmd(ISocket.CMD cmd) {
        this.cmd = cmd;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
