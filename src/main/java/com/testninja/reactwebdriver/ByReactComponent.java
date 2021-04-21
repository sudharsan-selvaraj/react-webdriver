package com.testninja.reactwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.AbstractFindByBuilder;
import org.openqa.selenium.support.PageFactoryFinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;


public class ByReactComponent extends BaseBy implements IFilterableBy {

    private String selector, props, state;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    @PageFactoryFinder(ReactFindByBuilder.class)
    public @interface FindBy {
        String rootSelector() default "";

        String name();

        String props() default "{}";

        String state() default "{}";
    }

    public static class ReactFindByBuilder extends AbstractFindByBuilder {
        @Override
        public By buildIt(final Object annotation, Field field) {
            final FindBy findBy = (FindBy) annotation;
            return new ByReactComponent(findBy.rootSelector(), findBy.name(), findBy.props(), findBy.state());
        }
    }

    public ByReactComponent(String rootSelector,
                            String selector,
                            String props,
                            String state) {
        super(rootSelector);
        this.selector = selector;
        this.props = props;
        this.state = state;
    }

    public ByReactComponent(String rootSelector, String selector) {
        this(rootSelector, selector, "{}", "{}");
    }

    public ByReactComponent props(String props) {
        this.props = props;
        return this;
    }

    public ByReactComponent state(String state) {
        this.state = state;
        return this;
    }

    @Override
    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return ByReact.executeQuery(
                javascriptExecutor,
                ByReact.locatorScript+
                "return getWebElements({\n" +
                        "    rootSelector: arguments[0],\n" +
                        "    selector: arguments[1],\n" +
                        "    props: arguments[2],\n" +
                        "    state: arguments[3],\n" +
                        "    context: arguments[4]\n" +
                        "})",
                rootSelector ,selector, props, state, context);

    }

    @Override
    public ReactComponentFilter getFilter() {
        return new ReactComponentFilter(rootSelector,selector, props, state);
    }

    @Override
    public String toString() {
        return "ByReactComponent [component=" + selector + "] [props=" + props + "] [state=" + state + "]";
    }
}