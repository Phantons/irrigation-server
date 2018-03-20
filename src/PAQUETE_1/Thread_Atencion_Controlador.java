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
				String mensajeRecibido = this.leerTexto();
				if(mensajeRecibido.substring(0, 7).equals("/AskCo/")){
					String id = mensajeRecibido.substring(7, mensajeRecibido.length() - 1);
					Controlador controller = null;
					ArrayList<Usuario_Concreto_General> usuariosExistentes = Usuarios_Totales.getUsuariosExistentes();
					for(int j1 = 0; j1 < usuariosExistentes.size(); j1 = j1 + 1){
						if(usuariosExistentes.get(j1).getControladorPorId(id) != null){
							controller = usuariosExistentes.get(j1).getControladorPorId(id);
							break;
						}
					}
					if(controller != null){
						this.enviarTexto(controller.getObjetoSerializado());
					}
					else{
						this.enviarTexto("IdFalso");
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/ShReg/")){
					String id = mensajeRecibido.substring(7, mensajeRecibido.length() - 1);
					ArrayList<Usuario_Concreto_General> usuariosExistentes = Usuarios_Totales.getUsuariosExistentes();
					Controlador controller = null;
					for(int j1 = 0; j1 < usuariosExistentes.size(); j1 = j1 + 1){
						if(usuariosExistentes.get(j1).getControladorPorId(id) != null){
							controller = usuariosExistentes.get(j1).getControladorPorId(id);
							break;
						}
					}
					if(controller != null){
						Integer[] riego = controller.getRegarAhora();
						String respuesta = "/";
						for(int j1 = 0; j1 < riego.length; j1 = j1 + 1){
							if(riego[j1] != null){
								respuesta = respuesta + Integer.toString(riego[j1]) + "/";
							}
							else{
								respuesta = respuesta + "null/";
							}
						}
						this.enviarTexto(respuesta);
					}
					else{
						this.enviarTexto("IdFalso");
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/InfRg/")){
					String[] separados = mensajeRecibido.substring(7, mensajeRecibido.length() - 1).split("/");
					String id = separados[0];
					Controlador controller = null;
					ArrayList<Usuario_Concreto_General> usuariosExistentes = Usuarios_Totales.getUsuariosExistentes();
					for(int j1 = 0; j1 < usuariosExistentes.size(); j1 = j1 + 1){
						if(usuariosExistentes.get(j1).getControladorPorId(id) != null){
							controller = usuariosExistentes.get(j1).getControladorPorId(id);
							break;
						}
					}
					if(controller != null){
						Boolean[] estado = new Boolean[32];
						for(int j1 = 0; j1 < 32; j1 = j1 + 1){
							if(separados[j1 + 1].equals("null") == false){
								estado[j1] = Boolean.parseBoolean(separados[j1 + 1]);
							}
							else{
								estado[j1] = null;
							}
						}
						controller.setEstado(estado);
					}
				}
			}
		}catch(Exception e){}
		finally{
			try{
				this.socket.close();
			}catch(Exception e2){}
		}
	}
}