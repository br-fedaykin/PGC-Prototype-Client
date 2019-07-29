package functionaltests;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.*;

import static org.junit.Assert.*;

import com.brunoarruda.hyperdcpabe.Session;

public class FunctionalTest {

    private static ConsoleOutputCapturer systemOutput;
    private static ConsoleInputFake systemInput;
    private Session session;
    private StubBlockChain blockchain;
    private String allOutput;

    @BeforeClass
    public static void beforeAll() {
        systemOutput = new ConsoleOutputCapturer();
        systemInput = new ConsoleInputFake();
    }

    @Before
    public void setUp() {
        systemOutput.start();
        systemInput.start();
        blockchain = new StubBlockChain(null, null);
        session = new Session(blockchain);
        allOutput = "";
    }

    @After
    public void tearDown() {
        systemInput.stop();
    }

    @Test
    public void testPatientCanPublishData() {
        /**
         * Alice wants to publish her EHR on Hyper-DCPABE
         * She starts a new session on Desktop
         *
        */

        systemInput.sendMany(
            "1",
            "2",
            "Alice",
            "alice@email.com",
            "3",
            "0"
        );
        session.runClient("console");
        allOutput = systemOutput.stop();
        allOutput = allOutput.toLowerCase();

        // She sees a list of available commands
        assertThat(allOutput, CoreMatchers.containsString("menu principal:"));

        // She chooses the command to generate keys.
        // The keys are printed on the console so she can save them on a secure database
        assertThat(allOutput, CoreMatchers.containsString("chave privada: "));
        assertThat(allOutput, CoreMatchers.containsString("chave pública: "));

        // She returns to main menu and choose to publish its user.
        // The client asks for name and e-mail and publish them on the chain
        assertThat(allOutput, CoreMatchers.containsString("publicado na blockchain"));

        /**
         * Alice asks on blockchain for public keys of attributes "CRM" and "CFM" to make
         * the basic encryption "authorized or CRM or CFM".
         *
         * Obs: CFM and CRM means in Brazil respectively Federal an Regional Council
         * of Medicine.
        */

        assertThat(allOutput, CoreMatchers.containsString("atributos recebidos"));

        // she receives the public key of attributes CRM and CFM after sometime.
        fail("Finish the test!");
        /**
         * she creates the attribute "authorized", and send its public key to the
         * blockchain
        */

        // with all keys, she encrypts the data: a PDF document.

        /**
         * The client digests the hash of the resulting file and encrypt with the same
         * policy used on that.
        */

        // She agrees to client sending both files to a known server

        // The client receives from the server an key that should be used to find the file

        /**
         * Finally, its published in blockchain a transaction containing:
        */

        // 1. the address of the transaction with her personal data

        /**
         * 2. an JSON object, encrypted with the same policy used to encrypt the
         * sent file, containing the server address of the file and the code to
         * find the file.
        */
    }

    @Test
    public void testAuthorizedUserCanRecoverPatientRecords(){
        // other patient request access to blockchain
        fail("Finish the test!");
        // container.obterPaciente() returns Paciente

        // cliente 2 java lê prontuários[rand] e pede pro servidor o arquivo, enviando código como post

        // serv devolve o arquivo teste

        // cliente 2 java vai decifrar teste e envia o hash via post ao serv

        // serv verifica o hash enviado, e devolve o arquivo pdf encriptado

        // cliente 2 java decifra o arquivo pdf

    }
}