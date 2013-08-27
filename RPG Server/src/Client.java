import java.io.*;
import java.net.Socket;

public class Client extends Thread implements Serializable{

	private static final long serialVersionUID = -75L;

	ObjectInputStream in = null;
	ObjectOutputStream out = null;

	Socket socket = null;
	Client[] clients = null;

	public static final int HEART_BEAT = -3;
	boolean running = true;

	public Client(Socket socket, Client[] clients){
		log("CONSTRUCT");
		this.socket = socket;
		this.clients = clients;
		log("1");
		this.start();
		log("2");
		log("END CONSTRUCT");
	}

	public void createConnections(){
		try{

			this.out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			this.in = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void run(){
		createConnections();
		log("Thread started");
		while(running){
			if(!socket.isClosed()){
				proccessInput(read());
			}
		}
		destroyClient();
	}

	public void proccessInput(Object o){
		if(o == null)
			return;
		if(o instanceof Integer){
			if((int) o == HEART_BEAT){
				write(HEART_BEAT);
			}
		}
		log(o);
	}

	public Object read(){
		Object o = null;
		try{
			o = in.readObject();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(o == null){
			destroyClient();
		}
		return o;
	}

	public void write(Object paramObject){
		try{
			out.writeObject(paramObject);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void destroyClient(){
		running = false;
		try{
			out.close();
			in.close();
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	//--------------------------------UTIL--------------------------------//

	public void log(Object paramObject){
		System.out.println(paramObject);
	}

}
