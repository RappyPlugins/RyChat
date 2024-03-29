package com.rappytv.rychat.commands;

import com.rappytv.rychat.RyChat;
import com.rappytv.rylib.RyLib;
import com.rappytv.rylib.util.Command;
import com.rappytv.rylib.util.I18n;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Emoji extends Command<RyChat> {

    public Emoji(String name, RyChat plugin) {
        super(name, plugin);
    }

    @Override
    public void execute(CommandSender sender, String prefix, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(RyLib.get().i18n().translate("onlyPlayer"));
            return;
        }

        if(!player.hasPermission("rychat.emojis.toggle")) {
            player.sendMessage(RyLib.get().i18n().translate("noPermission"));
            return;
        }
        boolean active = !player.hasPermission("rychat.emojis");
        User user = plugin.lp.getPlayerAdapter(Player.class).getUser(player);
        user.data().add(Node.builder("chat.emojis").value(active).build());
        plugin.lp.getUserManager().saveUser(user);

        String text = active
                ? plugin.i18n().translate("command.emoji.activated")
                : plugin.i18n().translate("command.emoji.deactivated");
        player.sendMessage(
                plugin.i18n().translate(
                        "command.emoji.success",
                        new I18n.Argument("state", text)
                )
        );
    }

    @Override
    public List<String> complete(CommandSender sender, String prefix, String[] args) {
        return null;
    }
}
