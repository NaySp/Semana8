import java.util.Set;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Chatters {
    // tendra una lista de personas que seran nuestros clientes
    // cada persona tiene un nombre y un canal para enviarle mensajes
    private Set<Person> clientes = new HashSet<>();

    public Chatters() {
    }

    // metodo para verificar si un usuario existe, retorna true si existe
    public boolean userExist(String username) {
        for (Person person : clientes) {
            if (person.getName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // metodo para agregar un usuario nuevo
    public void addUser(Person person) {
        boolean exist = userExist(person.getName());
        if (exist == true) {
            System.out.println("persona ya existe");
        } else {
            clientes.add(person);
            System.out.println("persona agragada");
        }

    }

    // metodo para eliminar un usuario
    public void eliminatePerson(Person person) {
        boolean exist = userExist(person.getName());
        if (exist == true) {
            clientes.remove(person);
            System.out.println("persona eliminada");
        } else {
            System.out.println("no existe");
        }
    }

    // metodo para enviar un mensaje a todos los usuarios
    public void sendMessageToall(String message) {
        for (Person person : clientes) {
            person.getOut().write(message);
        }
    }

}