function getReactElements({rootSelector, selector, props, state, context}) {
     props = props ? JSON.parse(props) : {};
     state = state ? JSON.parse(state) : {};

     let elements = window.resq.resq$$(selector, rootSelector ? document.querySelector(rootSelector) : context)

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

function getObjectDetails({rootSelector, selector, props, state, context, elementIndex, objectName, key}) {
    var nodes = getReactElements({rootSelector, selector, props, state, context});
    if(nodes.length >= elementIndex) {
    console.log(nodes[elementIndex][objectName])
        return getValueFromNestedObject(nodes[elementIndex][objectName], key)
    } else {
        return null;
    }
}

function getValueFromNestedObject(obj, key) {
        if(!key) {
            return obj;
        }
        const properties = key.split('.');
        return getValueFromNestedObject(obj[properties.shift()], properties.join('.'))
}