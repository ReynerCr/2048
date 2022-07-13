package codigo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Clase donde se crean las paginas de las instrucciones que se mostraran al usuario. */
public class Instrucciones {
	private static Instrucciones instance = null;
	
	private JPanel paginas[];
	private int pag;
	
	EtiquetaPersonalizada instrucc;
	BotonMenu bVolver;
	BotonMenu siguiente;
	
	/** Constructor por defecto y unico de Instrucciones. Es llamado desde getInstance si no se ha
	 *  creado la instancia.*/
	private Instrucciones() {
		paginas = new JPanel[3];
		pag = 0;
		
		inicializarComunes();
		crearPaginas();
	}//Instrucciones(pagina)
	
	/** Devuelve la instancia si se ha creado, y si no, lo hace. */
	public static Instrucciones getInstance() {
		if (instance == null)
			instance = new Instrucciones();
			
		return instance;
	}//getInstance()
	
	/** Metodo para inicializar las etiquetas o botones que se repiten en las tres paginas. */
	private void inicializarComunes() {
		//instrucciones
		instrucc = new EtiquetaPersonalizada("Instrucciones", 200, 50, 18);
		instrucc.setLocation((Menu.getInstance().getWidth()/3), 40);
		instrucc.setText(instrucc.getText() + " " + pag + "/4");
		instrucc.revalidate();
		instrucc.repaint();
		
		//boton de volver
		ActionListener volver =  new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paginas[pag].remove(instrucc);
				paginas[pag].remove(bVolver);
				paginas[pag].remove(siguiente);
				
				if (pag > 0) {
					Juego.getInstance().actualizarFrame(getPagina(--pag));
				}
				else {
					Juego.getInstance().actualizarFrame(Menu.getInstance());
				}
			}//actionPerformed
		};//ActionListener volver
		
		bVolver = new BotonMenu("Volver");
		bVolver.setSize(150, 80);
		bVolver.setLocation(30, Menu.getInstance().getHeight() - 121);
		bVolver.addActionListener(volver);
		
		//boton de siguiente pagina
		ActionListener siguientePagina =  new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paginas[pag].remove(instrucc);
				paginas[pag].remove(bVolver);
				paginas[pag].remove(siguiente);
				
				Juego.getInstance().actualizarFrame(getPagina(++pag));
			}//actionPerformed
		}; //ActionListener siguientePagina

		siguiente = new BotonMenu("Siguiente");
		siguiente.addActionListener(siguientePagina);
		siguiente.setSize(150, 80);
		siguiente.setLocation(415, Menu.getInstance().getHeight() - 121);
	}//InicializarComunes()
	
	/** Metodo parar instanciar las paginas y llamar a los metodos que anyaden el contenido a dichas paginas. */
	private void crearPaginas() {
		for (int i = 0; i < 3; i++) {
			paginas[i] = new JPanel(null);
			paginas[i].setSize(600, 800);
			paginas[i].setBackground(Color.black);
		}//for i
		
		crearPagina0();
		crearPagina1();
		crearPagina2();
	}//crearPaginas()
	
	/** Metodo para anyadir contenido a la pagina0. */
	private void crearPagina0() {
		AreaTextoPersonalizada texto;
		JLabel gif;
		
		//introduccion
		texto = new AreaTextoPersonalizada("  2048 es un juego de logica en el cual deberas"
				+ " crear cadenas con esferas (cuyos valores \nson potencias de dos) adyacentes que tengan el mismo valor"
				+ " que la esfera anterior o que \ncuyo valor sea igual a la suma de las esferas ya seleccionadas.\n"
				+ "\n"
				+ "No hay un limite de esferas que puedas sumar en un mismo camino, y puedes crear\n"
				+ "caminos en todas las direcciones!", 570, 95, 13);
		texto.setLocation(15, 140);
		paginas[0].add(texto);
		
		
		//gif 0
		gif = new JLabel(new ImageIcon(this.getClass().getResource("/recursos/imagenes/gifs/0.gif")));
		gif.setSize(gif.getIcon().getIconWidth(), gif.getIcon().getIconHeight());
		gif.setLocation(paginas[0].getWidth()/3, 290);
		paginas[0].add(gif);
		
		texto = new AreaTextoPersonalizada("  Se suman los valores de las esferas y generan una "
				+ "nueva esfera con dicho valor resultante.", 530, 18, 12);
		texto.setLocation(30, 390);
		paginas[0].add(texto);
		
		
		//gif 1
		gif = new JLabel(new ImageIcon(this.getClass().getResource("/recursos/imagenes/gifs/1.gif")));
		gif.setSize(gif.getIcon().getIconWidth(), gif.getIcon().getIconHeight());
		gif.setLocation(paginas[0].getWidth()/3, 440);
		paginas[0].add(gif);
		
		texto = new AreaTextoPersonalizada("  Se puede seleccionar esferas hasta que se suelte el "
				+ "clic.", 335, 18, 12);
		texto.setLocation(130, 540);
		paginas[0].add(texto);
	}//crearPagina0()
	
	/** Metodo para anyadir contenido a la pagina1. */
	private void crearPagina1() {
		AreaTextoPersonalizada texto;
		JLabel gif;
		
		//gif 2
		gif = new JLabel(new ImageIcon(this.getClass().getResource("/recursos/imagenes/gifs/2.gif")));
		gif.setSize(gif.getIcon().getIconWidth(), gif.getIcon().getIconHeight());
		gif.setLocation(paginas[1].getWidth()/3, 110);
		paginas[1].add(gif);

		texto = new AreaTextoPersonalizada("  Los trazos se pueden deshacer pasando el cursor por "
				+ "encima de la penultima esfera mar-\n"
				+ "cada.", 530, 30, 12);
		texto.setLocation(30, 210);
		paginas[1].add(texto);
		
		//gif 3
		gif = new JLabel(new ImageIcon(this.getClass().getResource("/recursos/imagenes/gifs/3.gif")));
		gif.setSize(gif.getIcon().getIconWidth(), gif.getIcon().getIconHeight());
		gif.setLocation(paginas[1].getWidth()/3, 260);
		paginas[1].add(gif);

		texto = new AreaTextoPersonalizada("  Si la suma de los valores de las esferas no da como"
				+ " resultado un numero que sea potencia \n"
				+ "de dos, este se truncara hasta el siguiente numero que si lo sea.", 530, 30, 12);
		texto.setLocation(30, 360);
		paginas[1].add(texto);
		
		
		//gif 4
		gif = new JLabel(new ImageIcon(this.getClass().getResource("/recursos/imagenes/gifs/4.gif")));
		gif.setSize(gif.getIcon().getIconWidth(), gif.getIcon().getIconHeight());
		gif.setLocation(160, 410);
		paginas[1].add(gif);

		texto = new AreaTextoPersonalizada("  Se puede seleccionar esferas adyacentes en cualquier direccion.", 382, 18, 12);
		texto.setLocation(110, 586);
		paginas[1].add(texto);
	}//crearPagina1()
	
	/** Metodo para anyadir contenido a la pagina2. */
	private void crearPagina2() {
		AreaTextoPersonalizada texto;
		JLabel gif;

		//gif 5
		gif = new JLabel(new ImageIcon(this.getClass().getResource("/recursos/imagenes/gifs/5.gif")));
		gif.setSize(400, 526);
		gif.setLocation(100, 95);
		paginas[2].add(gif);

		texto = new AreaTextoPersonalizada("  Diviertete!", 90, 20, 15);
		texto.setLocation(250, 620);
		paginas[2].add(texto);
	}//crearPagina2()
	
	/** Metodo para obtener la pagina i. */
	public JPanel getPagina(int i) {
		instrucc.setText("Instrucciones " + (pag + 1) + "/3");
		paginas[i].add(instrucc);
		paginas[i].add(bVolver);
		
		if (pag < 2) 
			paginas[i].add(siguiente);
		
		return paginas[i];
	}//getPagina(int)
}//class Instrucciones