package io.github.reactwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ReactComponent {

    private JavascriptExecutor jse;
    private SearchContext context;

    public ReactComponent(JavascriptExecutor jse, WebElement element) {
        this.jse = jse;
        this.context = (SearchContext) element;
    }

    public Object getProp() {
        return getComponentDetails("props", "");
    }

    public Object getProp(String propName) {
        return getComponentDetails("props", propName);
    }

    public Object getState() {
        return getComponentDetails("state", "");
    }

    public Object getState(String stateName) {
        return getComponentDetails("state", stateName);
    }


    private Object getComponentDetails(String objName, String key) {
        return ByReact.executeQuery(jse,
                ByReact.locatorScript +
                        "return getObjectDetails({" +
                        "    context: arguments[0]," +
                        "    objectName: arguments[1]," +
                        "    key: arguments[2]" +
                        "})",
                this.context, objName, key);
    }
}
