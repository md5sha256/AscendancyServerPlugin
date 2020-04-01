package com.gmail.andrewandy.ascendency.serverplugin.game.util;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Collection;
import java.util.HashSet;

public class MathUtils {

    public static Collection<Location<World>> createCircle(Location<World> centre, int radius) {
        if (radius < 0) {
            radius = -radius;
        }
        int x = centre.getBlockX();
        int y = centre.getBlockY();
        int z = centre.getBlockZ();
        //Eq x2 = r2 - z2 // r2 = x2 + z2 // z2 = r2 - x2
        int startX = x + radius;
        int startZ = x + radius;
        Collection<Location<World>> locations = new HashSet<>();
        int last = Integer.MAX_VALUE;
        for (int index = startX; last != startX; index++) {
            double zCoord = Math.pow(radius, 2) - Math.pow(z, 2);
            locations.add(new Location<>(centre.getExtent(), index, y, zCoord));
            last = index;
        }
        return locations;
    }

}
