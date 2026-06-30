package es.jccm.edu.gestionidentidades.adapter.out;

public class ValidadorLogin {

	public boolean isLoginCumplePolitica(String login){
		return login!=null
				&& login.matches("[a-z0-9]+") 
				&& login.length()>=8
				&& login.length()<=18;
	}

}