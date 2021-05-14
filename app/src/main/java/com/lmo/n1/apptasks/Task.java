package com.lmo.n1.apptasks;

public class Task {
    private int id;
    private String title;
    private String description;
    private String image;
    private boolean completed;

    public Task(){}

    public Task(int id, String title, String description, String image, boolean completed){
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setImage(image);
        this.setCompleted(completed);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", completed=" + completed +
                '}';
    }
}
