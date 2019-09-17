package misockets;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor  {

	public static void main(String[] args) {
		MarcoServidor mimarco=new MarcoServidor();
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MarcoServidor() {
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);

		Thread mihilo = new Thread(this);

		mihilo.start();
		
		}
	
	private	JTextArea areatexto;
	@Override



	public void run(){
		// System.out.println("Prueba qla buena");
		try{
			ServerSocket servidor = new ServerSocket(9999);

			String nick, ip, mensaje;

			PaqueteEnvio paquete_recibido;

			while(true){
				Socket misocket = servidor.accept();

			    // DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());
				ObjectInputStream paquete_datos=new ObjectInputStream(misocket.getInputStream());		
				// String mensaje_texto = flujo_entrada.readUTF();
				paquete_recibido=(PaqueteEnvio) paquete_datos.readObject();
				// areatexto.append("\n" + mensaje_texto);
				nick=paquete_recibido.getNick();
				ip=paquete_recibido.getIp();
				mensaje=paquete_recibido.getMensaje();

				areatexto.append("\n" + nick +": "+mensaje+" para "+ip);
				
				// Servidor se transforma en cliente aqui

				Socket enviaDestinatario=new Socket(ip,9090);

				ObjectOutputStream paqueteReenvio=new ObjectOutputStream(enviaDestinatario.getOutputStream());

				paqueteReenvio.writeObject(paquete_recibido);

				enviaDestinatario.close(); 

			    misocket.close();
		}
		} catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		
		
	}
}
