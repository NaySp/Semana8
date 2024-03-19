import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 6789;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, PORT);
            System.out.println("Conectado al servidor.");

            String message;
                    
            //canal de entrada para el usuario
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in)); 
            
            //usando el socket, crear los canales de entrada in y salida out
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                      
            //solicitar al usuario un alias, o nombre y enviarlo al servidor
            String user;
            while (true) {
                System.out.print("Ingrese su nombre de usuario: ");
                user = userInput.readLine();
                out.println(user);

                // Esperar la respuesta del servidor
                String response = in.readLine();
                if (response.equals("Aceptado")) {
                    System.out.println("Nombre de usuario aceptado.");
                    break;
                } else {
                    System.out.println("Nombre de usuario no aceptado. Por favor, ingrese otro nombre.");
                }
            }
            //no debe salir de este bloque hasta que el nombre no sea aceptado
            //al ser aceptado notificar, de lo contrario seguir pidiendo un alias
            while (true) {
                System.out.print("Ingrese un mensaje: ");
                message = userInput.readLine();
                out.println(message);if (message.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            //creamos el objeto Lector e iniciamos el hilo que nos permitira estar atentos a los mensajes
            //que llegan del servidor
            //inicar el hilo
            Lector lector = new Lector(in);
            Thread hiloLector = new Thread(lector);
            hiloLector.start();

            //estar atento a la entrada del usuario para poner los mensajes en el canal de salida out
            while (true) {
                System.out.print("Ingrese un mensaje: ");
                message = userInput.readLine();
                out.println(message);
                if (message.equalsIgnoreCase("chao")) {
                    break;
                }
            }

            userInput.close();
            in.close();
            out.close();
            socket.close();
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
