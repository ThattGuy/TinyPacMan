package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

public enum Elements {
    WALL('x'),
    WARP('W'),
    SMALLBALL('o'),
    FRUITSPAWN('F'),
    PACMANSPAWN('M'),
    POWERUP('O'),
    PORTAL('Y'),
    CAVERN('y'),
    BLINKY('B'),
    CLYDE('C'),
    INKY('I'),
    PINKY('R'),
    PACMAN('P'),
    GHOST('v'),
    FRUIT('f'),
    EMPTY(' ');

    private final char value;

    Elements(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }


    public static Elements getElement(char value) {
        switch (value) {
            case 'x':
                return Elements.WALL;
            case 'W':
                return Elements.WARP;
            case 'o':
                return Elements.SMALLBALL;
            case 'F':
                return Elements.FRUITSPAWN;
            case 'M':
                return Elements.PACMANSPAWN;
            case 'O':
                return Elements.POWERUP;
            case 'Y':
                return Elements.PORTAL;
            case 'y':
                return Elements.CAVERN;
            case 'B':
                return Elements.BLINKY;
            case 'C':
                return Elements.CLYDE;
            case 'I':
                return Elements.INKY;
            case 'R':
                return Elements.PINKY;
            case 'P':
                return Elements.PACMAN;
            case ' ':
                return Elements.EMPTY;
            case 'v':
                return Elements.GHOST;
            case 'f':
                return Elements.FRUIT;
            default:
                throw new IllegalArgumentException("Invalid element value: " + value);
        }
    }
}