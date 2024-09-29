package implementation;

/**
 * Exception levée lorsqu'une opération est effectuée sur un canal déconnecté.
 */
public class DisconnectedException extends Exception {

    private static final long serialVersionUID = 1L;

	// Constructeur par défaut
    public DisconnectedException() {
        super();
    }

    // Constructeur avec message d'erreur personnalisé
    public DisconnectedException(String message) {
        super(message);
    }
}
