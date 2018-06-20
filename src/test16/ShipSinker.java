/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test16;

import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Peter Thomsen
 */
public class ShipSinker {

    private List<Position> targetStack;

    public ShipSinker() {
        this.targetStack = new ArrayList<>();
    }

    public List<Position> getTargetStack() {
        return targetStack;
    }

    public void addTargets(PriorityPositions pp, Position p) {

        Position temp;

        temp = new Position(p.x + 1, p.y);  //right (assuming normal axes(x increasing to the right, and y increasing upwards))
        if (pp.isAvailable(temp)) {
            targetStack.add(temp);
        }
        temp = new Position(p.x, p.y + 1);  //up

        if (pp.isAvailable(temp)) {
            targetStack.add(temp);
        }
        temp = new Position(p.x - 1, p.y);  //left

        if (pp.isAvailable(temp)) {
            targetStack.add(temp);
        }
        temp = new Position(p.x, p.y - 1);  //down

        if (pp.isAvailable(temp)) {
            targetStack.add(temp);
        }
    }

    public Position pop() {  //off the top
        return targetStack.remove(targetStack.size() - 1);
    }
}
