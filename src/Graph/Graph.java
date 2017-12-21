package Graph;

import javax.swing.*;
import java.awt.*;

public class Graph{
    public static void main(String [] args) {
        new MyFrame(5,5);
    }
}

class MyFrame extends JFrame {
    int row,col; // the row and col of Graph
    GridLayout grid;
    public MyFrame(int r,int c){
       initGrid(r,c);
       setSize(500,500);
       setVisible(true);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       validate();
    }
    void initGrid(int r,int c){
        grid = new GridLayout(r,c);
        setLayout(grid);
        Bomb bomb [][] = new Bomb [r][c];
        for(int i = 0;i < r;i ++){
            for(int j = 0;j < c;j ++){
                bomb[i][j] = new Bomb(i,j);
                add(bomb[i][j]);
            }
        }
    }
}
