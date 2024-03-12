package Levels;

import gamestates.GameState;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


//Gestionarea nivelelor de joc
public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private BufferedImage background;
    private ArrayList<Level> levels;
    private int levelIndex = 0;


    //Constructorul
    public LevelManager(Game game)
    {
        this.game=game;
        //levelSprite= LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);

        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel(){
        levelIndex++;
        if (levelIndex >= levels.size()){
            levelIndex = 0;
            GameState.state = GameState.MENU;
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvData(newLevel.getLevelData());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    public void buildAllLevels(){
        BufferedImage[] allLevelsData = LoadSave.GetAllLevelsData();
        BufferedImage[] allLevelsBackground = LoadSave.GetAllLevelsBackground();
        BufferedImage[] allLevelsAtlas = LoadSave.GetAllLevelsAtlas();
        for (int i=0;i<3;i++) {
            Level level = new Level(allLevelsData[i], allLevelsBackground[i], allLevelsAtlas[i]);
            levels.add(level);
        }


    }


    //incarc in buffer imaginea cu tile-uri si o stochez intr-un tablou bidimensional


    //Desenarea nivelului curent
    public void draw(Graphics g, int lvlOffset)
    {
        g.drawImage(levels.get(levelIndex).getBackground(),0,0,1280,800,null);
        //g.drawImage(levelSprite[0],0,0,64,64,null);
        for(int j=0;j<800/64;j++)//parcurg imaginea/matricea cu harta primului nivel
            for(int i=0;i<levels.get(levelIndex).getLevelData()[0].length;i++){
                int index=levels.get(levelIndex).getSpriteIndex(i,j);//extrag pozitia corespunzatoare
                g.drawImage(levels.get(levelIndex).getLevelSprite()[index],i*64-lvlOffset, j*64,64,64,null);//desenez tile-ul corespunzator pozitiei din matricea hartii nivelului curent
            }

    }
    public void update(){

    }
    public Level getCurrentLevel(){
        return levels.get(levelIndex);
    }

    public int getAmountOfLevels() {
            return levels.size();
    }

}
