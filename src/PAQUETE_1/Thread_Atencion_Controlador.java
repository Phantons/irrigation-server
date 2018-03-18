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
				if(mensajeRecibido.substring(0, 7).equals("/ActMd/")){
					String id = mensajeRecibido.substring(7, mensajeRecibido.length() - 1);
					Usuario_Concreto_General usuarioActual = null;
					ArrayList<Usuario_Concreto_General> usuariosExistentes = Usuarios_Totales.getUsuariosExistentes();
					for(int j1 = 0; j1 < usuariosExistentes.size(); j1 = j1 + 1){
						if(usuariosExistentes.get(j1).getIdRaspberry().equals(id)){
							usuarioActual = usuariosExistentes.get(j1);
							break;
						}
					}
					if(usuarioActual != null){
						this.enviarTexto(usuarioActual.getModoActivo());
					}
					else{
						this.enviarTexto("IdFalso");
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/ShReg/")){
					String id = mensajeRecibido.substring(7, mensajeRecibido.length() - 1);
					Usuario_Concreto_General usuarioActual = null;
					ArrayList<Usuario_Concreto_General> usuariosExistentes = Usuarios_Totales.getUsuariosExistentes();
					for(int j1 = 0; j1 < usuariosExistentes.size(); j1 = j1 + 1){
						if(usuariosExistentes.get(j1).getIdRaspberry().equals(id)){
							usuarioActual = usuariosExistentes.get(j1);
							break;
						}
					}
					if(usuarioActual != null){
						Integer[] riego = usuarioActual.getInicioManual();
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
					Usuario_Concreto_General usuarioActual = null;
					ArrayList<Usuario_Concreto_General> usuariosExistentes = Usuarios_Totales.getUsuariosExistentes();
					for(int j1 = 0; j1 < usuariosExistentes.size(); j1 = j1 + 1){
						if(usuariosExistentes.get(j1).getIdRaspberry().equals(id)){
							usuarioActual = usuariosExistentes.get(j1);
							break;
						}
					}
					if(usuarioActual != null){
						Boolean[] estado = new Boolean[32];
						for(int j1 = 0; j1 < 32; j1 = j1 + 1){
							if(separados[j1 + 1].equals("null") == false){
								estado[j1] = Boolean.parseBoolean(separados[j1 + 1]);
							}
							else{
								estado[j1] = null;
							}
						}
						usuarioActual.setEstadoElectrovalvulas(estado);
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