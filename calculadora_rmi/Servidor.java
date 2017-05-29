/**
 * Inicializa o servico
 * autor: Rodrigo Campiolo
 * data: 22/11/2006
 */
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Servidor {
     public static void main(String args[]) {
       try {
             if (System.getSecurityManager() == null) {
                 System.setSecurityManager(new SecurityManager());
             }

             /* inicializa um objeto remoto */
             Calculadora calc = new Calc();

             /* registra o objeto remoto no Binder */
             Registry registry = LocateRegistry.getRegistry("localhost");
	         registry.bind("ServicoCalculadora", calc);

	         /* aguardando invocacoes remotas */
	         System.out.println("Servidor pronto ...");
	     } catch (Exception e) {
	         System.out.println(e);
         } //catch
     } //main
} //Servidor