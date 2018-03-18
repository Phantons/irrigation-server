package PAQUETE_1;
import java.util.ArrayList;

public class Usuarios_Totales {
	private static ArrayList<Usuario_Concreto_General> usuariosExistentes = new ArrayList<Usuario_Concreto_General>();
	
	public static synchronized ArrayList<Usuario_Concreto_General> getUsuariosExistentes(){
		ArrayList<Usuario_Concreto_General> salida = new ArrayList<Usuario_Concreto_General>();
		for(int j1 = 0; j1 < Usuarios_Totales.usuariosExistentes.size(); j1 = j1 + 1){
			salida.add(Usuarios_Totales.usuariosExistentes.get(j1));
		}
		return salida;
	}
	public static synchronized void anadirUsuario(Usuario_Concreto_General nuevoUsuario){
		Usuarios_Totales.usuariosExistentes.add(nuevoUsuario);
	}
}