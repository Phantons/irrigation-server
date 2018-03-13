package PAQUETE_1;
import java.net.Socket;
import java.util.ArrayList;

public class Thread_Atencion_App extends Thread_TCP_Cifrado{
	public Thread_Atencion_App(Socket socketApp){
		super(socketApp);
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