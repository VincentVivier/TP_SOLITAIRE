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
		
		setPreferredSize(new Dimension(72+8, 96+8));
		setSize(getPreferredSize());
		
		setBackground(new Color(50, 50, 255));

		setBorder(new javax.swing.border.BevelBorder(BevelBorder.LOWERED));
		
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
	
	// Gestion du curseur
	
	class GestionCurseur implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			c.p2c_sourisDetectee();
		}
		
	}
	
	public void showCliquable(){
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void showNonCliquable(){
		setCursor(Cursor.getDefaultCursor());
	}
	
	// fin gestion curseur
	
	// gestion DnD source
	
	public void c2p_debutDnDKO(CCarte cc){
		System.out.println("tout est KOOOOOOOOO");
	}
	
	public void c2p_debutDndOK(CTasDeCartes ct){
		ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop, ct.getPresentation(), myDSL);
		ptcMove = ct.getPresentation();
		// Ajout du panel à déplacer
		ptcMove.setSize(72, ptcMove.getComponentCount()*25 + 71);
		getRootPane().add(ct.getPresentation(), 0);
	}
	
	class MyDragGestureListener implements DragGestureListener{

		@Override
		public void dragGestureRecognized(DragGestureEvent dge) {
			theInitialEvent = dge;
			CCarte cc = null;
			try{
				origin = dge.getDragOrigin();
				PCarte pc = (PCarte)getComponentAt(origin);
				cc = pc.getControle();
			}catch (Exception e){}
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

		@Override
		public void dragDropEnd(DragSourceDropEvent dsde) {
			c.p2c_dragDropEnd(dsde.getDropSuccess());
			getRootPane().remove(ptcMove);
			getRootPane().repaint();
		}
	}
	
	class MyDragSourceMotionListener implements DragSourceMotionListener {

		@Override
		public void dragMouseMoved(DragSourceDragEvent dsde) {
			ptcMove.setLocation(dsde.getX()-(ptcMove.getWidth()/2),
					dsde.getY() - getRootPane().getParent().getY() - origin.y - 25);
		}
		
	}
	
	// gestion DnD drop
	
	public void c2p_dropOK(){
		theInitialDropEvent.acceptDrop(DnDConstants.ACTION_MOVE);
		theInitialDropEvent.getDropTargetContext().dropComplete(true);
	}
	
	public void c2p_dropKO(){
		theInitialDropEvent.rejectDrop();
	}
	
	public void c2p_showEmpilable(){
		oldColor = getBackground();
		setBackground(Color.green);
	}
	
	public void c2p_showNonEmpilable(){
		oldColor = getBackground();
		setBackground(Color.red);
	}
	
	public void c2p_showNeutre(){
		setBackground(oldColor);
	}
	
	class MyDropTargetListener implements DropTargetListener{

		PTasDeCartes ptc;
		
		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
			try {
				ptc = (PTasDeCartes) dtde.getTransferable().getTransferData(new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType));
				c.p2c_dragEnter(ptc.getControle());
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
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
			c.p2c_dragExit(ptc.getControle());
		}

		@Override
		public void drop(DropTargetDropEvent dtde) {
			theInitialDropEvent = dtde;
			c.p2c_drop(ptc.getControle());
		}	
	}
	
	// fin gestion DnD
}
