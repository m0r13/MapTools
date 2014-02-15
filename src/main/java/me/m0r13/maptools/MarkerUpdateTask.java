/*
 * Copyright 2013 Moritz Hilscher
 *
 * This file is part of MapTools.
 *
 * MapTools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MapTools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MapTools.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.m0r13.maptools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MarkerUpdateTask extends BukkitRunnable {

    private MapToolsPlugin plugin;

    public MarkerUpdateTask(MapToolsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        writePlayers(plugin.getServer().getOnlinePlayers());
    }

    public void writePlayers(Player[] players) {
        JSONArray playersJson = new JSONArray();
        for (Player player : players) {
            JSONObject json = new JSONObject();

            Location pos = player.getLocation();
            World world = player.getWorld();

            json.put("username", player.getName());
            json.put("x", pos.getX());
            json.put("y", pos.getY());
            json.put("z", pos.getZ());
            json.put("world", world.getName());
            json.put("dimension", world.getEnvironment().toString());
            json.put("health", player.getHealth());
            json.put("saturation", player.getSaturation());
            playersJson.add(json);
        }
        JSONObject json = new JSONObject();
        json.put("players", playersJson);

        try {
            File file = new File(plugin.getConfig().getString("markerFile"));
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(json.toJSONString());
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
