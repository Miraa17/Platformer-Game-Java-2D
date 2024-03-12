package main;


import gamestates.GameState;
import gamestates.Menu;
import gamestates.Playing;

import java.awt.*;

public class Game implements Runnable {

    private GameWindow gameWindow; //instanta obiect de tip GameWindow pentru a afisa fereastra jocului
    private GamePanel gamePanel; //instanta obiect de tip gamePanel pentru a desena jocul
    private Thread gameThread; // instanta firului de executie
    private final int FPS_SET = 60; //numar cadre pe secunda
    private final int UPS_SET = 100; //numar actualizari pe secunda

    private Playing playing;
    private Menu menu;

    private static Game gameSingleton = new Game();  //apel

    //constructorul clasei
    private Game() {

        initClasses(); //apelam functia initClasses
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();//apelul functiei de pornire a buclei jocului


    }

    private void initClasses()
    {
        menu=new Menu(this);
        playing=new Playing(this);
        int x = 0;
    }


    //Metoda responsabila de pornirea buclei jocului
    private void startGameLoop() {
        gameThread = new Thread(this);//creare fir de executie pentru a rula bucla de joc in paralel cu firul pricipal de executie al programului
        gameThread.start();//Metoda start este apelata pentru a porni firul de executie si a incepe bucla de joc
    }

    //Metoda care se ocupa de actualizarea starii jocului
    public void update() {

        switch (GameState.state)
        {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;

        }
    }


    //functie utilizata pentru desenare grafica
    public void render(Graphics g)
    {
        switch (GameState.state)
        {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }
    }

    //Gestionarea buclei de joc
    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }

    }

    //Metoda Singleton-sablon de proiectare care se asigura crearea unei singure instante a clasei Game in timpul rularii
    public static Game Singleton()
    {
        return gameSingleton;
    }

    //Setare focus fereastra
    public void windowFocusLost()
    {
        if(GameState.state==GameState.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }

    public Menu getMenu()
    {
        return menu;
    }

    public Playing getPlaying()
    {
        return playing;
    }

}