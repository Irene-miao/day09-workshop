package sg.edu.nus.iss.app;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;



public final class App 
{
    private App() {

    }


    public static void main( String[] args ) throws IOException
    {
        
        // need the random class to carry out randomize operation
        Random rand = new Random();

        // generate random number between 0 and 20
        Integer randomNumber = rand.nextInt(100);

        //store my guess
        Integer myGuess = 0;

        
       // open the socket server to listen on port 1234 for input
       System.out.println("Server running on port 1234");
        ServerSocket ss = new ServerSocket(1234);
        Socket sock = ss.accept();

        // prepare input coming through socket from client (receiving)
        InputStream is = sock.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bis);

        // prepare sending data out using socket to client (sending out)
        OutputStream os = sock.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);
    

        String msgRc = "";

        while (!msgRc.equals("quit")) {
            // guess XX
            msgRc = dis.readUTF();
        

        if (msgRc.contains("guess")) {
            myGuess = Integer.parseInt(msgRc.substring(6));
        }

        // allow user to guess until they got the random number correct
            if (myGuess < randomNumber) {
                dos.writeUTF("Your guessed number is lower");
            } else if (myGuess > randomNumber) {
                dos.writeUTF("Your guessed number is higher");
            } else {
                dos.writeUTF("You have finally guessed correctly");
            }

            // ensure records are written and send across socket
            dos.flush();
    }

        //close the input and output streams
        dos.close();
        bos.close();
        os.close();

        dis.close();
        bis.close();
        is.close();

    }
}
