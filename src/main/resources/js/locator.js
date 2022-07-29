function getReactElements({rootSelector, selector, props, state, context}) {
     props = props ? JSON.parse(props) : {};
     state = state ? JSON.parse(state) : {};

     let elements = window.resq.resq$$(selector, (rootSelector && typeof rootSelector == "string") ? document.querySelector(rootSelector) : context)

     if (Object.keys(props).length) {
         elements = elements.byProps(props)
     }
     if (Object.keys(state).length) {
         elements = elements.byState(state)
     }

     return elements;
}

function getWebElements({rootSelector, selector, props, state, context}) {
 let elements = getReactElements({rootSelector, selector, props, state, context});

 if (!elements.length) {
     return []
 }
 var nodes = []
 elements.forEach(function(element) {
     if (element.isFragment) {
         nodes = nodes.concat(element.node || [])
     } else if (element.node) {
         nodes.push(element.node)
     }
 })
 return [...nodes]
}

function getObjectDetails({context, objectName, key}) {
    var nodes =  window.resq.resq$$("*", context.parentElement);
    if(!nodes.length) {
        return null;
    }
    var node = nodes.filter(n => n.node == context)[0];
    return getValueFromNestedObject(node[objectName], key)
}

function getValueFromNestedObject(obj, key) {
        if(!key) {
            return obj;
        }
        const properties = key.split('.');
        return getValueFromNestedObject(obj[properties.shift()], properties.join('.'))
}