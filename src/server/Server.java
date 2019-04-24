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
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import shared.Bullet;
import shared.Config;
import shared.Food;
import shared.Protocol;

/**
 *
 * @author filippofinke
 */
public class Server extends Thread {

    private ServerSocket server;

    public static void playerJoined(ServerPlayer p) {
        ThreadsList.broadcast(new double[]{
            Protocol.PLAYER_JOINED,
            p.getId(),
            p.getX(),
            p.getY(),
            Protocol.END
        });
    }

    public static void playerUpdate(ServerPlayer p) {
        ThreadsList.broadcast(new double[]{
            Protocol.PLAYER_UPDATE,
            p.getId(),
            p.getPoints(),
            p.getX(),
            p.getY(),
            Protocol.END
        });
    }

    public static void playerLeft(ServerPlayer p) {
        ThreadsList.broadcast(new double[]{
            Protocol.PLAYER_LEFT,
            p.getId(),
            Protocol.END
        });
    }
    
    public static void foodSpawned(Food f) {
        ThreadsList.broadcast(new double[]{
            Protocol.FOOD_SPAWNED,
            f.getId(),
            f.getX(),
            f.getY(),
            Protocol.END
        });
    }
    
    public static void foodEated(Food f)
    {
        ThreadsList.broadcast(new double[]{
            Protocol.FOOD_EATED,
            f.getId(),
            Protocol.END
        });
    }
    
    public static void bulletSpawned(Bullet b)
    {
        ThreadsList.broadcast(new double[]{
            Protocol.BULLET_SPAWNED,
            b.getId(),
            b.getOwnerId(),
            b.getX(),
            b.getY(),
            Protocol.END
        });
    }
    
    public static void bulletUpdate(Bullet b) {
        ThreadsList.broadcast(new double[]{
            Protocol.BULLET_MOVED,
            b.getId(),
            b.getX(),
            b.getY(),
            Protocol.END
        });
    }
    
    public static void bulletDestroyed(Bullet b) {
        ThreadsList.broadcast(new double[]{
            Protocol.BULLET_DESTROYED,
            b.getId(),
            Protocol.END
        });
    }

    public static Point getRandomPoint(int radius) {
        return new Point(
                (int) (Math.random() * (Config.FIELD_WIDTH - radius - radius)) + radius,
                (int) (Math.random() * (Config.FIELD_HEIGHT - radius - radius)) + radius
        );
    }

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port);
        FoodManager fm = new FoodManager();
        BulletsManager bm = new BulletsManager();
        fm.start();
        bm.start();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.println("Waiting for a user...");
            try {
                Socket client = server.accept();
                System.out.println("Client accepted");
                int index = ThreadsList.getFreeIndex();
                if (index != -1) {
                    System.out.println("ServerThread started");
                    ServerThread thread = new ServerThread(client, index);
                    thread.start();
                    ThreadsList.add(thread);
                } else {
                    System.out.println("The server is full, disconnecting client");
                    client.getOutputStream().write(Protocol.SERVER_FULL);
                    client.close();
                }
            } catch (IOException ex) {
                System.out.println("Error accepting user: " + ex);
            }
        }
        System.out.println("Stopped server thread.");
    }
}
