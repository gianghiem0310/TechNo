package com.example.technosocialapp.model;

public class Friend {
    private long idFriend;
    private boolean followFriend;
    private long date;
    private long type;
    private boolean blockFriend;

    public Friend(long idFriend, boolean followFriend, long date, long type, boolean blockFriend) {
        this.idFriend = idFriend;
        this.followFriend = followFriend;
        this.date = date;
        this.type = type;
        this.blockFriend = blockFriend;
    }
    public Friend(){

    }

    public long getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(long idFriend) {
        this.idFriend = idFriend;
    }

    public boolean isFollowFriend() {
        return followFriend;
    }

    public void setFollowFriend(boolean followFriend) {
        this.followFriend = followFriend;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public boolean isBlockFriend() {
        return blockFriend;
    }

    public void setBlockFriend(boolean blockFriend) {
        this.blockFriend = blockFriend;
    }
}
