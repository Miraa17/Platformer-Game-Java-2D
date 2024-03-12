package utilz;

import Objects.Money;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Color;

import static Grahpics.Status.GameObjects.*;

public class Colision {

    //returneaza true daca obiectul poate fi mutat in pozitia corespunzatoare, false in caz negativ
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    //functia verifica coliziunile, ea primeste coordonatele punctului inn care se doreste verificarea, precum si matricea cu harta
    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int levelWidth = lvlData[0].length * 64;
        int levelHeight = lvlData.length * 63;
        //verificam daca punctul se afla in interioru zonei de joc
        if (x < 0 || x >= levelWidth)
            return true;
        if (y < 0 || y >= levelHeight)
            return true;
    //calculez casuta din matricea cu harta, conform punctului specificat
        int tileX = (int) (x / 64);
        int tileY = (int) (y / 64);

        return IsTileSolid((int) tileX, (int) tileY, lvlData);
    }

    public static boolean IsTileSolid(int tileX, int tileY, int[][] lvlData)
    {
        int tileValue = lvlData[tileY][tileX];
//        System.out.println(tileValue+" ");
        /*
        for(int i=0;i<lvlData[0].length;i++){
            for(int j=0;j<20;j++)
                System.out.print(lvlData[i][j]+" ");
            System.out.print("\n");
        }*/

        if (tileValue <40 && tileValue >= 0) {
            return true;
        }

        return false;
    }


    public static float GetEntityPosNextToWall(Rectangle2D.Float hitBox, float xspeed)
    {
        int currentTile=(int)(hitBox.x/64);
        if(xspeed>0)
        {
            //Right
            int tileXPosition=currentTile*64;
            int xOffset=(int)(64-hitBox.width);
            return tileXPosition + xOffset - 1;
        }
        else

            //Left
            return currentTile*64;

    }

    public static float GetEntityPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed)
    {
        int currentTile=(int)(hitBox.y/64);
        if(airSpeed>0)
        {
            //falling-touch the floor
            int tileYPos=currentTile*64;
            int yOffset=(int)(64-hitBox.height);
            return tileYPos+yOffset-1;
        }
        else

            //jumping
            return currentTile*64;

    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][]lvlData)
    {
        if(!IsSolid(hitBox.x,hitBox.y + hitBox.height+1, lvlData))
            if(!IsSolid(hitBox.x + hitBox.width,hitBox.y + hitBox.height+1, lvlData))
                return false;
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitBox, float xSpeed, int[][]lvlData)
    {
        return IsSolid(hitBox.x+xSpeed, hitBox.y+ hitBox.height+1, lvlData);
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
            if (!IsTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }

        return true;
    }
    public static boolean IsSightClear(int[][]lvlData, Rectangle2D.Float firstHitBox, Rectangle2D.Float secondHitBox, int tileY)
    {
        int firstXTile = (int) (firstHitBox.x / 64);
        int secondXTile = (int) (secondHitBox.x / 64);

        if (firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, tileY, lvlData);
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, tileY, lvlData);

    }
    public static ArrayList<Money> GetMoney(BufferedImage img) {
        ArrayList<Money> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == COIN_VALUE)
                    list.add(new Money(i * 64, j * 64, COIN));
                else if (value == STAR_VALUE)
                    list.add(new Money(i * 64, j * 64, STAR));
            }

        return list;
    }
}
