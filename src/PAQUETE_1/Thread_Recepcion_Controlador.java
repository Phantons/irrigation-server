package PAQUETE_1;
import java.net.ServerSocket;
import java.net.Socket;

public class Thread_Recepcion_Controlador extends Thread{
	public void run(){
		ServerSocket socket = null;
		Socket socketControlador = null;
		while(true){
			try{
				socket = new ServerSocket(4320);
				while(true){
					socketControlador = null;
					socketControlador = socket.accept();
					new Thread_Atencion_Controlador(socketControlador).start();
				}
			}catch(Exception e){}
			finally{
				if(socket != null){
					try{
						socket.close();
					}catch(Exception e2){}
				}
				socketControlador = null;
				socket = null;
			}
		}
	}
}