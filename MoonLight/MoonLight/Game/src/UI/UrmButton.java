package UI;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UrmButton extends PauseButton
{
    private BufferedImage[] img;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;
    public UrmButton(int x, int y, int width, int height, int rowIndex)
    {
        super(x, y, width, height);
        this.rowIndex=rowIndex;
        loadImg();

    }

    private void loadImg()
    {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        img = new BufferedImage[3];
        for (int i = 0; i < img.length; i++)
            img[i] = temp.getSubimage(i*56, rowIndex*56, 56,56);
    }

    public void update()
    {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void draw(Graphics g)
    {
        g.drawImage(img[index], x, y, 57, 57, null);
    }

    public void resetBools()
    {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
