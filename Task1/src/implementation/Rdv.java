package implementation;

public class Rdv {

    Broker b_accept, b_connect;
    Channel channel;
    int port;

    public Rdv(Broker b, int port){
        this.b_accept = b;
        this.port = port;
    }

    public synchronized Channel accept(Broker b){
        this.b_accept = b;

        return this.channel;
    }

    public synchronized Channel connect(Broker b){
        this.b_connect = b;


    }
}