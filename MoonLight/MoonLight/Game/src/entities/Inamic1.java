package entities;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static Grahpics.Status.Direction.LEFT;
import static Grahpics.Status.EnemyConstants.*;
import static utilz.Colision.*;

public class Inamic1 extends Enemy
{

    //hitBoxArma
    private Rectangle2D.Float cutieInamic;
    private int cutieOffsetX;

    public Inamic1(float x, float y) {
        super(x, y, (int)(32*2f), (int)(32*2f), INAMIC1);
        initHitBox(x,y,(int)(15*1.0f),(int)(30*1.0f));
        initCuitieInamic();
    }

    private void initCuitieInamic()
    {
        cutieInamic=new Rectangle2D.Float(x,y,(int)(30*2f),(int)(25*2f));
        cutieOffsetX=(int)(12*2f);
    }

    public void update(int[][] lvlData, Player player) {
        updateMove(lvlData, player);
        updateAnimationTick();
        updateCutieInamic();
    }

    private void updateCutieInamic()
    {
        cutieInamic.x= hitBox.x-cutieOffsetX;
        cutieInamic.y=hitBox.y;
    }

    private void updateMove(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);
        if (inAir)
            updateInAir(lvlData);

        else
            {
            switch (enemyState)
            {
                case IDLE:
                    newState(WALK);
                    //enemyState = WALK;
                    break;
                case WALK:
                    //if(canSeePlayer(lvlData,player))
                        //turnTowardsPlayer(player);
                    //if(isPlayerCloseForAttack(player))
                        //newState(ATTACK);

                    move(lvlData);
                    break;
                /*case ATTACK:
                    if(animationIndex==0)
                        atackCheck=false;
                    if(animationIndex==2 && !atackCheck)
                        checkPlayerHit(cutieInamic,player);
                    break;*/
                case HURT:
                    break;
            }
        }

    }

    public void drawCutieInamic(Graphics g, int xlvlOffset)
    {
        g.setColor(Color.red);
        //g.drawRect((int)cutieInamic.x-xlvlOffset,(int)cutieInamic.y,(int)cutieInamic.width,(int)cutieInamic.height);
    }
}
