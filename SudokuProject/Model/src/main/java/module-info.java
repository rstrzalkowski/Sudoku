module Model {
    requires java.desktop;
    requires org.apache.commons.lang3;
    requires java.sql;
    opens pl.first.firstjava;

    exports pl.first.firstjava;
    exports pl.first.firstjava.exception;
    opens pl.first.firstjava.exception;
}