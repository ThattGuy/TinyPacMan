package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

public class Pacman extends Element {
    private long lastMovedTime = 0;
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
        Level.Position myPos = level.getPositionOf(this);

        Level.Position neighboorPosition = level.getNeighboorPosition(myPos, level.getDirection());

        Element neighboor = (Element) level.getElement(neighboorPosition);

            if (!(level.getElement(neighboorPosition) instanceof Portal || level.getElement(neighboorPosition) instanceof Warp)) {

                System.out.println("points:" + level.get);

                IMazeElement oldPositionElement = new Empty(level);
                boolean moved = level.setElementPosition(this, neighboorPosition);

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
                        fruitsEaten *= 25;
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

        if (neighboor.isTransversable(this.getSymbol())){

        }
    }
}