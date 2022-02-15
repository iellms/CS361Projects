# Project 2 Report

Author: Ricky Peng, Andy Xu, Ian Ellmer

## Overview of the Project
In this project, we recreated the same App window as in Project 1, but we used FXML and CSS for layout and design style this time. We did not modify the program's functionailities, so every elements in the window have the same behavior as they did in Project 1. First, we removed all the codes that specify layout and design style of GUI elements in the ``Main.java``. Then, we used Scene Builder to recreate the same layout of the App, and exported the layout to a FXML file. We also created a CSS file ``Main.css`` to specify the design style of our GUI elements.

## Elegancy in the Project
Right after the layout and design code was separated from the ``Main.java`` file, the code looked so much clearer and more elegant. ``Main.fxml`` file now defines the layout, ``Main.css`` defines the design style of GUI elements, and ``Main.java`` defines program logic and handler method for GUI elements. Comparing to project 1 where layout, design, and program logic were written in the same file, this change improved code readability. It is also obvious that this change brings ease to any future development because every addition of GUI elements, design, and logic will be done in separate files.

We defined methods to handle events instead of creating extra classes. This simplied the code and improved code readability.

We also improved the elegancy in ``handleGoodbyeButton`` method. We implemented the ``goodbyeButton`` handler using ``appendText`` method rather than using ``setText`` and ``getText``.

## Dividing Up the Class
There was only one class named ``Main`` in this project, which was enough to create the application. It mainly included handlers for GUI buttons and defined the start method which created the stage and scene. We did not create classes for ``eventHandler``, but instead defined handler methods in the ``Main`` class for each element.

## Inelegancy in the Project
Since we created event handling methods for every interactive GUI element individually in the ``Main`` class, there seemed to be too many handler methods. It is still manageable at this stage; however, once other elements and features are added, we might need to think of more elegant ways to handle events.

## Dividing the Coding Task
* Ian created FXML and CSS file and added proper functionality to all buttons.
* Andy did the auto resizing of menu bar and the text box, added a condition so that the ``helloButton`` text would change only when the ``okButton`` is clicked, and modified CSS file.
* Ricky formatted comments and codes, refactored the ``handleGoodbyeButton`` method, modified FXML and CSS files to make the window looks more similar to Dale's screenshots, and wrote report.