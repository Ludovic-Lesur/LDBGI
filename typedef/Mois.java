/**
 * Javadoc
 * 
 * @author Ludovic Lesur
 * @since 20/08/2016
 */


package typedef ;


public enum Mois {
	
	Vide ("", ""),
	JAN ("Janvier", "JANUARY"),
	FEV ("F�vrier", "FEBRUARY"),
	MAR ("Mars", "MARCH"),
	AVR ("Avril", "APRIL"),
	MAI ("Mai", "MAY"),
	JUN ("Juin", "JUNE"),
	JUI ("Juillet", "JULY"),
	AOU ("Ao�t", "AUGUST"),
	SEP ("Septembre", "SEPTEMBER"),
	OCT ("Octobre", "OCTOBER"),
	NOV ("Novembre", "NOVEMBER"),
	DEC ("D�cembre", "DECEMBER") ;
	
	// Attributs de chaque �l�ment de l'�num�ration.
	private final String symbol ;
	private final String frenchName ;
	private final String englishName ;
	
	
	/**
		CONSTRUCTEUR DE L'ENUMERATION MOIS.
	    @param pFrenchName		Nom fran�ais du mois de type 'String'.
	    @param pEnglishName		Nom anglais du mois de type 'String'.
	    @return					Aucun.
	*/
	private Mois(String pFrenchName, String pEnglishName) {
		symbol = this.toString() ;
		frenchName = pFrenchName ;
		englishName = pEnglishName ;
	}
	
	
	/**
		RENVOIE LE SYMBOLE DU MOIS.
	    @param 				Aucun.
	    @return	symbol		Symbole du mois utilis� dans les fichiers XML, de type 'String'.
	*/
	public String getSymbol() {
		return symbol ;	
	}
	
	
	/**
		RENVOIE LE NOM FRANCAIS DU MOIS.
	    @param 				Aucun.
	    @return	frenchName	Nom fran�ais du mois de type 'String'.
	*/
	public String getFrenchName() {
		return frenchName ;
	}
	
	
	/**
		RENVOIE LE NOM ANGLAIS DU MOIS.
	    @param 					Aucun.
	    @return	englishName		Nom anglais du mois de type 'String'.
	*/
	public String getEnglishName() {
		return englishName ;
	}
	
	
	/**
		RENVOIE LE MOIS CORRESPONDANT A UN NOM ANGLAIS DONNE (LIEN AVEC LOCALDATE).
	    @param pEnglishName		Nom recherch� de type 'String'.
	    @return	affectation		Mois associ� � 'pEnglishName' si la recherche a donn� un r�sultat.
	    						'Vide' sinon.
	*/
	public static Mois affecter(String pEnglishName) {
		Mois affectation = Vide ;
		Mois[] listeMois = Mois.values() ;
		int i = 0 ;
		for (i=0 ; i<listeMois.length ; i++) {
			if (listeMois[i].getEnglishName().compareTo(pEnglishName) == 0) {
				affectation = listeMois[i] ;
				break ;
			}
		}
		return affectation ;
	}
	
	
	/**
		RENVOIE LA LISTE DES ITEMS SAUF VIDE.
	    @param 				Aucun.
	    @return	resultat	Liste des mois, de type 'String[]'.
	*/
	public static String[] getFrenchNames() {
		Mois[] listeMois = Mois.values() ;
		String[] resultat = new String[listeMois.length-1] ;
		int i = 0 ;
		for (i=0 ; i<listeMois.length-1 ; i++) {
			resultat[i] = listeMois[i+1].getFrenchName() ;
		}
		return resultat ;
	}
}
