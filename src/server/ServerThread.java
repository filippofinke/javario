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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import shared.Config;
import shared.Food;
import shared.FoodList;
import shared.Protocol;

/**
 *
 * @author filippofinke
 */
public class ServerThread extends Thread {

    private Socket client;

    private ServerPlayer player;

    private DataInputStream in;

    private DataOutputStream out;

    private int index;

    public int getIndex() {
        return index;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public void send(double[] array) throws IOException {
        for (double i : array) {
            out.writeDouble(i);
        }
    }

    public ServerThread(Socket client, int index) throws IOException {
        this.client = client;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
        this.index = index;
        this.player = new ServerPlayer(index, Server.getRandomPoint(Config.PLAYER_RADIUS));
    }

    @Override
    public void run() {
        Server.playerJoined(player);
        for (ServerThread thread : ThreadsList.getThreads()) {
            if (thread != null) {
                ServerPlayer p = thread.getPlayer();
                if (p != this.player) {
                    try {
                        this.send(new double[]{
                            Protocol.PLAYER_JOINED,
                            p.getId(),
                            p.getPoints(),
                            p.getX(),
                            p.getY()
                        });
                    } catch (IOException ex) {
                        System.out.println("Error sending player info");
                    }
                }
            }
        }
        for (Food f : FoodList.getFood()) {
            if (f != null) {
                try {
                    this.send(new double[]{
                        Protocol.FOOD_SPAWNED,
                        f.getId(),
                        f.getX(),
                        f.getY()
                    });
                } catch (IOException ex) {
                    System.out.println("Error sending food info");
                }
            }
        }
        try {
            long shootDelay = System.currentTimeMillis();
            int read = 0;
            while (((read = in.readByte()) != -1) && !isInterrupted()) {
                if (read == Protocol.PLAYER_MOVE) {
                    double angle = in.readDouble();
                    player.move(angle);
                } else if (read == Protocol.PLAYER_SHOOT) {
                    double angle = in.readDouble();
                    if (System.currentTimeMillis() - shootDelay > Config.BULLET_DELAY) {
                        player.shoot(angle);
                        shootDelay = System.currentTimeMillis();
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(this.getName() + " error reading socket");
        }
        System.out.println(this.getName() + " stopped");
        ThreadsList.remove(this);
        Server.playerLeft(player);
    }

}
