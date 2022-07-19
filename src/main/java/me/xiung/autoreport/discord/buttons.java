package me.xiung.autoreport.discord;

import me.xiung.autoreport.AutoReport;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.xiung.autoreport.AutoReport.plugin;

public class buttons extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent e){

        Message message = e.getMessage();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String target = message.getEmbeds().get(0).getFields().get(1).getValue();
        String reported = message.getEmbeds().get(0).getFields().get(0).getValue();
        JDA bot = AutoReport.bot;

        if (e.getButton().getId().equals("ban7d")){

            String reason = message.getEmbeds().get(0).getFields().get(2).getValue();
            String cmd = plugin.getConfig().getString("Buttons.ban7d");
            cmd = cmd.replace("{player}", target);
            cmd = cmd.replace("{reason}", reason);
            String finalCmd = cmd;
            Bukkit.getScheduler().runTask(plugin, () -> {
                    Bukkit.dispatchCommand(console, finalCmd);
            });

            message.delete().queue();
            e.getChannel().sendMessage("已封鎖 `" + target + "` 7 天").queue();

        }else if(e.getButton().getId().equals("mute1h")){

            String reason = message.getEmbeds().get(0).getFields().get(2).getValue();
            String cmd = plugin.getConfig().getString("Buttons.mute1h");
            cmd = cmd.replace("{player}", target);
            cmd = cmd.replace("{reason}", reason);
            String finalCmd = cmd;
            Bukkit.getScheduler().runTask(plugin, () -> {
                Bukkit.dispatchCommand(console, finalCmd);
            });

            message.delete().queue();
            e.getChannel().sendMessage("已禁言 `" + target + "` 1 小時").queue();

        }else if(e.getButton().getId().equals("cancel")){

            String cmd = plugin.getConfig().getString("Buttons.cancel");
            cmd = cmd.replace("{player}", reported);
            String finalCmd = cmd;
            Bukkit.getScheduler().runTask(plugin, () -> {
                Bukkit.dispatchCommand(console, finalCmd);
            });

            message.delete().queue();
            e.getChannel().sendMessage("已移除 `" + target + "` 的權限").queue();

        }
    }

}
