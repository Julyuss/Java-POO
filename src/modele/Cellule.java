package modele;
/**
 * cette classe represente une cellule de la simulation de fourmis
 * @author  Emmanuel Adam
 */
public class Cellule  
{
	/**coordonnee de la cellule dans la grille*/ 
	private int x,y;
	/**cellulle perçue par l'intrus (donc affichée) ou non*/
	private boolean visitee;
	/**cellule d'un intrus*/
	private boolean intrus;
	/**presence d'au moins un robot*/
	private boolean robot;
	/**defini la cellule vient d'etre videe de son contenu (nourriture)*/
	private boolean emptyNow;
	/**defini si la cellule contient un obstacle ou non*/
	private boolean obstacle;
	/**defini si la cellule contient le tresor ou non*/
	private boolean tresor;
	// opacité d'une cellule
	private double opacite = 1;
	// defini si la cellule est une sortie
	private boolean sortie;


	/** reference a la grille des cellule*/
	static Cellule [][]grille;

	/**a change recemment*/
	public boolean hasJustChanged;

	/** constructeur par defaut, inutilise*/
	public Cellule(){}

	/** constructeur initialisant la grille, les coordonnees et la nature de la cellule*/
	public Cellule(Cellule [][] grille, int x, int y)
	{
		Cellule.grille = grille;
		this.x = x; this.y = y;
		hasJustChanged = true;
	}

	/**
	 * @return the hasJustChanged
	 */
	public boolean isHasJustChanged() {
		return hasJustChanged;
	}


	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the fourmis
	 */
	public boolean isRobot() {
		return robot;
	}

	/**
	 * @param fourmis the fourmis to set
	 */
	public void setRobot(boolean fourmis) {
		this.robot = fourmis;
		this.hasJustChanged = true;
	}


	/**
	 * @return the emptyNow
	 */
	public boolean isEmptyNow() {
		return emptyNow;
	}

	/**
	 * @param emptyNow the emptyNow to set
	 */
	public void setEmptyNow(boolean emptyNow) {
		this.emptyNow = emptyNow;
	}

	public boolean isObstacle() {
		return obstacle;
	}

	public void setObstacle(boolean obstacle) {
		this.obstacle = obstacle;
	}

	public boolean isIntrus() {
		return intrus;
	}

	public void setIntrus(boolean intrus) {
		this.intrus = intrus;
	}

	public boolean isVisitee() {
		return visitee;
	}

	public void setVisitee(boolean visitee) {
		this.visitee = visitee;
	}

	public boolean isTresor() {
		return tresor;
	}

	public void setTresor(boolean tresor) {
		this.tresor = tresor;
	}
	
	public void setOpacite(double _opacite) {
		
		opacite = _opacite;
	}
	
	public double getOpacite() {
		
		return opacite;
	}
	
	public void setSortie(boolean _sortie) {
		
		sortie = _sortie;
	}
	
	public boolean isSortie() {
		
		return sortie;
	}

}