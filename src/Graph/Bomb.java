package Graph;

import javax.swing.*;

public class Bomb extends JButton{
    boolean isBomb; // is Bomb
    boolean isLeftClick; // Left Mouse Click
    boolean isRightClick; //Right Mouse Click
    int x,y; // position
    int bombRound; // the count of 8 blocks around
    public Bomb (int _x,int _y){
       x = _x;
       y = _y;
       isBomb = false;
       isLeftClick = false;
       isRightClick = false;
    }
    public void setBombRound(int cnt){
        bombRound = cnt;
    }
    public void setisLeftClick(boolean change){
        isLeftClick = change;
    }
    public void setRightClick(boolean change){
        isRightClick = change;
    }
    public boolean getLeftClick(){
        return isLeftClick;
    }
    public boolean getRightClick(){
        return isRightClick;
    }
}


