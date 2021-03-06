/**
 * Javadoc
 * 
 * @author Ludovic Lesur
 * @since 19/08/2016
 */

package graphic;

import typedef.*;
import data.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class RechercheEngin extends JFrame implements ActionListener, DocumentListener {

	private static final long serialVersionUID = 1L;

	// Lien avec les autres classes graphiques.
	private Interface i;
	private Parc p;
	private Engin enginCourant;
	private JPanel panel;
	private GridBagConstraints gbc;

	// Elements graphiques.
	private JLabel titre;
	private JComboBox<String> choixIdentifiant;
	private Identifiant identifiantCourant;
	private JTextField champRecherche;

	private JButton rechercher;
	private JButton precedent;
	private JButton suivant;
	private JLabel resultat;
	private JLabel espace;
	private JLabel affichage;

	private JRadioButton affVues;
	private JRadioButton affRemarques;
	private ButtonGroup choixAff;

	private boolean remarque;
	private boolean enable;

	/**
	 * CONSTRUCTEUR DE LA CLASSE RECHERCHERENGIN.
	 * 
	 * @param pI
	 *            Interface graphique mere, de type 'Interface'.
	 * @param mainPanel
	 *            Panel de l'interface graphique mere, de type 'JPanel'.
	 * @param mainGbc
	 *            Contraintes de l'interface graphique mere, de type
	 *            'GridBagConstraints'.
	 * @param parcEngins
	 *            Parc d'engins moteurs dans lequel doit s'effectuer les
	 *            recherches, de type 'Parc'.
	 * @return Aucun.
	 */
	public RechercheEngin(Interface pI, JPanel mainPanel, GridBagConstraints mainGbc, Parc parcEngins) {

		i = pI;
		p = parcEngins;
		enginCourant = new Engin();

		mainGbc.gridx = 2;
		mainGbc.gridy = 1;

		panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.gray);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 15, 15, 15);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.CENTER;

		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 0;
		titre = new JLabel("ENGINS  MOTEURS");
		titre.setFont(new Font(Interface.police.getFontName(), 1, 13));
		titre.setForeground(Color.white);
		panel.add(titre, gbc);

		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 0;
		espace = new JLabel("espace");
		espace.setFont(Interface.police);
		espace.setForeground(Color.gray);
		panel.add(espace, gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 5;
		gbc.gridy = 0;
		affichage = new JLabel("AFFICHAGE");
		affichage.setFont(new Font(Interface.police.getFontName(), 1, 13));
		affichage.setForeground(Color.white);
		panel.add(affichage, gbc);

		gbc.insets.bottom = 2;

		gbc.gridx = 0;
		gbc.gridy = 1;
		choixIdentifiant = new JComboBox<String>(Identifiant.getNames());
		choixIdentifiant.setFont(Interface.police);
		choixIdentifiant.addItemListener(new ItemState());
		panel.add(choixIdentifiant, gbc);
		identifiantCourant = Identifiant.values()[choixIdentifiant.getSelectedIndex() + 1]; // +1 car "Vide" n'est pas dans la liste.

		gbc.gridx = 1;
		gbc.gridy = 1;
		champRecherche = new JTextField(10);
		champRecherche.setFont(Interface.police);
		champRecherche.setEditable(true);
		champRecherche.getDocument().addDocumentListener(this);
		panel.add(champRecherche, gbc);

		gbc.gridwidth = 2;
		gbc.gridx = 2;
		gbc.gridy = 1;
		rechercher = new JButton("Rechercher");
		rechercher.setFont(Interface.police);
		rechercher.addActionListener(this);
		rechercher.setEnabled(false);
		panel.add(rechercher, gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 5;
		gbc.gridy = 1;
		affVues = new JRadioButton("Vues");
		affVues.setFont(Interface.police);
		affVues.setOpaque(false);
		affVues.setForeground(Color.white);
		affVues.setSelected(true);
		affVues.addActionListener(this);
		panel.add(affVues, gbc);

		gbc.insets.top = 2;
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		resultat = new JLabel("Aucun resultat.");
		resultat.setFont(Interface.police);
		resultat.setForeground(Color.orange);
		panel.add(resultat, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		precedent = new JButton("<");
		precedent.setFont(Interface.police);
		precedent.addActionListener(this);
		precedent.setEnabled(false);
		panel.add(precedent, gbc);

		gbc.gridx = 3;
		gbc.gridy = 2;
		suivant = new JButton(">");
		suivant.setFont(Interface.police);
		suivant.addActionListener(this);
		suivant.setEnabled(false);
		panel.add(suivant, gbc);

		gbc.gridx = 5;
		gbc.gridy = 2;
		affRemarques = new JRadioButton("Remarques");
		affRemarques.setFont(Interface.police);
		affRemarques.setOpaque(false);
		affRemarques.setForeground(Color.white);
		affRemarques.setSelected(true);
		affRemarques.addActionListener(this);
		panel.add(affRemarques, gbc);

		choixAff = new ButtonGroup();
		choixAff.add(affVues);
		choixAff.add(affRemarques);
		remarque = false;
		enable = true;

		mainPanel.add(panel, mainGbc);
	}

	/**
	 * RENVOIE L'ENGIN MOTEUR CHERCHE.
	 * 
	 * @param Aucun.
	 * @return enginTrouve Engin moteur trouve si la recherche a donne un
	 *         resultat. 'null' sinon.
	 */
	private Engin chercher() {
		Engin enginTrouve = null;
		String numCherche = champRecherche.getText();
		enginTrouve = p.rechercherEngin(identifiantCourant, numCherche);
		if (enginTrouve == null) {
			resultat.setText("Aucun resultat.");
			resultat.setForeground(Color.orange);
			precedent.setEnabled(false);
			suivant.setEnabled(false);
		} else {
			resultat.setText("1 resultat.");
			resultat.setForeground(Color.green);
			checkNumero();
		}
		return enginTrouve;
	}

	/**
	 * DEFINIT L'ENGIN MOTEUR COURANT.
	 * 
	 * @param newEngin
	 *            Engin moteur courant de type 'Engin'.
	 * @return Aucun.
	 */
	public void setEnginCourant(Engin newEngin) {
		if (newEngin != null) {
			enginCourant = newEngin;
			// Selection de l'identifiant.
			String identifiantCourant = enginCourant.getSerie().getIdentifiant().getName();
			String[] listeIdentifiants = Identifiant.getNames();
			int k = 0;
			for (k = 0; k < listeIdentifiants.length; k++) {
				if (listeIdentifiants[k].compareTo(identifiantCourant) == 0) {
					break;
				}
			}
			choixIdentifiant.setSelectedIndex(k);
			// Selection du numero.
			champRecherche.setText(enginCourant.getNumero());
			chercher();
		}
	}

	/**
	 * ACTIVE LE CHOIX ENTRE VUES ET REMARQUES.
	 * 
	 * @param Aucun.
	 * @return Aucun.
	 */
	public void setEnableTrue() {
		enable = true;
	}

	/**
	 * DESACTIVE LE CHOIX ENTRE VUES ET REMARQUES.
	 * 
	 * @param Aucun.
	 * @return Aucun.
	 */
	public void setEnableFalse() {
		enable = false;
	}

	/**
	 * VERIFIE LE TEXTE ENTRE DANS LE CHAMP DE RECHERCHE.
	 * 
	 * @param Aucun.
	 * @return Aucun.
	 */
	private void checkNumero() {
		String test = champRecherche.getText();
		if (test.isEmpty() == true) {
			rechercher.setEnabled(false);
			suivant.setEnabled(false);
			precedent.setEnabled(false);
		} else {
			rechercher.setEnabled(true);
			Engin eC = p.rechercherEngin(identifiantCourant, test);
			if (eC != null) {
				if (eC.getIndice() < eC.getSerie().getEffectif()) {
					suivant.setEnabled(true);
				}
				else {
					suivant.setEnabled(false);
				}
				if (eC.getIndice() > 1) {
					precedent.setEnabled(true);
				}
				else {
					precedent.setEnabled(false);
				}
			}
		}
	}

	/**
	 * DEFINIT LES ACTIONS DE LA LISTE DEROULANTE.
	 * 
	 * @param Aucun.
	 * @return Aucun.
	 */
	class ItemState implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == choixIdentifiant) {
				identifiantCourant = Identifiant.values()[choixIdentifiant.getSelectedIndex() + 1]; // +1 car "Vide" n'est pas dans la liste.
			}
		}
	}

	/**
	 * DEFINIT LES ACTIONS DES BOUTONS.
	 * 
	 * @param e
	 *            Evenement declenche par l'appui sur un bouton.
	 * @return Aucun.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rechercher) {
			i.afficherResultat(chercher(), remarque);
			enable = true;
		}
		if (e.getSource() == suivant) {
			Engin eC = p.rechercherEngin(identifiantCourant, champRecherche.getText());
			Serie sC = eC.getSerie();
			String newNum = sC.rechercherNumero(eC.getIndice() + 1);
			champRecherche.setText(newNum);
			i.afficherResultat(chercher(), remarque);
			enable = true;
		}
		if (e.getSource() == precedent) {
			Engin eC = p.rechercherEngin(identifiantCourant, champRecherche.getText());
			Serie sC = eC.getSerie();
			String newNum = sC.rechercherNumero(eC.getIndice() - 1);
			champRecherche.setText(newNum);
			i.afficherResultat(chercher(), remarque);
			enable = true;
		}
		if (e.getSource() == affVues) {
			remarque = false;
			if (enable == true) {
				i.afficherResultat(enginCourant, remarque);
			}
		}
		if (e.getSource() == affRemarques) {
			remarque = true;
			if (enable == true) {
				i.afficherResultat(enginCourant, remarque);
			}
		}
	}

	/**
	 * FONCTIONS DE VERIFICATION DE SAISIE CLAVIER.
	 * 
	 * @param e
	 *            Evenement declenche par une saisie clavier.
	 * @return Aucun.
	 */
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		checkNumero();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		checkNumero();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		checkNumero();
	}
}
