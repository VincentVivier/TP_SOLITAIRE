package solitaire.presentation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import solitaire.controleur.CCarte;
import solitaire.controleur.CColonne;
import solitaire.controleur.CTasDeCartes;

public class PColonne extends JPanel {

	private static final long serialVersionUID = 1L;
	
	CColonne c;
	RetournerCarteListener rcl;
	PTasDeCartes cachees;
	PTasDeCartes visibles;
	Color oldColor;
	
	//Gestion DnD source
	DragSource ds;
	DragSourceListener myDSL;
	DragSourceMotionListener myDSML;
	DragGestureEvent theInitialEvent;
	PTasDeCartes ptcMove;
	Point origin;
	
	// Gestion Dnd drop
	DropTarget dt;
	DropTargetDropEvent theInitialDropEvent;
	//fin gestion Dnd
	
	public PColonne(CColonne c, PTasDeCartes cachees, PTasDeCartes visibles) {
		this.c = c;
		this.cachees = cachees;
		this.visibles = visibles;
		
		// Supprimer le layout pour pouvoir jouer avec le déplacement des panels de cartes
		setLayout(null);
		
		// Rendre invisible pour ne plus avoir à gérer la couleur des panels (choix personnel)
		setOpaque(false);
		visibles.setOpaque(false);
		cachees.setOpaque(false);
		
		// Quelques effets visuels.
		setBorder(new javax.swing.border.BevelBorder(BevelBorder.LOWERED));
		setBackground(new Color(50, 50, 255)); // COuleur nécessaire pour le Border (au-dessus).
		
		// Définition des décalages sur x et y.
		cachees.setDxDy(0, 15);
		visibles.setDxDy(0, 25);
		
		// Ajout des tas de cartes (visibles par dessus les cachés).
		add(cachees);
		add(visibles, 0);

		// Définitions des tailles souhaitées.
		setPreferredSize(new Dimension(72, 600));
		setSize(getPreferredSize());
		visibles.setSize(72, 600);
		cachees.setSize(72, 200);
		
		// Création du listener écoutant le retournement d'une carte cachée
		rcl = new RetournerCarteListener();
		
		// Ajout direct du listeneur concernant la gestion du curseur.
		visibles.addMouseMotionListener(new GestionCurseur());
		
		// Gestion Dnd source
		myDSL = new MyDragSourceListener() ;
		ds = new DragSource();
		ds.createDefaultDragGestureRecognizer (visibles, DnDConstants.ACTION_MOVE, new MyDragGestureListener ());
		myDSML = new MyDragSourceMotionListener();
		ds.addDragSourceMotionListener(myDSML);
		
		// Gestion DnD drop
		dt = new DropTarget(visibles, new MyDropTargetListener());
		// fin Gestion DnD
	}
	
	/**
	 * Méthode appelée après la création de chaque colonne ainsi qu'àprès chaque modification
	 * du tas de cartes cachés afin de régler la position du panel de tas de cartes visibles en fonction
	 * des cachées.
	 */
	public void setAffichage(){
		visibles.setLocation(0, cachees.getComponentCount()*cachees.dy);
	}
	
	/**
	 * Active le listeneur concernant le retournement d'une carte cachée sur les visibles (coix personnel). 
	 */
	public void activerRetournerCarte(){
		visibles.addMouseListener(rcl);
	}
	
	/**
	 * Désactive le listener.
	 */
	public void desactiverRetournerCarte(){
		visibles.removeMouseListener(rcl);
	}
	
	class RetournerCarteListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				c.retournerCarte(); // Avertissement au contrôleur
				setAffichage(); // mise à jour du décalage des visibles.
				repaint();
			} catch (Exception e1) {
				System.err.println("Carte cachée impossible à retourner.");
				e1.printStackTrace();
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du curseur
	//---------------------------------------------------------------------------------------------
	
	class GestionCurseur implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// Avertir le contrôleur d'un mouvement en lui donnant le composant que la souris pointe.
			c.c2p_sourisDetectee(visibles.getComponentAt(e.getPoint()));
		}
		
	}
	
	/**
	 * Affichage du curseur main.
	 */
	public void showCliquable(){
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	/**
	 * Affichage du curseur classique. 
	 */
	public void showNonCliquable(){
		setCursor(Cursor.getDefaultCursor());
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion Drag and Drop : source
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Méthode appelée lorsqu'un début de DnD n'a pas pu aboutir.
	 */
	public void c2p_debutDnDKO(CCarte cc){
		// Inutilisé jusqu'à présent.
	}
	
	/**
	 * Appelée lorsqu'un DnD est accepté par le contrôleur.
	 * @param ct
	 * 		Le tas de carte à afficher.
	 */
	public void c2p_debutDndOK(CTasDeCartes ct){
		// Début du DnD
		ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop, ct.getPresentation(), myDSL);
		
		// Sauvegarde du panel à déplacer (nécessaire au DragSourceMotionListener).
		ptcMove = ct.getPresentation();
		
		// Ajout du panel à déplacer dans la fenêtre principale après avoir réglé sa taille.
		ptcMove.setSize(72, ptcMove.getComponentCount()*25 + 71);
		getRootPane().add(ct.getPresentation(), 0);
	}
	
	class MyDragGestureListener implements DragGestureListener{

		/**
		 * A la reconnaissance du drag.
		 */
		@Override
		public void dragGestureRecognized(DragGestureEvent dge) {
			theInitialEvent = dge; // Sauvegarde de l'event
			CCarte cc = null;
			try{
				// sauvegarde de l'origine (nécessaire au DragSourceMotionListener).
				origin = dge.getDragOrigin(); 
				
				// Récupération de la carte
				PCarte pc = (PCarte)visibles.getComponentAt(origin);
				cc = pc.getControle();
			}catch (Exception e){}
			
			// Avertissement au contrôleur avec la carte correspondante.
			c.p2c_debutDnd(cc);
		}
		
	}
	
	class MyDragSourceListener implements DragSourceListener{

		@Override
		public void dragEnter(DragSourceDragEvent dsde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragOver(DragSourceDragEvent dsde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dropActionChanged(DragSourceDragEvent dsde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragExit(DragSourceEvent dse) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * A la fin du DnD
		 */
		@Override
		public void dragDropEnd(DragSourceDropEvent dsde) {
			// Prévenir le contrôleur du succès ou non de l'opération
			c.p2c_dragDropEnd(dsde.getDropSuccess());
			
			// Supprimer le panel en mouvement.
			getRootPane().remove(ptcMove);
			getRootPane().repaint();
		}
	}
	
	class MyDragSourceMotionListener implements DragSourceMotionListener {

		/**
		 * Redéfinit la position du panel à chaque mouvement de la souris lors d'un DnD.
		 */
		@Override
		public void dragMouseMoved(DragSourceDragEvent dsde) {
			ptcMove.setLocation(dsde.getX() - getRootPane().getParent().getX() - origin.x,
					dsde.getY() - getRootPane().getParent().getY() - origin.y - 25);
			getRootPane().repaint();
		}
		
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du Drag and Drop : drop
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Apelée par le contrôleur s'il accepte le drop.
	 * La méthode termine l'opération de DnD.
	 */
	public void c2p_dropOK(){
		theInitialDropEvent.acceptDrop(DnDConstants.ACTION_MOVE);
		theInitialDropEvent.getDropTargetContext().dropComplete(true);
	}
	
	/**
	 * Appelée par le contrôleur si le drop n'est pas accepté.
	 * La méthode termine l'opération en rejettant le drop.
	 */
	public void c2p_dropKO(){
		theInitialDropEvent.rejectDrop();
	}
	
	/**
	 * Méthode facilitant la possibilité au joureur de savoir si le tas de cartes est déposable ou non.
	 * Elle retient l'ancienne couleur du panel avant d'avertir que tout est OK (vert).
	 */
	public void c2p_showEmpilable(){
		oldColor = visibles.getBackground();
		visibles.setBackground(Color.green);
		visibles.setOpaque(true);
	}
	
	/**
	 * Même principe que 'c2p_showEmpilable()'. 
	 * Elle retient l'ancienne couleur du panel avant d'avertir que tout est KO (rouge).
	 */
	public void c2p_showNonEmpilable(){
		oldColor = visibles.getBackground();
		visibles.setBackground(Color.red);
		visibles.setOpaque(true);
	}
	
	/**
	 * Même principe que 'c2p_showEmpilable()'. 
	 * Elle redéfinit la couleur du panel avec l'ancienne valeur.
	 */
	public void c2p_showNeutre(){
		visibles.setBackground(oldColor);
		visibles.setOpaque(false);
	}
	
	class MyDropTargetListener implements DropTargetListener{

		PTasDeCartes ptc;
		
		/**
		 * Lors d'un éventuel drop détecté.
		 */
		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
			try {
				// Récupération du Transferable (TasDeCartes) et sauvegarde de celui-ci.
				ptc = (PTasDeCartes) dtde.getTransferable().getTransferData(new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType));
				// Avertissement au contrôleur
				c.p2c_dragEnter(ptc.getControle());
				
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragExit(DropTargetEvent dte) {
			// Avertissement au contrôleur : nécessaire pour afficher le "neutre" -> retour visuel.
			c.p2c_dragExit(ptc.getControle());
		}

		@Override
		public void drop(DropTargetDropEvent dtde) {
			theInitialDropEvent = dtde; // Sauvegarde de l'évent.
			c.p2c_drop(ptc.getControle()); // Avertissement du drop avec le tas de carte en question.
		}	
	}
	//---------------------------------------------------------------------------------------------
	// Fin gestion du Drag and Drop
	//---------------------------------------------------------------------------------------------
}
