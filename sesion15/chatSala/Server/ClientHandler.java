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
    private Chatters clientes;

    public ClientHandler(Socket socket, Chatters clientes) {
        try {
            // Asignar los objetos recibidos a los atributos de la clase
            this.clientSocket = socket;
            this.clientes = clientes;
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Solicitar al cliente un nombre de usuario
            out.println("Ingrese su nombre de usuario: ");
            clientName = in.readLine();

            // Verificar si el nombre de usuario ya está en uso
            while (clientes.userExist(clientName)) {
                out.println("El nombre de usuario ya está en uso. Ingrese otro: ");
                clientName = in.readLine();
            }

            // Notificar a los demás clientes que un nuevo usuario se ha unido
            clientes.sendMessageToall(clientName + " se ha unido al chat.");

            // Agregar al nuevo usuario a la lista de clientes
            Person newClient = new Person(clientName, out);
            clientes.addUser(newClient);

            // Notificar al cliente que su nombre de usuario ha sido aceptado
            out.println("Usuario aceptado.");

            String message;
            // Escuchar y retransmitir los mensajes del cliente
            while ((message = in.readLine()) != null) {
                // Enviar el mensaje a todos los usuarios
                clientes.sendMessageToall(clientName + ": " + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cuando el cliente se desconecta, eliminarlo de la lista de clientes
            clientes.eliminatePerson(new Person(clientName, out));
            clientes.sendMessageToall(clientName + " se ha desconectado.");
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
