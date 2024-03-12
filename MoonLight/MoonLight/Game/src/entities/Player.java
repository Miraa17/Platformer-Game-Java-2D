package entities;

import db.DbConnection;
import gamestates.Playing;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Grahpics.Status.PlayerConstants.*;
import static utilz.Colision.*;
import static utilz.Colision.GetEntityPosNextToWall;
import static utilz.Colision.GetEntityPosUnderRoofOrAboveFloor;
public class Player extends Entity {
    private BufferedImage[][] animations;//stocare animatii jucator
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = RUNNING_RIGHT;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;

    //Variabile pentru gravitatie
    private float airSpeed = 0f;
    private float gravity = 0.04f * 2f;
    private float jumpSpeed = -2.25f * 2f;
    private float fallSpeedAfterColision = 0.5f * 2f;
    private boolean inAir = false;

    private float playerSpeed = 2.0f;

    private int[][] lvlData;
    private float xDrawOffset = 16*2f;
    private float yDrawOffset = 0*2f;

    //Status_Life
    private BufferedImage lifeBar;
    private int maxHealth=100;
    private int currentHealth=35;
    private int healthWidth=(int)(150*2f);

    //hitBoxArma
    private Rectangle2D.Float cutieArma;
    private boolean attackCheck;
    private Playing playing;
    private int score = 0;

    //Constructorul clar->initializeaza jucatorul cu poziti si dimensiunile
    public Player(float x, float y, int width, int height,Playing playing) {
        super(x, y, width, height);
        this.playing=playing;
        loadAnimations();//incarca animatiile
        initHitBox(x, y, 28*1.0f, 60*1.0f);
        initHitBoxArma();

    }

    public int getScore(){return  score;}

    private void initHitBoxArma()
    {
        cutieArma=new Rectangle2D.Float(x,y,(int)(20*2f),(int)(20*2f));
    }

    //Actualizare jucator
    public void update() {
        updateHealth();//actualizez viata jucatotului
        updateCutieArma();
        updatePosition();//actualizare pozitie jucator
        if (moving)
         checkPotionTouched();
        if(attacking)
            checkAttack();
        updateAnimationTick(); //actualizare animatie
        setAnimation(); //stabileste animatia in functie de starea curenta

    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitBox);
    }

    private void checkAttack()
    {
        if(attackCheck || animationIndex!=1)
            return;
        attackCheck=true;
        Rectangle2D.Float cutieInamic;
        playing.checkEnemyHit(cutieArma);
    }

    public void changeScore(int pointsToAdd){
        score += pointsToAdd;
        DbConnection dbConnection = new DbConnection();
        dbConnection.updateRecord(score);
        System.out.println("Score: " + score);

        // aici trebuie sa schimb scorul pe ecran

    }

    private void updateCutieArma()
    {
        if(right)
        {
            cutieArma.x=hitBox.x+hitBox.width+(int)(10*2f);
        }
        else
            if(left)
            {
                cutieArma.x=hitBox.x- hitBox.width-(int)(10*2f);
            }
            cutieArma.y=hitBox.y+(10*2f);

    }

    private void updateHealth()
    {
        healthWidth=(int)((currentHealth/(float)maxHealth)*(int)(150*2f));
    }

    //Deseneaza jucatorul pe ecran
    public void render(Graphics g, int lvlOffset) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitBox.x - xDrawOffset)-lvlOffset, (int) (hitBox.y - yDrawOffset), (int)(64*1.5), (int)(64*1.5), null);
        //drawHitBox(g,lvlOffset);
        //drawHitBoxArma(g,lvlOffset);
       // drawBarLife(g);
    }

    private void drawHitBoxArma(Graphics g,int lvlOffset)
    {
        g.setColor(Color.red);
        g.drawRect((int)cutieArma.x-lvlOffset,(int)cutieArma.y,(int)cutieArma.width,(int)cutieArma.height);
    }

//    private void drawBarLife(Graphics g)
//    {
//        g.drawImage(lifeBar, (int)(10*2f),(int)(10*2f),(int)(192*2f),(int)(58*2f),null);
//        g.setColor(Color.blue);
//        g.fillRect((int)(34*2f)+(int)(10*2f),(int)(14*2f)+(int)(10*2f),healthWidth,(int)(4*2f));
//    }


    //actualizeaza animatia in functie de viteza
    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
                attackCheck=false;
            }

        }
    }


    //Stabileste animatia in functie de starea curenta a jucatorului
    private void setAnimation() {
        int startAnimation = playerAction;
        if (moving)
            playerAction = RUNNING_RIGHT;
        else
            playerAction = IDLE;
        if (attacking) {
            playerAction = ATACK_RIGHT;
            if (startAnimation != ATACK_RIGHT) {

                animationIndex=1;
                animationTick=0;
                return;
            }
        }
        if (startAnimation != playerAction)
            resetAnimationTick();
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }


    //Actualizeaza pozitia jucatorului in functie de directia de deplasare si verifica coliziunile
    private void updatePosition() {
        moving = false;
        if (jump)
            jump();

        //if (!left && !right && !inAir)
          //return;

        if(!inAir)
            if((!left && !right) || (right && left))
                return;

        float xSpeed = 0;

        if (left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;
        if(!inAir)
            if(!IsEntityOnFloor(hitBox,lvlData))
                inAir=true;


        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData))
            {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPosition(xSpeed);
            } else
            {
                hitBox.y = GetEntityPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterColision;
                updateXPosition(xSpeed);
            }
        } else
            updateXPosition(xSpeed);

        moving = true;

    }

    private void jump()
    {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir()
    {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPosition(float xSpeed) {
        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
        } else
        {
            hitBox.x = GetEntityPosNextToWall(hitBox, xSpeed);
        }
    }

    public void changeHealth(int value)
    {
        currentHealth += value;

        if (currentHealth <= 0)
            currentHealth = 0;
        else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }
   // public void changePower(int value) {
       // System.out.println("Added power!");
    //}

    //incarca toate animatiile jucatorului intr-un fisier de imagine
    private void loadAnimations() {
        File is = new File("C:\\Users\\V\\Desktop\\Final\\Scor_1\\Scor\\Res\\textures\\Sprite_Character.png");

        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[12][8];
            for (int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++)
                    animations[j][i] = img.getSubimage(i * 64, j * 64, 64, 64);
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lifeBar= LoadSave.GetSpriteAtlas(LoadSave.VIATA);

    }

    //Seteaza datele nivelului
    public void loadLvData(int[][] lvlData)
    {
        this.lvlData = lvlData;
        if(!IsEntityOnFloor(hitBox, lvlData))
            inAir=true;
    }


    //Resetarea variabilelor de stare pentru directii
    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    //Setarea starii de atac a jucatorului
    public void setAttack(boolean attacking) {
        this.attacking = attacking;
    }

    public void setJump(boolean jump)
    {
        this.jump=jump;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitBox.x = x;
        hitBox.y = y;

        if (!IsEntityOnFloor(hitBox, lvlData))
            inAir = true;
    }
}

