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

import java.awt.Point;

/**
 *
 * @author filippofinke
 */
public class Player {

    private int id;

    private int points = 0;

    protected Point point;

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public Point getPoint() {
        return point;
    }

    public int getId() {
        return id;
    }

    public int getRadius() {
        int rad = Config.PLAYER_RADIUS + points / Config.FOOD_TO_RADIUS;
        if (rad > Config.PLAYER_MAX_RADIUS) {
            rad = Config.PLAYER_MAX_RADIUS;
        }
        return rad;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        if (points < 0) {
            points = 0;
        }
        this.points = points;
    }

    public void addPoint() {
        points++;
    }

    public void removePoint() {
        points--;
    }

    public Player(int id, Point point) {
        this.id = id;
        this.point = point;
    }

}
