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

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import shared.Protocol;

/**
 *
 * @author filippofinke
 */
public class MovementThread extends Thread implements MouseMotionListener, MouseListener {

    private double angle;

    private ProtocolManager pm;

    private JPanel container;

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public MovementThread(JPanel container, ProtocolManager pm) {
        this.container = container;
        this.pm = pm;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        while (!isInterrupted()) {
            if (System.currentTimeMillis() - start > 15) {
                try {
                    pm.sendDouble(Protocol.PLAYER_MOVE, angle);
                } catch (IOException ex) {
                    System.out.println("Error sending movement");
                }
                start = System.currentTimeMillis();
            }
        }
    }
    
    private double getAngle(Point destination)
    {
        double diffx = destination.getX() - container.getWidth() / 2;
        double diffy = destination.getY() - container.getHeight() / 2;
        return Math.atan2(diffy, diffx);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        GamePanel.mouse = e.getPoint();
        angle = getAngle(e.getPoint());
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        double shotAngle = getAngle(e.getPoint());
        try {
            pm.sendDouble(Protocol.PLAYER_SHOOT, shotAngle);
        } catch (IOException ex) {
            System.out.println("Error shooting!");
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
