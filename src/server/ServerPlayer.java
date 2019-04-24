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
package server;

import java.awt.Point;
import shared.Bullet;
import shared.BulletsList;
import shared.Config;
import shared.Food;
import shared.FoodList;
import shared.Player;

/**
 *
 * @author filippofinke
 */
public class ServerPlayer extends Player {

    public void move(double angle) {
        double x = point.getX();
        double y = point.getY();
        x += Math.cos(angle) * Config.PLAYER_SPEED;
        y += Math.sin(angle) * Config.PLAYER_SPEED;

        if (x < getRadius()) {
            x = getRadius();
        } else if (x > Config.FIELD_WIDTH - getRadius()) {
            x = Config.FIELD_WIDTH - getRadius();
        }
        if (y < getRadius()) {
            y = getRadius();
        } else if (y > Config.FIELD_HEIGHT - getRadius()) {
            y = Config.FIELD_HEIGHT - getRadius();
        }

        point.setLocation(x, y);
        collisionsCheck();
    }
    
    public void shoot(double angle) {
        if(getPoints() - Config.BULLET_COST >= 0)
        {
            setPoints(getPoints() - Config.BULLET_COST);
            Point p = new Point();
            p.setLocation(getX(), getY());
            Bullet b = new Bullet(BulletsList.getFreeIndex(), getId(), angle, p);
            BulletsList.add(b);
            Server.bulletSpawned(b);
        }
    }

    public void respawn() {
        setPoints(0);
        point = Server.getRandomPoint(getRadius());
    }

    public boolean contains(Point p, int pradius) {
        return point.distance(p) + pradius <= getRadius();
    }

    private void collisionsCheck() {
        for (ServerThread thread : ThreadsList.getThreads()) {
            if (thread != null) {
                ServerPlayer sp = thread.getPlayer();
                if (sp != null && sp != this) {
                    if (contains(sp.getPoint(), sp.getRadius())) {
                        setPoints(getPoints() + sp.getPoints() / 2);
                        sp.respawn();
                        Server.playerUpdate(sp);
                    } else if (sp.contains(getPoint(), getRadius())) {
                        sp.setPoints(sp.getPoints() + getPoints() / 2);
                        respawn();
                        Server.playerUpdate(sp);
                    }
                }
            }
        }
        for (Food f : FoodList.getFood()) {
            if (f != null) {
                if (contains(f.getPoint(), Config.FOOD_RADIUS)) {
                    this.addPoint();
                    FoodList.remove(f.getId());
                    Server.foodEated(f);
                }
            }
        }
        Server.playerUpdate(this);
    }

    public ServerPlayer(int id, Point point) {
        super(id, point);
    }

}
