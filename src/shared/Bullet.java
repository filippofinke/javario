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
public class Bullet {

    private int id;

    private int ownerId;

    private double angle;

    private long created;

    protected Point point;

    public long getCreated() {
        return created;
    }

    public int getOwnerId() {
        return ownerId;
    }

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
   

    public void move() {
        double x = getX();
        double y = getY();
        x += Math.cos(angle) * Config.BULLET_SPEED;
        y += Math.sin(angle) * Config.BULLET_SPEED;

        if (x < Config.BULLET_RADIUS) {
            x = Config.BULLET_RADIUS;
        } else if (x > Config.FIELD_WIDTH - Config.BULLET_RADIUS) {
            x = Config.FIELD_WIDTH - Config.BULLET_RADIUS;
        }
        if (y < Config.BULLET_RADIUS) {
            y = Config.BULLET_RADIUS;
        } else if (y > Config.FIELD_HEIGHT - Config.BULLET_RADIUS) {
            y = Config.FIELD_HEIGHT - Config.BULLET_RADIUS;
        }
        point.setLocation(x, y);
    }

    public void paint(int rx, int ry, Point p, Graphics g) {
        g.setColor(Color.BLACK);
        int x = rx - (p.x - point.x);
        int y = ry - (p.y - point.y);
        g.fillOval(x - Config.BULLET_RADIUS, y - Config.BULLET_RADIUS, Config.BULLET_RADIUS * 2, Config.BULLET_RADIUS * 2);
    }

    public Bullet(int id, int ownerId, double angle, Point point) {
        this.id = id;
        this.ownerId = ownerId;
        this.point = point;
        this.angle = angle;
        this.created = System.currentTimeMillis();
    }

    public Bullet(int id, int ownerId, Point point) {
        this(id, ownerId, 0, point);
    }

}
