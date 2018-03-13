package PAQUETE_1;
import java.net.Socket;
import java.util.ArrayList;

public class Thread_Atencion_Controlador extends Thread_TCP_Cifrado{
	public Thread_Atencion_Controlador(Socket socketControlador){
		super(socketControlador);
	}
	public void run(){
		try{
			if(this.inicializacionCifrado() == true){
				
			}
		}catch(Exception e){}
		finally{
			try{
				this.socket.close();
			}catch(Exception e2){}
		}
	}
}