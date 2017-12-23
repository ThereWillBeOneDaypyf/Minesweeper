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
    boolean [][] vis;
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
       GameListener gameListener = new GameListener();
       gameListener.setArea(this);
       newGame.addActionListener(gameListener);
       restart.addActionListener(gameListener);
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
        //removeAll();
        bomb = new Bomb [r][c];
        BombCLick mouseListener = new BombCLick();
        grid = new GridLayout(r,c);
        setLayout(grid);
        vis = new boolean[r][c];
        for(int i = 0;i < r;i ++){
            for(int j = 0;j < c;j ++){
                bomb[i][j] = new Bomb(i,j);
                bomb[i][j].addMouseListener(mouseListener);
                add(bomb[i][j]);
                vis[i][j] = false;
            }
        }
        createGraph();
        validate();
        mouseListener.setArea(this);
    }
    void initRandom(){
        createBomb = new Random(1000000007);
        int cnt = createBomb.nextInt();
        for(int i = 0;i < cnt;i++)
            createBomb.nextInt();
    }
    void deleteCurrent(){
       for(int i = 0;i < row;i++)
           for(int j = 0;j < col;j ++)
               remove(bomb[i][j]);
    }
    int getRow(){
        return this.row;
    }
    int getCol(){
        return this.col;
    }
    boolean judgeWin(){
       int sum = 0;
       for(int i = 0;i < row;i ++){
           for(int j = 0;j < col;j ++){
               if(vis[i][j] == true)
                   sum ++;
           }
       }
       if(sum == row * col - bombCnt)
           return true;
       return false;
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
            //System.out.println(bomb[r][c].getWidth() + " " + bomb[r][c].getHeight());
            isExist.put(new Pair(r,c),1);
            bomb[r][c].isBomb = true;
        }
    }
    void clearScreen(){
        deleteCurrent();
        initGrid(row,col);
    }
    void restart(){
        for(int i = 0;i < row;i ++){
            for(int j = 0;j < col;j++){
                vis[i][j] = false;
                bomb[i][j].clearFlag();
            }
        }
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
    public void actionPerformed(ActionEvent e) {
        String target = e.getActionCommand();
        if(needChange(1) && "Primary".equals(target)){
            area.deleteCurrent();
            area.row = MyFrame.PRIMARY_ROW;
            area.col = MyFrame.PRIMARY_COL;
            area.bombCnt = MyFrame.PRIMARY_BOMB_CNT;
            area.initGrid(MyFrame.PRIMARY_ROW,MyFrame.PRIMARY_ROW);
        }
        else if(needChange(2) && "Medium".equals(target)){
            area.deleteCurrent();
            area.row = MyFrame.MEDIUM_ROW;
            area.col = MyFrame.MEDIUM_COL;
            area.bombCnt = MyFrame.MEDIU_BOMB_CNT;
            area.initGrid(MyFrame.MEDIUM_ROW,MyFrame.MEDIUM_COL);
        }
        else if(needChange(3) && "Hard".equals(target)){
            area.deleteCurrent();
            area.row = MyFrame.HARD_ROW;
            area.col = MyFrame.HARD_COL;
            area.bombCnt = MyFrame.HARD_BOMB_CNT;
            area.initGrid(MyFrame.HARD_ROW,MyFrame.HARD_COL);
            area.bombCnt = MyFrame.HARD_BOMB_CNT;
        }
    }
}
class GameListener implements ActionListener{
    MyFrame area;
    public void setArea(MyFrame area){
        this.area = area;
    }
    public void actionPerformed(ActionEvent e){
       String target = e.getActionCommand();
       if("Restart".equals(target)){
           area.restart();
           area.validate();
       }
       else if("New Game".equals(target)){
           area.deleteCurrent();
           area.initGrid(area.row,area.col);
           area.validate();
       }
    }
}
class BombCLick implements MouseListener{
    Bomb curObj;
    MyFrame workArea;
    ImageIcon leftClickIcon;
    ImageIcon rightClickIcon;
    ImageIcon bombClick;
    int [] xdir = {0,1,0,-1,1,1,-1,-1};
    int [] ydir = {1,0,-1,0,1,-1,1,-1};
    public void setArea(MyFrame workArea){
        this.workArea = workArea;
        ImageIcon leftClickIcon = new ImageIcon("F:\\Minesweeper\\src\\Graph\\LeftClicIcon.jpg");
        ImageIcon rightClickIcon = new ImageIcon("F:\\Minesweeper\\src\\Graph\\rightClickIcon.jpg");
        ImageIcon bombClick = new ImageIcon("F:\\Minesweeper\\src\\Graph\\bombClick.jpg");
//        System.out.println(workArea.bomb[0][0].getWidth() + " " + workArea.bomb[0][0].getHeight());
//        Image temp = leftClickIcon.getImage().getScaledInstance(workArea.bomb[0][0].getWidth(),
//                workArea.bomb[0][0].getHeight(),leftClickIcon.getImage().SCALE_DEFAULT);
//        leftClickIcon = new ImageIcon(temp);
//        temp = rightClickIcon.getImage().getScaledInstance(workArea.bomb[0][0].getWidth(),
//                workArea.bomb[0][0].getHeight(),rightClickIcon.getImage().SCALE_DEFAULT);
//        rightClickIcon = new ImageIcon(temp);
//        temp = bombClick.getImage().getScaledInstance(workArea.bomb[0][0].getWidth(),
//                workArea.bomb[0][0].getHeight(),bombClick.getImage().SCALE_DEFAULT);
//        bombClick = new ImageIcon(temp);
    }
    void setLeftClickIcon(Bomb btn){
        btn.setIcon(leftClickIcon);
        workArea.validate();
    }
    void setRightClickIcon(Bomb btn){
        if(btn.isRightClick == true){
           btn.isRightClick = false;
           btn = btn.copyObj(btn);
        }
        else{
           btn.isRightClick = true;
           btn.setIcon(rightClickIcon);
        }
        workArea.validate();
    }
    public void solveRight(Bomb curObj){
        setRightClickIcon(curObj);
    }
    int checkBomb(int x,int y){
       int cnt = 0;
       for(int i = 0;i < 8; i++){
           int tx = x + xdir[i];
           int ty = y + ydir[i];
           if(tx < 0 || ty < 0 || tx >= workArea.row || ty >= workArea.col) {
               continue;
           }
           if(workArea.bomb[tx][ty].isBomb == true)
               cnt ++;
        }
        return cnt;
    }
    public void solveLeft(Bomb curObj){
        if(curObj.isBomb == true){
            JOptionPane.showMessageDialog(workArea,"You Lose");
            workArea.clearScreen();
        }
        else{
            LinkedList<Integer> q = new LinkedList<>();
            q.add(curObj.x * workArea.row + curObj.y);
            while(q.isEmpty() == false){
                int temp = q.poll();
                int bombRound = checkBomb(temp / workArea.row,temp % workArea.row);
                //System.out.println("x : " + temp / workArea.row + "y : " + temp % workArea.row);
                if(bombRound != 0){
                    workArea.vis[temp / workArea.row][temp % workArea.row] = true;
                    workArea.bomb[temp / workArea.row][temp % workArea.row].setText(Integer.toString(bombRound));
                    continue;
                }
                //setLeftClickIcon(workArea.bomb[temp / workArea.row][temp % workArea.row]);
                //workArea.bomb[temp / workArea.row][temp % workArea.row].disable();
                workArea.bomb[temp / workArea.row][temp % workArea.row].setEnabled(false);
                for(int i = 0;i < 4;i++){
                    int tx = xdir[i] + temp / workArea.row;
                    int ty = ydir[i] + temp % workArea.row;
                    if(tx < 0 || ty < 0 || tx >= workArea.row || ty >= workArea.col || workArea.vis[tx][ty] == true){
                        continue;
                    }
                    workArea.vis[tx][ty] = true;
                    q.add(tx * workArea.row + ty);
                }
            }
            for(int i = 0;i < workArea.row;i ++) {
                for(int j = 0;j < workArea.col;j ++){
                    System.out.print(workArea.vis[i][j] + " ");
                }
                System.out.println();
            }

            if(workArea.judgeWin() == true) {
                JOptionPane.showMessageDialog(workArea, "You Win");
                workArea.clearScreen();
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
           solveLeft(curObj);
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
