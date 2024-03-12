package UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import utilz.LoadSave;

public class LevelComplete
{
    private Playing playing;
    private UrmButton menu,next;
    private BufferedImage img;
    private int bgX, bgY, bgW,bgH;

    public LevelComplete(Playing playing)
    {
        this.playing=playing;
        initImg();
        initButtons();

    }

    private void initButtons()
    {
        int menuX = (int) (280 * 2f);
        int nextX= (int) (320 * 2f);
        int y = (int) (140 * 2f);


        next= new UrmButton(nextX, y, (int)(56*2f), (int)(56*2f) , 0);
        menu= new UrmButton(menuX, y, (int)(56*2f), (int)(56*2f), 2);
    }

    private void initImg()
    {
        img= LoadSave.GetSpriteAtlas(LoadSave.LEVELUP);
        bgW=img.getWidth();
        bgH=img.getHeight();
        bgX=626-bgW/2;
        bgY=120;
    }

    public void update()
    {
        next.update();
        menu.update();
    }

    private boolean IsIn(UrmButton button,MouseEvent e)
    {
        return button.getBounds().contains(e.getX(),e.getY());
    }

    public void draw(Graphics g)
    {
        g.drawImage(img,bgX,bgY,bgW,bgH,null);
        next.draw(g);
        menu.draw(g);
    }

    public void mouseMoved(MouseEvent e)
    {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if(IsIn(menu,e))
            menu.setMouseOver(true);
        else if(IsIn(next,e))
            next.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e)
    {
        if(IsIn(menu,e)) {
            if (menu.isMousePressed()) {
//                System.out.println("menu");
                playing.resetAll();
                GameState.state = GameState.MENU;
            }
        }else if(IsIn(next,e))
                if(next.isMousePressed()) {
//                    System.out.println("next");
                    playing.loadNextLevel();
                }

                menu.resetBools();
                next.resetBools();
    }
    public void mousePressed(MouseEvent e)
    {
        if(IsIn(menu,e))
            menu.setMousePressed(true);
        else if(IsIn(next,e))
            next.setMousePressed(true);
    }

}
