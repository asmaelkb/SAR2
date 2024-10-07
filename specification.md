# Spécification Mixte Events / Threads

Ces classes définissent un système de communication asynchrone entre clients et serveurs via des files de messages. Elles permettent la gestion des connexions, la réception et l'envoi de messages ainsi que la gestion des événements de connexion et de déconnexion.

Les méthodes sont toujours bloquantes, ce qui justifie le fait que cette version est mixte entre Thread et Events.


## Classe EventQueueBroker

QueueBroker est une classe abstraite utilisée pour gérer les connexions réseau et la communication via des files de messages (MessageQueue). Elle permet à un client de se connecter à un serveur ou à un serveur d'accepter des connexions entrantes.

### Constructeur

`QueueBroker(String name)`: Initialise une instance de QueueBroker avec un nom spécifié.

### Méthodes

`boolean bind(int port, AcceptListener listener)` :

- Associe le QueueBroker à un port spécifique et commence à écouter les connexions entrantes.
- Si une connexion est acceptée, l'interface AcceptListener est utilisée pour gérer cet événement.
- Retourne true si la liaison est réussie, sinon false.

`boolean unbind(int port)` :

- Arrête l'écoute des connexions sur le port spécifié.
- Retourne true si la désactivation est réussie, sinon false.

`boolean connect(String name, int port, ConnectListener listener)` :

- Tente de se connecter à un QueueBroker distant identifié par son nom et le port spécifié.
- Utilise l'interface ConnectListener pour notifier si la connexion a été acceptée (connected()) ou refusée (refused()).
- Retourne true si la tentative de connexion est initiée avec succès, sinon false.

### Interfaces internes

On crée des méthodes AcceptListener et ConnectListener puisque ces deux méthodes sont bloquantes. Il faut notifier quand est-ce que les méthodes accept et connect ont été acceptées et arrêtées d'être bloquées.

#### AcceptListener

`void accepted(MessageQueue queue)` : Méthode appelée lorsque le serveur accepte une nouvelle connexion, fournissant la file de messages correspondante.

#### ConnectListener

`void connected(MessageQueue queue)` : Méthode appelée lorsque la connexion au serveur est réussie, fournissant la file de messages correspondante.
`void refused()` : Méthode appelée si la tentative de connexion au serveur est refusée.

## Classe EventMessageQueue

MessageQueue est une classe abstraite qui représente une file de messages pour la communication entre les clients et les serveurs. Elle permet d'envoyer, de recevoir et de gérer les messages dans le cadre d'une connexion.

### Méthodes

 `void setListener(Listener l)` : Définit un écouteur (Listener) pour recevoir des notifications lorsque des messages sont reçus ou lorsque la connexion est fermée.

`boolean send(byte[] bytes)` :

- Envoie un message sous forme de tableau d'octets.
- Retourne true si l'envoi est réussi, sinon false.

`boolean send(byte[] bytes, int offset, int length)` :

- Envoie une partie d'un tableau d'octets, à partir de l'offset spécifié et sur une longueur donnée.
- Retourne true si l'envoi est réussi, sinon false.

`void close()` : Ferme la file de messages, interrompant toute communication future.

`boolean closed()` : Retourne true si la file de messages est fermée, sinon false.

### Interface interne

#### Listener:

`void received(byte[] msg)` : Méthode appelée lorsqu'un message est reçu via la file de messages.
`void closed()` : Méthode appelée lorsque la connexion est fermée.

## Executor (EventPump)

Chaque tâche (Runnable) est postée dans la pompe à événements.
EventPump est un singleton, une unique instance est créée quand la classe est chargée par la JVM.


