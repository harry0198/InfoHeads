package me.harry0198.infoheads.core.elements;

import me.harry0198.infoheads.core.model.Player;

public abstract class Element {

    /**
     * Call to specific action
     */
    public abstract void performAction(Player player);

    /**
     * Get content of Element
     * @return Object of Element
     */
    public abstract Object getContent();

    /**
     * Gets Type Element
     * @return InfoHeadType
     */
    public abstract InfoHeadType getType();

//    protected String removePlaceHolders(String string, PlayerDetailSnapshot player, PlayerInteractEvent event) {
//        string = ChatColor.translateAlternateColorCodes('&', string);
//        string = string.replace("{player-name}", player.getName());
//        string = string.replace("{player-x}", String.valueOf(player.getLocation().getBlockX()));
//        string = string.replace("{player-y}", String.valueOf(player.getLocation().getBlockY()));
//        string = string.replace("{player-z}", String.valueOf(player.getLocation().getBlockZ()));
//        string = string.replace("{block-x}", String.valueOf(event.getClickedBlock().getLocation().getBlockX()));
//        string = string.replace("{block-y}", String.valueOf(event.getClickedBlock().getLocation().getBlockY()));
//        string = string.replace("{block-z}", String.valueOf(event.getClickedBlock().getLocation().getBlockZ()));
//        if (InfoHeads.getInstance().papi)
//            string = PapiHook.execute(player, string);
//        return string;
//    }

    public enum InfoHeadType {
        MESSAGE, CONSOLE_COMMAND, PLAYER_COMMAND, DELAY, PLAYER_PERMISSION, END, PERMISSION, RENAME
    }
}
