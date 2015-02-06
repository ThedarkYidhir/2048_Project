package metier;

import java.util.ArrayList;

public class Plateau
{
	ArrayList<ArrayList<Integer>> plateau ;
	
	public Plateau()
	{
		this.plateau = new ArrayList<ArrayList<Integer>>();
		
		// Creation des lignes du plateau
		for( int i = 0 ; i < 4 ; i++ )
			this.plateau.add(new ArrayList<Integer>());
		
		initPlateau();
	}
	
	/**
	 * Initialisation du tableau � 0
	 */
	private void initPlateau()
	{
		for( ArrayList<Integer> ligne : plateau ) // pour toutes les lignes
		{
			// On les cases � 0
			for( int i = 0 ; i < 4 ; i++ )
				ligne.add(0);
		}
		
		
		
		// Config Test : premiere ligne pleine de 2 
		plateau.get(0).set(0,2);
		plateau.get(0).set(1,2);
		plateau.get(0).set(2,2);
		plateau.get(0).set(3,2);
		

		
	}
	
	/**
	 * Initialise le jeu en mettant 2 nouveaux chiffres aleatoires.
	 */
	public void debut()
	{
		int posAleatoire 		= positionAleatoire() ;
		int lignePosAleatoire 	= (int) Math.ceil(((double)posAleatoire)/4);// recup�re la ligne du nombre tir�e
		int colPosAleatoire 	= posAleatoire%4 ;							// recup�re la colonne du nombre tir�e
		// Si le nombre tir� est en fin de ligne
		if ( colPosAleatoire == 0 )
			colPosAleatoire = 4 ;

		plateau.get(lignePosAleatoire-1).set(colPosAleatoire-1, tirageAleatoire());
		
		posAleatoire 		= positionAleatoire() ;
		lignePosAleatoire 	= (int) Math.ceil(((double)posAleatoire)/4);// recup�re la ligne du nombre tir�e
		colPosAleatoire 	= posAleatoire%4 ;							// recup�re la colonne du nombre tir�e
		// Si le nombre tir� est en fin de ligne
		if ( colPosAleatoire == 0 )
			colPosAleatoire = 4 ;

		plateau.get(lignePosAleatoire-1).set(colPosAleatoire-1, tirageAleatoire());
	}
	
	/**
	 * Tire un nouveau nombre aleatoire � mettre dans le plateau.
	 * 90% de chance de tomber sur un 2.
	 * 10% de chance de tomber sur un 4.
	 * @return tirage
	 */
	private int tirageAleatoire()
	{
		int tirage = (int)( Math.random()*( 9 - 0 + 1 ) ) + 0;
		
		if ( tirage < 9 )
			return 2 ;
		else
			return 4 ;
	}
	
	/**
	 * Tire un nombre aleatoire indiquant une position libre sur le plateau.
	 * En cas d'impossiblit� de tirer une case libre, la m�thode retourne -1.
	 * @return position
	 */
	private int positionAleatoire()
	{
		ArrayList<Integer> caseLibre = new ArrayList<Integer>();
		
		// On ajoute les cases inoccup�e dans l'arraylist pour tirer le numero parmis elles
		int numCellule = 1 ;
		for ( ArrayList<Integer> ligne : plateau )
		{
			for ( int cellule : ligne )
			{
				if ( cellule == 0 )
					caseLibre.add(numCellule);
				
				numCellule++ ;
			}
		}
		
		// Tirage d'une cellule libre dans le tableau
		if (! caseLibre.isEmpty() )
		{
			int tirage = (int)( Math.random()*( caseLibre.size()-1 - 0 + 1 ) ) + 0; 
		
			int position = caseLibre.get(tirage);
			return position ;
		}
		else
			return -1;

	}
	
	/**
	 * Toutes les cellules vont � gauche. Si 2 cellules identique se rencontre,
	 * elle fusionne et sa valeur double.
	 * @return possible
	 */
	public boolean gauche()
	{
		boolean possible = false ;
		
		// Pour toutes les lignes
		for ( ArrayList<Integer> ligne : plateau )
		{
			for ( int cellule = 1 ; cellule <= 3 ; cellule++ )
			{
				// On ne d�place pas les cases vides
				if ( ligne.get(cellule) != 0 )
				{
					/* Pour toutes les cellules de la ligne de gauche � droite
					 * On d�cale la cellule vers la gauche jusqu'� rencontrer un obstacle 
					 * ( qui entraine un bloquage ou une fusion ) 
					 */
					int celluleGauche = cellule-1 ;
					System.out.println("celluleGauche = "+celluleGauche);

					while ( celluleGauche >= 0 && ligne.get(celluleGauche) == 0 )
					{
						ligne.set(celluleGauche, ligne.get(celluleGauche+1)) ;
						ligne.set(celluleGauche+1, 0) ;
						
						celluleGauche--;
					}

					/* si la cellule n'est pas arriv� au bout, c'est parce qu'il a �t� bloqu�
					 * par une autre cellule. Dans ce cas, possibilit� de fusion.
					 */
					if ( celluleGauche != -1 )
					{
						// Si les 2 sont �gales, elles fusionnent
						if (ligne.get(celluleGauche) == ligne.get(celluleGauche+1))
						{
							ligne.set(celluleGauche+1, 0);
							ligne.set(celluleGauche, ligne.get(celluleGauche)*2);
						}
					}
					
					possible = true ;

				}
			}
		}
		
		return possible ;
	}
	
	/**
	 * Toutes les cellules vont � droite. Si 2 cellules identique se rencontre,
	 * elle fusionne et sa valeur double.
	 * @return possible
	 */
	public boolean droite()
	{
		boolean possible = false ;
		
		// Pour toutes les lignes
		for ( ArrayList<Integer> ligne : plateau )
		{
			for ( int cellule = 2 ; cellule >= 0 ; cellule-- )
			{
				// On ne d�place pas les cases vides
				if ( ligne.get(cellule) != 0 )
				{
					/* Pour toutes les cellules de la ligne de droite � gauche
					 * On d�cale la cellule vers la droite jusqu'� rencontrer un obstacle 
					 * ( qui entraine un bloquage ou une fusion ) 
					 */
					int celluleDroite = cellule+1 ;
					System.out.println("celluleDroite = "+celluleDroite);

					while ( celluleDroite <= 3 && ligne.get(celluleDroite) == 0 )
					{
						ligne.set(celluleDroite, ligne.get(celluleDroite-1)) ;
						ligne.set(celluleDroite-1, 0) ;
						
						celluleDroite++;
						
					}

					/* si la cellule n'est pas arriv� au bout, c'est parce qu'il a �t� bloqu�
					 * par une autre cellule. Dans ce cas, possibilit� de fusion.
					 */
					if ( celluleDroite != 4 )
					{
						// Si les 2 sont �gales, elles fusionnent
						if (ligne.get(celluleDroite-1) == ligne.get(celluleDroite))
						{
							ligne.set(celluleDroite-1, 0);
							ligne.set(celluleDroite, ligne.get(celluleDroite)*2);
						}
					}
					
					possible = true ;

				}
			}
		}
		
		return possible ;
	}
	
	public boolean haut()
	{
		return true;
	}
	
	public boolean bas()
	{
		return true;
	}
	
	
	public String toString()
	{
		String s = "" ;
		
		for( ArrayList<Integer> ligne : plateau ) // pour toutes les lignes
		{
			for ( int cellule : ligne ) // pour toutes les cellules de la ligne
				s += cellule+" ";
			
			s += "\n" ;
		}
		
		return s ;
	}
	
	public static void main(String[] args)
	{
		Plateau p = new Plateau() ;
		System.out.println(p);
			
		System.out.println(p.gauche()) ;
		System.out.println(p);
		
		System.out.println(p.gauche()) ;
		System.out.println(p);
		
		System.out.println(p.gauche()) ;
		System.out.println(p);
	}

}
