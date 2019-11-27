package be.intecbrussel.webcomponents.guestbook.domain;

import java.time.LocalDateTime;

public class GuestbookBean {
    private LocalDateTime date;
    private String name;
    private String message;

    public GuestbookBean(LocalDateTime date, String name, String message) {
        this.date = date;
        this.name = name;
        this.message = message;
    }

    public GuestbookBean() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GuestbookBean{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
