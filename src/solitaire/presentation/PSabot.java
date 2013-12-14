package solitaire.presentation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import solitaire.controleur.CCarte;
import solitaire.controleur.CSabot;
import solitaire.controleur.CTasDeCartes;

public class PSabot extends JPanel {

	private static final long serialVersionUID = 1L;
	
	CSabot c;
	RetournerTasListener rtl;
	RetournerCarteListener rcl;
	PTasDeCartes cachees;
	PTasDeCartes visibles;
	
	//Gestion DnD
	DragSource ds;
	DragSourceListener myDSL;
	DragSourceMotionListener myDSML;
	DragGestureEvent theInitialEvent;
	PTasDeCartes ptcMove;
	Point origin;
	//fin gestion Dnd

	public PSabot(CSabot c, PTasDeCartes cachees, PTasDeCartes visibles) {
		this.c = c;
		this.cachees = cachees;
		this.visibles = visibles;
		
		// modifications du panel cachees
		cachees.setPreferredSize(new Dimension(72, 96));
		cachees.setSize(cachees.getPreferredSize());
		
		// modifications cartes visibles
		visibles.setPreferredSize(new Dimension(122, 96));
		visibles.setSize(cachees.getPreferredSize());
		
		// ajout des tas de cartes
		add(cachees);
		add(visibles);
		
		// Définition des décalages sur x et y.
		cachees.setDxDy(0, 0);
		visibles.setDxDy(25, 0);
		
		// Quelques effets visuels.
		visibles.setBackground(new Color(50, 50, 255));
		cachees.setBackground(new Color(0, 0, 200));
		cachees.setBorder(new javax.swing.border.BevelBorder(BevelBorder.RAISED));
		visibles.setBorder(new javax.swing.border.BevelBorder(BevelBorder.LOWERED));
		
		// Création des listeners
		rtl = new RetournerTasListener();
		rcl = new RetournerCarteListener();
		
		// Ajout direct des listeners concernant la gestion  du curseur sur les visibles et les cachées.
		visibles.addMouseMotionListener(new GestionCurseurVisibles());
		cachees.addMouseMotionListener(new GestionCurseurCachees());
		
		// Gestion Dnd source
		myDSL = new MyDragSourceListener() ;
		ds = new DragSource();
		ds.createDefaultDragGestureRecognizer (visibles, DnDConstants.ACTION_MOVE, new MyDragGestureListener ());
		myDSML = new MyDragSourceMotionListener();
		ds.addDragSourceMotionListener(myDSML);
		// fin Gestion DnD
	}
	
	public void activerRetournerCarte(){
		cachees.addMouseListener(rcl);
	}
	
	public void desactiverRetournerCarte(){
		cachees.removeMouseListener(rcl);
	}
	
	public void activerRetournerTas(){
		cachees.addMouseListener(rtl);
	}
	
	public void desactiverRetournerTas(){
		cachees.removeMouseListener(rtl);
	}
	
	/**
	 * Effacer toutes les cartes visibles
	 * (appelée à chaque retournement de 3 cartes)
	 */
	public void effacerVisibles(){
		visibles.removeAll();
	}
	
	class RetournerTasListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				// Avertissement au contrôleur de retourner toutes les cartes visibles.
				c.retourner();
			} catch (Exception e1) {
				System.err.println("Tas impossible à retourner.");
				e1.printStackTrace();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
	}
	
	class RetournerCarteListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				// Avertissement au contrôleur de retourner 3 cartes de la pioche.
				c.retournerCarte();
			} catch (Exception e1) {
				System.err.println("Cartes impossible à retourner.");
				e1.printStackTrace();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du curseur
	//---------------------------------------------------------------------------------------------
	
	class GestionCurseurVisibles implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// Avertir le contrôleur d'un mouvement en lui donnant le composant que la souris pointe.
			c.c2p_sourisVisiblesDetectee(visibles.getComponentAt(e.getPoint()));
		}
		
	}
	
	class GestionCurseurCachees implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// Avertir le contrôleur d'un mouvement de la souris. Pas besoin d'envoyer de composant.
			c.c2p_sourisCacheesDetectee();
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
	
	public void c2p_debutDnDKO(CTasDeCartes ct){
		// Inutilisé jusqu'à présent.
	}
	
	/**
	 * Appelée lorsqu'un DnD est accepté par le contrôleur.
	 * @param ct
	 * 		Le tas de carte à afficher.
	 */
	public void c2p_debutDnDOK(CTasDeCartes ct){
		// Début du DnD
		ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop, ct.getPresentation(), myDSL);
		
		// Sauvegarde du panel à déplacer (nécessaire au DragSourceMotionListener).
		ptcMove = ct.getPresentation();
		
		// Ajout du panel à déplacer dans la fenêtre principale après avoir réglé sa taille.
		ct.getPresentation().setSize(72, 96);
		getRootPane().add(ct.getPresentation(), 0);
	}
	
	class MyDragGestureListener implements DragGestureListener{

		/**
		 * A la reconnaissance du drag.
		 */
		@Override
		public void dragGestureRecognized(DragGestureEvent dge) {
			theInitialEvent = dge; // Sauvegarde de l'event
			PCarte pcMove = null;
			CCarte cc = null;
			try{
				// sauvegarde de l'origine (nécessaire au DragSourceMotionListener).
				origin = dge.getDragOrigin();
				
				// Récupération de la carte
				pcMove = (PCarte)visibles.getComponentAt(origin);
				cc = pcMove.getControle();
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
		}
		
	}

	//---------------------------------------------------------------------------------------------
	// Fin gestion du Drag and Drop
	//---------------------------------------------------------------------------------------------
}
