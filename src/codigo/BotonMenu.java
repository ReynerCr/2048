package codigo;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/** Clase que extiende de JButton y sirve para la creacion de los botones del menu. */
@SuppressWarnings("serial")
public class BotonMenu extends JButton {
	
	/** Constructor por defecto de BotonMenu y que siempre es llamado. */
	private BotonMenu() {
		this.setBorderPainted(false);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setFont(new Font("Arial", Font.BOLD, 25));
		this.setContentAreaFilled(false);

		ImageIcon icono = Loader.getInstance().getBotones(0); //por defecto
		this.setIcon(icono);
		
		 icono = Loader.getInstance().getBotones(1); //mouse por encima
		 this.setRolloverIcon(icono);
	}//BotonMenu()
	
	/** Constructor parametrico: recibe un String que es el texto del boton. */
	public BotonMenu(String texto) {
		this();
		this.setText(texto);
	}//BotonMenu(String)
	
	/** Constructor parametrico: recibe un String (texto de boton), y tres enteros que son ancho, alto y el
	 *  tamanyo de la letra. */
	public BotonMenu(String texto, int ancho, int alto, int tamanyoLetra) {
		this(texto);
		
		ImageIcon icono = new ImageIcon(Loader.getInstance().getBotones(0).getImage().getScaledInstance(ancho, alto, Image.SCALE_FAST)); //por defecto
		this.setIcon(icono);
		
		icono = new ImageIcon(Loader.getInstance().getBotones(1).getImage().getScaledInstance(ancho, alto, Image.SCALE_FAST)); //mouse por encima
		this.setRolloverIcon(icono);
		this.setFont(new Font("Arial", Font.BOLD, tamanyoLetra));
		
		this.setSize(ancho, alto);
	}//BotonMenu(String, int, int, int)
}//class BotonMenu
