Application pour le test technique LBC



# Archtecture

### MVVM
L'architecture Model View ViewModel est l'architecture recommandée en ce moment sur Android. Elle permet une séparation claire entre les objets utilisés par l'application, les vues, et les classes de logique servant à fournir les informations à afficher aux différentes vues.

C'est une architecture simple, facile à mettre en oeuvre et très lisible, profitant en plus de l'injection de viewModels dans les vues directement par les plugins Android.

### NavGraph
Simplifie grandement la navigation entre plusieurs fragments. Cette méthode permet de passer d'un fragment à l'autre beaucoup plus simplement qu'avec les anciennes transactions, et les safeArgs liés permettent d'envoyer directement des objets complexes tout en maitrisant le type de ce qui est envoyé et reçu.

Android Studio rend également possible de visualier l'arbre de navigation afni d'avoir une vision plus claire et visuelle du fonctionnement de l'application et de ses différentes features.


# Patterns

### LiveData
L'utilisation de LiveData permet une communication simple entre les viewModels et les vues. Lorsque un évènement survient dans le viewModel (fin d'un traitement complexe ou d'un appel réseau par exemple), il peut directement mettre à jour une variable observée par les vues, qui peuvent ainsi mettre à jour leurs éléments graphiques à partir de cette nouvelle information.

### Databinding
Permet de lier de façon typée les éléments d'un layout à sa vue. Cette façon de procéder apporte plus de sécurité grace au typage, mais surtout permet de dynamiser l'affichage beaucoup plus facilement. En effet, grace au databinding, un layout peut directement observer une LiveData et mettre à jour un élément graphique sans aucune intervention. Cette méthode permet d'alléger le code traditionnellement trouvé dans les Activities ou les Fragments servant uniquement à mettre à jour un élément grahique lors d'un évenement.

Le databinding embarque aussi une façon plus avancée d'interragir avec une Activity ou un Fragment grace à la possibilité d'appeler directement une fonction de ces derniers lors d'un clic ou juste pour effectuer un affichage plus complexe.

### Repository (tentative d'implémentation sur la branche repository --> exception sans message sur une requête hors ligne suite à un soucis de cache RX)
Ce pattern dissocie le ViewModel de la gestion réseau directe. Le ViewModel va ainsi appeler un Repository qui va se charger de choisir de quelle source doivent être renvoyées les données.

Cette distinction améliore la lisibilité du code et permet d'ajouter de nouvelles sources de données plus facilement, que ça soit un autre serveur, une autre base de données, ou juste un fichier de resources local.


# Librairies

### Gson
Parser permettant de passer rapidement d'objets Java / Kotlin à des objets JSON et inversement. SImple d'utilisation et maintenance effectuée par Google, c'est une librairie de choix pour cet usage

### Picasso
Librairie de téléchargement d'image. Inclut directemement une gestion de cache et d'erreur. Très simple d'utilisation et rapide à l'exécution 

### RX + Retrofit
Un combo très puissant pour la gestion réseau, RX proposant une façon simple de gérer les threads et leurs retours, tandis que Retrofit s'occupe de la génération des requêtes elles-mêmes ainsi que la récupération de la réponse serveur.

L'initialisation de ce système est le seul défaut, étant un peu plus long que des méthodes plus directes et exigeant une sythaxe spécifique, mais permettant une gestion des retours et erreurs très poussée.

### Room
Gestionnaire de base de données locale recommandé par Google. Très simple et rapide à mettre en place.
