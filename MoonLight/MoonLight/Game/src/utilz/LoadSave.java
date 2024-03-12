package utilz;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import entities.Inamic1;

import static Grahpics.Status.EnemyConstants.INAMIC1;

public class LoadSave {
    public static final String LEVEL_ATLAS = "spritesheet.png"; //Incarc tiluri-le nivelului 1
    public static final String MENU_BUTTONS = "Buttons.png";//incarc imaginea cu butoanele pentru setari

    public static final String MENU_WINDOW = "menu_window.png";//incarc imaginea cadrului pentru meniu;

    public static final String PAUSE_BACKGROUND = "textures/grd.png";
    public static final String URM_BUTTONS = "textures/urm_buttons.png";
    public static final String BACKGROUND_SETT = "textures/Bg_setting.png";

    public static final String BACKGROUND_Lvl1 = "textures/BackLvl1.png";
    public static final String VIATA = "textures/viata.png";
    public static final String LEVELUP = "textures/LevelUp.png";
    public static final String COLECT = "textures/money.png";
    public static final String SCORE_IMAGES = "textures/score_numbers.png";

    public static final String ENEMY1 = "enemy_1.png";

    //incarca imaginea cu tiluri-le specifice nivelului 1 din fisier si converteste datele din imagine intr-o matrice
    public static BufferedImage GetSpriteAtlas(String filename) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] GetAllLevelsData() {
        URL url = LoadSave.class.getResource("/lvls/data");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] sortedFiles = new File[files.length];

//        System.out.println("??????????????");
//        System.out.println(files);
//        System.out.println("??????????????");

        for (int i = 0; i <= files.length - 1; i++) {
            for (int j = 0; j <= files.length - 1; j++) {
                if (files[j].getName().equals((i + 1) + ".png")) {
                    sortedFiles[i] = files[j];
                }
            }
        }
        BufferedImage []imgs=new BufferedImage[sortedFiles.length];
        for(int i=0;i<imgs.length;i++){
            try{
                imgs[i]=ImageIO.read(sortedFiles[i]);
            }catch(IOException e){
                throw new RuntimeException(e);
            }
        }
        return imgs;
    }


        public static BufferedImage[] GetAllLevelsBackground() {
            URL url = LoadSave.class.getResource("/lvls/background");
            File file = null;

            try {
                file = new File(url.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            File[] files = file.listFiles();
            File[] sortedFiles = new File[files.length];

//            System.out.println("??????????????");
//            System.out.println(files);
//            System.out.println("??????????????");

            for (int i = 0; i <= files.length - 1; i++) {
                for (int j = 0; j <= files.length - 1; j++) {
                    if (files[j].getName().equals((i + 1) + ".png")) {
                        sortedFiles[i] = files[j];
                    }
                }
            }BufferedImage []imgs=new BufferedImage[sortedFiles.length];
            for(int i=0;i<imgs.length;i++){
                try{
                    imgs[i]=ImageIO.read(sortedFiles[i]);
                }catch(IOException e){
                    throw new RuntimeException(e);
                }
            }
            return imgs;}
            public static BufferedImage[] GetAllLevelsAtlas () {
                URL url = LoadSave.class.getResource("/lvls/atlas");
                File file = null;

                try {
                    file = new File(url.toURI());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                File[] files = file.listFiles();
                File[] sortedFiles = new File[files.length];

//                System.out.println("??????????????");
//                System.out.println(files);
//                System.out.println("??????????????");

                for (int i = 0; i <= files.length - 1; i++) {
                    for (int j = 0; j <= files.length - 1; j++) {
                        if (files[j].getName().equals((i + 1) + ".png")) {
                            sortedFiles[i] = files[j];
                        }
                    }
                }

                BufferedImage[] images = new BufferedImage[files.length];

                for (int i = 0; i <= files.length - 1; i++) {
                    try {
                        images[i] = ImageIO.read(files[i]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return images;
            }


    //incarca harta->se parcurge fiecare pixel din imagine si se extrage valoarea de culoare pentru a afla valoarea din matrice
    public static int[][] GetLevelData(BufferedImage img){
        int[][] lvlData=new int[img.getHeight()][img.getWidth()];//[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];

        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color=new Color(img.getRGB(i,j));
                int value=color.getRed();//extragerea val. canalui rosu(0-47)
                if(value<=0 || value>=47)//test daca sunt in afara hartii
                    value=47;
                lvlData[j][i]=value;//val. canalului rosu e convertita in intreg si este stocata in matricea lvlData
            }
        //parcurg si afisez matricea
//        for(int i=0;i<12;i++){
//            for(int j=0;j<20;j++)
//                System.out.print(lvlData[i][j]+" ");
//            System.out.print("\n");
//        }

        return lvlData;
    }
}
