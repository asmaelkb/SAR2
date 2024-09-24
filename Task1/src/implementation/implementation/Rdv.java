package implementation;

public class Rdv {

    Broker b_accept, b_connect;
    Channel c_accept, c_connect;
    int port;

    boolean accepted, connected = false;

    public Rdv(Broker b, int port){
        this.b_accept = b;
        this.port = port;
        
        CircularBuffer in = new CircularBuffer(512);
        CircularBuffer out = new CircularBuffer(512);
        
        this.c_accept = new Channel(in, out);
        this.c_connect = new Channel(out, in);
    }

    public synchronized Channel accept(Broker b){
        this.b_accept = b;
        
        accepted = true;
        notifyAll(); // Réveiller le thread connect s'il attend
        
        // Attendre que connect soit appelé
        while (!connected) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        return c_accept;
    }

    public synchronized Channel connect(Broker b){
        this.b_connect = b;

        connected = true;
        notifyAll(); // Réveiller le thread accept s'il attend
        
        // Attendre que accept soit appelé
        while (!accepted) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        return c_connect;
    }
}