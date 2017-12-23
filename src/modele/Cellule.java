package modele;
/**
 * cette classe represente une cellule de la simulation de fourmis
 * @author  Emmanuel Adam
 */
public class Cellule  
{
	/**coordonnee de la cellule dans la grille*/ 
	private int x,y;
	/**cellulle perÃ§ue par l'intrus (donc affichÃ©e) ou non*/
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
	// défini une opacité
	private double opacity;

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

	public void setVisitee(boolean _visitee) {
		visitee = _visitee;
	}

	public boolean isTresor() {
		return tresor;
	}

	public void setTresor(boolean tresor) {
		this.tresor = tresor;
	}
	
	public void setOpacity(boolean _opacity) {
		if (_opacity) {
			this.opacity = 1.0;
			this.visitee = true;
		} else if (opacity>0){
			this.opacity = this.opacity - 1.0;
		} else
			this.visitee = false;
	}
}