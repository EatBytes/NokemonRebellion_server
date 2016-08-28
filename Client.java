import java.io.*;
import java.net.*;

public class Client {
    private final static String IP_STRING_MULTI = "224.0.0.1";
    private final static String IP_STRING_REPLY = "localhost";
    private final static int PORT_MULTI = 8510;
    private final static int PORT_REPLY = 8500;

    private final static int BUFFER_LENGTH = 1024;
    private final static byte BUFFER[] = new byte[BUFFER_LENGTH];

    public static void main(String argv[]) throws Exception {
        //MULTI
        InetAddress IP_MULTI = InetAddress.getByName(IP_STRING_MULTI);

        MulticastSocket socketMulti = new MulticastSocket(PORT_MULTI);
        socketMulti.joinGroup(IP_MULTI);
        DatagramPacket packetMulti = new DatagramPacket(BUFFER, BUFFER_LENGTH, IP_MULTI, PORT_MULTI);

        Thread multiThread = new Thread(new MultiThread(socketMulti, packetMulti));
        multiThread.start();

        //REPLY
        InetAddress IP_REPLY = InetAddress.getByName(IP_STRING_REPLY);

        DatagramSocket socketReply = new DatagramSocket(8501);
        byte[] bytes = createBytes();
        DatagramPacket packetReply = new DatagramPacket(bytes, bytes.length, IP_REPLY, PORT_REPLY);

        Thread replyThread = new Thread(new ReplyThread(socketReply, packetReply));
        replyThread.start();
    }

    private static byte[] createBytes() {
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        DataOutputStream daos = new DataOutputStream(baos);

        try {
            daos.writeInt(0); //ID
            daos.writeInt(5); //OPT
            daos.writeUTF("test");
        } catch (IOException e) {
            e.getStackTrace();
        }

        return baos.toByteArray();
    }
}

class ReplyThread implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket sendPacket;

    public ReplyThread(DatagramSocket s, DatagramPacket p) {
        socket = s;
        sendPacket = p;
    }

    @Override
    public void run() {
        boolean run = true;
        BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
        DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);

        while (run) {
            try {
                System.out.println("[REPLY] Wait to send :");

                BR.readLine();
                socket.send(sendPacket);
                socket.receive(receivePacket);
                System.out.println(new String(receivePacket.getData(), 0, receivePacket.getLength()));
            } catch (IOException e) {
                e.getStackTrace();
                run = false;
            }
        }
    }
}

class MultiThread implements Runnable {
    private MulticastSocket socket;
    private DatagramPacket receivePacket;

    public MultiThread(MulticastSocket s, DatagramPacket p) {
        socket = s;
        receivePacket = p;
    }

    @Override
    public void run() {
        boolean run = true;

        while (run) {
            try {
                socket.receive(receivePacket);
                System.out.println(new String(receivePacket.getData(), 0, receivePacket.getLength()));
            } catch (IOException e) {
                e.getStackTrace();
                run = false;
            }
        }
    }
}
