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

import java.io.IOException;
import shared.Config;

/**
 *
 * @author filippofinke
 */
public class ThreadsList {

    private static ServerThread threads[] = new ServerThread[Config.MAX_PLAYERS];

    public static ServerThread[] getThreads() {
        return threads;
    }

    public static int getFreeIndex() {
        for (int i = 0; i < threads.length; i++) {
            if (threads[i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    public static ServerThread get(int id) {
        if (id >= 0 && id < threads.length) {
            return threads[id];
        }
        return null;
    }

    public static void remove(ServerThread thread) {
        threads[thread.getIndex()] = null;
    }

    public static void add(ServerThread thread) {
        threads[thread.getIndex()] = thread;
    }

    public static synchronized void broadcast(double[] array) {
        for (int i = 0; i < threads.length; i++) {
            if (threads[i] != null) {
                try {
                    threads[i].send(array);
                } catch (IOException ex) {
                    System.out.println("Error sending info");
                }
            }
        }
    }
}
