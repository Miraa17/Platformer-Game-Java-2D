package Grahpics;

public class Status
{

    public static class GameObjects{

        public static final int COIN = 0;
        public static final int STAR = 1;

        public static final int COIN_VALUE = 155;
        public static final int STAR_VALUE = 156;

        public static final int COIN_WIDTH_DEFAULT = 32;
        public static final int COIN_HEIGHT_DEFAULT = 32;
        public static final int COIN_WIDTH = (int) (2f* COIN_WIDTH_DEFAULT);
        public static final int COIN_HEIGHT = (int) (2f* COIN_HEIGHT_DEFAULT);

        public static final int STAR_WIDTH_DEFAULT = 32;
        public static final int STAR_HEIGHT_DEFAULT = 32;
        public static final int STAR_WIDTH = (int) (2f* STAR_WIDTH_DEFAULT);
        public static final int STAR_HEIGHT = (int) (2f* STAR_HEIGHT_DEFAULT);


        public static int GetSpriteAmount(int object_type) {
//            switch (object_type) {
//                case COIN:
//                    return 3;
//                case STAR:
//                    return 3;
//            }
            return 1;
        }
    }


    public static class EnemyConstants
    {
        public static final int INAMIC1=55;

        public static final int ATTACK=0;
        public static final int HURT=1;
        public static final int IDLE=2;
        public static final int WALK=3;

        public static final int  INAMIC1_DrawOffsetX=(int)(19*2f);
        public static final int  INAMIC1_DrawOffsetY=(int)(15*2f);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {

            switch (enemy_type) {
                case INAMIC1:
                    switch (enemy_state) {
                        case ATTACK:
                            return 4;
                        case HURT:
                            return 4;
                        case IDLE:
                            return 4;
                        case WALK:
                            return 6;
                    }
            }

            return 0;
        }

        public static int GetHealth(int enemy_type)
        {
            switch (enemy_type)
            {
                case INAMIC1:
                    return 10;
                default:
                    return 1;//return 15
            }

        }
        public static  int GetEnamy(int enemy_type)
        {
            switch (enemy_type)
            {
                case INAMIC1:
                    return 15;
                default:
                    return 0;//return 0
            }

        }
    }
    public static class Direction
    {
        public static final int LEFT=0;
        public static final int UP=1;
        public static final int RIGHT=2;
        public static final int DOWN=3;

    }
    public static class PlayerConstants
    {
        public static final int RUNNING_LEFT=9;
        public static final int RUNNING_RIGHT=11;

        public static final int IDLE=10;
        public static final int ATACK_LEFT=5;
        public static final int ATACK_RIGHT=7;


        public static int GetSpriteAmount(int player_action)
        {
            switch(player_action)
            {
                case RUNNING_LEFT:
                    return 8;
                case RUNNING_RIGHT:
                    return 8;
                case IDLE:
                    return 5;
                case ATACK_LEFT:
                    return 6;
                case ATACK_RIGHT:
                    return 6;
                default:
                    return 1;

            }
        }

    }
}
