# jadam
Kids Java Programming Library


Java is a professional programming language and widely seen as difficult for children.

This project aims to make the Java programming language accessible for kids. 
It mainly hides Object Oriented Paradigms while offering visual appealing programming capabilities.

**jadam** provides both console and UI capabilities and allow children to play with geometrical forms, images and animations.

![Example](documentation/demo/Demo-src.png?raw=true "Srouce Code")
![Example](documentation/demo/Demo.png?raw=true "Example")

# Available functions

A single import line should be necessary ;

```java
    import static jadam.lib.*;
```


**jadam** main functions are

## console functions
```java
    println(line);
    readInt();
    readLong();
    readDouble();
    readFloat();
    readBoolean();
```

## util functions
```java
    eq(a,b) // null safe and array aware equals
    split(line,separators?,returnSeps?)
    isInt(String);
    intOf(String);
    isDouble(String);
    doubleOf(String);
    ...//etc
```

## math functions
```java
    random();
    randomDouble(from,to);
    randomInt(from,to);
    min(double...);
    max(double...);
    sin(x);
    cos(x);
    ... // all Math functions are imported automatically
    plot((double)->double); // plot a function
```

## draw functions
```java
    goTo(x,y); // set current position
    point(); // draw a point at the current position
    lineTo(x,y); // draw a line from the current position to (x,y) and 
                 // and set (x,y) as current position 
    arrowTo(x,y); // draw a line from the current position to (x,y) and 
                 // and set (x,y) as current position 
    closePolygon(); // create a polygon based on the suite of last lineTo calls
    ellipse(xradius, yradius); // draw an ellipse à the current position
    circle(radius); // draw a circle à the current position
    rectangle(w,h); // draw a circle à the current position
    square(w); // draw a circle à the current position
    text(string); // draw a circle à the current position
    image(path); // draw a circle à the current position

```

## grid manipulation
```java
    setGridTicX(double); 
    setGridTicY(double); 
    setGridTic(double); 
    setGridIntervalX(min,max); 
    setGridIntervalY(min,max); 
    setGridInterval(min,max); 
    showGrid(); 
    hideGrid(); 

```

## styling
```java
    setLineWidth(double); 
    setLabel(text); 
    flipH(); 
    flipV(); 
    flipVH(); 
    setFlipH(); 
    setFlipV(); 
    setFlipVH(); 
    setFlipVH(); 
    setSize(); 
    setSize(); 
    setWidth(); 
    setHeight(); 
    setLineStyle(); 
    setLineStyleDashed(); 
    setLineStyleNormal(); 
    setBackgroundColor(); 
    setPointColor(); 
    setPointStyle(); 
    setPointStyle(); 
    setLineColor(); 
    setTextColor(); 
    fill(); 
    drawBorder(); 
    setFont(); 
    setFontSize(); 
    align();
    rotate(angle);
```

## animation
animations are applied to the previous element
```java
    //animate rotation
    rotate(double fromAngle, double toAngle, int seconds);
    //animate move
    move(double fromAngle, double toAngle, int seconds);
    quadCurve(); // use quadratic curves for movements
    linearCurve(); // use linear curves for movements
    speedUp() ; // use speeding up animation for movements and rotations 
    speedDown() // use speeding down animation for movements and rotations
    speedUpAndDown() // use speeding up and then down animation for movements and rotations
    speedDownAndUp() // use speeding down and then up animation for movements and rotations
```

## parallel execution
```java
    run(Runnable); // run in background (new thread)
    sleep(millis) // wait for millis
```



# Example
He is a mode sophisticated example

![Example](documentation/demo/Example.png?raw=true "Example")


# Maven support
Here is a simple **pom.xml** file to be able to use **jadam**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>net.thevpc.jadam.examples</groupId>
    <artifactId>jadam-tutorials</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
    <dependencies>
        <dependency>
            <groupId>net.thevpc.jadam</groupId>
            <artifactId>jadam</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
    <repositories>
        <repository>
            <id>thevpc</id>
            <name>thevpc</name>
            <url>https://thevpc.net/maven</url>
        </repository>
    </repositories>
</project>

```

An empty project under examples folder is provided as a starting project template
