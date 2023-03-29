package ua.glumaks.service;

public enum RestPathType {
    GET_DOC("files/doc"),
    GET_PHOTO("files/photo");


    private final String link;

    RestPathType(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return this.link;
    }
}

