# CS 2340 Trip Planner
Team 9: Billy Bolton, Charles Kilpatrick, Janki Patel, Samarth Patel, Gina Yu

The Trip Planner webapp uses Google Maps Locations and data from users' social media accounts to recommend
places to eat and activities to do based on specified times, rating and cost preferences, and transportation.

This repository contains the development and source files that are used to build the package that 
deploys into the web application and compiled source files.

# User Account Management

All user accounts are accessed with a JDBC connection to a database on a locally-hosted MySQL server.
All server interactions are managed via http requests sent through Java Servlets.

Users may register, login with, and update the username or password of their accounts.