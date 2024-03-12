package Objects;

public class Score extends GameObjects
{

    public Score(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(32,32);
        xDrawOffset=(int)(2f*2);
        yDrawOffset=(int)(2f*3);
    }
}
