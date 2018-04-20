/*
 * Written in 2018 by Daniel Saukel
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software
 * to the public domain worldwide.
 *
 * This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software. If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.erethon.holographicmenus.example;

import de.erethon.holographicmenus.HolographicMenus;
import de.erethon.holographicmenus.menu.HButton;
import de.erethon.holographicmenus.menu.HButton.Type;
import de.erethon.holographicmenus.menu.HButtonBuilder;
import de.erethon.holographicmenus.menu.HButtonClickEvent;
import de.erethon.holographicmenus.menu.HMenu;
import de.erethon.holographicmenus.menu.HMenuPage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class of HMExample
 *
 * @author Daniel Saukel
 */
public class HMExample extends JavaPlugin implements Listener {

    private HMenu menu1;
    private HButton button1;

    private void setupMenus() {
        menu1 = new HMenu("Name Menu 1", HMenu.Type.PRIVATE);
        menu1.setFollowingOnMove(true);
        menu1.setDistance(2.0D);
        menu1.setRotationTolerance(15.0F);
        HMenu menu2 = new HMenu("Name Menu 2", HMenu.Type.PRIVATE, 2.0D, 15.0F);

        // Buttons can easily be built with the builder class.
        button1 = new HButtonBuilder("&4Button Text").type(Type.TITLE).pos(-.1, .3).build();
        // Leaving out the type() method will make the builder use the BUTTON type
        HButton button2 = new HButtonBuilder(new ItemStack(Material.IRON_SWORD)).pos(.1, 0).close(true).perm("my.node").build();
        HButton button3 = new HButtonBuilder("&9Give iron sword").pos(0, 0).type(Type.BUTTON)
                .close(true).sound("ui.button.click").cmd("minecraft:give %v1% iron_sword").varMsgs("Enter player name").build();
        HButton staticButton = new HButtonBuilder("&2Next Page").type(Type.NEXT_PAGE).pos(0, .6).build();
        HMenuPage page1 = new HMenuPage(button1, button2);
        HMenuPage page2 = new HMenuPage(button3);
        menu1.addMenuPage(page1, page2);
        menu1.addStaticButton(staticButton);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        setupMenus();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            menu1.open(HolographicMenus.getInstance(), (Player) sender);
            return true;
        } else {
            return false;
        }
    }

    @EventHandler
    public void onButtonClick(HButtonClickEvent event) {
        Player player = event.getPlayer();
        if (event.getMenu() != menu1) {
            return;
        }
        player.sendMessage("HButtonClickEvent allows you to perform custom actions when a button is clicked");
        if (event.getButton() == button1) {
            event.getPlayer().sendMessage("You clicked button 1!");
        } else if (event.getButton().getLabel(event.getPlayer()).contains("iron sword")) {
            // Do stuff
        }
    }

}
