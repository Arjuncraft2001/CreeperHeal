package com.nitnelave.CreeperHeal.listeners;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;

import at.pavlov.cannons.event.ProjectileImpactEvent;
import at.pavlov.cannons.projectile.Projectile;

import com.nitnelave.CreeperHeal.CreeperHeal;
import com.nitnelave.CreeperHeal.block.BurntBlockManager;
import com.nitnelave.CreeperHeal.block.CreeperBurntBlock;
import com.nitnelave.CreeperHeal.block.CreeperHanging;
import com.nitnelave.CreeperHeal.block.ExplodedBlockManager;
import com.nitnelave.CreeperHeal.config.CfgVal;
import com.nitnelave.CreeperHeal.config.CreeperConfig;
import com.nitnelave.CreeperHeal.config.WCfgVal;
import com.nitnelave.CreeperHeal.config.WorldConfig;
import com.nitnelave.CreeperHeal.utils.CreeperLog;
import com.nitnelave.CreeperHeal.utils.CreeperUtils;
import com.nitnelave.CreeperHeal.utils.FactionHandler;
import com.nitnelave.CreeperHeal.utils.Suffocating;

/**
 * Listener for the entity events.
 * 
 * @author nitnelave
 * 
 */
public class CannonListener implements Listener
{

    /**
     * Listener for the EntityExplodeEvent. Record when appropriate the
     * explosion for later replacement.
     * 
     * @param event
     *            The EntityExplode event.
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(ProjectileImpactEvent event)
    {
        WorldConfig world = CreeperConfig.getWorld(event.getImpactLocation().getWorld());

        if (!FactionHandler.shouldIgnore(event.blockList(), world))
        {
            Projectile proj = event.getProjectile();
            if (proj == null && !world.isAbove(event.getImpactLocation()))
                return;
            if (world.shouldReplace(proj))
                ExplodedBlockManager.processExplosion(event, CreeperUtils.getReason(proj));
        }
    }
}
