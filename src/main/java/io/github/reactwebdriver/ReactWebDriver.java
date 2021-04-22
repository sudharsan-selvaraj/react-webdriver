package io.github.reactwebdriver;

import org.openqa.selenium.JavascriptExecutor;

public class ReactWebDriver {

    private String rootSelector;

    private final JavascriptExecutor javascriptExecutor;

    public ReactWebDriver(JavascriptExecutor javascriptExecutor, String rootSelector) {
        this.javascriptExecutor = javascriptExecutor;
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

    public ReactComponent getComponent(IFilterableBy by) {
        return new ReactComponent(javascriptExecutor, by.getFilter());
    }

    public ReactComponent getComponent(IFilterableBy by, Integer index) {
        return new ReactComponent(javascriptExecutor, by.getFilter()).nthIndex(index);
    }
}
