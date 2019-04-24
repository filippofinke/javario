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
package shared;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author filippofinke
 */
public class Food {
    
    private int id;
    
    protected Point point;
    
    public int getId() {
        return id;
    }

    public Point getPoint() {
        return point;
    }
    
    public double getX() {
        return point.getX();
    }
    
    public double getY() {
        return point.getY();
    }
    
    public void paint(int rx, int ry, Point p, Graphics g){
        g.setColor(Color.GREEN);
        int x = rx - (p.x - point.x);
        int y = ry - (p.y - point.y);
        g.fillOval(x - Config.FOOD_RADIUS, y - Config.FOOD_RADIUS, Config.FOOD_RADIUS * 2, Config.FOOD_RADIUS * 2);
    }
    

    public Food(int id, Point point) {
        this.id = id;
        this.point = point;
    }
    
}
