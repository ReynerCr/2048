package codigo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/** Clase donde se instancia el menu y las distintas secciones del programa, que no tienen que ver 
 * directamente con el funcionamiento del juego. Funciona como panel para Juego.*/
@SuppressWarnings("serial")
public class Menu extends PanelPadre {
	
	private static Menu instance = null;
	
	private BotonMenu nuevoJ;
	private BotonMenu instrucciones;
	private BotonMenu top10;
	private BotonMenu creditos;
	private BotonMenu salir;
	private BotonMenu bVolver;
	
	/** Constructor por defecto y unico de Menu. Es llamado desde getInstance() si todavía no se ha creado. */
	private Menu() {
		iniciarBotones();
	}//Menu()
	
	/** Metodo estatico que devuelve instance si ya se instancio, en caso contrario lo instancia. */
	public static Menu getInstance() {
		if (instance == null)
			instance = new Menu();
		
		return instance;
	}//getInstance()
	
	/** Metodo que llama a los metodos para inicializar botones. */
	private void iniciarBotones() {
		eVolverMenu();
		bNuevoJuego();
		bInstrucciones();
		bTop10();
		bCreditos();
		bSalir();
	}//iniciarBotones()
	
	/** Metodo para inicializar el boton de volver al menu, que se utiliza en otros submenus. */
	private void eVolverMenu() {
		ActionListener volverMenu =  new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Juego.getInstance().actualizarFrame(Menu.getInstance());
			}//actionPerformed()
		};//ActionListener volverMenu
		
		bVolver = new BotonMenu("Volver");
		bVolver.addActionListener(volverMenu);
		bVolver.setSize(150, 80);
		bVolver.setLocation(30, this.getHeight() - 150);
	}//eVolverMenu()
	
	/** Metodo para inicializar el boton de nuevo juego y que crea el submenu donde se pregunta al usuario
	 *  el nombre y luego se le redirige al juego en si. */
	private void bNuevoJuego() { 
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelPadre panel = new PanelPadre();
				
				BotonMenu nombre = new BotonMenu("Ingrese su nombre", 200, 50, 15);
				nombre.setLocation(200, 400);
				panel.add(nombre);
				
				EtiquetaPersonalizada etiqueta = new EtiquetaPersonalizada("Maximo 8 caracteres y ninguno igual a \"@\".", 480, 30, 16);
				etiqueta.setLocation(60, 450);
				panel.add(etiqueta);
				
				JTextField cajaT = new JTextField();
				cajaT.setFont(new Font("Arial", Font.BOLD, 20));
				cajaT.setBounds(200, 350, 200, 50);
				
				ActionListener al2 = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						cajaT.setText(cajaT.getText().trim());
						
						if (cajaT.getText().isEmpty()) 
							JOptionPane.showMessageDialog(null, "Nombre no puede estar vacio.");
						
						else if(cajaT.getText().contains("@")) 
							JOptionPane.showMessageDialog(null, "El caracter \"@\" no esta permitido en el nombre.");
						else if (cajaT.getText().length() > 8)
							JOptionPane.showMessageDialog(null, "Nombre no puede ser mayor de 8 caracteres.");
						else {
							if (EntornoJuego.getInstance().getNombre() != null) {
								EntornoJuego.getInstance().reiniciar();
							}
							EntornoJuego.getInstance().setNombre(cajaT.getText());
							Juego.getInstance().actualizarFrame(EntornoJuego.getInstance());
						}//else para ir al juego
					}//actionPerformed()
				};//ActionListener al2
				
				cajaT.addActionListener(al2);
				nombre.addActionListener(al2);
				
				panel.add(cajaT);
				panel.add(bVolver);
				
				Juego.getInstance().actualizarFrame(panel);
				panel = null;
			}//actionPerfomed()
		};//ActionListener al
		
		nuevoJ = new BotonMenu("Nuevo juego");
		nuevoJ.addActionListener(al);
		nuevoJ.setBounds((this.getWidth()/2)/2, 200, 300, 50);
		
		this.add(nuevoJ);
	}//bNuevoJuego()
	
	/** Metodo para inicializar el boton para ir a instrucciones y que al pulsarlo crea una instancia de Instrucciones. */
	private void bInstrucciones() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Juego.getInstance().actualizarFrame(Instrucciones.getInstance().getPagina(0));
			}//actionPerformed()
		};//ActionListener al
		
		instrucciones = new BotonMenu("Instrucciones");
		instrucciones.addActionListener(al);
		instrucciones.setBounds((this.getWidth()/2)/2, nuevoJ.getY() + 80, 300, 50);
		
		this.add(instrucciones);
	}//bInstrucciones()
	
	/** Metodo para inicializar el boton para ir al top10, donde se muestra la lista de jugadores que entraron al top10
	 *  al jugar. */
	private void bTop10() {
		ActionListener al = new ActionListener() {
			@SuppressWarnings("resource")
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelPadre panel = new PanelPadre();
				EtiquetaPersonalizada etiqueticas[] = new EtiquetaPersonalizada[10];
				String dir = "top10.dat";
				Scanner entrada = null;
				try {
					entrada = new Scanner (new File(dir));
					int i = 0;
					while(entrada.hasNextLine() && i < 10) {
						String cadenas[] = (entrada.nextLine()).split("@");
						if (Long.parseLong(cadenas[1]) != 0) {
							etiqueticas[i] = new EtiquetaPersonalizada((i+1) + ". " + cadenas[0] + "......"+ cadenas[1], 540, 50, 18);
							etiqueticas[i].setLocation(30, 190 + (i*40));
							panel.add(etiqueticas[i]);
							i++;
						}
						else
							break;
					}//while para leer los datos y mostrarlos
					
					if (i == 0) 
						throw new IOException(); //reutilizo codigo
					
					entrada.close();
				} catch (IOException e1) {
					if (entrada != null)
						entrada.close();
					
					for (int i = 0; i < etiqueticas.length && etiqueticas[i]!=null; i++)
						panel.remove(etiqueticas[i]);
					
					etiqueticas[0] = new EtiquetaPersonalizada("No hay registros de jugadores.", 540, 50, 18);
					etiqueticas[0].setLocation(30, 320);
					panel.add(etiqueticas[0]);
					
					JOptionPane.showMessageDialog(null, "El archivo esta corrupto, se creara de nuevo al jugar.");
					new File(dir).delete();
				} catch (ArrayIndexOutOfBoundsException e1) {
					entrada.close();
					for (int i = 0; i < etiqueticas.length && etiqueticas[i]!=null; i++)
						panel.remove(etiqueticas[i]);
					
					etiqueticas[0] = new EtiquetaPersonalizada("No hay registros de jugadores.", 540, 50, 18);
					etiqueticas[0].setLocation(30, 320);
					panel.add(etiqueticas[0]);
					
					JOptionPane.showMessageDialog(null, "El archivo esta corrupto, se creara de nuevo al jugar.");
					new File(dir).delete();
				}//fin de catchs
				
				panel.add(bVolver);
				
				Juego.getInstance().actualizarFrame(panel);
				panel = null;
			}//ActionPerformed
		};//ActionListener al
		
		top10 = new BotonMenu("Top 10");
		top10.addActionListener(al);
		top10.setBounds((this.getWidth()/2)/2, instrucciones.getY() + 80, 300, 50);
		
		this.add(top10);
	}//bTop10()
	
	/** Metodo para inicializar el boton para ir a los creditos, donde se muestra al autor y la version del juego. */
	private void bCreditos() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelPadre panel = new PanelPadre();
				EtiquetaPersonalizada etiqueta;
				
				etiqueta =  new EtiquetaPersonalizada("Creditos", 200, 50, 18);
				etiqueta.setLocation((panel.getWidth()/3), 40);
				panel.add(etiqueta);
				
				etiqueta = new EtiquetaPersonalizada("Autor: Reyner Contreras.", 540, 50, 18);
				etiqueta.setLocation(30, 300);
				panel.add(etiqueta);
						
				etiqueta = new EtiquetaPersonalizada("Version: 2.00", 200, 50, 15);
				etiqueta.setLocation((panel.getWidth()/3), 550);
				panel.add(etiqueta);
				
				panel.add(bVolver);
				Juego.getInstance().actualizarFrame(panel);
				panel = null;
			}//actionPerformed
		};//ActionListener al
		
		creditos = new BotonMenu("Creditos");
		creditos.addActionListener(al);
		creditos.setBounds((this.getWidth()/2)/2, top10.getY() + 80, 300, 50);
		
		this.add(creditos);
	}//bCreditos()

	/** Metodo para inicializar el boton de salir del juego, y que hace exactamente eso. */
	private void bSalir() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}//actionPerformed()
		};//ActionListener al
		
		salir = new BotonMenu("Salir");
		salir.addActionListener(al);
		salir.setBounds((this.getWidth()/2)/2, creditos.getY() + 80, 300, 50);
		
		this.add(salir);
	}//bSalir()
}//class Menu