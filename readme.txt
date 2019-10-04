Setup av system:
JDK version: 13, no new language features
JFX version: 13

--EDIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
Det er ikke lenger med instillingene nederst men de hjelper. Dette grunnet at maven nå er fungerende både på kompilering og launching gjennom plugin modulen, med underscriptene: javafx:compile og javafx:run :D



VM options:
--module-path "PATH TO FOLDER\SET_gruppe\libraries\javafx-sdk-13\lib" --add-modules javafx.controls,javafx.fxml 


1: Legg til vm options da maven sitt launch system ikke fungere helt riktig enda pga refleksjon.

2: launch ved å bruke MainJavaFX i intellij j, ikke med maven

Husk å endre den gamle JDK-en i systemet deres fra før, til den nye nr 13. Dette gjelder da under: Project settings, og videre under div faner i menyen.

