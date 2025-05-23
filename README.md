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

## Console functions
```java
    println(line);
    readInt();
    readLong();
    readDouble();
    readFloat();
    readBoolean();
```

## Util functions
```java
    eq(a,b) // null safe and array aware equals
    split(line,separators?,returnSeps?)
    isInt(String);
    intOf(String);
    isDouble(String);
    doubleOf(String);
    ...//etc
```

## Math functions and Plots
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

## Geometric Forms and Drawings
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
    rectangle(w,h); // draw a rectangle à the current position
    square(w); // draw a square à the current position
    text(string); // draw some text à the current position
    image(path); // draw an image à the current position

```

## Grid and axes
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

## Styling
    styling methods are applicable to the previous element
```java
    setLineWidth(double); 
    setLabel(text);
        // add a label for the previous element
    flipH(); 
        // flip/unflip image horizontally
    flipV();
        // flip/unflip image vertically
    flipVH();
        // flip/unflip image vertically and horizontally
    setFlipH(boolean);
        // flip image horizontally
    setFlipV(boolean);
        // flip image vertically
    setFlipVH(boolean);
        // flip image vertically and horizontally
    setSize(number,number?);
        // set element size (width and hight)
    setWidth(number);
        // set element width
    setHeight(number);
        // set element height
    setLineStyle(enum|string|int);
         // values can be NORMAL, DASHED
    setBackgroundColor(color|string|int);
         // set background color
         // values can be red, blue, yellow, etc. or html format like #1245FF
    setPointColor(color|string|int);
        // set point color
        // values can be red, blue, yellow, etc. or html format like #1245FF
    setPointStyle(enum|string|int);
        // set line/border style
        // values can be CROSS,PLUS,DOT
    setLineColor(color|string|int);
        // set line color
        // values can be red, blue, yellow, etc. or html format like #1245FF
    setTextColor(color|string|int);
        // set text color
        // values can be red, blue, yellow, etc. or html format like #1245FF
    fill(boolean?);
        // set line color
        // values can be red, blue, yellow, etc. or html format like #1245FF
    drawBorder(boolean?);
        // draw border
    setFont(font|string|number);
        // change font
    setFontSize(number); 
        // change font size
    align(enum|string|int);
        // change element alignment
    setAngle(angle);
        // change element rotation angle
```

## Animations
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

## Parallel execution
```java
    run(Runnable); // run in background (new thread)
    sleep(millis) // wait for millis
```



# Example
He is a more sophisticated example

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
            <url>https://maven.thevpc.net</url>
        </repository>
    </repositories>
</project>

```

An empty project under examples folder is provided as a starting project template
