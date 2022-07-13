package codigo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/** Clase donde se crean las esferas, donde se ejecutan los movimientos y se suman los valores; es decir, 
 * es la clase donde se maneja todo lo que tiene que ver con esferas y con el juego en si. Funciona como panel para EntornoJuego.*/
@SuppressWarnings("serial")
public class Tablero extends JPanel {
	private Esfera esferas[][];
	private Esfera esfLista[];
	private int esfNum = -1; //-1 es que no hay nada cargado
	private int sumatoriaValores; //me guarda los valores de las esferas seleccionadas
	private int mayorValor;
	private int MAX_FILAS = 7;
	private int MAX_COLUMNAS = 5;
	public final int distX = 80;
	public final int distY = 80;
	private boolean pausa = false;
	private Timer caida;
	
	/** Constructor por defecto y unico de Tablero. */
	public Tablero() {
		this.setLayout(null);
		this.setOpaque(false);
		mayorValor = 8;
		
		do {
			iniciarEsferas();
		} while (!hayJugadasDisponibles()); 
		
		caida = new Timer(150, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pausa = false;
			}//actionPerformed()
		});//caida
		caida.setRepeats(false);
	}//Tablero()
	
	/** Metodo para inicializar las esferas. */
	private void iniciarEsferas() {
		int x, y;
		esferas = new Esfera[MAX_FILAS][MAX_COLUMNAS];
		for (int i = 0; i < MAX_FILAS; i++) {
			y = (distY * i) + 15;
			for (int j = 0; j < MAX_COLUMNAS; j++) {
				x = (distX * j) + 105;
				int aux = (int) ((Math.random() * 8) + 1);
				esferas[i][j] = new Esfera(aux);
				esferas[i][j].setLocation(x, y);
				esferas[i][j].addMouseListener(eventoEsferas(i, j));
				this.add(esferas[i][j]);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, "Ocurrio un error al generar las esferas." + e.getMessage());
					System.exit(2);
				}//catch
			}//for j
		}//for i
	}//iniciarEsferas()
	
	/** Metodo donde se crea el evento para cada una de las esferas y que es la clave del juego, ya que desde aqui 
	 * se hacen los movimientos de las esferas, la sumatoria y la comprobacion de fin de juego. Recibe dos enteros 
	 * (i y j) que indican la posicion de la esfera a la que se le va a anyadir el evento; y devuelve un 
	 * MouseListener que es el evento en si. */
	private MouseListener eventoEsferas(int i, int j) {
		MouseListener ml = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) { }//mouseClicked() no es necesario
			@Override
			public void mouseExited(MouseEvent e) { }//mouseExited no lo necesito
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!pausa) {
					if (esfNum > 0 && esfLista[esfNum-1]==esferas[i][j]) {
						sumatoriaValores -= esfLista[esfNum].getValor();
						esfLista[esfNum].setActivo(false, true);
						esfLista[esfNum] = null;
						esfNum--;
						
						if (esfNum == -1) {
							esfLista = null;
						}
					}//if mi esfera actual es la misma que la anterior de la lista
					else if (!(esferas[i][j].getActivo())) {
						if  (esferas[i][j].getValor() == sumatoriaValores || (esfNum > 0 && esfLista[esfNum].getValor() == esferas[i][j].getValor())) {
							int direccion = retornarDireccion(i, j);
							if (direccion != 0) {
								sumatoriaValores += esferas[i][j].getValor();
								esfNum++;
								esferas[i][j].setActivo(true, true);
								esfLista[esfNum] = esferas[i][j];
							}//if direccion != 0
						}//if
					}//else if 
				}//if !pausa
			}//mouseEntered()
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (!pausa) {
					esfLista = new Esfera[MAX_FILAS * MAX_COLUMNAS];
					esfNum++;
					esferas[i][j].setActivo(true, true);
					esfLista[esfNum] = esferas[i][j];
					sumatoriaValores = esferas[i][j].getValor();
				}//if !pausa
			}//mousePressed()
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (!pausa) {
					if (esfNum > 0) {
						pausa = true;
						esfLista[esfNum].setValor(sumatoriaValores);
						esfLista[esfNum].setActivo(false, false);
						
						for (int j = 0; j < MAX_COLUMNAS; j++) {
							for (int i = (MAX_FILAS - 1); i >= 0; i--) {
								if (esferas[i][j] == null || esferas[i][j].getActivo()) {
									if (esferas[i][j] != null) {
										if (esferas[i][j].getActivo())
											esferas[i][j].setActivo(false, false);
										if (esferas[i][j] != esfLista[esfNum]) {
											remove(esferas[i][j]);
											esferas[i][j] = null;
										}//if esfera != esfLista[esfNum]
									}//if esfera != null
									
									esferas[i][j] = comprobarEsferas(i, j);
									resetearMl(i, j);
									esferas[i][j].iniciarCaida((distY * i) + 15);
								}//if
							}//for j
						}//for i
						
						Loader.getInstance().startBell(2);
						EntornoJuego.getInstance().actualizarPuntaje(sumatoriaValores);
						
						if (!hayJugadasDisponibles()) 
							EntornoJuego.getInstance().finDeJuego();
						
						if (esfLista[esfNum].getValor() > mayorValor) 
							mayorValor = esfLista[esfNum].getValor();
						
						caida.start();
					}//esfNum > 0
					else {
						if (esfLista != null)
							esfLista[esfNum].setActivo(false, true);
					}//else
				}//if !pausa
				
				esfNum = -1;
				esfLista = null;
				sumatoriaValores = 0;
			}//mouseReleased
		};//MouseListener ml
		
		return ml;
	}//eventoEsferas(int, int)
	
	/** Metodo que borra los MouseListeners de una esfera, y vuelve a anyadir otro MouseListener de 
	 * eventoEsferas actualizado con las nuevas posiciones. Recibe dos enteros que son las nuevas posiciones
	 *  de la esfera. */
	private void resetearMl(int i, int j) {
		MouseListener ml2[] = esferas[i][j].getMouseListeners();
		
		for (int k = 0; k < ml2.length; k++)
			esferas[i][j].removeMouseListener(ml2[k]);
		
		esferas[i][j].addMouseListener(eventoEsferas(i, j));
	}//resetearMl(int, int)
	
	/** Metodo para comprobar una esfera (que se localiza con los dos enteros que recibe como parametro). 
	 *  Cuando dicha esfera es igual a null, se busca una esfera que este arriba en la misma fila y si la 
	 *  consigue, enotnces esta cambiara su posicion con la primera; si no, se generan tantas esferas como null
	 *  se consiguieron mientras se subia en la fila y se colocan en donde hay espacios.*/
	private Esfera comprobarEsferas(int i, int j) {
		if ((i == 0 && esferas[i][j] == null) || (i == 0 && esferas[i][j].getActivo())) {
			Esfera esferita;
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Ocurrio un error al generar las esferas." + e.getMessage());
				System.exit(2);
			}
			
			int aux = (int) ((Math.random()) * 20);
			
			if (aux >= 0 && aux < 16)  {//80% de que se genere una esfera que sea de 2 a la mayor, si no es de 64k
				if (aux < 65536) //que no se genere esfera de 64k
				aux = ((int) ((Math.random() + 0.01d) * mayorValor));
				else  
					aux = ((int) ((Math.random()) * mayorValor));
			}//if (aux >= 0 && aux < 16)

			else //20% de que se genere una esfera del 2 al 8
				aux = ((int) (Math.random() * 8) + 1);
			
			esferita = new Esfera(aux);
			esferita.setLocation((distX * j) + 105, -85);
			add(esferita);
			
			return esferita;
		}//cuando ya no hay mas para revisar; i == 0

		else if (esferas[i][j] != null && !(esferas[i][j].getActivo())) {
			Esfera esferita = esferas[i][j];
			esferas[i][j] = null;
			return esferita;
		}
		
		else {
			Esfera esferita = comprobarEsferas(i - 1, j);
			return esferita;
		}
	}//comprobarEsfera(int i, int j)
	
	/** Metodo para comprobar si la esfera en la posicion es adyacente a la esfera que se selecciono. 
	 * recibe dos parametros que indican la posicion de la esfera seleccionada y devuelve un numero distinto 
	 * de cero si es adyacente, en caso contrario devuelve 0. Este metodo solo se utiliza en eventoEsferas.*/
	private int retornarDireccion(int i, int j) {
		int direccion = 0;
		
		if ((i > 0) && (j > 0) && esferas[i-1][j-1] == esfLista[esfNum]) //sup izq
			direccion = 1;
		else if ((i > 0) && esferas[i-1][j] == esfLista[esfNum]) //arriba
			direccion = 2;
		else if ((i > 0) && (j < MAX_COLUMNAS - 1) && esferas[i-1][j+1] == esfLista[esfNum])//sup der
			direccion = 3;
		else if ((j > 0) && esferas[i][j-1] == esfLista[esfNum]) //izq
			direccion = 4;
		else if ((j < MAX_COLUMNAS - 1) && esferas[i][j+1] == esfLista[esfNum]) //der
			direccion = 5;
		else if ((i < MAX_FILAS - 1) && (j > 0) && esferas[i+1][j-1] == esfLista[esfNum]) //inf izq
			direccion = 6;
		else if ((i < MAX_FILAS - 1) && esferas[i+1][j] == esfLista[esfNum]) //abajo
			direccion = 7;
		else if ((i < MAX_FILAS - 1) && (j < MAX_COLUMNAS - 1) && esferas[i+1][j+1] == esfLista[esfNum]) //inf der
			direccion = 8;
		
		return direccion;
	}//retornarDireccion(int, int)
	
	/** Metodo para pausar o despausar el juego. */
	public void setPausa(boolean estado) {
		pausa = estado;
	}//setPausa()
	
	/** Metodo para saber si el juego esta pausado o no. */
	public boolean getPausa() {
		return pausa;
	}//getPausa()
	
	/** Metodo que se ejecuta al finalizar un movimiento exitoso (cuando se suman las esferas y se genera una nueva) 
	 * que comprueba todo el tablero en busqueda de jugadas disponibles. Si consigue al menos una, devuelve true; si no,
	 * devuelve false. */
	private boolean hayJugadasDisponibles() {
		boolean jugadas = false;
		for (int i = 0; i < MAX_FILAS; i++) {
			for (int j = 0; j < MAX_COLUMNAS && jugadas!=true; j++) {
				if ((i > 0) && (j > 0) && esferas[i][j].getValor() == esferas[i-1][j-1].getValor())  //diag sup izq
					jugadas = true;
				
				else if ((i > 0) && esferas[i][j].getValor() == esferas[i-1][j].getValor())  //arriba
					jugadas = true;
				
				else if ((i > 0) && (j < MAX_COLUMNAS - 1) && esferas[i][j].getValor() == esferas[i-1][j+1].getValor())  //diag sup der
					jugadas = true;
				
				else if ((j > 0) && esferas[i][j].getValor() == esferas[i][j-1].getValor())  //izq
					jugadas = true;
				
				else if ((j < MAX_COLUMNAS - 1) && esferas[i][j].getValor() == esferas[i][j+1].getValor())  //der
					jugadas = true;
				
				else if ((i < MAX_FILAS - 1)  && (j > 0) && esferas[i][j].getValor() == esferas[i+1][j-1].getValor())  //diag inf izq
					jugadas = true;
				
				else if ((i < MAX_FILAS - 1) && esferas[i][j].getValor() == esferas[i+1][j].getValor())  //abajo
					jugadas = true;
				
				else if ((i < MAX_FILAS - 1) && (j < MAX_COLUMNAS - 1) && esferas[i][j].getValor() == esferas[i+1][j+1].getValor()) //diag inf der
					jugadas = true;
			}//for j
		}//for i
		return jugadas;
	}//hayJugadasDisponibles()
}//class Tablero