package com.deneme.routing;

public interface Selectable {

    void setEnabled(boolean enabled);

    void setSelected(boolean selected);

    void setFiltered(boolean filtered);

    boolean isEnabled();

    boolean isSelected();

    boolean isFiltered();
}
