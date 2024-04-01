package com.xhpcd.server.session;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/11/8:42
 * @Description:
 */
public class GroupSessionMemoryImpl implements GroupSession{
    public static Map<String,Group> Map = new HashMap();
    @Override
    public Group createGroup(String name, Set<String> members) {
        if(Map.containsKey(name)){
            return null;
        }
       Group group = new Group(name,members);
       Map.put(name,group);
        return group;
    }

    @Override
    public Group joinMember(String name, String member) {
        Group group = Map.get(name);
        group.getMembers().add(member);
        return group;
    }

    @Override
    public Group removeMember(String name, String member) {
        Group group = Map.get(name);
        group.getMembers().remove(member);
        return group;
    }

    @Override
    public Group removeGroup(String name) {

        return Map.remove(name);
    }

    @Override
    public Set<String> getMembers(String name) {
        return Map.get(name).getMembers();
    }

    @Override
    public List<Channel> getMembersChannel(String name) {
       List<Channel> collect =  Map.get(name).getMembers().stream().map(s->SessionFactory.getSession().getChannel(s)).filter((e)->e!=null).collect(Collectors.toList());
        return collect;
    }
}
