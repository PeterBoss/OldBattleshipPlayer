/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test16;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Peter
 */
public class ShipPlacer {

    private List<Position> validSpots = new ArrayList<>();
    private Random rnd = new Random();

    ;
    public ShipPlacer(Board b) {
        for (int i = 0; i < b.sizeX(); i++) {
            for (int j = 0; j < b.sizeY(); j++) {
                validSpots.add(new Position(i, j));
            }
        }
    }

    public void placeAllShips(Fleet f, Board b) {
        for (int i = 0; i < f.getNumberOfShips(); i++) {
            for (int j = 0; j < 100000; j++) {  //100k is arbitrary
                if (placeShip(f.getShip(i), b)) {
                    break;
                }
            }
        }
    }

    public boolean placeShip(Ship s, Board b) {

        List<Position> temp = new ArrayList<>();
        Position pos = new Position(rnd.nextInt(b.sizeX() - 2) + 1, rnd.nextInt(b.sizeY() - 2) + 1);
        Boolean vertical = rnd.nextBoolean();

        for (int j = 0; j < s.size(); j++) {
            if (vertical) {
                temp.add(new Position(pos.x, pos.y + j));
            } else {
                temp.add(new Position(pos.x + j, pos.y));
            }
        }

        if (validSpots.containsAll(temp)) {
            validSpots.removeAll(temp);
            b.placeShip(pos, s, vertical);
            return true; 
        }
        return false;
    }

}
