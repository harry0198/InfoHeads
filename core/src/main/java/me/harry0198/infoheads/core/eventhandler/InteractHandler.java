package me.harry0198.infoheads.core.eventhandler;

import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.Player;

public class InteractHandler {

    public void interactWithHead(Player snapshot, Location interactedWithLocation) {
//        // Is a valid block & has basic use perms
//        if (isOffHand(e) || e.getClickedBlock() == null || !e.getPlayer().hasPermission(Constants.BASE_PERM + "use"))
//            return;
//
//        Map<Location, InfoHeadConfiguration> infoHeads = plugin.getDataStore().getInfoHeads();
//
//        // Checks if there is an infohead at this location
//        if (!infoHeads.containsKey(e.getClickedBlock().getLocation())) return;
//
//        if (e.getPlayer().isSneaking() && e.getAction().equals(Action.LEFT_CLICK_BLOCK))
//            return;
//
//        e.setCancelled(true);
//
//        String permission = plugin.getDataStore().getInfoHeads().get(e.getClickedBlock().getLocation()).getPermission();
//        InfoHeadConfiguration configuration = infoHeads.get(e.getClickedBlock().getLocation());
//
//        if (e.getPlayer().isSneaking() && e.getPlayer().hasPermission(Constants.ADMIN_PERM)) {
//            new WizardGui(new WizardViewModel(plugin, configuration)).open(e.getPlayer());
//            return;
//        }
//
//        // Checks if player has infohead specific perms
//        if (permission != null)
//            if (!e.getPlayer().hasPermission(permission)) {
//                MessageUtil.sendMessage(e.getPlayer(), MessageUtil.Message.NO_PERMISSION);
//                return;
//            }
//
//        // Checks if player is on cooldown
//
//        if (configuration.getTimestamps().containsKey(e.getPlayer().getUniqueId())) {
//
//            long playerTimestamp = configuration.getTimestamps().get(e.getPlayer().getUniqueId());
//            if (playerTimestamp > System.currentTimeMillis()) {
//                e.getPlayer().sendMessage(MessageUtil.returnTimeMessage(playerTimestamp - System.currentTimeMillis(), MessageUtil.getString(MessageUtil.Message.COOLDOWN)));
//                return;
//            } else {
//                configuration.getTimestamps().remove(e.getPlayer().getUniqueId());
//            }
//        }
//
//        if (configuration.getExecuted().contains(e.getPlayer().getUniqueId())) {
//            MessageUtil.sendMessage(e.getPlayer(), MessageUtil.Message.ONE_TIME);
//            return;
//        }
//
//        final List<Element> elements = new ArrayList<>(plugin.getDataStore().getInfoHeads().get(e.getClickedBlock().getLocation()).getElementList());
//        elements.add(new EndElement(elements));
//
//        // Loops through elements
//        Iterator<Element> element = elements.iterator();
//        int time = 0;
//        while (element.hasNext()) {
//            Element el = element.next();
//            if (el.getType().equals(Element.InfoHeadType.DELAY))
//                time = time + (int) el.getContent();
//
//            Bukkit.getScheduler().runTaskLater(plugin, () -> {
//
//                if (e.getPlayer() != null && e.getPlayer().isOnline())
//                    el.performAction(e.getPlayer(), e);
//
//            }, time * 20L);
//        }
//
//        // Permission Removal -> EndElement
//
//        // If Cooldown Exists, add it to player's stamp
//        if (configuration.getCooldown() != null) {
//            configuration.getTimestamps().put(e.getPlayer().getUniqueId(), System.currentTimeMillis() + configuration.getCooldown().toMs());
//        }
//
//        if (configuration.isOnce()) {
//            configuration.getExecuted().add(e.getPlayer().getUniqueId());
//        }

    }

//    /**
//     * Define if the hand used in event is off hand
//     *
//     * @param event Event to analyse
//     * @return Is off hand
//     */
//    private boolean isOffHand(final PlayerInteractEvent event) {
//        try {
//            return event.getHand() == EquipmentSlot.OFF_HAND;
//        } catch (NoSuchMethodError e) {
//            return false;
//        }
//    }
}
