import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

    ServerSocket serversocket;
    Socket client;
    //final String HOST;
    final int PORT;
    BufferedReader in;
    PrintWriter out;

    public Server(int PORT) {
        //  this.HOST = HOST;
        this.PORT = PORT;
    }

    public void run() throws IOException {
        serversocket = new ServerSocket(PORT);
        System.out.println("Connection Starting on port:" + serversocket.getLocalPort());

        //accept connection from client
        client = serversocket.accept();
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
        menu();
    }

    public void menu () {
        while (true) {
            String choice = null;
            try {
                choice = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (choice.equals("1")) {
                fileWriter();
                break;
            } else if (choice.equals("2")) {
                try {
                    logInfo();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else //if (choice.equals("3"))
            {
                System.out.println("Disconnected...");
                break;
            }
        }
    }

    public void logInfo() throws Exception {
        //open buffered reader for reading data from client
        boolean access = false;

        do {
            String username = in.readLine();
            String password = in.readLine();
            if (checkUser(username,password))
            //(username.equalsIgnoreCase("user") && password.equals("qwerty"))
            {
                out.println("Access granted");
                access = true;
            } else {
                out.println("Invalid login or password");
            }
            out.flush();
        } while (!access);
    }

    // create map
    public Map<String, String> mapLogPass() {
        Map<String, String> mapLoginPassword = new HashMap<>();
        Scanner dataIn = new Scanner
                ("C:\\Users\\User\\IdeaProjects\\HW7\\src\\loginPassword.txt");

        while (dataIn.hasNextLine()) {
            String[] words = dataIn.nextLine().split(",");
            mapLoginPassword.put(words[0], words[1]);
        }
        return mapLoginPassword;
    }

    public boolean checkUser(String log, String pass) {
        boolean check = false;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader
                    ("C:\\Users\\User\\IdeaProjects\\HW7\\src\\loginPassword.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                if (log.equalsIgnoreCase(words[0]) && pass.equals(words[words.length - 1])) {
                    check = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    public void fileWriter() {
        try {

            FileWriter file = new FileWriter
                    ("C:\\Users\\User\\IdeaProjects\\HW7\\src\\loginPassword.txt", true);
            String login = in.readLine();
            String password = in.readLine();
            file.write(login + "," + password + "\n");
            file.close();

        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
        public Map<String,String> addMapUser() {
            Map<String,String> mapLogPass= new HashMap<>();

            String login,password;
            try {
                login = in.readLine();
                password = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
                mapLogPass.put(login,password);
            return mapLogPass;
        }
    }


