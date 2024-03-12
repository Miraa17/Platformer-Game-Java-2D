package gamestates;

import Levels.LevelManager;

import Objects.ObjectManager;
import UI.GameOver;
import UI.LevelComplete;
import UI.PauseOver;
import entities.EnemyManager;
import entities.Player;
import main.Game;
import main.GameWindow;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class Playing extends State implements StateMethods
{
    private Player player; //instanta unui obiect de tip "Player" pentru a gestiona jucatorul
    private LevelManager levelManager; // instanta unui obiec de tip LevelManager pentru a gestiona nivelurile
    private LevelComplete levelComplete;
    private boolean lvlComplete = false;

    //Var pentru inamici
    private EnemyManager enemyManager;
    private ObjectManager objectManager;

    //Variabile->meniu
    private PauseOver pauseOver;
    private boolean paused=false;
    private GameOver gameOver;

    //Variabile utilizate in crearea camerei:
    private int xLvlOffset;
    private int leftBorder=(int)(0.2*64*(800/64));
    private int righttBorder=(int)(0.8*64*(800/64));
    private int lvlTilesWide= 60;
    private int maxTilesOffset=lvlTilesWide-(800/64);
    private int maxLvlOffset=maxTilesOffset*64;

    //incarcam background lvl 1
    BufferedImage backgroundLvl1;

    public Playing(Game game)
    {
        super(game);
        initClasses();
       // backgroundLvl1=LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_Lvl1);
        loadStartLevel();
    }

    //Metoda pentru initializarea obiectelor jocului
    private void initClasses()
    {
        levelManager=new LevelManager(game);//instantiez un obiect de tip levelManager
        objectManager = new ObjectManager(this);
        enemyManager=new EnemyManager(this);
        player=new Player(200,200,(int)(64),(int)(64),this);//instantiaza un obiect de tip Player
        player.loadLvData(levelManager.getCurrentLevel().getLevelData());//se incarca datele pentru nivelul curent
        pauseOver=new PauseOver(this);
        levelComplete=new LevelComplete(this);

    }

    public void loadNextLevel(){
        resetAll();
        levelManager.loadNextLevel();
        lvlComplete=false;
    }

    private void loadStartLevel()
    {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    public void setLevelComplete(boolean levelCompleted){
        this.lvlComplete = levelCompleted;
    }

    public void update()
    {
        if (paused){
            pauseOver.update();
        }else if (lvlComplete) {
            levelComplete.update();
        }else{
            levelManager.update();
            objectManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(),player);
            checkCloseToBorder();
        }

    }

    private void checkCloseToBorder()
    {
        int playerX=(int)player.getHitBox().x;
        int diff=playerX-xLvlOffset;

        if(diff>righttBorder)
            xLvlOffset+=diff-righttBorder;

        else if(diff<leftBorder)
            xLvlOffset+=diff-leftBorder;

        if(xLvlOffset>maxLvlOffset)
            xLvlOffset=maxLvlOffset;

        else if(xLvlOffset<0)
            xLvlOffset=0;
    }

    @Override
    public void draw(Graphics g)
    {
        //g.drawImage(backgroundLvl1,0,0,1280,800,null);
        levelManager.draw(g,xLvlOffset);
        player.render(g,xLvlOffset);
        enemyManager.draw(g,xLvlOffset);
        objectManager.draw(g, xLvlOffset);
        if(paused)
        {
            g.setColor(new Color(0,0,0,100));
            g.fillRect(0,0,64*(3840/64), 64*60);
            pauseOver.draw(g);
        }else if (lvlComplete){
            levelComplete.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(e.getButton()==MouseEvent.BUTTON1)
            player.setAttack(true);
    }


    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                player.setLeft(true);
                break;

            case KeyEvent.VK_RIGHT:
                player.setRight(true);
                break;

            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused=!paused;
                break;
                //GameState.state=GameState.MENU;
        }
    }

    public void resetAll()
    {
        paused = false;
        lvlComplete = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    public void checkEnemyHit(Rectangle2D.Float cutieInamic)
    {
        enemyManager.checkEnemyHit(cutieInamic);
    }

    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                player.setLeft(false);
                break;

            case KeyEvent.VK_RIGHT:
                player.setRight(false);
                break;

            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;

        }

    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOver.mousePressed(e);
        else if (lvlComplete)
            levelComplete.mousePressed(e);
        /*if(e.getButton()==MouseEvent.BUTTON1)
            player.setAttack(true);*/
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (paused)
            pauseOver.mouseReleased(e);
        else if (lvlComplete)
            levelComplete.mouseReleased(e);
        /*if(e.getButton()==MouseEvent.BUTTON1)
            player.setAttack(false);*/

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if (paused)
            pauseOver.mouseMoved(e);
        else if (lvlComplete)
            levelComplete.mouseMoved(e);
    }
    public void unpauseGame()

    {
        paused = false;
    }
    public void pauseGame()

    {
        paused = true;
    }

    public void windowFocusLost()
    {
        player.resetDirBooleans();
    }

    public Player getPlayer()
    {
        return player;
    }

    public EnemyManager getEnemyManager(){
        return enemyManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }


}
