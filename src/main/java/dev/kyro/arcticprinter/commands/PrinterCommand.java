package dev.kyro.arcticprinter.commands;

import com.massivecraft.factions.FPlayers;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticprinter.controllers.PrinterManager;
import dev.kyro.arcticprinter.enums.PrinterEndReason;
import dev.kyro.arcticprinter.objects.PrinterPlayer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class PrinterCommand implements CommandExecutor {

    @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {

            System.out.println("no lol");
            return false;
        }

        Player player = (Player) sender;

        if(PrinterManager.inPrinter(player)) {

            PrinterManager.getPrinterPlayer(player).exitPrinter(PrinterEndReason.COMMAND_DISABLE);
        } else {

            if(!FPlayers.getInstance().getByPlayer(player).isInOwnTerritory()) {

                AOutput.error(player, "Not in your own territory");
                return false;
            }

            for(ItemStack itemStack : player.getInventory()) {

                if(itemStack != null && itemStack.getType() != Material.AIR) {

                    AOutput.error(player, "Inventory is not empty");
                    return false;
                }
            }

            for(ItemStack itemStack : player.getInventory().getArmorContents()) {

                if(itemStack != null && itemStack.getType() != Material.AIR) {

                    AOutput.error(player, "Please take off your armor");
                    return false;
                }
            }

            new PrinterPlayer(player);
        }

        return false;
    }
}
