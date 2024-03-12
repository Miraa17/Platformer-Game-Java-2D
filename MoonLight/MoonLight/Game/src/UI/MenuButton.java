package UI;

import gamestates.GameState;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.charset.Charset;

public class MenuButton
{
    private int xPosition, yPosition, rowIndex,index;
    private GameState state;
    private boolean mouseOver, mousePressed;

    private Rectangle bounds;

    private BufferedImage[] img;
    public MenuButton(int xPosition, int yPosition, int rowIndex, GameState state)
    {
        this.xPosition=xPosition;
        this.yPosition=yPosition;
        this.rowIndex=rowIndex;
        this.state=state;

        loadImg();
        initBounds();
    }

    private void initBounds()
    {
        bounds=new Rectangle(xPosition-(int)(50*2f)/2,yPosition,(int)(50*2f),(int)(28*2f) );
    }


    //incarc imaginea cu butoanele pentru setari
    private void loadImg()
    {

        img=new BufferedImage[2];
        BufferedImage temp= LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for(int i=0;i<img.length;i++)
            img[i]=temp.getSubimage(i*230, rowIndex*110,230,110);
    }

    //functie necesara desenarii butoanelor
    public void draw(Graphics g)
    {
        g.drawImage(img[index],xPosition-(int)(50*2f)/2,yPosition, (int)(50*2f),(int)(28*2f),null);
    }


    public void update()
    {
        index=0;
        if(mouseOver)
            index=1;
      // if(mousePressed)
        // index=2;
    }

    public boolean isMouseOver()
    {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver)
    {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed()
    {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed)
    {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void applyGameState()
    {
        GameState.state=state;
    }
    public void resetBools()
    {
        mouseOver=false;
        mousePressed=false;
    }


}
