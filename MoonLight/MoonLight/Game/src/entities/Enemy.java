package entities;

import java.awt.geom.Rectangle2D;

import static Grahpics.Status.EnemyConstants.*;
import static utilz.Colision.*;
import static Grahpics.Status.Direction.*;

public abstract class Enemy extends Entity {
    protected int animationIndex, enemyState, enemyType;
    protected int animationTick, animationSpeed = 15;
    protected boolean firstUpdate = true;
    protected float fallSpeed;
    protected float gravity = 0.04f * 2f;
    protected float walkSpeed = 0.35f * 2f;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float DistanceAtack=64;
    protected boolean inAir; //var. nu suntem in aer la inceput

    //viata
    protected  int maxHealth;
    protected int curentHealth;
    protected boolean activ=true;
    protected boolean atackCheck;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.enemyState=IDLE;
        initHitBox(x, y, width, height);
        maxHealth=GetHealth(enemyType);
        curentHealth=maxHealth;
    }

    protected void firstUpdateCheck(int[][] lvlData)
    {
        if (!IsEntityOnFloor(hitBox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, lvlData)) {
            hitBox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitBox.y =GetEntityPosUnderRoofOrAboveFloor(hitBox, fallSpeed);
            tileY=(int)(hitBox.y/64);
        }
    }

    protected void move(int[][] lvlData)
    {
        float xSpeed = 0;
        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;
        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData))
            if (IsFloor(hitBox, xSpeed, lvlData)) {
                hitBox.x += xSpeed;
                return;
            }
        changeDir();//intram aici daca ultimele 2 if-uri su
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitBox.x > hitBox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitBox().y / 64);
        if (playerTileY == tileY)
            if (isPlayerInRange(player))
            {
                if (IsSightClear(lvlData, hitBox, player.hitBox, tileY))
                    return true;
            }

        return false;
    }

    protected boolean isPlayerInRange(Player player)
    {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absValue <= DistanceAtack * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absValue <= DistanceAtack;
    }


    protected void newState(int enemyState)
    {
        this.enemyState = enemyState;
        animationTick = 0;
        animationIndex = 0;
    }

    public void hurt(int amount)
    {
        curentHealth-=amount;
        if(curentHealth<=0)
            newState(HURT);
    }
    protected void checkPlayerHit(Rectangle2D.Float cutieInamic, Player player)
    {
        if(cutieInamic.intersects(player.hitBox))
            player.changeHealth(-GetEnamy(enemyType));
        atackCheck=true;
    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(enemyType, enemyState)) {
                animationIndex = 0;
                switch (enemyState) {
                    case ATTACK:
                        enemyState = IDLE;
                    case HURT:
                        activ = false;
                }
            }
           // animationIndex++;
            //if (animationIndex >= GetSpriteAmount(enemyType, enemyState)) {
              //  animationIndex = 0;
                //if(enemyState==ATTACK)
                  //  enemyState=IDLE;
                //else if(enemyState==HURT)
                  //  activ=false;
            }
        }



    protected void changeDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;

    }

    public void resetEnemy() {
        hitBox.x = x;
        hitBox.y = y;
        firstUpdate = true;
        curentHealth = maxHealth;
        newState(IDLE);
        activ = true;
        fallSpeed = 0;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean isActiv()
    {
        return activ;
    }
}
