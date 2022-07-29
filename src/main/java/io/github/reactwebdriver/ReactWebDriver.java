package io.github.reactwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public class ReactWebDriver {

    private String rootSelector;

    private final JavascriptExecutor javascriptExecutor;
    private final SearchContext context;

    public ReactWebDriver(SearchContext context, String rootSelector) {
        this.context = context;
        if(context instanceof RemoteWebElement) {
               this.javascriptExecutor =  ((JavascriptExecutor)((RemoteWebElement) context).getWrappedDriver());
        } else {
            this.javascriptExecutor = (JavascriptExecutor) context;
        }
        withRootSelector(rootSelector);
    }


    /**
     * Set the root selector for finding react in the DOM
     *
     * @param rootSelector like "#root" (which is the default)
     */

    public ReactWebDriver withRootSelector(String rootSelector) {
        this.rootSelector = rootSelector;
        return this;
    }


    public ByReactFactory makeByReactFactory() {
        return new ByReactFactory(rootSelector);
    }

    public void waitForReactToLoad() {
        ByReact.loadResqScript(javascriptExecutor);
        javascriptExecutor.executeAsyncScript("window.resq.waitToLoadReact().then(arguments[arguments.length-1])");
    }

    public ReactComponent getComponent(WebElement element) {
        return new ReactComponent(this.javascriptExecutor, element);
    }
}
