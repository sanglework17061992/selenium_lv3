package com.sangle.selenium.exceptions;

/**
 * Exception thrown when an element interaction fails due to the element not being in an interactable state.
 * This exception provides context about why the interaction failed and suggests possible solutions.
 */
public class ElementNotInteractableException extends RuntimeException {
    
    private final String elementName;
    private final String attemptedAction;
    private final String elementState;
    
    public ElementNotInteractableException(String message) {
        super(message);
        this.elementName = null;
        this.attemptedAction = null;
        this.elementState = null;
    }
    
    public ElementNotInteractableException(String elementName, String attemptedAction, String elementState) {
        super(String.format("Cannot perform '%s' on element '%s'. Element state: %s", 
                           attemptedAction, elementName, elementState));
        this.elementName = elementName;
        this.attemptedAction = attemptedAction;
        this.elementState = elementState;
    }
    
    public ElementNotInteractableException(String elementName, String attemptedAction, String elementState, Throwable cause) {
        super(String.format("Cannot perform '%s' on element '%s'. Element state: %s. Cause: %s", 
                           attemptedAction, elementName, elementState, cause.getMessage()), cause);
        this.elementName = elementName;
        this.attemptedAction = attemptedAction;
        this.elementState = elementState;
    }
    
    public String getElementName() {
        return elementName;
    }
    
    public String getAttemptedAction() {
        return attemptedAction;
    }
    
    public String getElementState() {
        return elementState;
    }
}