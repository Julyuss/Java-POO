package modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import application.AppliLaby;
import javafx.scene.shape.Circle;

/**classe representant un robot evoluant a la recherche d'un intrus*/
public class Robot {
	/**position du robot*/
	Point point;
	/**direction  du robot*/
	private Direction direction;
	/**etat du robot*/
	private EtatRobot etat;	

	/**lien vers le terrain dans lequel se trouve le robot*/
	private Terrain terrain;
	/**taille du terrain*/
	private int taille;
	/**position de l'intrus*/
	private static Cellule posIntrus ;
	/** champ de perception d'un robot */
	private int champPerception;


	/**objet necessaire pour le tirage aleatoire de la prochaine direction*/
	private Random hasard;

	/**objet graphique associe au robot*/
	private Circle dessin;

	public Robot(){}

	/**construit un robot
	 * @param _x coordonee x initiale du robot
	 * @param _y coordonee y initiale du robot
	 * @param _terrain terrain ou se trouve le robot
	 */
	public Robot(int _x, int _y, Terrain _terrain)
	{
		point = new Point(_x, _y);
		terrain=_terrain;
		taille = terrain.getTaille();
		direction = Direction.randomDirection();
		etat = EtatRobot.CHERCHER;
		hasard = new Random();
	}

	/**active les actions du robot selon son etat*/
	public void evoluer()
	{
		switch(etat)
		{
		case CHERCHER: //recherche de nourriture
			direction = getBestDirection(); //s'orienter vers la case offrant le plus de nourriture, sinon le plus de ph�romone, sinon au hasard devant
			bougerVersDirection();
			break;
		case PISTER:
			break;
		}
	}


	/**recherche la direction a prendre.. 
	 * SI aucun intrus n est detecte, pour un comportement "realiste" le robot a un rayon de braquage de 45degres 
	 * @return si un intrus a ete detecte, retourne la direction vers l'intrus, sinon sinon retourne une des trois directions devant le robot
	 * */
	private Direction getBestDirection()
	{
		Direction bestDirection = direction;
		Cellule maPosIntrus = detecterIntrus();
		if(maPosIntrus != null &&  maPosIntrus !=posIntrus) posIntrus = maPosIntrus ;
		Direction []dirAlentours = null;
		if (posIntrus!=null) 
		{
			bestDirection = Direction.getDirectionFromVector((int)(posIntrus.getX()-point.getX()), (int)(posIntrus.getY()-point.getY()));
			if(bestDirection==null) return null;
			dirAlentours = bestDirection.get3Dir();		
		}
		else
		{
			dirAlentours = direction.get3Dir();
		}
			ArrayList<Direction> listeDir = possibleNextDirections(dirAlentours);			
			if(!listeDir.isEmpty())
			{
				if (posIntrus==null || (posIntrus!=null  && listeDir.size()<3))
				{
					int i = hasard.nextInt(listeDir.size());
					bestDirection = listeDir.get(i); 
				}
				else if (posIntrus!=null) bestDirection = listeDir.get(1);
			}
			else // si pas possible, faire demi-tour
				bestDirection = direction.getInverse();	
		
		return bestDirection;
	}

	/**cherche si l'intrus est dans le voisinage
	 * @return la cellule du voisinage ou se trouve l'intrus, null sinon*/
	private Cellule detecterIntrus() {
		Cellule posIntrus = null;
		Cellule[][]grille = terrain.getGrille();
		int i,j=0;
		for( i=-champPerception; i<=champPerception; i++)
		{
			int xi = point.x+i;
			if(xi<0 || xi>=taille) continue;
			for( j=-champPerception; j<=champPerception; j++)
			{
				int yi = point.y+j;
				if(yi<0 || yi>=taille) continue;
				if(grille[xi][yi].isIntrus())
				{
					posIntrus = grille[xi][yi];
					break;
				}
			}			
		}
		if(posIntrus!=null && posIntrus.getX()==point.getX() && posIntrus.getY()==point.getY() )
		{
			terrain.changerEtatIntrus(EtatIntrus.PERDU);
			System.out.println("PERDU !!!");
			//AppliLaby.littleCycle.stop();
		}
		return posIntrus;
	}


	/**retourne une liste de directions possibles vers des cases videsdans les directions donnees
	 * @param directions tableaux des drections dans lesquelles il faut tester si les cellules sont vides de robots
	 * @return une liste de directions possibles vers des cases vides de robots*/
	private ArrayList<Direction> possibleNextDirections(Direction []directions)
	{
		ArrayList<Direction> liste = new ArrayList<Direction>();
		for(Direction dir:directions)
		{
			Cellule cell = getNextCellule(dir);
			if(cell != null && !cell.isRobot() && !cell.isObstacle()  )
			{
				if(dir.ordinal()%2!=0)
				{
					Direction[] dir3 = dir.get3Dir();
					boolean ok = true;
					for(int i=0; i<3;i+=2)
					{
						cell = getNextCellule(dir3[i]);
						ok = ok && (cell != null && !cell.isRobot() && !cell.isObstacle() );
					}
					if(ok) liste.add(dir);
				}
				else	liste.add(dir);
			}
				
		}
		return liste;		
	}

	/**fait avancer le robot dans sa direction si la case devant existe et est non occupee*/
	private void bougerVersDirection()
	{
		Cellule cell = getNextCellule(direction);
		if(cell!=null && !cell.isRobot() && !cell.isObstacle()) 
		{
			Cellule[][] grille = terrain.getGrille();
			grille[point.x][point.y].setRobot(false);
			point.x = cell.getX();
			point.y = cell.getY();
			AppliLaby.moveRobot(this,  point);
			//			dessin.setCenterX((point.x+1) * pas);
			//			dessin.setCenterY((point.y+2) * pas);
			cell.setRobot(true);
			if(posIntrus!=null && posIntrus.getX()==point.getX() && posIntrus.getY()==point.getY() )
			{
				if(cell.isIntrus())
				{
					terrain.changerEtatIntrus(EtatIntrus.PERDU);
					System.out.println("PERDU !!!");
					//AppliLaby.littleCycle.stop();					
				}
				else posIntrus=null;
			}
		}
	}




	/**donne la prochaine case dans la direction donnée
	 * @param dir la direction
	 * @return la cellule voisine dans la direction donnée, null si aucune cellule*/
	private Cellule getNextCellule(Direction dir)
	{
		Cellule cell = null;
		if(dir!=null)
		{
			Point newPoint = dir.getNextPoint(point);
			if ((newPoint.x>=0 && newPoint.x < taille) && (newPoint.y>=0 && newPoint.y<taille))
			{
				Cellule[][] grille = terrain.getGrille();
				cell = grille[newPoint.x][newPoint.y];
			}
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
	
	public void setChampPerception(int _champPerception) {
		
		champPerception = _champPerception;
	}

}
