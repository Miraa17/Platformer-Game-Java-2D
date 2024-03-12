package UI;

import gamestates.Playing;

import java.awt.*;
import java.awt.event.KeyEvent;
import gamestates.GameState;

public class GameOver
{
    private Playing playing;

    public GameOver(Playing playing)
    {
        this.playing = playing;
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(0, 0, 3840, 800);

        g.setColor(Color.white);
        g.drawString("Game Over", 3840/ 2, 150);
        g.drawString("Press esc to enter Main Menu!", 3840 / 2, 300);

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameState.state = GameState.MENU;
        }
    }
}
