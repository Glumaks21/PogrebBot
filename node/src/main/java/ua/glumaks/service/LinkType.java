package ua.glumaks.service;

public enum LinkType {
    GET_DOC("files/doc"),
    GET_PHOTO("files/photo");


    private final String link;

    LinkType(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return this.link;
    }
}

