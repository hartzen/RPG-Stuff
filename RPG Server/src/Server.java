import java.net.*;

public class Server{

	Client[] clients;
	Socket socket;
	ServerSocket server;
	boolean running = true;

	public static void main(String[] boobies){
		new Server();
	}

	public Server(){
		clients = new Client[2];
		try{
			server = new ServerSocket(69);
			System.out.println("//---------LISTENING ON PORT: " + server.getLocalPort() + "----------//");
		}catch(Exception e){
			e.printStackTrace();
		}

		while(running){
			try{
				socket = server.accept();
				System.out.println("//---------ACCEPTED CLIENT FROM IP: " + socket.getInetAddress() + "----------//");
				throwOutTheTrash();

			}catch(Exception e){
				e.printStackTrace();
			}
			for(int i = 0; i <= 2; i++){
				if(clients[i] == null){
					clients[i] = new Client(socket, clients);
					break;
				}
			}
		}
	}

	private void throwOutTheTrash(){
		System.gc();
	}

	public void halt(){
		this.running = false;
	}
}
