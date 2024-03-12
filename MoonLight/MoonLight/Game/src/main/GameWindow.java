package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;//Focus pe panoul de joc

public class GameWindow
{
    private JFrame jFrame; // decalare obiect de tip JFrame

    public GameWindow(GamePanel gamePanel)
    {
        jFrame=new JFrame(); //Vom crea o instanta a unui obiect de tip JFrame

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Activam butonul de inchidere al ferestrei,butonul X
        jFrame.add(gamePanel); // unim "Fereastra" cu "GamePanel", adaugam resursa grafica in fereastra
        jFrame.setResizable(false);//Fereastra nu poate fi redimensionata
        jFrame.pack();//impachetam toate resursele impreuna
        jFrame.setLocationRelativeTo(null);//Centram fereastra
        jFrame.setVisible(true); //setam fereastra ca fiind vizibila
        jFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e)
            {
                gamePanel.getGame().windowFocusLost();//centram focusul pe panoul de joc al ferestrei
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });



    }
}
