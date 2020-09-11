# TP3 - Design patterns

## Objectifs

Il vous est demandé d’effectuer une ré-ingénierie d’un code existant en mettant en œuvre les patrons de conception vus en cours.

Ceci devrait vous prendre 2 ou 3 séances.

### Déroulement

Ce TP est organisé en deux étapes :

- une ré-ingénierie (refactoring) du code utilisé dans les premiers TP afin de mieux structurer le projet et de le rendre plus modulaire,
- l’extension des fonctionnalités pour réaliser un programme plus complet.

Le plus simple est de partir répertoire `stopcovid` fourni dans votre archive. Si vous le souhaitez, vous pouvez aussi repartir d'un répertoire vierge, mais attention à ne rien oublier au passage !

### Ressources

- [Cours (et pointeurs à la fin du cours) sur les Design patterns](https://perso.liris.cnrs.fr/lionel.medini/enseignement/M1IF01/CM-patterns.pdf)
- [Aide à la mise en place du pattern MVC pour ce TP](./mvc.md)

## Consignes

Il est demandé de travailler en binômes.

Penser à remplir dès à présent TOMUSS pour indiquer votre dépôt forge.
*Cf*. instructions dans [../projet-note.md](../projet-note.md).

### Outils

Il est conseillé d'utiliser un IDE capable __a minima__ de générer des diagrammes UML à partir du code, ou mieux, de faire du __round-trip engineering__ (prendre en compte les modifications du code dans les schémas et inversement). C'est le cas avec IntelliJ, et Eclipse avec un plugin comme [Object Aid](https://www.objectaid.com/) (explication rapides en vidéo [ici](https://www.youtube.com/watch?v=0Zlh56mTS6c)).

Il est aussi possible de [générer un diagramme de classe via JavaDoc](http://gochev.blogspot.com/2011/03/generate-javadoc-with-uml-diagrams.html).

### Qualité du code

Le but de ce cours en général est de vous apprendre à écrire du code propre (irréprochable ?). 
On accordera une attention particulière à la qualité du code à tous les niveaux (style, indentation, architecture...). 
Privilégiez la qualité du code à la quantité de fonctionnalités.

## Partie 1 : Ré-ingénierie

Le code fourni lors de la première séance est ensemble de classes
relativement fouillis (nous en avons déjà parlé dans
[architecture-et-dependances.md](../TP1-java/architecture-et-dependances.md)).
En particulier les couches graphique et métier ne sont pas séparées.
Il va vous falloir réorganiser le code en appliquant les patrons de conception adéquats.

### Pattern Modèle-Vue-Contrôleur

Vous allez maintenant redéfinir la structure de base de l’application.
Mettez en place un pattern MVC pour :

- séparer le métier (la logique de l'application) et l’affichage
  des éléments (boutons et champs textes),

- propager les changements du modèle métier (statut des utilisateurs, ...) dans la vue,

- répercuter les entrées utilisateur (clic sur un bouton, validation
  d'un champ texte avec la touche « entrée »...) sous la forme
  adéquate dans le modèle métier.

Pour vous aider, vous pouvez utiliser les slides du cours sur les patterns contrôleur et MVC, ainsi que le document [Mettre en place le pattern MVC](mvc.md). Ce document propose des questions pertinentes à se poser, ainsi que quelques éléments de réponses, mais ce ne sont ni les seules questions ni les seules réponses pertinentes.

**Pour chacun des patterns implémentés à partir de cette partie, vous décrirez (en 1 ou 2 paragraphes) pourquoi vous avez choisi de l'utiliser, et vous fournirez dans votre rapport un/des diagramme(s) UML illustrant comment vous l'avez mis en œuvre.**

#### Flexibilité du modèle MVC

Une des propriétés du MVC est qu'on peut avoir un nombre quelconque de vues, identiques ou non, d'un modèle donné.
On peut vérifier simplement que notre MVC vérifie cette propriété en ajoutant quelque chose comme ceci dans le programme principal :

```java
        // First view, provided in skeleton
        new JfxView(c, stage, 600, 600);

        // Second view
        new JfxView(c, new Stage(), 400, 400);
```

Vous devriez voir une deuxième fenêtre, de taille différente, qui
affiche l'interface graphique.
Les deux fenêtres doivent être synchronisées : toute action qui modifie le modèle faite dans
l'une est répercutée immédiatement dans l'autre. Certaines actions peuvent être locales à la vue, par exemple sélectionner les utilisateurs dans le simulateur de proximité n'a pas d'effet tant qu'on n'a pas cliqué sur le bouton « meet ».

Remarque : si la deuxième fenêtre vous gêne, mettez en commentaire le
morceau de code concerné, mais conservez-le et vérifier qu'il marche
toujours au moment du rendu.

### Autres patterns

Reprenez les transparents du cours et parcourez la liste des patterns GRASP, des patterns de création, de structure, des principes SOLID, et posez-vous la question de leur applicabilité sur votre projet.
Appliquez ceux qui vous semblent pertinents, et de la même façon que précédemment, documentez cela dans votre rapport (si possible, en suivant la progression du cours : GRASP, création, structure...).

Vous devrez avoir au moins 3 patterns autres que MVC appliqués dans votre projet et décrits dans votre rapport.

### Anticiper sur le TP 5 "test"

Avant d'aller plus loin sur les extensions, c'est une bonne idée de progresser en tests : nous serons plus en sécurité pour continuer le refactoring et les extensions avec une bonne base de tests. 
Allez jeter un œil au [TP "Test"](../TP4-tests/README.md) (en particulier la section sur le TDD), et revenez pour la suite de ce TP après.
À vous de voir dans quel ordre vous voulez avancer précisément.

## Partie 2 : Extension

Dans toute cette partie, l'ajout de fonctionnalité est un prétexte pour se servir de design patterns.
Ajoutez chaque fonctionnalité en appliquant les principes et patterns vus en cours, et justifiez-la dans le rapport.

### N'envoyer que les contacts rencontrés plusieurs fois

Vérifiez que la modification demandée lors du TP 1 est toujours fonctionnelle (section « Limiter les contacts transmis au serveur »).

### Choix de la stratégie de sélection des contacts à envoyer

Ajoutez la possibilité de choisir entre la stratégie « envoyer tous les contacts » (comme dans la base de code fournie) ou « n'envoyer que les contacts rencontrés au moins deux fois » à l'interface (par exemple en ajoutant une ComboBox pour choisir entre les deux stratégies).

Si vous êtes tentés d'écrire du code de la forme

```java
if (strategie == 1) { // NON
    ...
} else {
    ...
}
```

Relisez votre cours sur les design-patterns, il y a plus propre et extensible ...

### Sélection des 10 contacts les plus fréquents

On souhaite expérimenter une autre stratégie, pour limiter les faux positifs : dans le cas où l'utilisateur a enregistré beaucoup de contacts, n'envoyer que les 10 qu'il a croisé le plus souvent (cette stratégie est probablement douteuse sur le plan épidémiologique, mais fera un exercice de conception amusant). Ajouter cette possibilité en vous assurant que vous n'avez pas à modifier le code gérant les deux autres stratégies.

### Possibilité de supprimer des contacts

On souhaite maintenant permettre aux utilisateurs de supprimer des contacts de leur liste (ce qu'ils pourraient souhaiter faire par exemple avant de se déclarer infecté si ils souhaitent ne pas notifier un utilisateur).

![Suppression d'un contact](img/stopcovid-delete-contacts.png)

Cette copie d'écran a été obtenue avec le code ci-dessous dont vous
pouvez vous inspirer :

```java
final HBox box = new HBox();
final Label labelContact = new Label(
    i.getKey().getName() + ": " + i.getValue().toString() + " ");
final Button b = new Button("x");
b.setOnMouseClicked(event -> ...);
box.setStyle("-fx-padding: 2;" + "-fx-border-style: solid inside;"
    + "-fx-border-width: 1;" + "-fx-border-insets: 5;"
    + "-fx-border-radius: 5;" + "-fx-border-color: black;");
box.setAlignment(Pos.BASELINE_CENTER);
box.getChildren().addAll(labelContact, b);
```

Cette modification devrait être essentiellement locale à la vue (vous devrez ajouter une méthode au contrôleur et au modèle, mais ces méthodes sont triviales).

### Bouton « meet » directement sur les utilisateurs

Plutôt que d'avoir deux listes déroulantes et un bouton dans la partie droite de l'interface, ajoutez à chaque utilisateur une liste déroulante pour choisir la personne à rencontrer, et un bouton « meet ». Sélectionner « User 2 » et cliquer sur « meet » dans le cadre de l'utilisateur « User 1 » aura le même effet que de sélectionner « User 1 » et « User 2 » dans la partie droite puis cliquer sur « meet ».

Cette fois-ci, la modification doit être purement locale à la vue. Si vous avez du modifier le modèle ou le contrôleur, revoyez votre MVC.

### Création de la liste d'utilisateurs

Pour permettre de personnaliser la liste des utilisateurs, on souhaite pouvoir instancier le modèle avec ceci :

```java
final StopCovidModel model = new StopCovidModelBuilder()
    .add("Foo")
    .add("Bar")
    .add("Boz")
    .build();
```

Écrivez le code nécessaire à cela.

### Autre chose ?

Si vous avez codé correctement toutes les fonctionnalités ci-dessus, ajoutez-en quelques unes au choix. Vous pouvez en inventer, et si vous n'êtes pas inspirés voici quelques exemples :

- Importation de la liste des utilisateurs depuis un fichier (par exemple un nom par ligne dans un fichier texte), comme variante de `StopCovidModelBuilder` ci-dessus. Bien sûr, hors de question d'ajouter une dépendance à `java.io.File` dans `StopCovidModel`.

- Un bouton « auto » qui lance une simulation aléatoire automatique : N fois de suite, on choisit deux utilisateurs au hasard et on les fait se rencontrer, ou bien un utilisateur se déclare infecté.

- Un fonctionnement plus proche d'un protocole préservant la vie privée de ses utilisateurs, donc utilisant des pseudonymes renouvelés périodiquement pour que personne ne puisse remonter aux personnes physiques correspondant aux contacts stockés.

## Rendu du TP / projet

Voir les consignes **À RESPECTER IMPÉRATIVEMENT** dans
[../projet-note.md](../projet-note.md).
