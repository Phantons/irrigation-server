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
					String idRaspi = credencialesSeparadas[2];
					ArrayList<Usuario_Concreto_General> usuariosExistentes = Usuarios_Totales.getUsuariosExistentes();
					boolean idRaspiLibre = true;
					boolean nombreUsuarioLibre = true;
					for(int j1 = 0; j1 < usuariosExistentes.size(); j1 = j1 + 1){
						if(usuariosExistentes.get(j1).getIdRaspberry().equals(idRaspi)){
							idRaspiLibre = false;
							break;
						}
						if(usuariosExistentes.get(j1).getNombreUsuario().equals(nombreDeUsuario)){
							nombreUsuarioLibre = false;
							break;
						}
					}
					if((idRaspiLibre == true) && (nombreUsuarioLibre == true)){
						if((MetodosAuxiliares.nombreUsuarioPasswordFactible(nombreDeUsuario)) && (MetodosAuxiliares.nombreUsuarioPasswordFactible(contrasena))){
							Usuarios_Totales.anadirUsuario(new Usuario_Concreto_General(nombreDeUsuario, contrasena, idRaspi));
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
									estadoRiego = usuariosTotales.get(j1).getEstadoElectrovalvulas();
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
						Boolean[] arrayEstado = usuarioActual.getEstadoElectrovalvulas();
						if(arrayEstado[numeroValvula] != null){
							Integer[] arrayRegar = usuarioActual.getInicioManual();
							arrayRegar[numeroValvula] = tiempo;
							usuarioActual.setInicioManual(arrayRegar);
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
				else if(mensajeRecibido.substring(0, 7).equals("/MActv/")){
					String[] credencialesSeparadas = mensajeRecibido.substring(7, mensajeRecibido.length() - 1).split("/");
					String token = credencialesSeparadas[0];
					String modoActivo = credencialesSeparadas[1];
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
						usuarioActual.setModoActivo(modoActivo);
						this.enviarTexto("/Correcto/");
					}
					else{
						this.enviarTexto("/TokenMal/");
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/MInac/")){
					String[] credencialesSeparadas = mensajeRecibido.substring(7, mensajeRecibido.length() - 1).split("/");
					String token = credencialesSeparadas[0];
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
						ArrayList<String> modos = new ArrayList<String>();
						for(int j1 = 1; j1 < credencialesSeparadas.length; j1 = j1 + 1){
							modos.add(credencialesSeparadas[j1]);
						}
						usuarioActual.setModosInactivos(modos);
						this.enviarTexto("/Correcto/");
					}
					else{
						this.enviarTexto("/TokenMal/");
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/CnAct/")){
					if(mensajeRecibido.length() == 48){
						String token = mensajeRecibido.substring(7, 47);
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
							if(usuarioActual.getModoActivo() != null){
								this.enviarTexto("/AnswerAc/" + usuarioActual.getModoActivo() + "/");
							}
							else{
								this.enviarTexto("/AnswerAc/null/");
							}
						}
						else{
							this.enviarTexto("/TokenMal/");
						}
					}
				}
				else if(mensajeRecibido.substring(0, 7).equals("/CnIna/")){
					if(mensajeRecibido.length() == 48){
						String token = mensajeRecibido.substring(7, 47);
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
							ArrayList<String> modos = usuarioActual.getModosInactivos();
							String respuesta = "/AnswerIn/";
							for(int j1 = 0; j1 < modos.size(); j1 = j1 + 1){
								respuesta = respuesta + modos.get(j1) + "/";
							}
							this.enviarTexto(respuesta);
						}
						else{
							this.enviarTexto("/TokenMal/");
						}
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