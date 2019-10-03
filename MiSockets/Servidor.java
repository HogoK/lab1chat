package misockets;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
// import java.net.ServerSocket;
import java.net.*;
import java.time.LocalDateTime;
import java.time.Month;
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
	

	int s_socket = Integer.parseInt(JOptionPane.showInputDialog("Ingrese puerto del servidor: "));

	public void run(){
		try{
			areatexto.append(".:Servidor con Conexion:."+"\n" + "IP del Servidor: " + InetAddress.getLocalHost().getHostAddress());
			areatexto.append("\n" + "Puerto del Servidor: " + s_socket);
			ServerSocket servidor = new ServerSocket(s_socket);
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
				
				//******************* DEFINE LA FECHA/HORA PARA LOS MENSAJES**************************
				LocalDateTime locaDate = LocalDateTime.now();
				int dia  = locaDate.getDayOfMonth();
				Month mes  = locaDate.getMonth();
				int anno  = locaDate.getYear();
				int hora  = locaDate.getHour();
				int minuto = locaDate.getMinute();
				int segundo = locaDate.getSecond();
				String fechaHora = "[" + dia  + "/"+ mes +"/"+ anno +"]["+ hora  + ":"+ minuto +":"+segundo+"] "; 
				//******************************************************************************
				
    
				if(!mensaje.equals(" Online")) {
				areatexto.append("\n"+ fechaHora + nick + ": " + mensaje + " para "+ ip);
				Socket enviaDestinatario = new Socket(ip,9090);
				ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
				paqueteReenvio.writeObject(paquete_recibido);
				paqueteReenvio.close();
				enviaDestinatario.close();
			    misocket.close();
				}else {
					//***************DETECTA USUARIOS ONLINE***************
					
					InetAddress localizacion = misocket.getInetAddress();
					String IpRemota = localizacion.getHostAddress();
					areatexto.append("\n"+ fechaHora + nick + ", ip: "+ IpRemota +" se a unido al chat.");


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


