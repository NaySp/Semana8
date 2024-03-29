import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {

        int PORT = 6789;
        Chatters clientes = new Chatters(); // lista de clientes

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado. Esperando clientes...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado");

                // crea el objeto para gestionar al cliente y le envia la informacion necesaria
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientes);

                // inicia el hilo para ese cliente
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
