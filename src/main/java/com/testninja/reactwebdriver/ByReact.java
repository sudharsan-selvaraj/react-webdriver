package com.testninja.reactwebdriver;

import org.openqa.selenium.JavascriptExecutor;

import java.util.Scanner;

public class ByReact {

    static final String DEFAULT_ROOT = "#root";

    static String resqScript, locatorScript;

    static {
        resqScript = readScriptFromResource("/js/resq.js");
        locatorScript = readScriptFromResource("/js/locator.js");
    }

    public static ByReactFactory factory(String rootSelector) {
        return new ByReactFactory(rootSelector);
    }

    public static ByReactComponent component(String componentName) {
        return factory(DEFAULT_ROOT).component(componentName);
    }

    private static String readScriptFromResource(String fileName) {
        return new Scanner(ByReact.class.getResourceAsStream(fileName), "UTF-8").useDelimiter("\\A").next();
    }

     static void loadResqScript(JavascriptExecutor javascriptExecutor) {
        javascriptExecutor.executeScript(ByReact.resqScript);
    }

    static Object executeQuery(JavascriptExecutor executor, String script, Object ...args) {
        ByReact.loadResqScript(executor);
        return executor.executeScript(script, args);
    }
}
