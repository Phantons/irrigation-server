package PAQUETE_1;
import java.util.ArrayList;

public class MetodosAuxiliares {
	private static String todosCaracteres = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static String getTokenAleatorio(){
		boolean tokenLibre = false;
		String salida = "";
		int numeroAleatorio = 0;
		while(tokenLibre == false){
			salida = "";
			for(int j1 = 0; j1 < 40; j1 = j1 + 1){
				numeroAleatorio = (int) (Math.random() * (61));
				salida = salida + MetodosAuxiliares.todosCaracteres.charAt(numeroAleatorio);
			}
			tokenLibre = MetodosAuxiliares.checkTokenLibre(salida);
		}
		return salida;
	}
	public static boolean nombreUsuarioPasswordFactible(String nombreUsuario){
		if(nombreUsuario.length() > 25){
			return false;
		}
		if(nombreUsuario.length() < 5){
			return false;
		}
		for(int j1 = 0; j1 < nombreUsuario.length(); j1 = j1 + 1){
			if((nombreUsuario.charAt(j1) == ' ') || (nombreUsuario.charAt(j1) == '/')){
				return false;
			}
		}
		return true;
	}
	private static boolean checkTokenLibre(String token){
		ArrayList<Usuario_Concreto_Activo> UsuariosActivosActuales = Usuarios_Totales_Activos.getUsuariosActivos();
		for(int j1 = 0; j1 < UsuariosActivosActuales.size(); j1 = j1 + 1){
			if(UsuariosActivosActuales.get(j1).getToken().equals(token)){
				return false;
			}
		}
		return true;
	}
}