package entities;
import java.awt.*;
import java.awt.Rectangle;

import java.awt.Color;
import java.awt.geom.Rectangle2D;


//Clasa abstracta care reprezunta o entitate din joc
public abstract class Entity {
    protected float x, y;//pozitia entitatii
    protected int width, height;//dimensiunile entitatii
    protected Rectangle2D.Float hitBox;//forma rectangulara pentru detectarea coliziunilor


    //Constructorul clasei->utilizat pentru initializarea variabilelor
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    //Metoda utilizata pentru a desena dreptunghiul/hitBoxul entitatii
    protected void drawHitBox(Graphics g,int xLvlOffset)
    {
        g.setColor(Color.PINK);//setarea culorii dreptunghiului
        g.drawRect((int)hitBox.x-xLvlOffset,(int)hitBox.y, (int)hitBox.width, (int)hitBox.height);//desenarea efectiva
    }

    //Metoda pentru initializarea cutiei la pozitia initiala a entitatii
    protected void initHitBox(float x, float y, float width, float height)
    {
        hitBox=new Rectangle2D.Float(x,y,width,height);//creare dreptunghi cu pozitia si dimensiunile entitatii
    }

    //actualizarea pozitiei hitBoxului cu cea a entitatii
   // protected void updateHitBox()
    //{
      //  hitBox.x=(int)x;
        //hitBox.y=(int)y;
    //}


    //Returnarea hitBoxului entitatii
    public Rectangle2D.Float getHitBox()
    {
        return hitBox;
    }
}


