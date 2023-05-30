package pt.isec.pa.a2019128044.tinypac.ui.text;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TerminalSize;

import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngine;
import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Elements;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;

import java.io.IOException;

public class TextUI implements IGameEngineEvolve {

    GameContext context;
    Screen screen;

    public TextUI(GameContext context) throws IOException {
        this.context = context;
        screen = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(80, 40)).createScreen();
        screen.setCursorPosition(null);

        show();
    }

    public void show() throws IOException {

        char[][] level = context.getLevel();
        screen.startScreen();

        System.out.println();


        for (int y = 0; y < level.length; y++) {
            for (int x = 0; x < level[y].length; x++) {

                Elements elements = Elements.getElement(level[y][x]);
                TextColor tc = switch (elements) {
                    case WALL -> TextColor.ANSI.BLUE;
                    case WARP -> TextColor.ANSI.GREEN;
                    case SMALLBALL -> TextColor.ANSI.YELLOW;
                    case FRUITSPAWN -> TextColor.ANSI.RED;
                    case PACMANSPAWN -> TextColor.ANSI.BLACK;
                    case POWERUP -> TextColor.ANSI.YELLOW_BRIGHT;
                    case PORTAL -> TextColor.ANSI.BLUE_BRIGHT;
                    case CAVERN -> TextColor.ANSI.BLACK;
                    case BLINKY -> TextColor.ANSI.RED_BRIGHT;
                    case CLYDE -> TextColor.ANSI.RED;
                    case INKY -> TextColor.ANSI.CYAN;
                    case PINKY -> TextColor.ANSI.MAGENTA;
                    case PACMAN -> TextColor.ANSI.YELLOW;
                    case EMPTY -> TextColor.ANSI.BLACK;
                };
                TextColor bc = switch (elements) {
                    case WALL -> TextColor.ANSI.BLUE;
                    case WARP -> TextColor.ANSI.GREEN;
                    case SMALLBALL -> TextColor.ANSI.BLACK;
                    case FRUITSPAWN -> TextColor.ANSI.RED;
                    case PACMANSPAWN -> TextColor.ANSI.BLACK;
                    case POWERUP -> TextColor.ANSI.BLACK;
                    case PORTAL -> TextColor.ANSI.BLUE_BRIGHT;
                    case CAVERN -> TextColor.ANSI.BLACK;
                    case BLINKY -> TextColor.ANSI.RED;
                    case CLYDE -> TextColor.ANSI.RED_BRIGHT;
                    case INKY -> TextColor.ANSI.CYAN_BRIGHT;
                    case PINKY -> TextColor.ANSI.MAGENTA_BRIGHT;
                    case PACMAN -> TextColor.ANSI.YELLOW_BRIGHT;
                    case EMPTY -> TextColor.ANSI.BLACK;
                };
                screen.setCharacter(x, y, TextCharacter.fromCharacter(level[y][x], tc, bc)[0]);
            }
        }
        screen.refresh();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        System.out.printf("[%d]\n",currentTime);

        try {
            show();
            KeyStroke keyStroke = screen.pollInput();
            if (keyStroke == null) {
                return;
            }
            switch (keyStroke.getKeyType()) {
                case ArrowUp -> context.pressKey(KEYPRESS.UP);
                case ArrowDown -> context.pressKey(KEYPRESS.DOWN);
                case ArrowLeft -> context.pressKey(KEYPRESS.LEFT);
                case ArrowRight -> context.pressKey(KEYPRESS.RIGHT);
                case Escape -> context.pressKey(KEYPRESS.ESC);
                default -> {
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
