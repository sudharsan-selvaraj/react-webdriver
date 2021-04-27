# react-webdriver

An idea inspired by [@Paul Hammant](https://github.com/paul-hammant) 's [ngWebdriver](https://github.com/paul-hammant/ngWebDriver) project, <i>react-webdriver</i> help you to locate web elements in your REACT app using components, props and states for Selenium java tests. It works with Firefox, Chrome and all the other Selenium-WebDriver browsers.

# How it works

<i>react-webdriver</i> make use of [resq(React Element Selector Query)](https://github.com/baruchvlz/resq) which does most of the heavy lifting in finding react elements from the DOM.

# Usage
```java
ChromeDriver driver = new ChromeDriver();
/* #root is the selector for root element where the react app is rendered */
ReactWebDriver reactWebdriver = new ReactWebDriver((JavascriptExecutor) driver, "#root");
```

### Locator
```java
/* Filter just by component name */
ByReact.component("NavLink")

/* Filter by component name and prop */
ByReact.component("NavLink").props("{ \"name\" : \"home\" }")

/* Filter by component name and state */
ByReact.component("NavLink").state("{ \"title\" : \"Go to home\" }")

/* Filter by component name and both prop & state */
ByReact.component("NavLink").props("{ \"name\" : \"home\" }")state("{ \"title\" : \"Go to home\" }")
```
## Using Page Objects
This work in the same way as WebDriver's page object technology, there is a custom `FindBy` 
annotations for ReactWebDriver:
```java
@ByReactComponent.FindBy(
        name = "NavLink",
        props = "{\"name\": \"home\"}"
)
public WebElement homeNavLink;
```

## Example App
Consider the below React App:
```javascript
class MyComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: props.id
        };
    }

    render() {
        return ( 
            <div> Name: { this.props.name} </div> 
            <div> Id: { this.props.id} </div>    
        )
    }

}

const App = () => ( <
    <div>
    	<MyComponent name = "java" id = "1" />
    	<MyComponent name = "javascript" id = "2" />
    </div>
)

ReactDOM.render( <App/> , document.getElementById('root'))
```

## To select all <i><b>MyComponent</b></i>
```java
 driver.findElements(ByReact.component("MyComponent"))
```
or using a wildcard selector:
```java
driver.findElements(ByReact.component("*Component"))
or
driver.findElements(ByReact.component("My*"))
```

## Filtering by props
```java
 driver.findElement(ByReact.component("MyComponent").props("{ "\"name\": \"java\" }"))
```
will return the first <b>MyComponent</b>. 
## Filtering by state
```java
 driver.findElement(ByReact.component("MyComponent").state("{ "\"id\": \"1\" }"))
```
## Filtering by both props and state
```java
 driver.findElement(ByReact.component("MyComponent").props("{ "\"name\": \"java\" }").state("{ "\"id\": \"1\" }"))
```
# Retrieve React Properties from element
Create a ReactDriver instance
```java
ChromeDriver driver = new ChromeDriver();
ReactWebDriver reactWebdriver = new ReactWebDriver((JavascriptExecutor) driver, "#root");
```
Using the reactDriver, we will find the required ReactComponent using
```java
ReactComponent component = reactWebDriver.getComponent(ByReact.component("MyComponent"));
```
By default, `getComponent` method will return the first matching node from the DOM if more than one node present for the given selector.
We can also find a specific node by passing the index as below
```java
ReactComponent component = reactWebDriver.getComponent(ByReact.component("MyComponent"), 1);
```
This will fetch the 2nd element from the list of matched elements. Index starts from `0`.
Now using the react component, we can fetch the `props` and `state` of the element
```java
assertEquals(component.getProp("name"), "java");
assertEquals(component.getProp("id"), "1");
```
and to retrieve the state
```java
assertEquals(component.getState("id"), 1);
```
# Installation

## Maven
```xml
<dependency>
  <groupId>io.github.sudharsan-selvaraj</groupId>
  <artifactId>react-webdriver</artifactId>
  <version>1.0</version>
</dependency>
```

## Gradle
```groovy
implementation group: 'io.github.sudharsan-selvaraj', name: 'react-webdriver', version: '1.0'
```