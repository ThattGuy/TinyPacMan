package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

public class Pacman extends Element {
    int points;
    int fruitsEaten;

    public Pacman(Level level) {
        super('P', level);
        points = 0;
        fruitsEaten = 0;
    }

    @Override
    public void evolve(long currentTime) {

        //if (currentTime - lastMovedTime >= 10) {
            //todo perguntar ao professor como ajustar o click do relogio para se mover mais do que uma vez por segundo
            Level.Position myPos = level.getPacmanPos();

            Level.Position neighboorPosition = level.getNeighboorPosition(myPos, level.getDirection());


        System.out.println("neighboor position:" + neighboorPosition + "\n direction: " + level.getDirection());

            if (!(level.getElement(neighboorPosition) instanceof Portal) || !(level.getElement(neighboorPosition) instanceof Warp)) {

                IMazeElement oldPositionElement = new Empty(level);
                boolean moved = level.setElementPosition(this, neighboorPosition);

                System.out.println("neighboor" + level.getElement(neighboorPosition).getSymbol());

                //In case nextzone has a smallBall
                if(level.getElement(neighboorPosition) instanceof SmallBall){
                    points++;
                }

                //In case nextzone has a PowerUp
                if(level.getElement(neighboorPosition) instanceof PowerUp){
                    points += 5;
                }

                //In case nextzone is a fruitzone and is fruit
                if(level.getElement(neighboorPosition) instanceof FruitZone){
                    if(((FruitZone) level.getElement(neighboorPosition)).hasFruit()){
                        points = fruitsEaten * 25;
                        fruitsEaten++;
                    }
                    oldPositionElement = new FruitZone(level);
                }

                //In case nextzone is a PacmanSpawn
                if(level.getElement(neighboorPosition) instanceof PacmanSpawn){
                    if (moved) {
                        level.setElementPosition(new PacmanSpawn(level), myPos);
                    }
                }

                if (moved) {
                    level.setElementPosition(oldPositionElement, myPos);
                }

            }
        }
      //  lastMovedTime = currentTime;
    //}


    public int getPoints() {
        return points;
    }
}