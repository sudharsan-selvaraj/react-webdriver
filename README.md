# webdriver-react-selector

An idea inspired by [@Paul Hammant](https://github.com/paul-hammant) 's [ngWebdriver](https://github.com/paul-hammant/ngWebDriver) project, <i>webdriver-react-selector</i> help you to locate web elements in your REACT app using components, props and states for Selenium java tests. It works with Firefox, Chrome and all the other Selenium-WebDriver browsers.

# How it works

<i>webdriver-react-selector</i> make use of [resq(React Element Selector Query)](https://github.com/baruchvlz/resq) which does most of the heavy lifting in finding react elements from the DOM.

# Usage
```java
ChromeDriver driver = new ChromeDriver();
ReactWebDriver reactWebdriver = new ReactWebDriver((JavascriptExecutor) driver, "#root");
```

### Locator
```java
/* Filter just by component name */
ByReact.component("NavLink")

/* Filter by component name and prop */
ByReact.component("NavLink").prop("{ \"name\" : \"home\" }")

/* Filter by component name and state */
ByReact.component("NavLink").state("{ \"title\" : \"Go to home\" }")

/* Filter by component name and both prop & state */
ByReact.component("NavLink").prop("{ \"name\" : \"home\" }")state("{ \"title\" : \"Go to home\" }")
```
## Using Page Objects
The work in the same way at WebDriver's page object technology, there is a custom `FindBy` 
annotations for ReactWebDriver:
```java
@ByReactComponent.FindBy(
        name = "NavLink",
        props = "{\"name\": \"home\"}"
)
public WebElement homeNavLink;
```

Installation steps will be added soon...