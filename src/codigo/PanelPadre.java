package codigo;

import java.awt.Graphics;
import javax.swing.JPanel;

/** Clase de utilidad que simplemente indica el tamanyo del panel y pinta la imagen de fondo sobre el. Funciona como panel para Juego.*/
@SuppressWarnings("serial")
public class PanelPadre extends JPanel {
	/** Constructor por defecto y unico de PanelPadre() */
	public PanelPadre() {
		this.setSize(600, 800);
		this.setLayout(null);
	}//PanelPadre()
	
	/** Sobreescritura del metodo paint para que el fondo del panel sea la imagen seleccionada en Loader. */
	@Override
	public void paint(Graphics g){
        g.drawImage(Loader.getInstance().getFondo(), 0, 0, getWidth(), getHeight(), this);
        super.setOpaque(false);
        super.paint(g);
    }//paint(Graphics)
}//class PanelPadre
