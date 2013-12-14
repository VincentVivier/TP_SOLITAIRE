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
import javax.swing.border.BevelBorder;

import solitaire.controleur.CSolitaire;

public class PSolitaire extends JFrame {

	private static final long serialVersionUID = 1L;

	CSolitaire c;
	
	public PSolitaire(CSolitaire cSolitaire, PSabot sabot, PTasDeCartesColorees[] ptcc, 
			PColonne[] ptca) {
		super("GLI - Solitaire - VIVIER/ECONOMIDES");
		
		this.c = c;
		
		// Ajout d'un listener pour détecter une éventuelle fermeture de l'pplication.
		addWindowListener(new MyWindowListener());
		
		// Définition de la taille et de la couleur de l'arrière plan.
		setPreferredSize(new Dimension(750, 700));
		setSize(this.getPreferredSize());
		getContentPane().setBackground(new Color(0, 0, 250));
		
		// Définition du panel nord et de ses propriétés
		JPanel pNord = new JPanel();
		pNord.setLayout(new BorderLayout());
		pNord.setOpaque(false);
		
		// Ajout du sabot dans le panel nord (Ouest)
		sabot.setOpaque(false);
		pNord.add(sabot, BorderLayout.WEST);
		
		// Ajout des tas de cartes colorés avec le font correspondant dans le panel nord (Est)
		JLabel face;
		JPanel pCC = new JPanel();
		pCC.setBorder(new javax.swing.border.BevelBorder(BevelBorder.RAISED));
		pCC.setBackground(new Color(0, 0, 200));
		for (int i = 0 ; i < ptcc.length ; i++){
			face = new JLabel(new ImageIcon(ClassLoader.getSystemResource("solitaire/cartesCSHD/CarteInit" + (i+1) + ".gif")));
			face.setSize(72, 96);
			face.setLocation(4, 4);
			ptcc[i].add(face, -1);
			pCC.add(ptcc[i]);
		}
		pNord.add(pCC, BorderLayout.EAST);
		
		// Ajout du panel nord au nord de la fenêtre
		add(pNord, BorderLayout.NORTH);
		
		// Ajout de chaque colonne dans le panel des colonnes 
		JPanel pColonnes = new JPanel();
		pColonnes.setBackground(Color.YELLOW);
		pColonnes.setOpaque(false);
		for (int i = 0 ; i < ptca.length ; i++){
			pColonnes.add(ptca[i], BorderLayout.CENTER);
		}
		
		// Ajout du panel des colonnes au centre dela fenêtre
		add(pColonnes, BorderLayout.CENTER);
		
		// Rendre visible le tout
		setVisible(true);
		
	}
	
	class MyWindowListener implements WindowListener {
		
		@Override
		public void windowOpened(WindowEvent e) {
			
		}

		/**
		 * Arrêt de l'application lorsque la fenêtre est fermée.
		 */
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
