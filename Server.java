import com.pokemonrebellion.core.CoreDependency;
import com.pokemonrebellion.thread.ReplyThread;

public class Server {
    public static void main(String[] args) throws Exception {
        CoreDependency coreDependency = new CoreDependency();

        //REPLY
        Thread replyThread = new Thread(new ReplyThread(coreDependency));
        replyThread.start();
    }
}
