	package tools;
	
	import java.security.SecureRandom;

	public class Tools {

	    public static String generateToken() {
	        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	        int tokenLength = 60;
	        SecureRandom random = new SecureRandom();
	        
	        StringBuilder token = new StringBuilder(tokenLength);
	        for (int i = 0; i < tokenLength; i++) {
	            int index = random.nextInt(characters.length());
	            token.append(characters.charAt(index));
	        }
	        return token.toString();
	    }

	    public static void main(String[] args) {
	        // Prueba de generación de token
	        System.out.println("Token generado: " + generateToken());
	    }
	}
