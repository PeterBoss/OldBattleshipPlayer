/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test16;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Board;
import battleship.interfaces.Ship;
import java.util.Random;

/**
 *
 * @author Peter Thomsen
 */
public class TestPlayer implements BattleshipsPlayer {

    private final static Random rnd = new Random();
    private boolean targetFound;  //switches between normal and kill-mode
    private ShipSinker sinker;  //methods for kill-mode
    private Position lastShot;
    private int enemiesLeft;
    private ShipPlacer placer;
    private Position inc;
    private int count;
    private int shotNum;
    PriorityPositions priPos;

    public TestPlayer() {
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        sinker = new ShipSinker();  //reinitializing at start of round. Can be moved to startRound? maybe make reset methods for them
        placer = new ShipPlacer(board);
        targetFound = false;
        lastShot = null;
        enemiesLeft = fleet.getNumberOfShips();  //not used in version 13
        priPos = new PriorityPositions(board.sizeX(), board.sizeY());
        priPos.reset();  //set all positions to available (temp fix?)
        placer.placeAllShips(fleet, board); //random placement, probably final. focusing on maximizing own score.

    }

    @Override
    public void incoming(Position pos) {
        //Do nothing
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        priPos.resetValues();
        for (Ship s : enemyShips) {
            priPos.calculateAll(s);
        }
        System.out.println(priPos.toString());
        if (targetFound) {
            priPos.targeting();
        }
        Position shot;
        if (targetFound) {  //kill-mode
//            shot = sinker.pop();  //take from top of stack
//            priPos.setUnavailable(shot);
            shot = priPos.popHighest();
        } else {
            shot = priPos.popHighest();
        }
        lastShot = shot;
        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        
//        if (enemiesLeft != enemyShips.getNumberOfShips()) {
//            targetFound = false;
//            priPos.resetHitValues();
//            enemiesLeft = enemyShips.getNumberOfShips();
//            return;
//        }
        
        if (hit) {
            targetFound = true;  //switch to kill-mode
            sinker.addTargets(priPos, lastShot);  //add possible targets to stack
            for (Position p : sinker.getTargetStack()) {  //check if positions are available
                if (!priPos.isAvailable(p)) {
                    sinker.getTargetStack().remove(p);
                }
            }
            priPos.shipKiller(lastShot);
        }
        if (sinker.getTargetStack().isEmpty()) {  //if no more possible targets, ship must be sunk. Resume normal targeting
            targetFound = false;
        }
    }

    @Override
    public void startMatch(int rounds) {
        //Do nothing
    }

    @Override
    public void startRound(int round) {
        //Do nothing
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        //Do nothing
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        //Do nothing
    }
}
