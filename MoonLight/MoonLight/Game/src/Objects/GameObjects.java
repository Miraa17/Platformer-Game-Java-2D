package Objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameObjects
{
    protected int x,y,objType;
    protected Rectangle2D.Float hitBox;
    protected boolean doAnimation, activ=true;
    protected int animationTick=0, animationIndex=0;
    protected int xDrawOffset, yDrawOffset;

    public GameObjects(int x, int y, int objType)
    {
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= 15) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 3)
            {
                animationIndex = 0;
            }
        }
    }

    public void reset()
    {
        animationIndex = 0;
        animationTick = 0;
        activ = true;
        doAnimation = true;
    }
    protected void initHitbox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int) (width *2f), (int) (height * 2f));
    }

    public void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x - xLvlOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public int getObjType() {
        return objType;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public int getAniIndex() {
        return animationIndex;
    }
    public void setAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

}
