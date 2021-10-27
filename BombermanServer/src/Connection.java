import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
	public static void main(String[] args) {
		final int port = 65432;
		
		try (ServerSocket socketServeur = new ServerSocket(port)) {
			System.out.println("Lancement du serveur");
			while (true) {
				try (
					Socket socketClient = socketServeur.accept();
					BufferedReader in = new BufferedReader(
							new InputStreamReader(socketClient.getInputStream())
					);
				) {
					System.out.println("Connexion avec : " + socketClient.getInetAddress());
					String message = in.readLine();
					System.out.println(message);
				}
			}
		} catch (IOException e) {
			System.out.println("Erreur sur le port " + port + " ou erreur de connexion");
		}
	}
}
