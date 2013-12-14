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
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.border.BevelBorder;

import solitaire.controleur.CCarte;
import solitaire.controleur.CTasDeCartes;
import solitaire.controleur.CTasDeCartesColorees;

public class PTasDeCartesColorees extends PTasDeCartes{

	private static final long serialVersionUID = 1L;
	private static Color oldColor;
	
	CTasDeCartesColorees c;
	
	//Gestion DnD source
	DragSource ds;
	DragSourceListener myDSL;
	DragSourceMotionListener myDSML;
	DragGestureEvent theInitialEvent;
	PTasDeCartes ptcMove;
	Point origin;
	
	//Gestion DnD drop
	DropTarget dt;
	DropTargetDropEvent theInitialDropEvent;
	// fin gestion DnD
	
	public PTasDeCartesColorees(CTasDeCartesColorees c) {
        super(c);
        this.c = c;
		
        // Définition des tailles (+8 pour apercevoir la couleur d'aide au joueur lors d'un drag enter).
		setPreferredSize(new Dimension(72+8, 96+8));
		setSize(getPreferredSize());
		
		// Quelques effets de style
		setBackground(new Color(50, 50, 255));
		setBorder(new javax.swing.border.BevelBorder(BevelBorder.LOWERED));
		
		// Ajout direct du listeneur concernant la gestion du curseur.
		addMouseMotionListener(new GestionCurseur());
		
		//Gestion DnD source
		myDSL = new MyDragSourceListener() ;
		ds = new DragSource();
		ds.createDefaultDragGestureRecognizer (this, DnDConstants.ACTION_MOVE, new MyDragGestureListener ());
		myDSML = new MyDragSourceMotionListener();
		ds.addDragSourceMotionListener(myDSML);
		
		//Gestion DnD drop
		dt = new DropTarget(this, new MyDropTargetListener());
		// fin gestion DnD
	}
	
	// Empile les cartes centrées selon x et y
	public void empiler(PCarte pc){
		pc.setLocation((int) ((this.getSize().getWidth()-72)/2), 
						(int) ((this.getSize().getHeight()-96)/2));
		add(pc, 0);
		repaint(); // Nécessaire sinon mauvais placement des cartes
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du curseur
	//---------------------------------------------------------------------------------------------
	
	class GestionCurseur implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// Avertir le contrôleur d'un mouvement de souris sur le tas.
			c.p2c_sourisDetectee();
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
	// Gestion du Drag and Drop : source
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Méthode appelée lorsqu'un début de DnD n'a pas pu aboutir.
	 */
	public void c2p_debutDnDKO(CCarte cc){
		//Inutilisé jusqu'à présent.
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
				PCarte pc = (PCarte)getComponentAt(origin);
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
			ptcMove.setLocation(dsde.getX()-(ptcMove.getWidth()/2),
					dsde.getY() - getRootPane().getParent().getY() - origin.y - 25);
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
		oldColor = getBackground();
		setBackground(Color.green);
	}
	
	/**
	 * Même principe que 'c2p_showEmpilable()'. 
	 * Elle retient l'ancienne couleur du panel avant d'avertir que tout est KO (rouge).
	 */
	public void c2p_showNonEmpilable(){
		oldColor = getBackground();
		setBackground(Color.red);
	}
	
	/**
	 * Même principe que 'c2p_showEmpilable()'. 
	 * Elle redéfinit la couleur du panel avec l'ancienne valeur.
	 */
	public void c2p_showNeutre(){
		setBackground(oldColor);
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
	// Fin de gestion du Drag and Drop
	//---------------------------------------------------------------------------------------------
}
