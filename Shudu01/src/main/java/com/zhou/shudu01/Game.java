package com.zhou.shudu01;

/**
 * Created by Administrator on 14-4-20.
 */
public class Game {
    private final String str="360000000004230800000004200"
            +"070460003820000014500013020"
            +"001900000007048300000000045";
    private int sudoku []=new int[9*9];
    private int used[][][]=new int[9][9][];

    public Game(){
        sudoku=fromPuzzleString(str);
        calculataAllUsedTiles();
    }

    private int getTile(int x, int y){
        return sudoku[y*9+x];
    }

    public String getTileString(int x, int y){
        int v=getTile(x,y);
        if (v==0){
            return "";
        }else{
            return String.valueOf(v);
        }

    }
    protected int[] fromPuzzleString(String src){
        int[] sudo=new int[src.length()];
        for (int i=0; i<sudo.length; i++){
            sudo[i]=src.charAt(i)-'0';
        }
        return sudo;
    }

    public void calculataAllUsedTiles(){
        for(int x=0; x<9; x++)
            for(int y=0; y<9; y++)
                used[x][y]=calculateUsedTiles(x,y);
    }

   //取出某一单元格不可用数据
    public int[] getUsedTitlesByCoor(int x, int y){
        return used[x][y];
    }

    public int[] calculateUsedTiles(int x, int y){
        int c[]=new int[9];

        for (int i=0; i<9; i++){
            if(i==y) continue;
            int t=getTile(x,i);
            if(t!=0) c[t-1]=t;
        }

        for (int i=0; i<9; i++){
            if(i==x) continue;
            int t=getTile(i,y);
            if(t!=0) c[t-1]=t;
        }

        int startX=(x/3)*3;
        int startY=(y/3)*3;
        for (int i=startX; i<startX+3; i++)
            for (int j=startY; j<startY+3; j++){
                if(i==x && j==y) continue;
                int t=getTile(i,j);
                if(t!=0) c[t-1]=t;
            }

        int nused=0;
        for(int t:c){
            if (t!=0) nused++;
        }

        int c1[]=new int[nused];
        nused=0;
        for (int t:c){
            if(t!=0) c1[nused++]=t;
        }
        return c1;
    }

    protected boolean setTileIfValid(int x, int y, int value){
        int tiles[]=getUsedTiles(x,y);
        if(value!=0){
            for (int tile:tiles)
            {
                if(tile==value) return false;
            }
        }
        setTile(x,y,value);
        calculataAllUsedTiles();
        return true;
    }

    private void setTile(int x, int y, int value) {
               sudoku[y*9 + x]=value;
    }

    private int[] getUsedTiles(int x, int y) {
        return used[x][y];
    }
}
