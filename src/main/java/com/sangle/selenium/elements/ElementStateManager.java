package com.sangle.selenium.elements;

import com.sangle.selenium.logging.StepLogger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

/**
 * Manages the state and caching of WebElement instances to optimize performance
 * and provide better error handling for stale element scenarios.
 */
public class ElementStateManager {
    
    private ElementState currentState = ElementState.FRESH;
    private WebElement cachedElement;
    private long lastInteractionTime;
    private long cacheValidityDuration = 5000; // 5 seconds default
    private int stalenessCheckCount = 0;
    private int refreshCount = 0;
    private final Object stateLock = new Object();
    private final String elementName;
    
    public ElementStateManager(String elementName) {
        this.elementName = elementName;
        this.lastInteractionTime = System.currentTimeMillis();
    }
    
    /**
     * Gets the current state of the element.
     * @return current ElementState
     */
    public ElementState getCurrentState() {
        synchronized (stateLock) {
            return currentState;
        }
    }
    
    /**
     * Checks if the cached element reference is stale.
     * @return true if element is stale or not cached
     */
    public boolean isElementStale() {
        synchronized (stateLock) {
            if (cachedElement == null) {
                currentState = ElementState.NOT_FOUND;
                return true;
            }
            
            try {
                // Quick staleness check - this will throw if stale
                cachedElement.isDisplayed();
                // If we get here, element is not stale
                if (currentState == ElementState.STALE) {
                    currentState = ElementState.CACHED;
                }
                return false;
            } catch (StaleElementReferenceException e) {
                currentState = ElementState.STALE;
                stalenessCheckCount++;
                StepLogger.warn("Stale element detected - " + 
                    String.format("Element '%s' became stale (occurrence #%d)", elementName, stalenessCheckCount));
                return true;
            } catch (Exception e) {
                // Other exceptions (like element not found) also indicate staleness
                currentState = ElementState.NOT_FOUND;
                return true;
            }
        }
    }
    
    /**
     * Checks if the cached element is still valid and within cache duration.
     * @return true if cache is valid and not stale
     */
    public boolean isCacheValid() {
        synchronized (stateLock) {
            if (cachedElement == null) {
                return false;
            }
            
            // Check cache expiration
            long cacheAge = System.currentTimeMillis() - lastInteractionTime;
            if (cacheAge > cacheValidityDuration) {
                StepLogger.info("Element cache expired - " +
                    String.format("Cache age: %dms, validity: %dms", cacheAge, cacheValidityDuration));
                return false;
            }
            
            // Check staleness
            return !isElementStale();
        }
    }
    
    /**
     * Refreshes the element state, clearing cache and resetting counters.
     */
    public void refreshElement() {
        synchronized (stateLock) {
            StepLogger.info("Refreshing element state - " +
                String.format("Element '%s' - refresh #%d", elementName, refreshCount + 1));
            
            currentState = ElementState.LOADING;
            cachedElement = null;
            refreshCount++;
            // Don't reset staleness count as it's useful for debugging
        }
    }
    
    /**
     * Updates the cache with a new element instance.
     * @param element the new WebElement to cache
     */
    public void updateCache(WebElement element) {
        synchronized (stateLock) {
            this.cachedElement = element;
            this.lastInteractionTime = System.currentTimeMillis();
            this.currentState = ElementState.CACHED;
            
            StepLogger.info("Element cache updated - " +
                String.format("Element '%s' cached successfully", elementName));
        }
    }
    
    /**
     * Gets the cached element if it's valid.
     * @return cached WebElement or null if not valid
     */
    public WebElement getCachedElement() {
        synchronized (stateLock) {
            if (isCacheValid()) {
                return cachedElement;
            }
            return null;
        }
    }
    
    /**
     * Sets the cache validity duration.
     * @param durationMs cache validity in milliseconds
     */
    public void setCacheValidityDuration(long durationMs) {
        synchronized (stateLock) {
            this.cacheValidityDuration = durationMs;
            StepLogger.info("Cache validity updated - " +
                String.format("Element '%s' cache validity set to %dms", elementName, durationMs));
        }
    }
    
    /**
     * Gets the number of times element became stale.
     * @return staleness count
     */
    public int getStalenessCount() {
        synchronized (stateLock) {
            return stalenessCheckCount;
        }
    }
    
    /**
     * Gets the number of times element was refreshed.
     * @return refresh count
     */
    public int getRefreshCount() {
        synchronized (stateLock) {
            return refreshCount;
        }
    }
    
    /**
     * Gets cache statistics for debugging.
     * @return formatted statistics string
     */
    public String getCacheStatistics() {
        synchronized (stateLock) {
            long cacheAge = cachedElement != null ? 
                System.currentTimeMillis() - lastInteractionTime : 0;
                
            return String.format(
                "Element '%s' - State: %s, Staleness: %d, Refreshes: %d, Cache Age: %dms", 
                elementName, currentState.getDescription(), stalenessCheckCount, refreshCount, cacheAge);
        }
    }
    
    /**
     * Forces the element state to a specific value (for testing/debugging).
     * @param newState the state to set
     */
    public void forceState(ElementState newState) {
        synchronized (stateLock) {
            this.currentState = newState;
            if (newState == ElementState.NOT_FOUND || newState == ElementState.STALE) {
                this.cachedElement = null;
            }
            StepLogger.info("Element state forced - " +
                String.format("Element '%s' state set to: %s", elementName, newState.getDescription()));
        }
    }
}