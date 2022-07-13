package codigo;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JTextArea;

/** Clase que extiende de JTextArea y sirve para los mensajes de Instrucciones. */
@SuppressWarnings("serial")
public class AreaTextoPersonalizada extends JTextArea {
	/** Constructor por defecto de AreaTextoPersonalizada, recibe un String que es el texto
	 *  del area de texto. */
	public AreaTextoPersonalizada(String texto) {
		this.setFont(new Font("Arial", Font.BOLD, 25));
		this.setText(texto);
	}//AreaTextoPersonalizada(String)
	
	/** Constructor por defecto de AreaTextoPersonalizada: recibe un String que es el texto
	 *  del area de texto y tres enteros que son el ancho y alto del area de texto, y el 
	 *  tamanyo de la letra . */
	public AreaTextoPersonalizada(String texto, int ancho, int alto, int tamanyo) {
		super(texto);
		this.setEditable(false);
		this.setSize(ancho, alto);
		this.setFont(new Font("Arial", Font.BOLD, tamanyo));
	}//AreaTextoPersonalizada(String, int, int, int)
	
	/** Sobreescritura del metodo paint para que se pinte de fondo la imagen de las etiquetas. */
	@Override
	public void paint(Graphics g){
        g.drawImage(Loader.getInstance().getEtiqueta().getImage(), 0, 0, getWidth(), getHeight(), this);
        super.setOpaque(false);
        super.paint(g);
    }//paint(Graphics g)
}//class AreaTextoPersonalizada
