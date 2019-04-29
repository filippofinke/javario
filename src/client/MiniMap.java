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
import javax.swing.JPanel;
import shared.Bullet;
import shared.BulletsList;
import shared.Config;
import shared.Player;

/**
 *
 * @author Carlo Pezzotti
 */
public class MiniMap extends JPanel {

    private Player player;

    public MiniMap() {
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void paint(Graphics g) {
        this.setSize(Config.FIELD_WIDTH / Config.MINIMAP_ZOOM, Config.FIELD_HEIGHT / Config.MINIMAP_ZOOM);
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.black);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.red);
        for (Player p : PlayersList.getPlayers()) {
            if (p != null) {
                if (p != player) {
                    int r = p.getRadius() / Config.MINIMAP_ZOOM;
                    g.fillOval((int) p.getX() / Config.MINIMAP_ZOOM - r, (int) p.getY() / Config.MINIMAP_ZOOM - r, r * 2, r * 2);
                }
            }
        }
        g.setColor(Color.BLACK);
        for (Bullet b : BulletsList.getBullets()) {
            if (b != null) {
                int r = Config.BULLET_RADIUS / Config.MINIMAP_ZOOM;
                g.fillOval((int) b.getX() / Config.MINIMAP_ZOOM - r, (int) b.getY() / Config.MINIMAP_ZOOM - r, r * 2, r * 2);
            }
        }
        g.setColor(Color.BLUE);
        int r = player.getRadius() / Config.MINIMAP_ZOOM;
        g.fillOval((int) player.getX() / Config.MINIMAP_ZOOM - r, (int) player.getY() / Config.MINIMAP_ZOOM - r, r * 2, r * 2);
    }
}
