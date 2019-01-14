package com.bao.retrofit2demo.entry;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RepoEntry {
    private int id;
    private String name;
    private Owner owner;
    @SerializedName(value="htmlUrl" ,alternate={"html_url","HtmlUrl"})
    private String htmlUrl;
    private String description;
    private boolean fork;

    @SerializedName("created_at")
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }



    private class Owner{
        private String login;
        private String type;
        @SerializedName("site_admin")
        private boolean stieAdmin;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isStieAdmin() {
            return stieAdmin;
        }

        public void setStieAdmin(boolean stieAdmin) {
            this.stieAdmin = stieAdmin;
        }
    }
}
