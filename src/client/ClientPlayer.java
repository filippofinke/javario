/*
 * Copyright (C) 2019 filippofinke
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
import shared.Player;

/**
 *
 * @author filippofinke
 */
public class ClientPlayer extends Player {

    public void paint(int rx, int ry, Point p, Graphics g) {
        int radius = this.getRadius();
        int x = rx;
        int y = ry;
        g.setColor(Color.YELLOW);
        if (this.getId() != GamePanel.player.getId()) {
            g.setColor(Color.RED);
            x = rx - (p.x - point.x);
            y = ry - (p.y - point.y);
        }
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g.setColor(Color.BLACK);
        g.drawString("P: " + getPoints(), x, y);
    }

    public ClientPlayer(int id, Point point) {
        super(id, point);
    }

}
