import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class Client {
    Socket socket;
    final String HOST;
    final int PORT;
    BufferedReader in;
    PrintWriter out;

    public Client(String HOST, int PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
    }

    public void run() {
        //Create socket connection
        try {
            socket = new Socket(HOST, PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //create printwriter for sending login to server
        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //create Buffered reader for reading response from server
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String choice = JOptionPane.showInputDialog(null,
                "Hello client!\n" +
                        "Registration press - 1\n" +
                        "Authorisation press - 2\n" +
                        "For exit press - any button\n");

//        out.println(choice);
//        out.flush();

        while (true) {
            out.println(choice);
            out.flush();
            if (choice.equals("1")) {
                newRegistration();
                break;
            } else if (choice.equals("2")) {
                try {
                    logIn();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            } else //if (choice.equals("3"))
            {
                System.out.println("Disconnected...");
                break;
            }
        }
    }

    private void logIn() {
        boolean access = false;
        do {
            //prompt for user name
            String username = JOptionPane.showInputDialog(null, "Enter User Name:");

            //send username to server
            out.println(username);

            //prompt for password
            String password = JOptionPane.showInputDialog(null, "Enter Password");

            //send password to server
            out.println(password);
            out.flush();

            //read response from server
            String response;
            try {
                response = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //display response
            JOptionPane.showMessageDialog(null, response);
            if (response.equals("Access granted")) {
                access = true;
            }
        } while (!access);

    }

    private void newRegistration() {

        //prompt for user name
        String username = JOptionPane.showInputDialog(null, "Enter User Name:");

        //send username to server
        out.println(username);

        //prompt for password
        String password = JOptionPane.showInputDialog(null, "Enter Password: ");

        //send password to server
        out.println(password);
        out.flush();
        JOptionPane.showMessageDialog(null, "User " + username +  " is registration");

    }
}

