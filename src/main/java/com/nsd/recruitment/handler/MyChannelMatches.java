package com.nsd.recruitment.handler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;

public class MyChannelMatches implements ChannelMatcher {
    @Override
    public boolean matches(Channel channel) {
        return true;
    }
}
