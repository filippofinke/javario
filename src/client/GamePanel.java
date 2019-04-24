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
import java.io.IOException;
import javax.swing.JPanel;
import shared.Bullet;
import shared.BulletsList;
import shared.Food;
import shared.FoodList;

/**
 *
 * @author filippofinke
 */
public class GamePanel extends JPanel implements ProtocolManagerListener {

    private MovementThread mt;

    public static ClientPlayer player = null;

    private String status = "";
    
    public static Point mouse = null;

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawString("@ Filippo Finke", 3, getHeight() - 5);
        if (player != null) {
            Point mainPlayer = player.getPoint();

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            for (Food f : FoodList.getFood()) {
                if (f != null) {
                    f.paint(centerX, centerY, mainPlayer, g);
                }
            }

            for (ClientPlayer player : PlayersList.getPlayers()) {
                if (player != null) {
                    player.paint(centerX, centerY, mainPlayer, g);
                }
            }
            
            for (Bullet b : BulletsList.getBullets()) {
                if (b != null) {
                    b.paint(centerX, centerY, mainPlayer, g);
                }
            }
        } else {
            g.setColor(Color.BLACK);
            int sWidth = g.getFontMetrics().stringWidth(status);
            g.drawString(status, (getWidth() - sWidth) / 2, getHeight() / 2);
        }
        
        
        /*if(mouse != null)
        {
            g.setColor(Color.BLACK);
            g.drawLine(getWidth() / 2, getHeight() / 2, mouse.x, getHeight() / 2);
            g.drawLine(mouse.x, getHeight() / 2, mouse.x, mouse.y);
            g.drawLine(getWidth() / 2, getHeight() / 2, mouse.x, mouse.y);
            double diffx = mouse.getX() - getWidth() / 2;
            double diffy = mouse.getY() - getHeight() / 2;
            g.drawString("Diffx:" + diffx, getWidth() / 2, getHeight() / 2 + 30);
            g.drawString("Diffy:" + diffy, getWidth() / 2, getHeight() / 2 + 40);
            double angle = Math.atan2(diffy, diffx);
            g.drawString("Angle: " + angle + " (" + Math.toDegrees(angle) + "°)",getWidth() / 2, getHeight() / 2 + 60);
            
            int nextX = (int)(Math.cos(angle) * 200);
            int nextY = (int)(Math.sin(angle) * 200);
            int cw = getWidth() / 2; 
            int ch = getHeight() / 2;
            g.setColor(Color.RED);
            g.drawLine(cw, ch, cw + nextX, ch + nextY);
        }*/
    }

    public GamePanel() {
        try {
            status = "Connecting to the server..";
            ProtocolManager pm = new ProtocolManager(this);
            pm.start();

            mt = new MovementThread(this, pm);
            this.addMouseMotionListener(mt);
            this.addMouseListener(mt);
            mt.start();
        } catch (IOException ex) {
            System.out.println("Error can't connect to the server");
            status = "Can't connect to the server!";
        }
    }

    @Override
    public void update() {
        repaint();
    }
}
