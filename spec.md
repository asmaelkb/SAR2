# Task 1

## Spécifications

### Introduction

Le projet consiste à créer une communication entre des Tasks qui sont des processus ou des threads actifs. Ces Tasks échangent des données via des Channels. Chaque Channel relie deux tasks et permet l'envoi et la réception de données en utilisant une approche FIFO.

Pour établir les connexions entre les Tasks, des Brokers sont utilisés. Un Brokers peut être associé soit à un serveur, soit à un client, et est identifié par un nom unique. Chaque Broker peut gérer plusieurs Tasks.

Les échanges se font à travers des buffers circulaires, assurant une communication sans perte et ordonnée, basé sur une sémantique de flux (type TCP).

### Broker

Un Broker permet de gérer l'établissement des connexions entre les tâches (Tasks) et l'ouverture des canaux de communication (Channels). Chaque broker possède un nom unique et peut gérer plusieurs connexions en parallèle (multithreadé).

Les connexions sont établies en utilisant un modèle client-serveur, où un serveur attend les connexions sur un port spécifique et les clients se connectent en spécifiant le nom et le port du serveur. Les ports utilisés sont uniques pour chaque Broker. Par exemple, le port 80 sur une machine A est distinct du port 80 sur une machine B.

- **Broker(string name)** : Constructeur du broker qui prend un nom `name` en paramètre. Ce nom doit être unique pour identifier chaque broker.
</br>

- **Channel accept(int port)** : Méthode appelée par le serveur pour accepter une connexion entrante sur le `port` donné en paramètre. Elle renvoie un Channel quand une connexion est établie avec un client.
</br>

- **Channel connect(String name, int port)** : Méthode utilisée par un client pour se connecter à un Broker identifié par son nom `name` et un `port` spécifique. Elle retourne un Channel une fois la connexion réussie.
</br>

### Channel

Un Channel représente un canal de communication entre deux tâches (Task). Il utilise un buffer circulaire de bytes pour gérer l'envoi et la réception de messages, garantissant une communication FIFO lossless (sans pertes). L'ordre des messages est préservé, ce qui signifie que les données envoyées en premier sont reçues en premier.

Le Channel n'est pas multithreadé, mais plusieurs threads peuvent lire et écrire des données sur le même Channel.

- **int read(byte[] bytes, int offset, int length)** : Cette méthode permet de lire des données depuis le buffer circulaire du Channel. Elle copie les bytes dans le tableau `bytes` à partir de l'`offset` donné, jusqu'à `length` bytes et renvoie le nombre d'octets effectivement lus. La méthode se bloque si aucune donnée n'est disponible dans le buffer.
</br>

- **int write(byte[] bytes, int offset, int length)** : Cette méthode permet d'écrire des données dans le buffer circulaire du Channel à partir d'un tableau de bytes `bytes`, en commençant à un `offset` donné et une longueur `length` définie. La méthode retourne le nombre de bytes effectivement écrits. Si le buffer est plein, l'écriture se bloque.
</br>

- **void disconnect()** : Cette méthode ferme le Channel du côté de la tâche (Task) qui l'appelle. Aucun message ne peut donc être envoyé ou reçu après la fermeture.
</br>

- **boolean disconnected()** : Cette méthode retourne un booléen indiquant si la connexion a bien été fermée ou non.
</br>

### Task

Une Task est un acteur actif (un thread), et interagit avec un Broker pour établir des canaux de communication (Channel).

Chaque Task fonctionne de manière indépendante et peut envoyer et recevoir des données via des Channels fournis par le Broker.

- **Task(Broker b, Runnable r)** : Constructeur permettant de créer une nouvelle Task associée à un Broker `b` pour gérer les connexions et qui exécute un Runnable `r`.
</br>

- **static Broker getBroker()** : Cette méthode renvoie le Broker associé à la Task en cours afin d'établir des connexions.
</br>

