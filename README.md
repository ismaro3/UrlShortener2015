# Web Engineering 2015-2016 / UrlShortener2015
[![Build Status](https://travis-ci.org/UNIZAR-30246-WebEngineering/UrlShortener2015.svg)](https://travis-ci.org/UNIZAR-30246-WebEngineering/UrlShortener2015)

This is the shared repository for the project developed in this course. Go to the [wiki](wiki) to start your project.

## Projects

* [Common](common) is the project that provides a minimum set of shared features.
* [Demo](demo) is the template project and the sandbox for solving blocking issues.

## Teams

[TO BE COMPLETED]

## Starting procedure

* The team leader forks this repository
* Each team member forks the fork of the respective team leader
* Import your own fork into eclipse. You must import the common project, the demo project and your project. For example, if your project is _BangladeshGreen_ you must import `UrlShortener2015.common`, `UrlShortener2015.demo` and `UrlShortener2015.bangladeshGreen`. Other projects are optional.
* Create the folder `src\main\java` in your project.
* Copy the contents of `src\main\java` from `UrlShortener205.demo` into your project.
* Rename the package `urlshortener2015.demo` to your color. For example if your project is _BangladeshGreen_ you must rename it to `urlshortener2015.bangladeshgreen`.
* Test that your program run using command line (`gradle run`) or within eclipse (either as Java application or as Gradle application).
* Do `$ curl -v -d "url=http://www.unizar.es/" -X POST http://localhost:8080/link` and check that appears a line in the console that contains `u.d.web.UrlShortenerControllerWithLogs`

Now you can start to add new functionality to your project.

## Push & Pull

* Each team member should work in its local repository.
* Periodically, each team member must __push__ its work to its repository in GitHub and then make a __pull request__ for sent your changes to the repository of the team.
* Periodically, each team member must __pull__ from GitHub to fetch and merge changes from remote repositories (your team changes or changes in the original repository).

## Organization

* 9/10/2015. 19:45-21:45. All members of the group meet in a bar and comment what we want our service to do. In addition to the proposals seen in class, we all think together more things our service to do. These ideas are collected in a paper document.

* 19/10/2015. 19:00-20:30. All members of the group meet in 0.1 laboratory. We decide exactly all the things we want our service to do, and we realize an approximation of the marks ("bare pass", "notable" and "outstanding") of each section we have decided. We set a meeting with the professor Francisco J. López Pellicer to comment the sections and marks we have decided. A summary is realized in a paper document.

* 20/10/2015. 19:15-19:30. All members of the group do a meeting with the professor Francisco J. López Pellicer in his office. We comment him our service proposals and we come to the conclusion to collect all proposals in a document with the asked format. We set another meeting with him before the agreement of our contract.

* 23/10/2015. 16:30-18:00. A document with the asked format is realized. We collect all our objectives (what we want about this work), and our services with their respectives description of the expected marks. The written document is not the last document (this means, it's not our contract), but a draft to be confirmed with the professors before the signature of it. The document is sent to the professor Francisco J. López Pellicer.

* 25/10/2015. 18:00-19:30. We get back the draft with some indications made by Francisco J. López Pellicer with changes we should make. We improve our document with the suggested changes and we return it to Francisco J. López Pellicer. The next step is to wait for our turn to set our project agreement. It is supposed to realize it during this week or the beginning of the next one.

* 03/11/ 2015 19:00-21:00. After meeting with de proffesor and discuss the contract, two members team sign (Alberto Álvarez Aldea and Iñigo Gascón Royo) the final contract project. The other two members (Santiago Gil Begué and David Nicuesa Aranda) must sign the project during this week.

