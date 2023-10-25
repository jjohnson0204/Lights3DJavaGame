package com.lightsengine.core.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BaseNotificationClass {
    private PropertyChangeSupport propertyChangeSupport;

    public BaseNotificationClass() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}

