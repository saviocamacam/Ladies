/**
 * Solicita o servico
 * autor: Rodrigo Campiolo
 * data:22/11/2006
 */

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

 public class Cliente {
     public static void main(String args[]) {
         try {
             System.out.println ("Cliente iniciado ...");

             if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
             } //if

            /* obtem a referencia para o objeto remoto */
             Registry registry = LocateRegistry.getRegistry("localhost");
	     Calculadora c = (Calculadora)registry.lookup("ServicoCalculadora");

	     System.out.println("20+4=" + c.soma(20,4));
	     System.out.println("20-4=" + c.subtrai(20,4));
	     System.out.println("20*4=" + c.multiplica(20,4));
	     System.out.println("20/4=" + c.divide(20,4));
         } catch (Exception e) {
            System.out.println(e);
         } //catch

     } //main
 } //Cliente