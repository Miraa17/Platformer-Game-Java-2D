package Objects;

import Levels.Level;
import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Grahpics.Status.GameObjects.COIN;
import static Grahpics.Status.GameObjects.STAR;

public class ObjectManager
{
    private Playing playing;
    private BufferedImage[][] moneyImages;
    private ArrayList<Money> money;
    private BufferedImage[] scoreImages;
    private ArrayList<Score> scores;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
        scores =new ArrayList<>();

        for (int i=1; i<=10; i++){
            scores.add(new Score(100+i*32,100,0));
        }

    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Money m : money)
            if (m.isActiv()) {
                if (hitbox.intersects(m.getHitBox())) {
                    m.setActiv(false);
                    m.setAnimation(false);
                    applyEffectToPlayer(m);
                }
            }
    }

    public void applyEffectToPlayer(Money m) {
        if (m.getObjType() == COIN)
        {
            playing.getPlayer().changeScore(15);
            System.out.println("am ajuns aici");
        }
        else if (m.getObjType() == STAR)
            playing.getPlayer().changeScore(30);
    }

      public void loadObjects(Level newLevel) {
        money = newLevel.getMoney();
    }

    private void loadImgs() {
        BufferedImage moneySprites = LoadSave.GetSpriteAtlas(LoadSave.COLECT);
        moneyImages = new BufferedImage[2][3];

        moneyImages[0][0] = moneySprites.getSubimage(0, 0, 32, 32);
        moneyImages[0][1] = moneySprites.getSubimage(32, 0, 32, 32);
        moneyImages[0][2] = moneySprites.getSubimage(64, 0, 32, 32);

        moneyImages[1][0] = moneySprites.getSubimage(0, 64, 32, 32);
        moneyImages[1][1] = moneySprites.getSubimage(32, 64, 32, 32);
        moneyImages[1][2] = moneySprites.getSubimage(64, 64, 32, 32);

        BufferedImage scoreSprites = LoadSave.GetSpriteAtlas(LoadSave.SCORE_IMAGES);
        scoreImages = new BufferedImage[10];

        for (int i=9; i>=0; i--){
            scoreImages[i] = scoreSprites.getSubimage(i * 32, 0, 32, 32);
        }

    }

    public void update() {
        for (Money m : money)
            if (m.isActiv())
                m.update();

    }
    public void draw(Graphics g, int xLvlOffset) {
        drawMoney(g, xLvlOffset);
        drawScore(g, xLvlOffset);

    }

    private void drawMoney(Graphics g, int xLvlOffset)
    {
        for (Money m : money)
            if (m.isActiv()) {
                try{
                    g.drawImage(
                            moneyImages[m.getObjType()][m.getAniIndex()],
                            (int) (m.getHitBox().x - m.getxDrawOffset() - xLvlOffset),
                            (int) (m.getHitBox().y - m.getyDrawOffset()),
                            (int)(16*2f), (int)(16*2f), null);
                }catch (Exception e){
                    int a = 0;
                }
            }
    }

    private void drawScore(Graphics g, int xLvlOffset)
    {
        int playerScore = playing.getPlayer().getScore();
        String playerScoreAsString = Integer.toString(playerScore);
        int maxScoreCharacters = 10;

        int numbersOfZeros = 10;
        if (playerScore != 0){
            numbersOfZeros = maxScoreCharacters - playerScoreAsString.length();
        }

        int a = 0;
        for (int i=0; i<10; i++){
            Score score = scores.get(i);
            if (i<numbersOfZeros){
                g.drawImage(scoreImages[9],
                        (int) (score.getHitBox().x - score.getxDrawOffset() - xLvlOffset),
                        (int) (score.getHitBox().y - score.getyDrawOffset()),
                        (int)(16*2f), (int)(16*2f), null);
            }else{
                int digit = Integer.parseInt(String.valueOf(playerScoreAsString.charAt(a)));
                BufferedImage scoreImage = null;
                if (digit == 0){
                    scoreImage = scoreImages[9];
                }else{
                    scoreImage = scoreImages[digit-1];
                }
                g.drawImage(scoreImage,
                        (int) (score.getHitBox().x - score.getxDrawOffset() - xLvlOffset),
                        (int) (score.getHitBox().y - score.getyDrawOffset()),
                        (int)(16*2f),
                        (int)(16*2f),
                        null);
                a++;

            }

        }

//        for (int i=9; i>=0; i--){
//            Score score = scores.get(i);
//            if (i>maxScoreCharacters-numbersOfZeros-1){
//                g.drawImage(scoreImages[9],
//                        (int) (score.getHitBox().x - score.getxDrawOffset() - xLvlOffset),
//                        (int) (score.getHitBox().y - score.getyDrawOffset()),
//                        (int)(16*2f), (int)(16*2f), null);
//            }else{
//                g.drawImage(scoreImages[Integer.parseInt(String.valueOf(playerScoreAsString.charAt(i)))],
//                        (int) (score.getHitBox().x - score.getxDrawOffset() - xLvlOffset),
//                        (int) (score.getHitBox().y - score.getyDrawOffset()),
//                        (int)(16*2f), (int)(16*2f), null);
//            }
//
//        }

    }

    public void resetAllObjects() {
        for (Money m : money)
            m.reset();
    }

    public ArrayList<Money> getMoney() {return money;}

}
