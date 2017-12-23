package modele;

import java.awt.Point;
import application.AppliLaby;
import javafx.scene.shape.Circle;

/**classe representant un robot evoluant a la recherche de nourriture a l'aide de pheromones*/
public class Intrus {
	/**position du robot*/
	Point point;
	/**direction  du robot*/
	private Direction direction;
	/**etat du robot*/
	private EtatIntrus etat;	

	/**lien vers le terrain dans lequel se trouve la  fourmi*/
	private Terrain terrain;
	/**taille du terrain*/
	private int taille;
	


	/**objet graphique associe a le robot*/
	private Circle dessin;

	public Intrus(){}

	/**construit un robot
	 * @param _x coordonee x initiale du robot
	 * @param _y coordonee y initiale du robot
	 * @param _terrain terrain ou se trouve le robot
	 */
	public Intrus(int _x, int _y, Terrain _terrain)
	{
		point = new Point(_x, _y);
		terrain=_terrain;
		taille = terrain.getTaille();
		etat = EtatIntrus.RECHERCHE;
	}



	/**fait avancer le robot dans sa direction si la case devant existe et est non occupee*/
	public void bougerVersDirection()
	{
		Cellule cell = getNextCellule(direction);
		if(cell!=null && !cell.isRobot() && !cell.isObstacle()) 
		{
			Cellule[][] grille = terrain.getGrille();
			grille[point.x][point.y].setIntrus(false);
			point.x = cell.getX();
			point.y = cell.getY();
			grille[point.x][point.y].setIntrus(true);
			AppliLaby.moveIntrus(this, point);
			
			cell.setIntrus(true);
			if(cell.isTresor())  etat = EtatIntrus.RETOUR;

			for(int k = 0; k < this.terrain.getTaille(); k++) {
				for(int l = 0; l < this.terrain.getTaille(); l++) {
					grille[k][l].setVisitee(false);
				}
			}
			for(int i=-2; i<=2; i++)
			{
				int xi = point.x+i;
				if(xi<0 || xi>=taille) continue;
				for(int j=-2; j<=2; j++)
				{
					int yj = point.y+j;
					if(yj<0 || yj>=taille) continue;
					grille[xi][yj].setVisitee(true);
				}
			}
			
		}
	}

	/**donne la prochaine case dans la direction donn�e
	 * @param dir la direction
	 * @return la cellule voisine dans la direction donn�e, null si aucune cellule*/
	private Cellule getNextCellule(Direction dir)
	{
		Cellule cell = null;
		Point newPoint = dir.getNextPoint(point);
		if ((newPoint.x>=0 && newPoint.x < taille) && (newPoint.y>=0 && newPoint.y<taille))
		{
			Cellule[][] grille = terrain.getGrille();
			cell = grille[newPoint.x][newPoint.y];
		}
		return cell;
	}	
	/**
	 * @return the dessin
	 */
	public Circle getDessin() {
		return dessin;
	}

	/**
	 * @param dessin the dessin to set
	 */
	public void setDessin(Circle dessin) {
		this.dessin = dessin;
	}


	public Point getPoint() {
		return point;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public EtatIntrus getEtat() {
		return etat;
	}

}
