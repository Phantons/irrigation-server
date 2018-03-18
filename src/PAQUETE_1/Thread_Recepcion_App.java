package PAQUETE_1;
import java.net.ServerSocket;
import java.net.Socket;

public class Thread_Recepcion_App extends Thread{
	public void run(){
		ServerSocket socket = null;
		Socket socketApp = null;
		while(true){
			try{
				socket = new ServerSocket(4321);
				while(true){
					socketApp = null;
					socketApp = socket.accept();
					new Thread_Atencion_App(socketApp).start();
				}
			}catch(Exception e){}
			finally{
				if(socket != null){
					try{
						socket.close();
					}catch(Exception e2){}
				}
				socketApp = null;
				socket = null;
			}
		}
	}
}