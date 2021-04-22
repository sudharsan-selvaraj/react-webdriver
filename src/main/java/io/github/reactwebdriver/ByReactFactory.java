package io.github.reactwebdriver;
public class ByReactFactory {

    private String rootSelector;

    public ByReactFactory(String rootSelector) {
        this.rootSelector = rootSelector;
    }

    public ByReactComponent component(String selector) {
        return new ByReactComponent(rootSelector, selector);
    }
}
