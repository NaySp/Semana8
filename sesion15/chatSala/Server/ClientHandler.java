import java.io.*;
import java.net.*;
import java.util.*;

//esta clase se debe encargar de gestionar los clientes de forma individual
//implementa la interfaz Runnable y en el metodo run valida el nombre de usuario
//agrega el usuario y su canal de comunicacion a la lista de chatters
//permite enviar mensajes a todos los usuarios
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private String clientName;
    Chatters clientes;

    public ClientHandler(Socket socket, Chatters clientes) {
        this.clientSocket = socket;
        this.clientes = clientes;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            out.println("Enter your username:");
            clientName = in.readLine();

            // Validate username
            while (clientes.userExist(clientName)) {
                out.println("Username already taken. Please enter a different username:");
                clientName = in.readLine();
            }

            // Notify all clients about the new user
            clientes.sendMessageToall(clientName + " has joined.");

            // Add the new user to the list of clients
            clientes.addUser(new Person(clientName, out));

            // Notify the new user that they've been accepted
            out.println("Welcome, " + clientName + "!");

            // Handle messages from the client
            String message;
            while ((message = in.readLine()) != null) {
                clientes.sendMessageToall(clientName + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Remove the client when they disconnect
            clientes.eliminatePerson(new Person(clientName, out));
            clientes.sendMessageToall(clientName + " has left.");
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
