package com.sangle.selenium.elements;

/**
 * Enumeration representing the different states an element can be in.
 * This helps track element lifecycle and optimize interactions.
 */
public enum ElementState {
    
    /**
     * Element is fresh and ready for interaction.
     * This is the initial state when an element is first created.
     */
    FRESH("Element is fresh and ready"),
    
    /**
     * Element reference has become stale.
     * This happens when the DOM is refreshed or element is re-rendered.
     */
    STALE("Element reference is stale"),
    
    /**
     * Element was not found in the DOM.
     * This indicates the element may not exist or locator is incorrect.
     */
    NOT_FOUND("Element not found in DOM"),
    
    /**
     * Element is currently being loaded/located.
     * This is a transient state during element lookup operations.
     */
    LOADING("Element is being loaded"),
    
    /**
     * Element is cached and verified as valid.
     * This indicates the element can be safely used without re-lookup.
     */
    CACHED("Element is cached and valid"),
    
    /**
     * Element exists but is not in an interactable state.
     * This happens when element is present but disabled, hidden, or overlapped.
     */
    NOT_INTERACTABLE("Element is not interactable");

    private final String description;
    
    ElementState(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Checks if the element state indicates it's ready for interaction.
     * @return true if element can be interacted with
     */
    public boolean isInteractable() {
        return this == FRESH || this == CACHED;
    }
    
    /**
     * Checks if the element state indicates it needs refresh.
     * @return true if element needs to be re-located
     */
    public boolean needsRefresh() {
        return this == STALE || this == NOT_FOUND || this == NOT_INTERACTABLE;
    }
    
    /**
     * Checks if the element state is terminal (cannot be recovered).
     * @return true if element state cannot be improved
     */
    public boolean isTerminal() {
        return this == NOT_FOUND;
    }
}