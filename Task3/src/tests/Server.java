package tests;

import events.EventMessageQueue;
import events.Executor;
import events.EventQueueBroker;
import events.TaskEvent;

public class Server {

    private EventQueueBroker queueBroker;

    public Server(EventQueueBroker qb) {
        this.queueBroker = qb;
    }

    public void startServer(int port) {
        queueBroker.bind(port, queue -> {
            System.out.println("New client connected on port: " + port);

            TaskEvent clientTask = new TaskEvent() {
                @Override
                public void run() {
                    handleClient(queue);
                }
            };
            Executor.getSelf().post(clientTask);
        });
    }

    private void handleClient(EventMessageQueue mq) {
        boolean[] messageEchoed = { false };

        mq.setListener(new EventMessageQueue.Listener() {
            @Override
            public void received(byte[] msg) {
                String receivedMessage = new String(msg);
                System.out.println("Server received message: " + receivedMessage);

                if (!messageEchoed[0]) {
                    Executor.getSelf().post(() -> {
                        System.out.println("Echoing message: " + receivedMessage);
						messageEchoed[0] = true;
                    });
                }
            }

            @Override
            public void closed() {
                System.out.println("Client disconnected");
            }
        });
    }

}