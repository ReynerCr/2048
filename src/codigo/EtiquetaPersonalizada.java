package codigo;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/** Clase que es utilizada para la creacion de etiquetas. Extiende de JLabel.*/
@SuppressWarnings("serial")
public class EtiquetaPersonalizada extends JLabel {
	
	/** Constructor parametrico de EtiquetaPersonalizada: recibe un String que es lo que 
	 * pone como texto en la etiqueta. */
	public EtiquetaPersonalizada(String texto) {
		ImageIcon imagen = Loader.getInstance().getEtiqueta();
		this.setIcon(imagen);
		
		this.setText(texto);
		this.setFont(new Font("Arial", Font.BOLD, 25));
		this.setHorizontalTextPosition(SwingConstants.CENTER);
	}//EtiquetaPersonalizada(String)
	
	/** Constructor parametrico de EtiquetaPersonalizada: recibe un String que es lo que 
	 * pone como texto en la etiqueta, y tres enteros que indican ancho y alto de la etiqueta,
	 *  y el ultimo indica el tamanyo de las letras. */
	public EtiquetaPersonalizada(String texto, int ancho, int alto, int tamanyo) {
		this(texto);
		
		this.setFont(new Font("Arial", Font.BOLD, tamanyo));
		this.setIcon(new ImageIcon(Loader.getInstance().getEtiqueta().getImage().getScaledInstance(ancho, alto, Image.SCALE_FAST)));
		this.setSize(ancho, alto);
	}//EtiquetaPersonalizada(String, int, int, int)
}//class EtiquetaPersonalizada
