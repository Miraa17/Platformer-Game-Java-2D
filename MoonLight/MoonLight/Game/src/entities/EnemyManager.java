package entities;

import Levels.Level;
import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static Grahpics.Status.EnemyConstants.*;

public class EnemyManager
{
    private Playing playing;
    private BufferedImage[][] inamicSprite;
    private ArrayList<Inamic1> inamici= new ArrayList<>();
    public EnemyManager(Playing playing)
    {
        this.playing=playing;
        loadEnemyImg();
    }

    public void loadEnemies(Level level)
    {
        inamici=level.getInamici();
    }

    public void update(int[][] lvlData, Player player)
    {
        boolean isAnyActive = false;
        for (Inamic1 c : inamici)
            if(c.isActiv()) {
                isAnyActive = true;
                c.update(lvlData, player);
            }

        if (!isAnyActive){
            playing.setLevelComplete(true);
        }
    }

    public void draw(Graphics g, int xLvlOffset)
    {
        drawInamic(g,xLvlOffset);
    }

    private void drawInamic(Graphics g, int xLvlOffset) {
        for (Inamic1 c : inamici)
        if(c.isActiv())
        {
            g.drawImage(inamicSprite[c.getEnemyState()][c.getAnimationIndex()], (int) c.getHitBox().x - xLvlOffset - INAMIC1_DrawOffsetX, (int) c.getHitBox().y - INAMIC1_DrawOffsetY, (int) (64 * 1.2f), (int) (64 * 1.2f), null);
            c.drawCutieInamic(g, xLvlOffset);
        }
    }

    public void checkEnemyHit(Rectangle2D.Float cutieInamic)
    {
        for(Inamic1 i:inamici)
            if(i.isActiv())
            if(cutieInamic.intersects(i.getHitBox()))
            {
                i.hurt(10);
                return;
            }

    }
    private void loadEnemyImg()
    {
        inamicSprite=new BufferedImage[4][6];
        BufferedImage temp= LoadSave. GetSpriteAtlas(LoadSave.ENEMY1);
        for(int j=0; j<inamicSprite.length;j++)
            for(int i=0; i<inamicSprite[j].length;i++)
                inamicSprite[j][i]=temp.getSubimage(i*32,j*32,32,32);


    }
    public void resetAllEnemies() {
        for (Inamic1 c : inamici)
            c.resetEnemy();
    }
}
