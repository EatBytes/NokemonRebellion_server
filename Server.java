import com.pokemonrebellion.thread.ReplyThread;

public class Server {
    public static void main(String[] args) throws Exception {
        //REPLY
        Thread replyThread = new Thread(new ReplyThread());
        replyThread.start();
    }
}
