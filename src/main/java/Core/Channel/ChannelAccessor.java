package Core.Channel;

import Core.Channel.Enums.ActiveStatus;
import Core.Channel.Enums.MicStatus;
import Core.Channel.Enums.MuteStatus;

public class ChannelAccessor extends AbstractChannelAccessor{
    @Override
    public void setName(AbstractChannel channel, String name) {
        channel.setName(name);
    }

    @Override
    public void setVolume(AbstractChannel channel, int level) {
        channel.setVolume(level);
    }

    @Override
    public void setActiveStatus(AbstractChannel channel, ActiveStatus status) {
        channel.setActiveStatus(status);
    }

    @Override
    public void setMuteStatus(AbstractChannel channel, MuteStatus status) {
        channel.setMuteStatus(status);
    }

    @Override
    public void setMicStatus(AbstractChannel channel, MicStatus status) {
        channel.setMicStatus(status);
    }

    @Override
    public void setOnScreen(AbstractChannel channel, boolean value) {
        channel.setOnScreen(value);
    }
}
