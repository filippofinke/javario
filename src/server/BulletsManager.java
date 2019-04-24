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

import shared.Bullet;
import shared.BulletsList;
import shared.Config;
import shared.Player;

/**
 *
 * @author filippofinke
 */
public class BulletsManager extends Thread {

    public BulletsManager() {
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        System.out.println("BulletsManager - Started at " + start);
        while (!isInterrupted()) {
            if (System.currentTimeMillis() - start > Config.BULLET_UPDATE_DELAY) {
                start = System.currentTimeMillis();
                for (Bullet b : BulletsList.getBullets()) {
                    if (b != null) {
                        if (System.currentTimeMillis() - b.getCreated() < Config.BULLET_TIMEOUT) {
                            b.move();
                        } else {
                            BulletsList.remove(b.getId());
                            Server.bulletDestroyed(b);
                        }
                    }
                }
                for (Bullet b : BulletsList.getBullets()) {
                    if (b != null) {
                        for (ServerThread thread : ThreadsList.getThreads()) {
                            if (thread != null) {
                                ServerPlayer p = thread.getPlayer();
                                if (p != null) {
                                    if (p.getId() != b.getOwnerId() && p.contains(b.getPoint(), Config.BULLET_RADIUS)) {
                                        ServerPlayer owner = ThreadsList.get(b.getOwnerId()).getPlayer();
                                        int np = p.getPoints() - Config.BULLET_COST * 2;
                                        if (np >= 0) {
                                            owner.setPoints(owner.getPoints() + Config.BULLET_COST * 2);
                                            p.setPoints(np);
                                        } else {
                                            owner.setPoints(owner.getPoints() + p.getPoints());
                                            p.respawn();
                                        }
                                        BulletsList.remove(b.getId());
                                        Server.bulletDestroyed(b);
                                        Server.playerUpdate(owner);
                                        Server.playerUpdate(p);
                                    }
                                }
                            }
                        }
                        Server.bulletUpdate(b);
                    }
                }
            }
        }
    }
}
