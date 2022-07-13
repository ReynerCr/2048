package codigo;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/** Clase donde se carga la mayoria de archivos que necesita el juego para funcionar (a excepcion de los gif de Instrucciones).
 *  Tiene metodos para devolver dichos archivos. */
public class Loader {
	private static Loader instance = null;
	
	public static final int MAX_ESFERAS = 17;

	private Image background; //fondo
	private ImageIcon etiquetas;
	private ImageIcon botones[]; 
	private ImageIcon esferas[];
	private ImageIcon otros[]; //aqui se guarda icono para boton de menu, pausar, iniciar, etc
	private Clip music; //musica
	private Clip bells[]; //sonidos dentro del juego al hacer movimientos
	
	/** Constructor por defecto y unico de Loader, donde se carga todo. Es llamado desde getInstance si todavia 
	 * no se ha instanciado instance*/
	private Loader() {
		botones = new ImageIcon[2];
		botones[0] = new ImageIcon(this.getClass().getResource("/recursos/imagenes/imagen0.png"));
		botones[1] = new ImageIcon(this.getClass().getResource("/recursos/imagenes/imagen1.png"));
		
		etiquetas = new ImageIcon(this.getClass().getResource("/recursos/imagenes/imagen1.png"));

		esferas = new ImageIcon[MAX_ESFERAS];

		for (int i = 0; i < MAX_ESFERAS; i++) 
			esferas[i] = new ImageIcon(this.getClass().getResource("/recursos/imagenes/esferas/" + i + ".png"));
		
		otros = new ImageIcon[10];
		for (int i = 0; i < 10; i++)
			otros[i] = new ImageIcon(this.getClass().getResource("/recursos/imagenes/otros/" + i + ".png"));
		
		setFondo(0);
		
		bells = new Clip[3];
	    try {
	    	InputStream audioSrc = this.getClass().getResourceAsStream("/recursos/sonido/music0.wav");
	    	audioSrc = new BufferedInputStream(audioSrc);
	    	AudioInputStream soundIn = AudioSystem.getAudioInputStream(audioSrc);
	        AudioFormat format = soundIn.getFormat();
	        DataLine.Info info = new DataLine.Info(Clip.class, format);
			music = (Clip)AudioSystem.getLine(info);
			music.open(soundIn);
			music.loop(Clip.LOOP_CONTINUOUSLY);
			for (int i = 0; i < 3; i++) {
				audioSrc = this.getClass().getResourceAsStream("/recursos/sonido/bell" + i + ".wav");
		    	audioSrc = new BufferedInputStream(audioSrc);
				soundIn = AudioSystem.getAudioInputStream(audioSrc);
				bells[i] = (Clip)AudioSystem.getLine(info);
				bells[i].open(soundIn);
			}//for
		} catch (Exception e1) {
			if (music != null && music.isOpen())
				music.close();
			for (int i = 0; i < 3; i++) {
				if (bells[i] != null && bells[i].isOpen())
					bells[i].close();
			}//for
			JOptionPane.showMessageDialog(null, "Ocurrio un problema al cargar alguno de los archivos de sonido. " + e1.getMessage());
			System.exit(1);
		}//catch
	}//Loader()
	
	/** Metodo que devuelve instance si ya se instancio, o si no, lo instancia. */
	public static Loader getInstance() {
		if (instance == null)
			instance = new Loader();
		
		return instance;
	}//static Loader getInstance()
	
	/** Metodo para devolver la musica de fondo. */
	public Clip getMusic() {
		return music;
	}//getMusic()
	
	/** Metodo que devuelve el sonido en la posicion i, pero antes lo para y posiciona en el inicio. */
	public void startBell(int i) {
		bells[i].stop();
		bells[i].setMicrosecondPosition(0);
		
		bells[i].start();
	}//startBell(int)
	
	/** Metodo que establece el fondo del juego. */
	public void setFondo(int opcion) {
		URL url = this.getClass().getResource("/recursos/imagenes/background" + opcion + ".png");
		background = new ImageIcon(url).getImage();
	}//setFondo(int)
	
	/** Devuelve el fondo. */
	public Image getFondo() {
		return background;
	}//getFondo()
	
	/** Devuelve la imagen de etiquetas . */
	public ImageIcon getEtiqueta() {
		return etiquetas;
	}//getEtiquetas()
	
	/** Devuelve el boton i. */
	public ImageIcon getBotones(int i) {
		return botones[i];
	}//getBotones(int)
	
	/** Metodo que devuelve la imagen de una esfera i. */
	public ImageIcon getEsfera(int i) {
		if (i < MAX_ESFERAS)
			return esferas[i];
		else
			return null;
	}//getEsfera(int)
	
	/** Metodo que devuelve una imagen de otros i. */
	public ImageIcon getOtros(int i) {
		return otros[i];
	}//getOtros(int)
}//Class Loader
