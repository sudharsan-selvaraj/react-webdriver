package com.testninja.reactwebdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;
import java.util.NoSuchElementException;

public abstract class BaseBy extends By {

    protected final String rootSelector;

    protected BaseBy(String rootSelector) {
        this.rootSelector = rootSelector;
    }

    private final JavascriptExecutor getJavascriptExecutor(SearchContext context) {
        JavascriptExecutor jse;
        if (context instanceof RemoteWebElement) {
            jse = (JavascriptExecutor) ((RemoteWebElement) context).getWrappedDriver();
        } else {
            jse = (JavascriptExecutor) context;
        }
        return jse;
    }

    protected final Object errorIfNull(Object o) {
        if (o == null || o instanceof List && ((List) o).size() == 0) {
            throw new NoSuchElementException("Cannot locate an element using "+ this);
        }
        return o;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        JavascriptExecutor javascriptExecutor = getJavascriptExecutor(context);
        if (context instanceof WebDriver) {
            context = null;
        }
        return ((List<WebElement>) errorIfNull(getObject(context, javascriptExecutor))).get(0);
    }

    protected abstract Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor);

    @Override
    public List<WebElement> findElements(SearchContext context) {
        JavascriptExecutor javascriptExecutor = getJavascriptExecutor(context);
        if (context instanceof WebDriver) {
            context = null;
        }
        return (List<WebElement>) getObject(context, javascriptExecutor);
    }
}
