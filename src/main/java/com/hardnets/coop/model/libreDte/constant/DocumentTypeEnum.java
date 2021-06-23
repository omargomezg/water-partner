package com.hardnets.coop.model.libreDte.constant;

public enum DocumentTypeEnum {
    DTE(38);

    private final int documentId;

    DocumentTypeEnum(final int id) {
        this.documentId = id;
    }

    public int getValue() {
        return documentId;
    }
}
