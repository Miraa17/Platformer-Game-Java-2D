package Levels;

import Objects.Money;
import entities.Inamic1;
import main.Game;
import utilz.Colision;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Grahpics.Status.EnemyConstants.INAMIC1;
import static Grahpics.Status.GameObjects.COIN;
import static Grahpics.Status.GameObjects.STAR;

public class Level {

    private BufferedImage lvlDataImg, background, lvlAtlas;
    private BufferedImage[] levelSprite;
    private int [][] lvlData;
    private ArrayList<Inamic1> inamici;
    private ArrayList<Money> money;
    private static int lvlIndex=0;
    public Level(BufferedImage lvlDataImg, BufferedImage background, BufferedImage lvlAtlas)
    {
        this.lvlDataImg =lvlDataImg;
        this.background=background;
        this.lvlAtlas=lvlAtlas;
        if(lvlIndex==0)
            importSprites0();
        else importSprites1();
        this.lvlData = LoadSave.GetLevelData(this.lvlDataImg);
        loadInamici();
        money = Colision.GetMoney(lvlDataImg);
        lvlIndex++;
    }

    public ArrayList<Money> getMoney() {
        return money;
    }

    public void setMoney(ArrayList<Money> money) {
        this.money = money;
    }

    private void importSprites0() {
        levelSprite=new BufferedImage[105]; //retinea matricea de tile-uri
        for(int j=0;j<7;j++)    //parcurg matricea cu tile-uri aferente nivelului 1
            for(int i=0;i<7;i++)
                levelSprite[i+j*7]=lvlAtlas.getSubimage(i*64, j*64, 64,64);//iau din matrice tile-ul corespunzator
    }
    private void importSprites1() {
        levelSprite=new BufferedImage[105]; //retinea matricea de tile-uri
        for(int j=0;j<6;j++)    //parcurg matricea cu tile-uri aferente nivelului 1
            for(int i=0;i<6;i++)
                levelSprite[i+j*6]=lvlAtlas.getSubimage(i*64, j*64, 64,64);//iau din matrice tile-ul corespunzator
    }

    public int getSpriteIndex(int x, int y)
    {
        return lvlData[y][x];//obtinerea pozitiei corespunzatoare din matricea hartii
    }
    public int[][] getLevelData()
    {
        return lvlData; //returnez matricea hartii
    }

    public void loadInamici()
    {
        ArrayList<Inamic1> list = new ArrayList<>();
        for(int j = 0; j< lvlDataImg.getHeight(); j++)
            for(int i = 0; i< lvlDataImg.getWidth(); i++){
                Color color=new Color(lvlDataImg.getRGB(i,j));
                int value=color.getGreen();
                if(value==INAMIC1)
                    list.add(new Inamic1(i*64,j*64));
            }
        this.inamici = list;
    }

    public ArrayList<Inamic1> getInamici(){
        return this.inamici;
    }
    public BufferedImage getBackground(){
        return background;
    }
    public BufferedImage[]getLevelSprite(){
        return levelSprite;
    }

    //public ArrayList<Money> getMoney() {
      //  return moneyy;
    //}

}