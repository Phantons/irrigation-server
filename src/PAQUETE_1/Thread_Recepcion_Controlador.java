package PAQUETE_1;
import java.net.ServerSocket;
import java.net.Socket;

public class Thread_Recepcion_Controlador extends Thread{
	public void run(){
		ServerSocket socket = null;
		Socket socketSubUsuario = null;
		while(true){
			try{
				socket = new ServerSocket(3520);
				while(true){
					socketSubUsuario = null;
					socketSubUsuario = socket.accept();
					new Thread_Atencion_Controlador(socketSubUsuario).start();
				}
			}catch(Exception e){}
			finally{
				if(socket != null){
					try{
						socket.close();
					}catch(Exception e2){}
				}
				socketSubUsuario = null;
				socket = null;
			}
		}
	}
}