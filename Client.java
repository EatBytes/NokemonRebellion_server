import java.io.*;
import java.net.*;

public class Client {
    private final static String _EXT_IP = "224.0.0.1";
    private final static String _SERV_IP= "localhost";
    private final static int EXT_PORT = 8510;
    private final static int SERV_PORT = 8500;

    private final static int BUFFER_LENGTH = 1024;
    private final static byte BUFFER[] = new byte[BUFFER_LENGTH];

    public static void main(String argv[]) throws Exception {
        //Addr
        InetAddress EXT_IP = InetAddress.getByName(_EXT_IP);
        InetAddress SERV_IP = InetAddress.getByName(_SERV_IP);

        //Socket
        MulticastSocket socket = new MulticastSocket(EXT_PORT);
        socket.joinGroup(EXT_IP);

        //Packets
        DatagramPacket ext_packet = new DatagramPacket(BUFFER, BUFFER_LENGTH, EXT_IP, EXT_PORT);
        DatagramPacket serv_packet = new DatagramPacket(BUFFER, BUFFER_LENGTH, SERV_IP, SERV_PORT);

        //Threads
        Thread receiveThread = new Thread(new ReceiveThread(socket, ext_packet));
        receiveThread.start();

        Thread replyThread = new Thread(new ReplyThread(socket, serv_packet));
        replyThread.start();
    }
}

class ReplyThread implements Runnable {
    private MulticastSocket socket;
    private DatagramPacket sendPacket;

    public ReplyThread(MulticastSocket s, DatagramPacket p) {
        socket = s;
        sendPacket = p;
    }

    @Override
    public void run() {
        boolean run = true;
        BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));

        while (run) {
            try {
                System.out.println("[REPLY] Wait to send :");

                String s = BR.readLine();
                byte[] bytes = createBytes(s);

                sendPacket.setData(bytes);
                sendPacket.setLength(bytes.length);

                socket.send(sendPacket);
            } catch (IOException e) {
                e.getStackTrace();
                run = false;
            }
        }
    }

    private byte[] createBytes(String s) {
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        DataOutputStream daos = new DataOutputStream(baos);

        try {
            daos.writeInt(2); //ID
            daos.writeInt(5); //OPT
            daos.writeUTF(s);
        } catch (IOException e) {
            e.getStackTrace();
        }

        return baos.toByteArray();
    }
}

class ReceiveThread implements Runnable {
    private MulticastSocket socket;
    private DatagramPacket receivePacket;

    public ReceiveThread(MulticastSocket s, DatagramPacket p) {
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
