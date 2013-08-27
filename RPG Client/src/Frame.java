import java.awt.*;
import java.io.*;
import java.net.Socket;

import javax.swing.JApplet;

public class Frame extends JApplet{

	private static final long serialVersionUID = -75L;
	Socket socket = null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;

	public static final int HEART_BEAT = -3;

	Thread mainloop = new Thread(new Runnable(){
		public void run(){
			proccessInput(read());
		}
	});

	public Frame(){
		try{
			socket = new Socket("localhost", 69);
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
		}catch(Exception e){
			e.printStackTrace();
		}
		createFrame();
		mainloop.setPriority(10);
		mainloop.start();
	}

	public void proccessInput(Object o){
		if(o instanceof Integer){
			if((int) o == HEART_BEAT){
				write(HEART_BEAT);
			}
		}
		log(o);
	}

	public void createFrame(){
		Container c = getContentPane();

		c.setSize(800, 600);
		c.setLayout(null);
		c.setBackground(Color.BLACK);
	}

	public void paint(Graphics g2){
		super.paint(g2);

		Graphics2D g = (Graphics2D) g2;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.YELLOW);
		g.fillRect(100, 100, 50, 50);

		log("hi");
		repaint();
	}

	public void destroy(){

	}

	// --------------------------------UTIL--------------------------------//

	public Object read(){
		Object o = null;
		try{
			o = in.readObject();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(o == null){
			System.out.println("IT SHIT IT'S PANTS");
			destroy();
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

	private void log(Object o){
		System.out.println(o);
	}
}
