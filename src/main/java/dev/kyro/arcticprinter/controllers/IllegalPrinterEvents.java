package dev.kyro.arcticprinter.controllers;

import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticprinter.ArcticPrinter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class IllegalPrinterEvents implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public static void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if(!PrinterManager.inPrinter(player) || event.getCursor() == null) return;

        if(ArcticPrinter.illegalItems.contains(event.getCursor().getType())) {

            player.setItemOnCursor(new ItemStack(Material.AIR));
            event.setCancelled(true);
            AOutput.error(player, "That item is not allowed");
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public static void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player) || event.getItem() == null) return;
        ItemStack itemStack = event.getItem();

        if(ArcticPrinter.illegalItems.contains(itemStack.getType())) {

            player.setItemInHand(new ItemStack(Material.AIR));
            event.setCancelled(true);
            AOutput.error(player, "That item is not allowed");
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public static void onEntityInteract(PlayerInteractAtEntityEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public static void onItemPickup(PlayerPickupItemEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public static void onItemDrop(PlayerDropItemEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public static void onAttack(EntityDamageByEntityEvent event) {

        if(!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public static void inventoryOpen(InventoryOpenEvent event) {

        Player player = (Player) event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);
    }
}
