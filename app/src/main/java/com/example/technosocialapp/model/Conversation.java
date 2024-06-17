package com.example.technosocialapp.model;

public class Conversation {
    private long idPerson;
    private String namePerson;
    private String avatarPerson;
    private long date;
    private String lastMessages;

    public Conversation(long idPerson, String namePerson, String avatarPerson, long date, String lastMessages) {
        this.idPerson = idPerson;
        this.namePerson = namePerson;
        this.avatarPerson = avatarPerson;
        this.date = date;
        this.lastMessages = lastMessages;
    }
    public Conversation() {

    }

    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public String getAvatarPerson() {
        return avatarPerson;
    }

    public void setAvatarPerson(String avatarPerson) {
        this.avatarPerson = avatarPerson;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLastMessages() {
        return lastMessages;
    }

    public void setLastMessages(String lastMessages) {
        this.lastMessages = lastMessages;
    }
}
