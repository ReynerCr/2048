package codigo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/** Clase que maneja el temporizador del juego y que actualiza la etiqueta que lo muestra al usuario. */
@SuppressWarnings("serial")
public class Tiempo extends JLabel {
	private int minutos;
	private int segundos;
	private Timer timer;
	
	/** Constructor por defecto y unico de Tiempo, donde se inicializa el temporizador y se reinicia. */
	public Tiempo() {
		reiniciar();
		timer = new Timer(1000, new TimerTiempo());
		
		this.setSize(80, 80);
		this.setText("00:00");
		this.setFont(new Font("Arial", Font.BOLD, 25));
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setIcon(Loader.getInstance().getOtros(1));
		
		timer.start();
	}//Tiempo()
	
	/** Metodo que reinicia el temporizador. */
	public void reiniciar() {
		minutos = 0;
		segundos = 0;
		if (timer != null)
			timer.restart();
	}//reiniciar()
	
	/** Metodo para parar el temporizador. */
	public void pararTiempo() {
		timer.stop();
	}//pararTiempo()
	
	/** Metodo para reanudar el temporizador. */
	public void iniciarTiempo() {
		timer.restart();
	}//iniciarTiempo()
	
	/** Metodo que devuelve los minutos. */
	public int getMinutos() {
		return minutos;
	}//getMinutos()
	
	/** Metodo que devuelve los segundos. */
	public int getSegundos() {
		return segundos;
	}//getSegundos()
	
	/** Metodo que devuelve un String que es en realidad el tiempo en formato MM:SS. */
	public String getTiempo() {
		String min = Integer.toString(minutos);
		String seg = Integer.toString(segundos);
		
		if (minutos < 10)
			min = String.format("%02d",minutos);
		if (segundos < 10)
			seg = String.format("%02d", segundos);
		
		return (min + ":" + seg);
	}//getTiempo()
	
	/** Metodo que devuelve un booleano que indica si el temporizador esta activo o no. */
	public boolean getActivo() {
		return timer.isRunning();
	}//getActivo()
	
	/** Creacion de la clase TimerTiempo que es realmente un ActionListener sobreescrito. */
	class TimerTiempo implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			segundos++;
			
			if (segundos == 60) {
				segundos = 0;
				minutos++;
			}
			
			setText(getTiempo());
			
			revalidate();
			repaint();
		}//actionPerformed()
	};//class TimerTiempo
}//class Tiempo
