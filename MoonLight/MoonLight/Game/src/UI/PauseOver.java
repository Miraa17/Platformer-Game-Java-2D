package UI;

import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import gamestates.GameState;

public class PauseOver
{
    private Playing playing;
    private BufferedImage background;
    private UrmButton menuButton, replayButton, unpauseButton;
    private int bgX,bgY,bgW,bgH;
    public PauseOver(Playing playing)
    {
        this.playing=playing;
        loadBackground();
        createUrmButtons();
    }

    private void createUrmButtons()
    {
        int menuX = (int) (240 * 2f);
        int replayX = (int) (302 * 2f);
        int unpauseX = (int) (364 * 2f);
        int bY = (int) (138 * 2f);

        menuButton = new UrmButton(menuX, bY, (int)(56*2f), (int)(56*2f) , 2);
        replayButton = new UrmButton(replayX, bY, (int)(56*2f), (int)(56*2f), 1);
        unpauseButton = new UrmButton(unpauseX, bY, (int)(56*2f), (int)(56*2f), 0);
    }

    private void loadBackground()
    {
        background= LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW=background.getWidth();
        bgH=background.getHeight();
        bgX=626-bgW/2;
        bgY=120;


    }

    public void update()
    {
        menuButton.update();
        replayButton.update();
        unpauseButton.update();
    }

    public void draw(Graphics g)
    {
        g.drawImage(background,bgX,bgY,bgW,bgH,null);

        menuButton.draw(g);
        replayButton.draw(g);
        unpauseButton.draw(g);
    }


    public void mousePressed(MouseEvent e)
    {
        if (isIn(e, menuButton))
            menuButton.setMousePressed(true);
        else if (isIn(e, replayButton))
            replayButton.setMousePressed(true);
        else if (isIn(e, unpauseButton))
            unpauseButton.setMousePressed(true);
    }


    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuButton))
        {
            if (menuButton.isMousePressed())
            {
                GameState.state = GameState.MENU;
                playing.unpauseGame();
            }

        } else if (isIn(e, replayButton))
        {
            if (replayButton.isMousePressed())
            {

            }
        } else if (isIn(e, unpauseButton))
        {
            if (unpauseButton.isMousePressed())
                playing.unpauseGame();
        }

        menuButton.resetBools();
        replayButton.resetBools();
        unpauseButton.resetBools();
    }


    public void mouseMoved(MouseEvent e){

        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);

        if (isIn(e, menuButton))
            menuButton.setMouseOver(true);
        else if (isIn(e, replayButton))
            replayButton.setMouseOver(true);
        else if (isIn(e, unpauseButton))
            unpauseButton.setMouseOver(true);

    }

    private boolean isIn(MouseEvent e, PauseButton b)
    {
        return b.getBounds().contains(e.getX(), e.getY());
    }



}
