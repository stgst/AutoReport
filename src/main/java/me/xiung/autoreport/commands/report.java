package me.xiung.autoreport.commands;

import me.xiung.autoreport.AutoReport;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static me.xiung.autoreport.AutoReport.plugin;

public class report implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if( sender instanceof Player ){
            Player player = (Player) sender;
            JDA bot = AutoReport.bot;

            if(args.length == 2){
                String targetName = args[0];
                Player target = Bukkit.getServer().getPlayerExact(targetName);

                if( target == null){
                    player.sendMessage(ChatColor.RED + "你檢舉的玩家不在線上");
                }else {
                    String reason = args[1];
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7你檢舉了 &6" + target.getName() + "&7 ,因為 &e" + reason));

                    String channelId = plugin.getConfig().getString("Discord.ReportCH");

                    Button ban = Button.danger("ban7d", "封鎖 7 天");
                    Button mute = Button.secondary("mute1h", "禁言 1 小時");
                    Button cancel = Button.primary("cancel", "移除檢舉玩家權限");

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("玩家檢舉功能");
                    embed.addField("檢舉玩家", player.getName(), false);
                    embed.addField("被檢舉玩家", target.getName(), false);
                    embed.addField("理由", reason, false);

                    ArrayList<Button> buttons = new ArrayList<Button>();
                    buttons.add(ban);
                    buttons.add(mute);
                    buttons.add(cancel);

                    Message message = (Message) new MessageBuilder()
                            .setEmbeds(embed.build())
                            .setActionRows(ActionRow.of(buttons))
                            .build();

                    bot.getTextChannelById(channelId).sendMessage(message).queue();
                }
            }else {
                player.sendMessage(ChatColor.RED + "Usage: /report <player> <reason>");
                return true;
            }
        }
        return true;
    }
}
