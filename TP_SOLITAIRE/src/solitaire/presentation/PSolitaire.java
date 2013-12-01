package solitaire.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import solitaire.controleur.CSolitaire;

public class PSolitaire extends JFrame {

	private static final long serialVersionUID = 1L;

	CSolitaire c;
	
	public PSolitaire(CSolitaire cSolitaire, PSabot sabot, PTasDeCartesColorees[] ptcc, 
			PColonne[] ptca) {
		super("GLI - Solitaire");
		
		this.c = c;
		
		addWindowListener(new MyWindowListener());
		
		setPreferredSize(new Dimension(750, 700));
		setSize(this.getPreferredSize());
	//	setResizable(false);
		
		
		JPanel pNord = new JPanel();
		pNord.setLayout(new BorderLayout());
		pNord.setBackground(Color.PINK);
		
		// Ajout du sabot
		sabot.setBackground(Color.black);
		pNord.add(sabot, BorderLayout.WEST);
		
		// Ajout des tas de cartes colorés avec le font correspondant
		JLabel face;
		JPanel pCC = new JPanel();
		pCC.setBackground(Color.BLUE);
		for (int i = 0 ; i < ptcc.length ; i++){
			face = new JLabel(new ImageIcon(ClassLoader.getSystemResource("solitaire/cartesCSHD/CarteInit" + (i+1) + ".gif")));
			face.setSize(72, 96);
			ptcc[i].add(face, -1);
			pCC.add(ptcc[i]);
		}
		pNord.add(pCC, BorderLayout.EAST);
		add(pNord, BorderLayout.NORTH);
		
		// Ajout des colonnes
		JPanel pColonnes = new JPanel();
		pColonnes.setBackground(Color.ORANGE);
		for (int i = 0 ; i < ptca.length ; i++){
			pColonnes.add(ptca[i], BorderLayout.CENTER);
		}
		add(pColonnes, BorderLayout.CENTER);
		
		// Rendre visible le tout
		setVisible(true);
		
	}
	
	class MyWindowListener implements WindowListener {
		
		@Override
		public void windowOpened(WindowEvent e) {
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("Arret de l'appli");
			System.exit(0);
		}

		@Override
		public void windowClosed(WindowEvent e) {
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
	
		}
	}
}
