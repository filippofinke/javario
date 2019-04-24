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

import shared.Config;
import shared.Food;
import shared.FoodList;

/**
 *
 * @author filippofinke
 */
public class FoodManager extends Thread {

    public FoodManager() {
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        System.out.println("FoodManager - Started at " + start);
        while (!isInterrupted()) {
            int index = FoodList.getFreeIndex();
            if (index != -1 && System.currentTimeMillis() - start > Config.FOOD_DELAY) {
                Food f = new Food(index, Server.getRandomPoint(Config.FOOD_RADIUS));
                FoodList.add(f);
                Server.foodSpawned(f);
                start = System.currentTimeMillis();
            }
        }
    }

}
