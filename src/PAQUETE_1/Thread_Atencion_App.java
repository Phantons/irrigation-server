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
				String mensajeRecibido = this.leerTexto();
				if(mensajeRecibido.substring(0, 7).equals("/LogIn/")){
					String[] credencialesSeparadas = mensajeRecibido.substring(7, mensajeRecibido.length() - 1).split("/");
					String nombreDeUsuario = credencialesSeparadas[0];
					String contrasena = credencialesSeparadas[1];
					ArrayList<Usuario_Concreto_General> usuariosExistentes = Usuarios_Totales.getUsuariosExistentes();
					boolean encontrado = false;
					for(int j1 = 0; j1 < usuariosExistentes.size(); j1 = j1 + 1){
						if(usuariosExistentes.get(j1).getNombreUsuario().equals(nombreDeUsuario)){
							if(usuariosExistentes.get(j1).getContrasena().equals(contrasena)){
								String token = Usuarios_Totales_Activos.anadirUsuarioActivo(nombreDeUsuario);
								this.enviarTexto("/Correcto/" + token + "/");
								encontrado = true;
							}
							else{
								this.enviarTexto("/Incorrec/");
								encontrado = true;
							}
						}
					}
					if(encontrado == false){
						this.enviarTexto("/Incorrec/");
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/LogOu/")){
					if(mensajeRecibido.length() == 48){
						String token = mensajeRecibido.substring(7, 47);
						Usuarios_Totales_Activos.eliminarUsuarioActivo(token);
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/NewUs/")){
					String[] credencialesSeparadas = mensajeRecibido.substring(7, mensajeRecibido.length() - 1).split("/");
					String nombreDeUsuario = credencialesSeparadas[0];
					String contrasena = credencialesSeparadas[1];
					ArrayList<Usuario_Concreto_General> usuariosExistentes = Usuarios_Totales.getUsuariosExistentes();
					boolean nombreUsuarioLibre = true;
					for(int j1 = 0; j1 < usuariosExistentes.size(); j1 = j1 + 1){
						if(usuariosExistentes.get(j1).getNombreUsuario().equals(nombreDeUsuario)){
							nombreUsuarioLibre = false;
							break;
						}
					}
					if(nombreUsuarioLibre == true){
						if((MetodosAuxiliares.nombreUsuarioPasswordFactible(nombreDeUsuario)) && (MetodosAuxiliares.nombreUsuarioPasswordFactible(contrasena))){
							Usuarios_Totales.anadirUsuario(new Usuario_Concreto_General(nombreDeUsuario, contrasena));
							this.enviarTexto("/CreadoOk/");
						}
						else{
							this.enviarTexto("/BadUsPas/");
						}
					}
					else{
						this.enviarTexto("/IdOcupad/");
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/GetRe/")){
					if(mensajeRecibido.length() == 48){
						String token = mensajeRecibido.substring(7, 47);
						String idControlador = mensajeRecibido.substring(48, mensajeRecibido.length() - 1);
						String nombreDeUsuario = null;
						ArrayList<Usuario_Concreto_Activo> usuariosActivos = Usuarios_Totales_Activos.getUsuariosActivos();
						for(int j1 = 0; j1 < usuariosActivos.size(); j1 = j1 + 1){
							if(usuariosActivos.get(j1).getToken().equals(token)){
								nombreDeUsuario = usuariosActivos.get(j1).getNombreUsuario();
								break;
							}
						}
						if(nombreDeUsuario != null){
							Boolean[] estadoRiego = null;
							ArrayList<Usuario_Concreto_General> usuariosTotales = Usuarios_Totales.getUsuariosExistentes();
							for(int j1 = 0; j1 < usuariosTotales.size(); j1 = j1 + 1){
								if(usuariosTotales.get(j1).getNombreUsuario().equals(nombreDeUsuario)){
									estadoRiego = usuariosTotales.get(j1).getControladorPorId(idControlador).getEstadoRiego();
									break;
								}
							}
							String salida = "/AnswerRg/";
							for(int j1 = 0; j1 < estadoRiego.length; j1 = j1 + 1){
								if(estadoRiego[j1] != null){
									salida = salida + Boolean.toString(estadoRiego[j1]) + "/";
								}
								else{
									salida = salida + "null/";
								}
							}
							this.enviarTexto(salida);
						}
						else{
							this.enviarTexto("/TokenMal/");
						}
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/Regar/")){
					String[] credencialesSeparadas = mensajeRecibido.substring(7, mensajeRecibido.length() - 1).split("/");
					String token = credencialesSeparadas[0];
					int numeroValvula = Integer.parseInt(credencialesSeparadas[1]);
					int tiempo = Integer.parseInt(credencialesSeparadas[2]);
					String idControlador = credencialesSeparadas[3];
					String nombreDeUsuario = null;
					ArrayList<Usuario_Concreto_Activo> usuariosActivos = Usuarios_Totales_Activos.getUsuariosActivos();
					for(int j1 = 0; j1 < usuariosActivos.size(); j1 = j1 + 1){
						if(usuariosActivos.get(j1).getToken().equals(token)){
							nombreDeUsuario = usuariosActivos.get(j1).getNombreUsuario();
							break;
						}
					}
					if(nombreDeUsuario != null){
						Usuario_Concreto_General usuarioActual = null;
						ArrayList<Usuario_Concreto_General> usuariosTotales = Usuarios_Totales.getUsuariosExistentes();
						for(int j1 = 0; j1 < usuariosTotales.size(); j1 = j1 + 1){
							if(usuariosTotales.get(j1).getNombreUsuario().equals(nombreDeUsuario)){
								usuarioActual = usuariosTotales.get(j1);
							}
						}
						boolean todoBien = usuarioActual.getControladorPorId(idControlador).regarConcreto(numeroValvula, tiempo);
						if(todoBien == true){
							this.enviarTexto("/True/");
						}
						else{
							this.enviarTexto("/False/");
						}
					}
					else{
						this.enviarTexto("/TokenMal/");
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/UpCon/")){
					String[] credencialesSeparadas = mensajeRecibido.substring(7, mensajeRecibido.length() - 1).split("/");
					String token = credencialesSeparadas[0];
					String nuevoControlador = credencialesSeparadas[1];
					String idControlador = credencialesSeparadas[2];
					String nombreDeUsuario = null;
					ArrayList<Usuario_Concreto_Activo> usuariosActivos = Usuarios_Totales_Activos.getUsuariosActivos();
					for(int j1 = 0; j1 < usuariosActivos.size(); j1 = j1 + 1){
						if(usuariosActivos.get(j1).getToken().equals(token)){
							nombreDeUsuario = usuariosActivos.get(j1).getNombreUsuario();
							break;
						}
					}
					if(nombreDeUsuario != null){
						Usuario_Concreto_General usuarioActual = null;
						ArrayList<Usuario_Concreto_General> usuariosTotales = Usuarios_Totales.getUsuariosExistentes();
						for(int j1 = 0; j1 < usuariosTotales.size(); j1 = j1 + 1){
							if(usuariosTotales.get(j1).getNombreUsuario().equals(nombreDeUsuario)){
								usuarioActual = usuariosTotales.get(j1);
							}
						}
						Controlador controller = usuarioActual.getControladorPorId(idControlador);
						if(controller != null){
							controller.setObjetoSerializado(nuevoControlador);
						}
						else{
							//TODO: Revisar que el ID no este cojido
							usuarioActual.anadirControlador(new Controlador(idControlador, nuevoControlador));
						}
						this.enviarTexto("/Correcto/");
					}
					else{
						this.enviarTexto("/TokenMal/");
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/AllCo/")){
					String token = mensajeRecibido.substring(7, mensajeRecibido.length() - 1);
					String nombreDeUsuario = null;
					ArrayList<Usuario_Concreto_Activo> usuariosActivos = Usuarios_Totales_Activos.getUsuariosActivos();
					for(int j1 = 0; j1 < usuariosActivos.size(); j1 = j1 + 1){
						if(usuariosActivos.get(j1).getToken().equals(token)){
							nombreDeUsuario = usuariosActivos.get(j1).getNombreUsuario();
							break;
						}
					}
					if(nombreDeUsuario != null){
						Usuario_Concreto_General usuarioActual = null;
						ArrayList<Usuario_Concreto_General> usuariosTotales = Usuarios_Totales.getUsuariosExistentes();
						for(int j1 = 0; j1 < usuariosTotales.size(); j1 = j1 + 1){
							if(usuariosTotales.get(j1).getNombreUsuario().equals(nombreDeUsuario)){
								usuarioActual = usuariosTotales.get(j1);
							}
						}
						ArrayList<String> pene = usuarioActual.getControladoresSerializados();
						String respuesta = "/AnswerCo/";
						for(int j1 = 0; j1 < pene.size(); j1 = j1 + 1){
							respuesta = respuesta + pene.get(j1) + "/";
						}
						this.enviarTexto(respuesta);
					}
					else{
						this.enviarTexto("/TokenMal/");
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