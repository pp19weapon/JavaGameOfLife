package com.engine.main;

public class Cell {
    enum STATE{
        DEAD,
        ALIVE
    }


    protected int posX;
    protected int posY;
    protected STATE state;

    public Cell(int posX, int posY, STATE state){
        this.posX = posX;
        this.posY = posY;
        this.state = state;
    }

    ///GETTERS
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public STATE getState() { return state; }

    ///SETTERS
    public void setState(STATE state) { this.state = state; }
}
