package Objects;

public class Money extends GameObjects
{

    public Money(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation=true;
        initHitbox(32,32);
        xDrawOffset=(int)(2f*2);
        yDrawOffset=(int)(2f*3);
    }

    public void update() {
        updateAnimationTick();
    }

}
