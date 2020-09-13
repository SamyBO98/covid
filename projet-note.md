# Rendu du Mini Projet "StopCovid"

**Votre travail devra être rendu sous forme d’un projet déposé sur la Forge Lyon 1, au plus tard le dimanche 20 septembre 2020 à 23h59.**

Le mini-projet noté est le fil rouge de tous les TPs. Vous commencez à
travailler sur la base de code au [lab1](TP1-java/), et vous ajoutez
des fonctionnalités tout en améliorant la qualité du code dans la
suite.

Les consignes ci-dessous sont à respecter **impérativement** pour le rendu.

## Rapport

Votre rendu inclura un rapport, au format PDF, qui doit comprendre
obligatoirement :

- une présentation globale du projet (rapide : ne répétez pas
  l'énoncé),

- Une section « design patterns », donnant une motivation des choix d’architecture (et des patterns choisis), et leur explication en s’aidant de diagrammes appropriés et adaptés au degré de précision et au type d’explication. Donc des diagrammes de classe, mais pas que cela, et pas de plats de spaghettis générés automatiquement représentant tout le code.
  
- Une section « éthique » où vous détaillerez les problématiques d'une application de suivi de contacts comme StopCovid en terme de vie privée : quels sont les enjeux pour la vie privée d'une telle application ? Quelles sont les solutions techniques existantes et comment répondent-elles aux questions de vie privée ? Quels sont les avantages et inconvénients des approches possibles ? La solution technique utilisée dans votre application est-elle satisfaisante vis-à-vis de ces questions ? Quelles alternatives au suivi de contacts par proximité auraient pu être mises en œuvre (ou ont été mises en œuvre dans d'autres pays que la France), et en quoi ces autres solutions changent les problématiques de vie privée ?

  On ne vous demande pas de prendre position (la question « êtes-vous pour ou contre StopCovid ? » est hors-sujet ici), mais de détailler les enjeux et les solutions possibles. Autrement dit : posez les bonnes question, mais ne cherchez pas forcément à y répondre lorsque vous estimez que ce n'est pas de votre domaine de compétences.

  Quelques sources d'inspiration :

  * Un article en français qui explique les principes des protocoles ROBERT (derrière StopCovid) et la solution Exposure Notification (proposée par Apple et Google, fortement inspirée de DP-3T) : [A propos des protocoles de traçage pour le Covid-19 : Google/Apple vs. INRIA/Fraunhofer](https://linuxfr.org/users/codefish/journaux/a-propos-des-protocoles-de-tracage-pour-le-covid-19-google-apple-vs-inria-fraunhofer)

  * Une explication du protocole ROBERT : [Un protocole de suivi des contacts rapprochés,rigoureux et respectueux de la vie privée.](https://www.inria.fr/sites/default/files/2020-04/Pr%C3%A9sentation%20du%20protocole%20Robert.pdf)

  * Le site [https://risques-tracage.fr/](risques-tracage.fr), « Le traçage anonyme, dangereux oxymore; Analyse de risques à destination des non-spécialistes »

  * [Coronavirus: How to Do Testing and Contact Tracing](https://medium.com/@tomaspueyo/coronavirus-how-to-do-testing-and-contact-tracing-bde85b64072e)

  * [Traçage de contacts (ou pas)](https://sites.google.com/view/covid19-distanciationsociale/)

  * [« Contact tracing » : Bruno Sportisse, PDG d’Inria, donne quelques éléments pour mieux comprendre les enjeux](https://www.inria.fr/fr/contact-tracing-bruno-sportisse-pdg-dinria-donne-quelques-elements-pour-mieux-comprendre-les-enjeux)
  
  Appuyez vos réflexions et affirmations sur des cas concrets en citant vos sources. Cette partie doit faire environ une page.
  
- Une section « tests » où vous décrirez les tests manuels que vous
  avez réalisés.

## Qualité du code

### Style

Assurez-vous que votre programme respecte toujours le style imposé
(`mvn test`, qui doit lancer checkstyle).

Bien sûr, respecter le style doit se faire en corrigeant (si besoin)
votre code, mais *pas* en modifiant le fichier de configuration de
checkstyle.

### Design-pattern

Assurez-vous d'avoir appliqué toutes les consignes du
[lab3](TP3-patterns/).

### Tests et intégration continue

Vérifiez que l'intégration continue mise en place au
[lab2](TP2-outils/) fonctionne toujours.

Les tests automatisés tels que décrits au [lab5](TP4-tests/) doivent
être lancés automatiquement par `mvn tests`, et doivent tous passer
avec succès.

### Portabilité

Clonez, compilez et exécutez votre code **sur une machine vierge**
(c'est-à-dire sur laquelle vous n'avez installé aucune dépendance, ni
configuré le compte utilisateur de façon particulière). Une grande
partie du barème est liée à l'exécution de votre travail. Il est
important que nous arrivions à l'exécuter **directement**. "Ça marche
chez moi" n'est pas une excuse et une démo *a posteriori* ne permet
pas de remonter une note de TP.

## Projet Forge et TOMUSS

Les projets seront rendus en binômes. La date limite est indiquée sur
la page d'accueil du cours.

**Ajoutez les utilisateurs @thomas.bonis, @fadam, @LIONEL.MEDINI,
@matthieu.moy, @piannetta, @EMMANUEL.COQUERY, @tbatal avec le niveau de privilège
"reporter" (ou plus, mais pas "guest") à votre projet sur la forge**

Dans la feuille TOMUSS du cours, indiquez l'URL de votre projet (qui
doit ressembler à `https://forge.univ-lyon1.fr/<login>/mif01-2020`). Il
faut impérativement :

- **Que la commande `git clone <url>` fonctionne.**

- **Que les deux membres du binôme entrent exactement la même URL dans TOMUSS**

Pensez à remplir dès à présent TOMUSS indiquant votre URL.
Le dépôt ne sera relevé qu’après la date de rendu.

Votre dépôt sur la Forge devra contenir :

- un répertoire `stopcovid/` (le répertoire doit impérativement avoir exactement ce nom)
- un fichier maven (`stopcovid/pom.xml`) pour le build du projet
- les sources (fichiers Java)
- les fichiers natifs de votre modélisation UML (indiquez quel outil a été utilisé)
- le rapport en PDF (6 pages maximum, format libre), dans un fichier qui doit impérativement s'appeler `rapport.pdf` à la racine du dépôt Git.

## Barème indicatif (le barème sera ramené à 20), à utiliser comme checklist pour vérifier que vous avez tout fait

- Réalisation et exécution :
  - En cas de non-respect des consignes, malus de 1 point par consigne non-respectée :
    - `git clone` qui ne fonctionne pas pour les correcteurs (mauvaise URL dans TOMUSS, mauvais droits sur la forge)
    - Projet public (et non privé) sur la forge
    - Non-respect des consignes de rendu via TOMUSS
    - Absence de fichier pom.xml ou .gitlab-ci.yml
    - Retard : -1 point par jour de retard (arrondi supérieur)
    - Mauvais nommage des fichiers (`rapport.pdf` ou répertoire `stopcovid`) : -1 par fichier mal nommé.
  - Compilation Maven sans erreur (1 pts)
  - Code qui tourne directement sur l’ordinateur de l’évaluateur (1 pts)
  - Aucun warning checkstyle (2 pts)
  - Qualité et structure globale du code, utilisation de Packages (1 pt)
  - Fichier .gitignore correct (aucun fichier "untracked" après un "mvn test") (1 pts)
  - Au moins une "pull-request" intégrée dans master (1 pt)
  - Au moins une "issue" sur le projet (1 pt)
  - Interface (UI) propre (1 pts)
  - Extensions obligatoires (cf. [TP3-patterns/](TP3-patterns/)) :
    - N'envoyer que les contacts rencontrés plusieurs fois (1 pt)
    - Choix de la stratégie de sélection des contacts à envoyer (1 pt)
    - Sélection des 10 contacts les plus fréquents (1 pt)
    - Possibilité de supprimer des contacts (2 pts)
    - Bouton « meet » directement sur les utilisateurs (2 pts)
    - Création de la liste d'utilisateurs (1 pt)
  - Autres extensions (3 pts)
  - Modification de l'interface utilisateur (1 pt)
  - Tests automatiques (3 pts)
  - Intégration continue opérationnelle (gitlab affiche "Commit: passed" sur le dernier commit) (1 pt)
  - Principes GRASP bien appliqués (1 pt)
  - Design-patterns (création, structure, SOLID, ...) (5 pts)
- Rapport et modélisation :
  - Partie « design patterns » (6pts)
  - Partie « éthique » (3pts)
  - Partie « tests » (1pts)
  - Qualité globale des explications (3pts)
  - Les points suivants entraînent des malus (jusqu’à -5 pts)
    - Contenu et forme (voir ci-dessus)
    - Orthographe
