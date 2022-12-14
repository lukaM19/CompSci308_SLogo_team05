open module slogo_app {
    // list all imported class packages since they are dependencies
    requires java.desktop;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;
    requires javafx.web;
    requires org.reflections;

    // allow other classes to access listed packages in your project
    exports slogo;
}
