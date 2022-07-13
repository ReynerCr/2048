package codigo;

import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;

/** Clase que sirve de ventana para los demas paneles.*/
@SuppressWarnings("serial")
public class Juego extends JFrame {
	private static Juego instance = null;

	/** Constructor por defecto y unico de Juego. Es llamado desde getInstance.*/
	private Juego() {
		super("2048");
		this.setSize(600, 800);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(this.getClass().getResource("/recursos/icon.png")).getImage());
		
		iniciarComponentes();
	}//Juego()
	
	/** Metodo para inicializar los componentes (basicamente crea la instancia de Menu y pone el panel
	 *  en la ventana). */
	private void iniciarComponentes() {
		Menu.getInstance();
		this.setContentPane(Menu.getInstance());
	}//iniciarComponentes()
	
	/** Metodo que devuelve la instancia si ya esta instanciada, y si no, la crea . */
	public static Juego getInstance() {
		if (instance==null)
			instance = new Juego();
		
		return instance;
	}//getInstance()

	/** Metodo que actualiza el panel actual con el PanelPadre que recibe como parametro. */
	public void actualizarFrame(PanelPadre panel) {
		Juego.getInstance().setContentPane(panel);
		Juego.getInstance().revalidate();
		Juego.getInstance().repaint();
	}//actualizarFrame(PanelPadre)
	
	/** Metodo sobrecargado que actualiza el panel actual con el JPanel que recibe como parametro.*/
	public void actualizarFrame(JPanel panel) {
		Juego.getInstance().setContentPane(panel);
		Juego.getInstance().revalidate();
		Juego.getInstance().repaint();
	}//actualizarFrame(JPanel)
}//class Juego
