Accept/Connect :
Désormais, la connexion (accept/connect) est adapté pour envoyer des messages, et non plus un flux d'octets. La connexion se fait désormais avec un QueueBroker, afin de le différencier d'un Broker classique qui se connecte à un Channel, puisque QueueBroker se connecte à un MessageQueue.

MessageQueue : 

Cette classe utilise la classe Channel pour désormais envoyer/recevoir des messages de taille variable au lieu d'envoyer un flux d'octets dans un buffer circulaire. 
receive() renvoie le prochain message disponible dans le MessageQueue.

Le MessageQueue doit être FIFO et lossless (sans perte de message).

Déconnexion : 

La méthode close() ferme la connexion d'un côté du MessageQueue.




