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
        return switch (value) {
            case 'x' -> Elements.WALL;
            case 'W' -> Elements.WARP;
            case 'o' -> Elements.SMALLBALL;
            case 'F' -> Elements.FRUITSPAWN;
            case 'M' -> Elements.PACMANSPAWN;
            case 'O' -> Elements.POWERUP;
            case 'Y' -> Elements.PORTAL;
            case 'y' -> Elements.CAVERN;
            case 'B' -> Elements.BLINKY;
            case 'C' -> Elements.CLYDE;
            case 'I' -> Elements.INKY;
            case 'R' -> Elements.PINKY;
            case 'P' -> Elements.PACMAN;
            case ' ' -> Elements.EMPTY;
            case 'v' -> Elements.GHOST;
            case 'f' -> Elements.FRUIT;
            default -> throw new IllegalArgumentException("Invalid element value: " + value);
        };
    }
}