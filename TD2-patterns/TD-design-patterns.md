Université Claude Bernard Lyon 1 - Master 1 informatique - Gestion de projet et génie Logiciel 


# TD Design Patterns

L'exercice ci-dessous est en partie tiré de l'ouvrage suivant : [UML2 par la pratique - Études de cas et exercices corrigés](https://www.eyrolles.com/Informatique/Livre/uml-2-par-la-pratique-9782212120141/).


## &Eacute;tude d'un système de réservation de vols

Cette étude de cas concerne un système simplifié de réservation de vols pour une agence de voyages.
Les interviews des experts métier auxquelles on a procédé ont permis de résumer leur connaissance du domaine sous la forme des phrases suivantes :

1.  Des compagnies aériennes proposent différents vols.
2.  Un vol est ouvert à la réservation et refermé sur ordre de la compagnie.
3.  Un client peut réserver un ou plusieurs vols, pour des passagers différents.
4.  Une réservation concerne un seul vol et un seul passager.
5.  Une réservation peut être annulée ou confirmée.
6.  Un vol a un aéroport de départ et un aéroport d’arrivée.
7.  Un vol a un jour et une heure de départ, et un jour et une heure d’arrivée.
8.  Un vol peut comporter des escales dans des aéroports.
9.  Une escale a une heure d’arrivée et une heure de départ.
10. Chaque aéroport dessert une ou plusieurs villes.

L'objectif de l'exercice est de mettre au point un modèle statique d’analyse (aussi appelé modèle du domaine) à partir de ces « morceaux de connaissance », et d'aboutir à un diagramme de classes UML.

Une première partie de cet exercice consiste à définir les classes, leurs attributs et les associations de ce modèle. Pour chacune de ces phrases, cette étape est riche en questionnements et en discussions et nous vous encourageons à la réaliser en marge de ce TD. Pour vous faire gagner du temps, une solution possible (mais ce n'est pas la seule) vous est fournie ci-dessous :

<img alt="Modélisation intermédiaire : classes, attributs, associations" src="./ClassDiagram1.png" width="1000px">

Dans ce TD, nous allons partir de cette modélisation partielle et la développer pour appliquer des design patterns.

## Principes GRASP

Dans cette partie, on se focalisera sur le modèle d'analyse et aura pour objectif d'obtenir une modélisation aussi générique et réutilisable que possible.

1. Utiliser les principes d'affectation des responsabilités pour rajouter les opérations correspondant aux phrases 2 et 5.


2. En utilisation "normale" du système d'information (SI), quelles sont les classes dont on créera régulièrement des instances ? Pour chacune de ces classes, quelles sont celles qui seront chargées de les créer ?


3. Il y a dans le diagramme ci-dessus une classe qui a trop de responsabilités. Comment faire pour améliorer cet élément de la modélisation ?


4. Réaliser une décomposition en packages en favorisant la réutilisabilité et l'indépendance de ces packages


5. Pour des raisons d’organisation sur le projet, nous avons la contrainte suivante : le package Vols doit dépendre du package  Reservations, et non l’inverse. Proposez une modification minimale des diagrammes de classes précédents permettant de se conformer à cette contrainte.


6. Généraliser la modélisation et réutiliser autant que possible les packages pour proposer un système de réservation de voyages en bus. Fusionner autant que possible les 2 modélisations.

<!-- Remarque : cette partie est un peu longue et fastidieuse ; elle peut être rapidement évoquée à l'oral.
	Méthode :
	  - Isoler les classes communes dans de nouveaux packages, afin de pouvoir les réutiliser.
	  - Factoriser les propriétés communes dans des classes abstraites.
-->


## Design Patterns

Dans cette partie, on s'intéresse aux classes d'implémentation du SI de l'agence de voyage. On cherchera à mettre en œuvre des bonnes pratiques de conception afin de favoriser la réutilisabilité et la maintenabilité du code.

### Patterns de création

1. Détailler le processus de création d'une réservation.


2. Détailler le processus de création d'un client (qui peut être soit une personne physique, soit une personne morale).


3. Détailler le processus de création d'un vol.


### Patterns de structure

1. Préciser le point d'entrée du SI de l'agence de voyage, spécifiquement dédié à la réservation (pas de gestion des vols)


2. Comment faire pour faire en sorte de ne pas ré-entrer toutes les informations sur le passager si celui-ci est également le client ?


3. Chaque vol ayant un nombre de sièges défini pour chaque compagnie affréteuse, comment vérifier qu'il reste des places au moment de la création de la réservation sans augmenter le couplage ?


4. L'agence de voyage souhaite proposer une assurance (en option) sur certains de ses voyages. Comment implémenter cela ?


Il existe de nombreux autres patterns (de comportement, architecturaux...), que vous aurez l'occasion de manipuler en TP. N'hésitez pas à vous référer au cours pour en savoir plus...
