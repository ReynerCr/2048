package codigo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

/** Clase que contiene todo para la creacion de las esferas en Tablero. */
@SuppressWarnings("serial")
public class Esfera extends JLabel {
	private int valor;
	private boolean activo;
	private Timer caida;
	private int posicion;
	private JLabel difuminado;
	
	/** Constructor por defecto y unico de Esfera. Recibe un int que es el valor que tendra
	 *  la esfera. */
	public Esfera(int valor) {
		this.valor = valor;
		setValor(valor);
		this.setSize(60, 60);
		activo = false;
		caida = new Timer(10, new TimerCaida());
	}//Esfera(int)

	/** Metodo para establecer el valor de la esfera. Es truncado al ultimo numero que es potencia
	 *  de dos menor al valor que recibe. Tambien establece cual sera la imagen que mostrara la 
	 *  esfera dentro del tablero.*/
	public void setValor(int valor) {
		//redondeo del valor que recibe la funcion
		int i = 1;
		while (valor >= Math.pow(2, i) && i <= 16) {
			i++;
		}
		if (i > 1)
			i--;
		
		valor = (int) (Math.pow(2, i));
		this.valor = valor;
		this.setIcon(Loader.getInstance().getEsfera(i));
	}//setValor(int)
	
	/** Devuelve el valor de la esfera. */
	public int getValor() {
		return valor;
	}//getValor()

	/** Metodo que activa o desactiva la esfera, reproduciendo el sonido correspondiente (si sonido
	 *  es true) y posicionando la imagen de seleccion debajo de la esfera si se activa. */
	public void setActivo(boolean activo, boolean sonido) {
		this.activo = activo;
		if (activo) {
			difuminado = new JLabel(Loader.getInstance().getEsfera(0));
			difuminado.setSize(70, 70);
			this.getParent().add(difuminado);
			difuminado.setLocation(this.getX() - 5, this.getY() - 5);
			if (sonido)
				Loader.getInstance().startBell(1);
		}//if activo
		else {
			this.getParent().remove(difuminado);
			this.getParent().revalidate();
			this.getParent().repaint();
			difuminado = null;
			if (sonido)
				Loader.getInstance().startBell(0);
		}//else
	}//setActivo(boolean, boolean)
	
	/** Devuelve el valor de activo. */
	public boolean getActivo() {
		return activo;
	}//getActivo()
	
	/** Metodo que inicia el timer para hacer el efecto de caida de la esfera. Recibe un int 
	 * que indica hasta que posicion tiene que caer dicha esfera. */
	public void iniciarCaida(int posicion) {
		this.posicion = posicion;
		caida.start();
	}//iniciarCaida(int)
	
	/** Clase que implementa ActionListener para el movimiento de la esfera (caida)
	 *  y detecta cuando debe parar.. */
	class TimerCaida implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (getY() < posicion) 
				setLocation(getX(), getY() + 10);
			else 
				caida.stop();
		}//actionPerformed()
	};//class TimerCaida
}//class Esfera
