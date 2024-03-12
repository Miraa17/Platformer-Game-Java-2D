package gamestates;

import UI.MenuButton;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods
{
    private MenuButton[] buttons=new MenuButton[3];
    private BufferedImage backgroundMenu, bgg_setting;
    private int menuX, menuY, menuWidth,menuHeight;
    public Menu(Game game)
    {
        super(game);
        loadButtons();
        loadBackground();
        bgg_setting=LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_SETT);
    }

    private void loadBackground()
    {
        backgroundMenu= LoadSave.GetSpriteAtlas(LoadSave.MENU_WINDOW);
        menuWidth=(int)(backgroundMenu.getWidth()*1f);
        menuHeight=(int)(backgroundMenu.getHeight()*1f);
        menuX=626-menuWidth/2;
        menuY=(int)(120*2f);

    }



    private void loadButtons()
    {
        buttons[0]=new MenuButton(626,(int)(150*2f),0,GameState.PLAYING);
        buttons[1]=new MenuButton(626,(int)(200*2f),1,GameState.QUIT);
        buttons[2]=new MenuButton(626,(int)(250*2f),2,GameState.OPTIONS);
    }

    @Override
    public void update()
    {
        for(MenuButton mb: buttons)
            mb.update();

    }

    @Override
    public void draw(Graphics g)
    {
        g.drawImage(bgg_setting,0,0,1280, 800,null);
        g.drawImage(backgroundMenu,menuX,menuY,menuWidth,menuHeight,null);
        for(MenuButton mb: buttons)
            mb.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        for(MenuButton mb: buttons)
        {
            if(isIn(e,mb))
            {
                mb.setMousePressed(true);
                break;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        for(MenuButton mb: buttons)
        {
            if(isIn(e,mb))
            {
                if(mb.isMousePressed())
                    mb.applyGameState();
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons()
    {
        for(MenuButton mb: buttons)
            mb.resetBools();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        for (MenuButton mb : buttons)
            if (isIn(e, mb))
            {
                mb.setMouseOver(true);
                break;
            }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
            GameState.state=GameState.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
