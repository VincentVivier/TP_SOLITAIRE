package solitaire.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.border.BevelBorder;

import solitaire.controleur.CTasDeCartesColorees;

public class PTasDeCartesColorees extends PTasDeCartes{

	private static final long serialVersionUID = 1L;
	
	CTasDeCartesColorees c;
	
	// gestion Dnd
	DropTarget dt;
	DropTargetDropEvent theInitialEvent;
	// fin gestion DnD
	
	public PTasDeCartesColorees(CTasDeCartesColorees c) {
		super(c);
		this.c = c;
		
		setPreferredSize(new Dimension(72, 96));
		setSize(getPreferredSize());
		
		setBackground(Color.MAGENTA);

		setBorder(new javax.swing.border.BevelBorder(BevelBorder.LOWERED));
		
		//Gestion DnD
		dt = new DropTarget(this, new MyDropTargetListener());
		// fin gestion DnD
	}
	
	// gestion DnD
	
	public void c2p_dropOK(){
		theInitialEvent.acceptDrop(DnDConstants.ACTION_MOVE);
		theInitialEvent.getDropTargetContext().dropComplete(true);
	}
	
	public void c2p_dropKO(){
		theInitialEvent.rejectDrop();
	}
	
	public void c2p_showEmpilable(){
		setBackground(Color.green);
	}
	
	public void c2p_showNonEmpilable(){
		setBackground(Color.red);
	}
	
	public void c2p_showNeutre(){
		setBackground(null);
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
			theInitialEvent = dtde;
			c.p2c_drop(ptc.getControle());
		}	
	}
	// fin gestion DnD
}
