package org.sid.gestion_v.entities;

public enum StatutVehicule {
    LIBRE("Disponible"),
    AFFECTE("Affecté"),
    EN_MAINTENANCE("En maintenance");

    private final String label;

    StatutVehicule(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
