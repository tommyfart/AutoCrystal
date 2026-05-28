package com.example.autocrystal;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Hand;

public class AutoCrystal implements ClientModInitializer {

    private static final double RANGE = 4.5;

    @Override
    public void onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null ||
                client.world == null ||
                client.interactionManager == null) {
                return;
            }

            var nearbyCrystals = client.world.getEntitiesByClass(
                    EndCrystalEntity.class,
                    client.player.getBoundingBox().expand(RANGE),
                    crystal -> true
            );

            for (EndCrystalEntity crystal : nearbyCrystals) {

                client.interactionManager.attackEntity(
                        client.player,
                        crystal
                );

                client.player.swingHand(Hand.MAIN_HAND);

                break;
            }
        });
    }
}
