package com.example.kitchenpal.messageFragment;

public class MessagesList {

    private String username, lastMessage, profilePic;
    private int unseenMessages;

    public MessagesList(String username, String lastMessage, String profilePic, int unseenMessages) {
        this.username = username;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getProfilePic() {
        return profilePic;
    }
    public int getUnseenMessages() {
        return unseenMessages;
    }
}
