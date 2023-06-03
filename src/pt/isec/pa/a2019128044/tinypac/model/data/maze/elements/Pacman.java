package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

public class Pacman extends Element {
    public Pacman(Level level) {
        super('P', level);
    }

    @Override
    public void evolve(long currentTime) {

        Level.Position myPos = level.getPositionOf(this);

        Level.Position neighboorPosition = level.getNeighboorPosition(myPos, level.getDirection());

        Element neighboor = (Element)level.getElement(neighboorPosition);

        System.out.println("myPos: " + myPos);
        System.out.println("neighboor position:" + neighboorPosition + "neighboor element: " + neighboor.getSymbol());

        System.out.println("direction:" + level.getDirection());

        if (neighboor.isTransversable(this.getSymbol())) {
            IMazeElement oldPositionElement = new Empty(level);
            boolean moved = level.setElementPosition(this, neighboorPosition);
            if (moved) {
                level.setElementPosition(oldPositionElement, myPos);
            }
        }




        /*if (!(level.getElement(neighboorPosition) instanceof Portal) || !(level.getElement(neighboorPosition) instanceof Warp)) {

            IMazeElement oldPositionElement = new Empty(level);
            boolean moved = level.setElementPosition(this, neighboorPosition);

            System.out.println("neighboor" + level.getElement(neighboorPosition).getSymbol());

            //In case nextzone has a smallBall
            if (level.getElement(neighboorPosition) instanceof SmallBall) {
                points++;
            }

            //In case nextzone has a PowerUp
            if (level.getElement(neighboorPosition) instanceof PowerUp) {
                points += 5;
            }

            //In case nextzone is a fruitzone and is fruit
            if (level.getElement(neighboorPosition) instanceof FruitZone) {
                if (((FruitZone) level.getElement(neighboorPosition)).hasFruit()) {
                    points = fruitsEaten * 25;
                    fruitsEaten++;
                }
                oldPositionElement = new FruitZone(level);
            }

            //In case nextzone is a PacmanSpawn
            if (level.getElement(neighboorPosition) instanceof PacmanSpawn) {
                if (moved) {
                    level.setElementPosition(new PacmanSpawn(level), myPos);
                }
            }

            if (moved) {
                level.setElementPosition(oldPositionElement, myPos);
            }
        }*/
    }

}