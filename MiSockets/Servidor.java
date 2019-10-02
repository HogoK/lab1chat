package MiSockets;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
// import java.net.ServerSocket;
import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);

		Thread mihilo = new Thread (this);

		mihilo.start();
		
		}
	
	

	public void run(){
		try{
			ServerSocket servidor = new ServerSocket(9999);
			String nick, ip, mensaje;
			ArrayList <String> listaIp = new ArrayList<String>();// Arreglo que guarda las ips de los clientes conectados al chat
			PaqueteEnvio paquete_recibido;
			while(true){
				Socket misocket = servidor.accept();
				
				
				ObjectInputStream paquete_datos = new ObjectInputStream(misocket.getInputStream());
				paquete_recibido = (PaqueteEnvio) paquete_datos.readObject();
				nick = paquete_recibido.getNick();
				ip = paquete_recibido.getIp();
				mensaje = paquete_recibido.getMensaje();
				
				if(!mensaje.equals(" Online")) {
				areatexto.append("\n" + nick + ": " + mensaje + " para "+ ip);
				Socket enviaDestinatario = new Socket(ip,9090);
				ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
				paqueteReenvio.writeObject(paquete_recibido);
				paqueteReenvio.close();
				enviaDestinatario.close();
			    misocket.close();
				}else {
					//***************DTECTA USUARIOS ONLINE***************
					InetAddress localizacion = misocket.getInetAddress();
					String IpRemota = localizacion.getHostAddress();
					System.out.println("-> Online " + IpRemota);
					listaIp.add(IpRemota);//Agrega la ip del cliente al arreglo de ips
					paquete_recibido.setIps(listaIp);
					
					for (String z:listaIp) {
						Socket enviaDestinatario = new Socket(z,9090);
						ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
						paqueteReenvio.writeObject(paquete_recibido);
						paqueteReenvio.close();
						enviaDestinatario.close();
					    misocket.close();
					}
					//****************************************************
				}
			}
			
		} catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		

	}
	private	JTextArea areatexto;
}
