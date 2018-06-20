/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test16;

import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Peter Thomsen
 */
public class PriorityPositions {

    private int xSize;
    private int ySize;
    private boolean[][] available;
    private int[][] values;
    private int[][] hitValues;
    Random rnd = new Random();

    public PriorityPositions(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        available = new boolean[xSize][ySize];
        values = new int[xSize][ySize];
        hitValues = new int[xSize][ySize];
    }

    public void reset() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                available[i][j] = true;
                values[i][j] = 0;
                hitValues[i][j] = 0;
            }
        }
    }

    public void setValue(Position pos, int value) {
        values[pos.x][pos.y] = value;
    }

    public void addToValue(Position pos, int value) {
        values[pos.x][pos.y] += value;
    }

    public int getValue(Position pos) {
        return values[pos.x][pos.y];
    }

    public Position popHighest() {
        int bestX = 0;
        int bestY = 0;
        int bestValue = values[0][0];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (available[i][j] && values[i][j] >= bestValue) {
                    bestValue = values[i][j];
                    bestX = i;
                    bestY = j;
                }
            }
        }
        available[bestX][bestY] = false;
        return new Position(bestX, bestY);
    }

    @Override
    public String toString() {
        String result = "\n";
        for (int row = 0; row < xSize; row++) {
            for (int column = 0; column < ySize; column++) {
                result += values[row][column] + " ";
            }
            result += "\n";
        }
        return result;
    }

    public boolean isAvailable(Position pos) {

        if (pos.x >= xSize) {
            return false;
        }
        if (pos.x < 0) {
            return false;
        }
        if (pos.y >= ySize) {
            return false;
        }
        if (pos.y < 0) {
            return false;
        }

        return available[pos.x][pos.y];

    }

    public void setUnavailable(Position pos) {
        available[pos.x][pos.y] = false;
    }

    public Position popRandom() {

        int x = 0;
        int y = 0;

        for (int i = 0; i < 100000; i++) {
            x = rnd.nextInt(xSize);
            y = rnd.nextInt(ySize);

            if (available[x][y]) {
                available[x][y] = false;
                return new Position(x, y);
            }

        }
        return new Position(x, y);
    }

    public Position popRandomOdd() {
        List<Position> temp = new ArrayList<>();
        Position res;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if ((i + j) % 2 == 1 && available[i][j]) {
                    temp.add(new Position(i, j));
                }
            }
        }

        res = temp.get(rnd.nextInt(temp.size()));
        available[res.x][res.y] = false;
        return res;
    }

    public int calculateValue(Ship s, Position p) {

        if (!isAvailable(p)) {
            return 0;
        }
        int tempValue = 0;

        switch (s.size()) {
            case 2: {
                if (isAvailable(new Position(p.x, p.y + 1))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x, p.y - 1))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x + 1, p.y))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x - 1, p.y))) {
                    tempValue++;
                }
                break;
            }
            case 3: {
                if (isAvailable(new Position(p.x, p.y + 1)) && isAvailable(new Position(p.x, p.y + 2))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x, p.y - 1)) && isAvailable(new Position(p.x, p.y - 2))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x + 1, p.y)) && isAvailable(new Position(p.x + 2, p.y))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x - 1, p.y)) && isAvailable(new Position(p.x - 2, p.y))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x, p.y + 1)) && isAvailable(new Position(p.x, p.y - 1))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x + 1, p.y)) && isAvailable(new Position(p.x - 1, p.y))) {
                    tempValue++;
                }
                break;
            }
            case 4: {
                if (isAvailable(new Position(p.x, p.y + 1)) && isAvailable(new Position(p.x, p.y + 2)) && isAvailable(new Position(p.x, p.y + 3))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x, p.y - 1)) && isAvailable(new Position(p.x, p.y - 2)) && isAvailable(new Position(p.x, p.y - 3))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x + 1, p.y)) && isAvailable(new Position(p.x + 2, p.y)) && isAvailable(new Position(p.x + 3, p.y))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x - 1, p.y)) && isAvailable(new Position(p.x - 2, p.y)) && isAvailable(new Position(p.x - 3, p.y))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x, p.y + 1)) && isAvailable(new Position(p.x, p.y + 2)) && isAvailable(new Position(p.x, p.y - 1))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x, p.y + 1)) && isAvailable(new Position(p.x, p.y - 1)) && isAvailable(new Position(p.x, p.y - 2))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x + 1, p.y)) && isAvailable(new Position(p.x + 2, p.y)) && isAvailable(new Position(p.x - 1, p.y))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x + 1, p.y)) && isAvailable(new Position(p.x - 1, p.y)) && isAvailable(new Position(p.x - 2, p.y))) {
                    tempValue++;
                }
                break;
            }
            case 5: {
                if (isAvailable(new Position(p.x, p.y + 1)) && isAvailable(new Position(p.x, p.y + 2)) && isAvailable(new Position(p.x, p.y + 3)) && isAvailable(new Position(p.x, p.y + 4))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x, p.y - 1)) && isAvailable(new Position(p.x, p.y - 2)) && isAvailable(new Position(p.x, p.y - 3)) && isAvailable(new Position(p.x, p.y - 4))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x + 1, p.y)) && isAvailable(new Position(p.x + 2, p.y)) && isAvailable(new Position(p.x + 3, p.y)) && isAvailable(new Position(p.x + 4, p.y))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x - 1, p.y)) && isAvailable(new Position(p.x - 2, p.y)) && isAvailable(new Position(p.x - 3, p.y)) && isAvailable(new Position(p.x - 4, p.y))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x, p.y + 1)) && isAvailable(new Position(p.x, p.y + 2)) && isAvailable(new Position(p.x, p.y + 3)) && isAvailable(new Position(p.x, p.y - 1))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x, p.y + 1)) && isAvailable(new Position(p.x, p.y + 2)) && isAvailable(new Position(p.x, p.y - 1)) && isAvailable(new Position(p.x, p.y - 2))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x, p.y + 1)) && isAvailable(new Position(p.x, p.y - 1)) && isAvailable(new Position(p.x, p.y - 2)) && isAvailable(new Position(p.x, p.y - 3))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x + 1, p.y)) && isAvailable(new Position(p.x + 2, p.y)) && isAvailable(new Position(p.x + 3, p.y)) && isAvailable(new Position(p.x - 1, p.y))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x + 1, p.y)) && isAvailable(new Position(p.x + 2, p.y)) && isAvailable(new Position(p.x - 1, p.y)) && isAvailable(new Position(p.x - 2, p.y))) {
                    tempValue++;
                }
                if (isAvailable(new Position(p.x + 1, p.y)) && isAvailable(new Position(p.x - 1, p.y)) && isAvailable(new Position(p.x - 2, p.y)) && isAvailable(new Position(p.x - 3, p.y))) {
                    tempValue++;
                }
                break;
            }

            default: {
                return 0;
            }
        }

        return tempValue;
    }

    public void calculateAll(Ship s) {

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                int temp = calculateValue(s, new Position(i, j));
                addToValue(new Position(i, j), temp);
            }
        }
    }

    public void resetValues() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                values[i][j] = 0;
            }
        }
    }

    public void shipKiller(Position p) {
        Position upPos = new Position(p.x, p.y + 1);
        Position downPos = new Position(p.x, p.y - 1);
        Position rightPos = new Position(p.x + 1, p.y);
        Position leftPos = new Position(p.x - 1, p.y);

        if (isAvailable(upPos)) {
            addToHitValue(upPos, 100);
        }
        if (isAvailable(downPos)) {
            addToHitValue(downPos, 100);
        }
        if (isAvailable(rightPos)) {
            addToHitValue(rightPos, 100);
        }
        if (isAvailable(leftPos)) {
            addToHitValue(leftPos, 100);
        }
    }

    public void addToHitValue(Position pos, int value) {
        hitValues[pos.x][pos.y] += value;
    }

    public void resetHitValues() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                hitValues[i][j] = 0;
            }
        }
    }

    public void targeting() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                values[i][j] += hitValues[i][j];
            }
        }
    }

    public String printHitValues() {
        String result = "\n";
        for (int row = 0; row < xSize; row++) {
            for (int column = 0; column < ySize; column++) {
                result += hitValues[row][column] + " ";
            }
            result += "\n";
        }
        return result;
    }
}
