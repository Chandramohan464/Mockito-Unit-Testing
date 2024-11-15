package com.mockito;

public class Book {
    private Long id;
    private boolean available;

    public Book(Long id, boolean available) {
        this.id = id;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
