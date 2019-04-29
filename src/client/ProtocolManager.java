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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import main.Main;
import shared.Bullet;
import shared.BulletsList;
import shared.Food;
import shared.FoodList;
import shared.Protocol;

/**
 *
 * @author filippofinke
 */
public class ProtocolManager extends Thread {

    private final int PORT = 5555;

    private Socket client;

    private ProtocolManagerListener listener;

    private DataOutputStream out;

    public ProtocolManager(ProtocolManagerListener listener) throws IOException {
        this.listener = listener;
        this.client = new Socket(Main.HOST, PORT);
        this.out = new DataOutputStream(client.getOutputStream());
    }

    public void send(byte command) throws IOException {
        out.write(command);
    }

    public void sendDouble(byte command, double value) throws IOException {
        send(command);
        out.writeDouble(value);
    }

    @Override
    public void run() {
        System.out.println("ProtocolManager started");
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            double read = 0;
            while (!isInterrupted()) {
                read = in.readDouble();
                if (read == Protocol.PLAYER_JOINED) {
                    int id = (int) in.readDouble();
                    Point p = new Point();
                    p.setLocation(in.readDouble(), in.readDouble());
                    ClientPlayer n = new ClientPlayer(id, p);
                    if (GamePanel.player == null) {
                        GamePanel.player = n;
                        GamePanel.mm.setPlayer(n);
                    }
                    PlayersList.add(n);
                } else if (read == Protocol.PLAYER_LEFT) {
                    PlayersList.remove((int) in.readDouble());
                } else if (read == Protocol.PLAYER_UPDATE) {
                    ClientPlayer pl = PlayersList.get((int) in.readDouble());
                    if (pl != null) {
                        pl.setPoints((int) in.readDouble());
                        pl.getPoint().setLocation(in.readDouble(), in.readDouble());
                    }
                } else if (read == Protocol.FOOD_SPAWNED) {
                    int id = (int) in.readDouble();
                    Point p = new Point();
                    p.setLocation(in.readDouble(), in.readDouble());
                    Food f = new Food(id, p);
                    FoodList.add(f);
                } else if (read == Protocol.FOOD_EATED) {
                    int id = (int) in.readDouble();
                    FoodList.remove(id);
                } else if (read == Protocol.BULLET_SPAWNED) {
                    int id = (int) in.readDouble();
                    int oid = (int) in.readDouble();
                    Point p = new Point();
                    p.setLocation(in.readDouble(), in.readDouble());
                    Bullet b = new Bullet(id, oid, p);
                    BulletsList.add(b);
                } else if (read == Protocol.BULLET_MOVED) {
                    Bullet b = BulletsList.get((int) in.readDouble());
                    if (b != null) {
                        b.getPoint().setLocation(in.readDouble(), in.readDouble());
                    }
                } else if (read == Protocol.BULLET_DESTROYED) {
                    int id = (int) in.readDouble();
                    BulletsList.remove(id);
                }
                while (in.readDouble() != Protocol.END) {
                }
                listener.update();
            }
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        System.out.println("ProtocolManager stopped");
    }

}
