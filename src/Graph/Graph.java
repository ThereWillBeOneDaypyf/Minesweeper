package Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Graph{
    public static void main(String [] args) {
        new MyFrame();
    }
}

class MyFrame extends JFrame {
    class Pair {
        int first;
        int second;
        public Pair(int _x,int _y) {
            first = _x;
            second = _y;
        }
        public int getFirst() {
            return first;
        }
        public int getSecond() {
            return second;
        }
    }
    final static int PRIMARY_ROW = 10,PRIMARY_COL = 10;
    final static int MEDIUM_ROW = 20,MEDIUM_COL= 20;
    final static int HARD_ROW = 30,HARD_COL = 30;
    final static int PRIMARY_BOMB_CNT = 10,MEDIU_BOMB_CNT = 20,HARD_BOMB_CNT = 30;
    int row,col,bombCnt; // the row and col of Graph
    Random createBomb;
    GridLayout grid;
    Bomb [][] bomb;
    JMenuBar menuBar;
    JMenu GameMenu;
    JMenuItem newGame,restart;
    JMenu settingMenu;
    JMenuItem primary,medium,hard;
    public MyFrame(){
       row = PRIMARY_ROW;
       col = PRIMARY_COL;
       bombCnt = PRIMARY_BOMB_CNT;
       initRandom();
       initMune();
       initGrid(this.row,this.col);
       setSize(500,500);
       setVisible(true);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       validate();
    }
    void initMune(){
       menuBar = new JMenuBar();
       GameMenu = new JMenu("Game");
       newGame = new JMenuItem("New Game");
       restart = new JMenuItem("Restart");
       GameMenu.add(newGame);
       GameMenu.add(restart);
       menuBar.add(GameMenu);
       settingMenu = new JMenu("Setting");
       primary = new JMenuItem("Primary");
       medium = new JMenuItem("Medium");
       hard = new JMenuItem("Hard");
       settingMenu.add(primary);
       settingMenu.add(medium);
       settingMenu.add(hard);
       menuBar.add(settingMenu);
       setJMenuBar(menuBar);
       LevelChangeListener levelSettingListenner = new LevelChangeListener();
       levelSettingListenner.setArea(this);
       primary.addActionListener(levelSettingListenner);
       medium.addActionListener(levelSettingListenner);
       hard.addActionListener(levelSettingListenner);
    }
    void initGrid(int r,int c){
        removeAll();
        grid = new GridLayout(r,c);
        setLayout(grid);
        bomb = new Bomb [r][c];
        for(int i = 0;i < r;i ++){
            for(int j = 0;j < c;j ++){
                bomb[i][j] = new Bomb(i,j);
                add(bomb[i][j]);
            }
        }
        createGraph();
        validate();
    }
    void initRandom(){
        createBomb = new Random(1000000007);
        int cnt = createBomb.nextInt();
        for(int i = 0;i < cnt;i++)
            createBomb.nextInt();
    }
    int getRow(){
        return this.row;
    }
    int getCol(){
        return this.col;
    }
    void createGraph(){
        HashMap<Pair,Integer> isExist = new HashMap<Pair, Integer>();
        for(int i = 0;i < bombCnt;i ++){
            int r = createBomb.nextInt(row);
            int c = createBomb.nextInt(col);
            while(isExist.containsKey(new Pair(r,c))){
                r = createBomb.nextInt(row);
                c = createBomb.nextInt(col);
            }
            System.out.println(r + "," + c);
            isExist.put(new Pair(r,c),1);
            bomb[r][c].isBomb = true;
        }
    }
    void clearScreen(){
        removeAll();
        setLayout(new BorderLayout());
        getContentPane().add("Center",new Label("You Lose"));
        validate();
    }
}
class LevelChangeListener implements ActionListener{
    MyFrame area;
    public void setArea(MyFrame area){
        this.area = area;
    }
    boolean needChange(int status){
        if(status == 1 && area.getRow() == MyFrame.PRIMARY_ROW)
            return false;
        else if(status == 2 && area.getRow() == MyFrame.MEDIUM_ROW)
            return false;
        else if(status == 3 && area.getRow() == MyFrame.HARD_ROW)
            return false;
        else
            return true;
    }
    void deleteCurrent(){
       for(int i = 0;i < area.row;i++)
           for(int j = 0;j < area.col;j ++)
               area.remove(area.bomb[i][j]);
    }
    public void actionPerformed(ActionEvent e) {
        String target = e.getActionCommand();
        if(needChange(1) && "Primary".equals(target)){
            deleteCurrent();
            area.row = MyFrame.PRIMARY_ROW;
            area.col = MyFrame.PRIMARY_COL;
            area.bombCnt = MyFrame.PRIMARY_BOMB_CNT;
            area.initGrid(MyFrame.PRIMARY_ROW,MyFrame.PRIMARY_ROW);
        }
        else if(needChange(2) && "Medium".equals(target)){
            deleteCurrent();
            area.row = MyFrame.MEDIUM_ROW;
            area.col = MyFrame.MEDIUM_COL;
            area.bombCnt = MyFrame.MEDIU_BOMB_CNT;
            area.initGrid(MyFrame.MEDIUM_ROW,MyFrame.MEDIUM_COL);
        }
        else if(needChange(3) && "Hard".equals(target)){
            deleteCurrent();
            area.row = MyFrame.HARD_ROW;
            area.col = MyFrame.HARD_COL;
            area.bombCnt = MyFrame.HARD_BOMB_CNT;
            area.initGrid(MyFrame.HARD_ROW,MyFrame.HARD_COL);
            area.bombCnt = MyFrame.HARD_BOMB_CNT;
        }
    }
}
class BombCLick implements MouseListener{
    Bomb curObj;
    MyFrame workArea;
    int [] xdir = {0,1,0,-1,1,1,-1,-1};
    int [] ydir = {1,0,-1,0,1,-1,1,-1};
    public void setArea(MyFrame workArea){
        this.workArea = workArea;
    }
    public void solveRight(Bomb curObj){

    }
    boolean checkBomb(int x,int y){
       if(workArea.bomb[x][y].isBomb == true)
           return true;
       for(int i = 0;i < 8; i++){
           int tx = x + xdir[i];
           int ty = y + ydir[i];
           if(tx < 0 || ty < 0 || tx >= workArea.row || ty >= workArea.col) {
               continue;
           }
           if(workArea.bomb[tx][ty].isBomb == true)
               return true;
        }
        return false;
    }
    public void solveLeft(Bomb curObj){
        if(curObj.isBomb == true){
            workArea.clearScreen();
        }
        else{
            LinkedList<Integer> q = new LinkedList<>();
            q.add(curObj.x * workArea.row + curObj.y);
            while(q.isEmpty() == false){
                int temp = q.poll();
                for(int i = 0;i < 4;i++){
                    int tx = xdir[i] + temp / workArea.row;
                    int ty = ydir[i] + temp % workArea.row;
                    if(tx < 0 || ty < 0 || tx >= workArea.row || ty >= workArea.col || checkBomb(tx,ty) == true){
                        workArea.bomb[temp / workArea.row][temp % workArea.row].disable();
                        continue;
                    }
                }
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
       curObj = (Bomb) mouseEvent.getSource();
       if(mouseEvent.getButton() == mouseEvent.BUTTON3){
           solveRight(curObj);

       }
       else if(mouseEvent.getButton() == mouseEvent.BUTTON1){
           solveRight(curObj);
       }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }
}
