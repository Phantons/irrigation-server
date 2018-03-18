package PAQUETE_1;
import java.util.ArrayList;

public class Usuarios_Totales_Activos {
	private static ArrayList<Usuario_Concreto_Activo> usuariosActivos = new ArrayList<Usuario_Concreto_Activo>();
	
	public static synchronized ArrayList<Usuario_Concreto_Activo> getUsuariosActivos(){
		ArrayList<Usuario_Concreto_Activo> salida = new ArrayList<Usuario_Concreto_Activo>();
		for(int j1 = 0; j1 < Usuarios_Totales_Activos.usuariosActivos.size(); j1 = j1 + 1){
			salida.add(Usuarios_Totales_Activos.usuariosActivos.get(j1));
		}
		return salida;
	}
	public static synchronized String anadirUsuarioActivo(String nombreUsuario){
		for(int j1 = 0; j1 < Usuarios_Totales_Activos.usuariosActivos.size(); j1 = j1 + 1){
			if(Usuarios_Totales_Activos.usuariosActivos.get(j1).getNombreUsuario().equals(nombreUsuario)){
				Usuarios_Totales_Activos.usuariosActivos.remove(j1);
				break;
			}
		}
		String token = MetodosAuxiliares.getTokenAleatorio();
		Usuarios_Totales_Activos.usuariosActivos.add(new Usuario_Concreto_Activo(nombreUsuario, token));
		return token;
	}
	public static synchronized void eliminarUsuarioActivo(String token){
		for(int j1 = 0; j1 < Usuarios_Totales_Activos.usuariosActivos.size(); j1 = j1 + 1){
			if(Usuarios_Totales_Activos.usuariosActivos.get(j1).getToken().equals(token)){
				Usuarios_Totales_Activos.usuariosActivos.remove(j1);
				return;
			}
		}
	}
}