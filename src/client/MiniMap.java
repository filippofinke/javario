/*
 * Copyright (C) 2019 carlo
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import shared.Config;
import shared.Player;

/**
 *
 * @author Carlo Pezzotti
 */
public class MiniMap extends JPanel{
    
    private Player player;
    
    public MiniMap(){
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    

    @Override
    public void paint(Graphics g) {
        this.setSize(Config.FIELD_WIDTH/8, Config.FIELD_HEIGHT/8);
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.black);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.red);
        for (Player p : PlayersList.getPlayers()) {
            if(p != null){
                if(p != player)
                    g.fillOval((int)p.getX()/8-5, (int)p.getY()/8-5, 10 , 10);
            }
        }
        g.setColor(Color.blue);
        g.fillOval((int)player.getX()/8 -5, (int)player.getY()/8 -5, 10 , 10);
    }   
}
