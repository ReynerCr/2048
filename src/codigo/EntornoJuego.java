package codigo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** Clase que hace de panel para lo que tiene que ver con el juego en si. En ella se anyade el temporizador 
 * puntaje, y los botones de pausa, volver al menu y reiniciar. Aparte de eso, se anyade el subpanel tablero
 * que es donde se hacen los movimientos con las esferas. */
@SuppressWarnings("serial")
public class EntornoJuego extends PanelPadre {
	private static EntornoJuego instance = null;
	
	private long puntaje = 0;
	private String nombre;
	private EtiquetaPersonalizada ePuntaje;
	private JButton pausar;
	private JButton reiniciar;
	private Tiempo time;
	private JButton volver;
	Tablero tablero;
	
	/** Constructor por defecto y unico de EntornoJuego(). Es llamado desde getInstance()*/
	private EntornoJuego() {
		this.setLayout(new BorderLayout());
		iniciarComponentes();
	}//EntornoJuego()
	
	/** Metodo para inicializar los componentes del panel. */
	private void iniciarComponentes() {
		time = new Tiempo();
		inciarParteSuperior();
		iniciarParteInferior();
		reiniciar();
	}//iniciarComponentes()
	
	/** Metodo que es llamado desde Menu, y que guarda el nombre que el usuario ingreso. */
	void setNombre(String nombre) {
		this.nombre = nombre;
	}//setNombre()
	
	/** Metodo que devuelve instance si ya se instancio, y si no, lo hace. */
	public static EntornoJuego getInstance() {
		if (instance == null)
			instance = new EntornoJuego();
		
		return instance;
	}//getInstance()

	/** Devuelve el nombre. Util para saber cuando se ha instanciado para reiniciarlo. */
	public String getNombre() {
		return nombre;
	}//getNombre()
	
	/** Inicia los componentes de la parte superior del panel. */
	private void inciarParteSuperior() {
		JPanel parteSuperior = new JPanel(new FlowLayout());
		parteSuperior.setOpaque(false);
		parteSuperior.add(time);
		
		ePuntaje = new EtiquetaPersonalizada(Long.toString(puntaje), 200, 80, 18);
		parteSuperior.add(ePuntaje);

		pausar = new JButton();
		pausar.setBorderPainted(false);
		pausar.setContentAreaFilled(false);
		pausar.setPreferredSize(new Dimension(80, 80));
		pausar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (time.getActivo()) {
					time.pararTiempo();
					tablero.setPausa(true);
					pausar.setIcon(Loader.getInstance().getOtros(5));
					pausar.setRolloverIcon(Loader.getInstance().getOtros(3));
				}
				else {
					time.iniciarTiempo();
					tablero.setPausa(false);
					pausar.setIcon(Loader.getInstance().getOtros(4));
					pausar.setRolloverIcon(Loader.getInstance().getOtros(2));
				}
			}//actionPerformed()
		});//pausar.addActionListener()
		parteSuperior.add(pausar);
		
		this.add(parteSuperior, BorderLayout.PAGE_START);
	}//iniciarParteSuperior()
	
	/** Metodo que instancia el objeto de tablero y lo anyade al panel. Tambien sirve para reiniciarlo. */
	private void iniciarTablero() {
		if (tablero != null) {
			remove(tablero);
			tablero = null;
		}
		
		tablero = new Tablero();
		this.add(tablero, BorderLayout.CENTER);
	}//iniciarTablero()
	
	/** Metodo para actualizar el puntaje. Es llamado desde Tablero. */
	void actualizarPuntaje(long puntaje) {
		this.puntaje += puntaje;
		ePuntaje.setText(Long.toString(this.puntaje));
	}//actualizarPuntaje()
	
	/** Metodo para finalizar el juego, muestra un mensaje con el nombre, puntaje y tiempo total; ademas, si 
	 * aplica para entrar a top10, entonces guarda nombre y puntaje en el archivo correspondiente. Al final 
	 * devuelve al usuario al menu. Es llamado desde Tablero.*/
	void finDeJuego() {
		time.pararTiempo();
		tablero.setPausa(true);
		JOptionPane.showMessageDialog(null, nombre + " tu puntaje es de: " + puntaje + " y el tiempo total: " + time.getTiempo());
		String dir = "top10.dat";
		String datos[][] = new String[10][2];
		int i = 0, j = 0;
		
		Scanner entrada = null;
		try {
			entrada = new Scanner(new File(dir));
			
			String cad[] = new String[2];
			while (entrada.hasNextLine() && i < 10) {
				if (cad[0] == null) 
					cad = entrada.nextLine().split("@");
				
				if (puntaje > Long.parseLong(cad[1]) || (Long.parseLong(cad[1]) == 0 && puntaje > 0)) {
					datos[i][0] = nombre;
					datos[i][1] = Long.toString(puntaje);
					puntaje = 0;
				}
				else {
					datos[i][0] = cad[0];
					datos[i][1] = cad[1];
					cad[0] = null;
				}
				i++;
			}//while
			cad = null;
			entrada.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error con el archivo de guardado, se creara uno nuevo solo con su puntaje actual.");
			datos[j][0] = nombre;
			datos[j][1] = Long.toString(puntaje);
			i++;
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error con el archivo de guardado, se creara uno nuevo solo con su puntaje actual.");
			datos[j][0] = nombre;
			datos[j][1] = Long.toString(puntaje);
			i++;
		} finally {
			if (entrada != null)
				entrada.close();
		}//finally para cerrar archivo
		
		PrintWriter pw = null;
		try {
			FileWriter fw = new FileWriter(new File(dir));
			BufferedWriter bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			
			for (j = 0; j < i; j++) {
				pw.println(datos[j][0] + "@" + datos[j][1]);
			}//for
			pw.println("-unknown-@0");
			pw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error al guardar el puntaje. " + e.getMessage());
		} finally {
			if (pw != null) {
				pw.close();
			}
		}//finally para cerrar archivos
		
		Juego.getInstance().actualizarFrame(Menu.getInstance());
		puntaje = 0;
	}//finDeJuego()
	
	/** Metodo para anyadir los componentes de la parte inferior del panel. */
	private void iniciarParteInferior() {
		JPanel parteInferior = new JPanel(new FlowLayout());
		parteInferior.setOpaque(false);
		
		volver = new JButton(Loader.getInstance().getOtros(6));
		volver.setSize(volver.getIcon().getIconWidth(), volver.getIcon().getIconHeight());
		volver.setRolloverIcon(Loader.getInstance().getOtros(8));
		volver.setContentAreaFilled(false);
		volver.setBorderPainted(false);
		volver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int pregunta = 0;
				if (puntaje != 0) {
					time.pararTiempo();
					pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de que desea volver al menu? "
							+ "Si lo hace perdera la sesion actual, pero su puntaje se guardara en "
							+ "caso de ser top10.", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					
					if (!(tablero.getPausa()))
						time.iniciarTiempo();
				}//if
				if (pregunta == 0) {
					if (puntaje != 0)
						finDeJuego();
					else {
						time.reiniciar();
						time.pararTiempo();
						
						time.setText(time.getTiempo());
						
						puntaje = 0;
						ePuntaje.setText(Long.toString(puntaje));
						
						Juego.getInstance().actualizarFrame(Menu.getInstance());
					}//else
				}//if se acepto la advertencia
			}//actionPerformed()
		});//volver.addActionListener()
		parteInferior.add(volver);
		
		reiniciar = new JButton(Loader.getInstance().getOtros(7));
		reiniciar.setRolloverIcon(Loader.getInstance().getOtros(9));
		reiniciar.setSize(reiniciar.getIcon().getIconWidth(), reiniciar.getIcon().getIconHeight());
		reiniciar.setContentAreaFilled(false);
		reiniciar.setBorderPainted(false);
		reiniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int pregunta = 0;
				if (puntaje != 0) {
					time.pararTiempo();
					
					pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de que desea reiniciar el tablero?",
							"Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (!(tablero.getPausa()))
						time.iniciarTiempo();
				}//if
				if (pregunta == 0) {
					reiniciar();
					revalidate();
					repaint();
				}//else
			}//actionPerformed()
		});//reiniciar.addActionListener()
		parteInferior.add(reiniciar);
		this.add(parteInferior, BorderLayout.PAGE_END);
	}//iniciarParteInferior()
	
	/** metodo que reinicia puntaje, tiempo y el tablero. */
	void reiniciar() {
		puntaje = 0;
		ePuntaje.setText(Long.toString(puntaje));
		
		pausar.setIcon(Loader.getInstance().getOtros(4));
		pausar.setRolloverIcon(Loader.getInstance().getOtros(2));
		
		iniciarTablero();
		
		time.reiniciar();
		time.setText(time.getTiempo());
	}//reiniciar()
}//class EntornoJuego()