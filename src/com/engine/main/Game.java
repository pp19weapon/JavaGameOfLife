package com.engine.main;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas{
    private static final long serialVersionUID = 7925735835093467255L;

    public static final int WIDTH = 1024, HEIGHT = WIDTH;

    private boolean running = false;

    private Simulation testSimulation;

    public Game(){
        new Window(WIDTH, HEIGHT, "Game of Life", this);
    }

    public synchronized void start(){
        running = true;

        int cellSize = 6;
        int mapSize = WIDTH / cellSize;
        int numberOfStartingCells = 1500;
        int startingPos = (mapSize / 2) - (int) (Math.sqrt(numberOfStartingCells) / 2);
        testSimulation = new Simulation(mapSize, cellSize);

        run();
    }

    public synchronized void stop(){
        running = false;
    }

    public void run() {
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running){
            tick();

            if (running){
                render();
            }

            frames++;

            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    private void tick() {
        testSimulation.Tick();
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bs.getDrawGraphics();

        graphics.setColor(Color.BLUE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);


        testSimulation.Draw(graphics);

        graphics.dispose();
        bs.show();
    }

    public static void main(String args[]){
        new Game();
    }
}
