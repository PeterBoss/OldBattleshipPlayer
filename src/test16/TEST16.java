/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test16;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

/**
 *
 * @author Peter Thomsen
 */
public class TEST16 implements PlayerFactory<BattleshipsPlayer>
{

    public TEST16(){}
    
    
    @Override
    public BattleshipsPlayer getNewInstance()
    {
        return new TestPlayer();
    }

    @Override
    public String getID()
    {
        return "TEST16";
    }

    @Override
    public String getName()
    {
        return "(⌐■_■)";
    }
    
    
}
