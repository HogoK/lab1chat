package MiSockets;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.rmi.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		
		addWindowListener(new EnvioOnline());// Instancia el envio online de conexion de cliente al chat
		
		
		}	
	
}


class EnvioOnline extends WindowAdapter{ // Clase que se encarga de enviar el paquete de informacion de conexion nueva al chat
	public void windowOpened(WindowEvent e) {
		try {
			Socket misocket = new Socket("192.168.0.3",9999);// Poner Aqui IP del PC-SERVIDOR
			PaqueteEnvio datos = new PaqueteEnvio();
			datos.setMensaje(" Online");
			
			
			ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());
			paquete_datos.writeObject(datos);
			misocket.close();
		} catch(Exception e2) {
			System.out.println(e2.getMessage());
		}
	}
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LaminaMarcoCliente(){
		
		String nick_usuario=JOptionPane.showInputDialog("Nick: ");
		
		
		JLabel n_nick = new JLabel("Nick: ");
		
		add(n_nick);
		
		nick = new JLabel();
		
		nick.setText(nick_usuario);

		add(nick);
	
		JLabel texto=new JLabel("Online: ");
		
		add(texto);
		 
		ip = new JComboBox<String>();
		
		//ip.addItem("192.168.0.3");
		//ip.addItem("192.168.0.5");
		//ip.addItem("192.168.0.8");

		add(ip);

		campochat = new JTextArea(15,20);

		add(campochat);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");

		EnviaTexto mievento = new EnviaTexto();

		miboton.addActionListener(mievento);
		
		add(miboton);

		Thread mihilo = new Thread(this);

		mihilo.start();
		
	}
	
	private class EnviaTexto implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			campochat.append("\n" + "Yo: " + campo1.getText());

			try{
				Socket misocket = new Socket("192.168.0.3",9999);//Poner aqui la IP del PC-SERVIDOR
				PaqueteEnvio datos = new PaqueteEnvio();
				datos.setNick(nick.getText());
				datos.setIp(ip.getSelectedItem().toString());
				datos.setMensaje(campo1.getText());
				ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());
				paquete_datos.writeObject(datos);
				misocket.close();

				// DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());
				// flujo_salida.writeUTF(campo1.getText());
				// flujo_salida.close();
			} catch (UnknownHostException e1){
				e1.printStackTrace();
			} catch(IOException e1){
				System.out.println(e1.getMessage());
			}

			
		}

	}
	
		
		
		
	private JTextField campo1;
	
	private JComboBox<String> ip;
	
	private JLabel nick;

	private JTextArea campochat;
	
	private JButton miboton;

	@Override
	public void run() {
		try{
			ServerSocket servidor_cliente = new ServerSocket(9090);
			Socket cliente;
			PaqueteEnvio paqueteRecibido;
			while(true){
				cliente = servidor_cliente.accept();
				ObjectInputStream flujoentrada = new ObjectInputStream(cliente.getInputStream());
				paqueteRecibido = (PaqueteEnvio) flujoentrada.readObject();
				//******************* DEFINE LA HORA PARA LOS MENSAJES**************************
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();
				//System.out.println("Hora actual: " + dateFormat.format(date));
				//******************************************************************************
				
				if(!paqueteRecibido.getMensaje().equals(" Online")) {
					campochat.append("\n[" + dateFormat.format(date) + "] " + paqueteRecibido.getNick() + ": " + paqueteRecibido.getMensaje());
				}else {
					// campochat.append("\n" + paqueteRecibido.getNick() + "(ip: " + paqueteRecibido.getIps() + ") se a unido al chat.");
					campochat.append("\n[" + dateFormat.format(date) + "] " + paqueteRecibido.getNick() + ", ip: " + paqueteRecibido.getIps() + " se a unido al chat.");
					ArrayList <String> IpsMenu = new ArrayList<String>();
					IpsMenu = paqueteRecibido.getIps();
					ip.removeAllItems();
					for(String z:IpsMenu){
						ip.addItem(z);
					}
				}
				
				
				
			}

		} catch (Exception e){
			System.out.println(e.getMessage());
		}

	}
	
}

class PaqueteEnvio implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nick, ip, mensaje;
	
	private ArrayList<String> Ips;

	public ArrayList<String> getIps() {
		return Ips;
	}

	public void setIps(ArrayList<String> ips) {
		Ips = ips;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}