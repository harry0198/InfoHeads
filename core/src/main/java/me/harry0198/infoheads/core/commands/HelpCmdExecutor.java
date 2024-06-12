package me.harry0198.infoheads.core.commands;


import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;
import me.harry0198.infoheads.core.utils.Constants;

/**
 * Executor for the help command, provides a help message
 * to the sender upon execution.
 */
public class HelpCmdExecutor extends CmdExecutor {

    /**
     * {@inheritDoc}
     */
    public HelpCmdExecutor(LocalizedMessageService localizedMessageService) {
        super(localizedMessageService,Constants.BASE_PERMISSION + "help");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean executeCmd(OnlinePlayer sender) {
        sender.sendMessage(getLocalizedMessageService().getMessage(BundleMessages.HELP));
        return true;
    }
}
