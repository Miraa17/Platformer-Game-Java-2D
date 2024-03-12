package main;

import Input.KeyManager;
import Input.MouseInputs;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

public class GamePanel extends JPanel
{
    private MouseInputs mouseInputs; //declarare obiect de tip MouseInputs
    private Game game; //declarere obiect de tip Game
    public GamePanel(Game game)//constructorul clasei GamePanel
    {

        mouseInputs=new MouseInputs(this);
        this.game=game;

        setPanelSize();//Setare Dimenisuni
        addKeyListener(new KeyManager(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    private void setPanelSize()
    {
        Dimension size=new Dimension(1280, 800);//Setare dimiensiuni fereastra

        //setMaximumSize(size);
        //setMinimumSize(size);
        setPreferredSize(size);
    }

    public void updateGame()
    {

    }

    public void paintComponent(Graphics g)//functia paintComponenet care ne ajuta sa desenam elemente grafice
    {
        super.paintComponent(g);//suprascriere functiei din JPanel pentru desenare elem grafice
        game.render(g);  //apelul functiei render din clasa game, utilizata pentru a desena nivelul curent si jucatorul

    }

    public Game getGame()
    {
        return game;
    }

}
