/**
 * flyingblocksapi Copyright (C) 2014 ase34 and contributors
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.ase34.flyingblocksapi.natives.v1_7_R4.util;

import net.minecraft.server.v1_7_R4.EntityFallingBlock;
import net.minecraft.server.v1_7_R4.EntityHorse;
import net.minecraft.server.v1_7_R4.EntityWitherSkull;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;

import de.ase34.flyingblocksapi.FlyingBlock;
import de.ase34.flyingblocksapi.natives.v1_7_R4.entities.CustomFallingBlock;
import de.ase34.flyingblocksapi.natives.v1_7_R4.entities.CustomHorse;
import de.ase34.flyingblocksapi.natives.v1_7_R4.entities.CustomWitherSkull;

public class SpawnHelper {

    public static EntityWitherSkull spawn(FlyingBlock flyingBlock, Location location) {
        World world = ((CraftWorld) location.getWorld()).getHandle();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        EntityWitherSkull skull = new CustomWitherSkull(flyingBlock, world);
        skull.setPosition(x, y + flyingBlock.getHeightOffset(), z);
        world.addEntity(skull);

        EntityHorse horse = new CustomHorse(world);
        horse.setAge(flyingBlock.getHorseAge());
        horse.setPosition(x, y + flyingBlock.getHeightOffset(), z);
        world.addEntity(horse);

        EntityFallingBlock block = new CustomFallingBlock(horse, world);
        block.id = CraftMagicNumbers.getBlock(flyingBlock.getMaterial());
        block.data = flyingBlock.getMaterialData();
        block.setPosition(x, y + flyingBlock.getHeightOffset(), z);
        world.addEntity(block);

        block.setPassengerOf(horse);
        horse.setPassengerOf(skull);

        return skull;
    }

}
